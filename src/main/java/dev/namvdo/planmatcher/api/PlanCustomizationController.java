package dev.namvdo.planmatcher.api;

import dev.namvdo.planmatcher.engine.PlanMatchingEngine;
import dev.namvdo.planmatcher.model.DataOption;
import dev.namvdo.planmatcher.model.Plan;
import dev.namvdo.planmatcher.model.SliderConfiguration;
import dev.namvdo.planmatcher.model.SmsOption;
import dev.namvdo.planmatcher.model.VoiceOption;
import dev.namvdo.planmatcher.response.CustomerPreference;
import dev.namvdo.planmatcher.response.PlanMatchResponse;
import dev.namvdo.planmatcher.service.ComponentService;
import dev.namvdo.planmatcher.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/plan-customization")
@RequiredArgsConstructor
@RestController
public class PlanCustomizationController {

  private final ComponentService componentService;
  private final PlanService planService;
  private final PlanMatchingEngine planMatchingEngine;

  @GetMapping("/slider-configurations")
  public ResponseEntity<Map<String, SliderConfiguration>> getSliderConfigurations() {
    return ResponseEntity.ok(componentService.getSlideConfigurations());
  }

  @GetMapping("/options/voice")
  public ResponseEntity<List<VoiceOption>> getVoiceOptions() {
    return ResponseEntity.ok(componentService.getVoiceOptions());
  }

  @GetMapping("/options/data")
  public ResponseEntity<List<DataOption>> getDataOptions() {
    return ResponseEntity.ok(componentService.getDataOptions());
  }

  @GetMapping("/options/sms")
  public ResponseEntity<List<SmsOption>> getSmsOptions() {
    return ResponseEntity.ok(componentService.getSmsOptions());
  }

  @GetMapping("/available-values/voice")
  public ResponseEntity<List<Integer>> getAvailableVoiceValues() {
    return ResponseEntity.ok(componentService.getAvailableVoiceValues());
  }

  @GetMapping("/available-values/data")
  public ResponseEntity<List<Integer>> getAvailableDataValues() {
    return ResponseEntity.ok(componentService.getAvailableDataValues());
  }

  @GetMapping("/available-values/sms")
  public ResponseEntity<List<Integer>> getAvailableSmsValues() {
    return ResponseEntity.ok(componentService.getAvailableSmsValues());
  }

  @GetMapping("/plans")
  public ResponseEntity<List<Plan>> getAllPlans() {
    return ResponseEntity.ok(planService.getAllPlans());
  }

  @GetMapping("/plans/{id}")
  public ResponseEntity<Plan> getPlanById(@PathVariable String id) {
    return planService.getPlanById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/match")
  public ResponseEntity<PlanMatchResponse> findMatchingPlans(@RequestBody CustomerPreference preference) {
    return ResponseEntity.ok(planMatchingEngine.findMatchingPlans(preference));
  }
}