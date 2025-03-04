package dev.namvdo.planmatcher.engine;

import ch.qos.logback.core.model.ComponentModel;
import dev.namvdo.planmatcher.model.Plan;
import dev.namvdo.planmatcher.model.PlanComponent;
import dev.namvdo.planmatcher.response.ComponentMatch;
import dev.namvdo.planmatcher.response.CustomerPreference;
import dev.namvdo.planmatcher.response.PlanMatch;
import dev.namvdo.planmatcher.response.PlanMatchResponse;
import dev.namvdo.planmatcher.service.ComponentService;
import dev.namvdo.planmatcher.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanMatchingEngine {
  private final PlanService planService;
  private final ComponentService componentService;
  private final SimilarityCalculator similarityCalculator;

  public PlanMatchResponse findMatchingPlans(CustomerPreference preference) {
    List<Plan> allPlans = planService.getAllPlans();

    List<PlanMatch> matches = allPlans.stream()
        .map(plan -> calculatePlanMatch(plan, preference))
        .sorted(Comparator.comparing(PlanMatch::getMatchScore).reversed())
        .limit(5).toList();

    return PlanMatchResponse.builder()
        .matches(matches)
        .sliderConfigurations(componentService.getSlideConfigurations())
        .build();

  }


  private PlanMatch calculatePlanMatch(Plan plan, CustomerPreference preference) {
    Map<String, ComponentMatch> componentMatches = new HashMap<>();
    double totalScore = 0.0;

    PlanComponent voiceComponent = plan.getComponents().get("voice");
    if (voiceComponent != null && preference.getVoiceMinutes() != null) {
      ComponentMatch voiceMatch = calculateVoiceMatch(voiceComponent, preference.getVoiceMinutes());
      componentMatches.put("voice", voiceMatch);
      totalScore += voiceMatch.getMatchScore() * 0.3;
    }

    PlanComponent dataComponent = plan.getComponents().get("data");
    if (dataComponent != null && preference.getDataGigabytes() != null) {
      ComponentMatch dataMatch = calculateDataMatch(dataComponent, preference.getDataGigabytes());
      componentMatches.put("voice", dataMatch);
      totalScore += dataMatch.getMatchScore() * 0.5;
    }


    PlanComponent smsComponent = plan.getComponents().get("sms");
    if (smsComponent != null && preference.getSmsCount() != null) {
      ComponentMatch smsMatch = calculateSmsMatch(smsComponent, preference.getSmsCount());
      componentMatches.put("sms", smsMatch);
      totalScore += smsMatch.getMatchScore() * 0.2;
    }
    if (preference.getTargetSegment() != null && preference.getTargetSegment().equalsIgnoreCase(plan.getTargetSegment())) {
      totalScore += 0.1;
    }

    if (preference.getMaxBudget() != null && preference.getMaxBudget() < plan.getMonthlyPrice().intValue()) {
      totalScore *= 0.5;
    }

    if (preference.getMinContractMonths() != null && plan.getMinContractMonths() > preference.getMinContractMonths()) {
      totalScore *= 0.8;
    }

    return PlanMatch.builder()
        .plan(plan)
        .matchScore(totalScore)
        .componentMatches(componentMatches)
        .build();
  }


  private ComponentMatch calculateVoiceMatch(PlanComponent planComponent, Integer preferredMinutes) {
    boolean exact = false;
    if (planComponent.getMinutes() != null && preferredMinutes.equals(planComponent.getMinutes())) {
      exact = true;
    } else if (planComponent.getMinutes() == null){
      exact = preferredMinutes >= 2000;
    }

    double similarity = 0.0;
    if (exact) {
      similarity = 1.0;
    } else {
      similarity = similarityCalculator.calculateVoiceSimilarity(preferredMinutes, planComponent.getMinutes(), componentService.getAvailableVoiceValues());
    }
    return ComponentMatch.builder()
        .componentId(planComponent.getId())
        .matchScore(similarity)
        .exactMatch(exact)
        .build();
  }

  private ComponentMatch calculateDataMatch(PlanComponent component, Integer preferredGigabytes) {
    boolean exact = false;
    if (component.getGigabytes() != null && component.getGigabytes().equals(preferredGigabytes)) {
      exact = true;
    } else if (component.getGigabytes() == null){
      exact = preferredGigabytes >= 50;
    }

    double similarity = 0.0;
    if (exact) {
      similarity = 1.0;
    } else {
      similarity = similarityCalculator.calculateDataSimilarity(
          preferredGigabytes,
          component.getGigabytes(),
          componentService.getAvailableDataValues()
      );
    }

    return ComponentMatch.builder()
        .componentId(component.getId())
        .exactMatch(exact)
        .matchScore(similarity)
        .build();

  }

  private ComponentMatch calculateSmsMatch(PlanComponent component, Integer preferredCount) {
    boolean exact = false;
    if (component.getCount() != null && component.getCount().equals(preferredCount)) {
      exact = true;
    } else if (component.getCount() == null) {
      exact = preferredCount >= 500;
    }

    double similarity = 0.0;
    if (exact) {
      similarity = 1.0;
    } else {
      similarity = similarityCalculator.calculateSmsSimilarity(preferredCount, component.getCount(), componentService.getAvailableSmsValues());
    }
    return ComponentMatch.builder()
        .matchScore(similarity)
        .exactMatch(exact)
        .componentId(component.getId())
        .build();
  }
}
