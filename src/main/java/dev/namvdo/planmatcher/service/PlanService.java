package dev.namvdo.planmatcher.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.namvdo.planmatcher.model.Plan;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanService {
  private final ObjectMapper objectMapper;
  private List<Plan> plans;


  @PostConstruct
  public void loadPlans() {
    try {
      InputStream inputStream = getClass().getResourceAsStream("/data/plans.json");
      plans = objectMapper.readValue(inputStream, new TypeReference<List<Plan>>() {});
      log.info("Loaded {} plans", plans.size());
    } catch (IOException e) {
      log.error("Failed to load plans", e);
      throw new RuntimeException("Failed to initialize plan service", e);
    }
  }


  @Cacheable("allPlans")
  public List<Plan> getAllPlans() {
    return plans;
  }

  @Cacheable(value = "planById", key = "#id")
  public Optional<Plan> getPlanById(String id) {
    return plans.stream()
        .filter(plan -> plan.getId().equals(id))
        .findFirst();
  }

  @Cacheable(value = "plansByTargetSegment", key = "#targetSegment")
  public List<Plan> getPlansByTargetSegment(String targetSegment) {
    return plans.stream()
        .filter(plan -> plan.getTargetSegment().equals(targetSegment))
        .collect(Collectors.toList());
  }

  @Cacheable(value = "plansByComponentId", key = "#componentId")
  public List<Plan> getPlansByComponentId(String componentId) {
    return plans.stream()
        .filter(plan -> plan.getComponents().values().stream()
            .anyMatch(component -> component.getId().equals(componentId)))
        .collect(Collectors.toList());
  }

  @Cacheable(value = "plansByFeature", key = "#feature")
  public List<Plan> getPlansByFeature(String feature) {
    return plans.stream()
        .filter(plan -> plan.getFeatures().contains(feature))
        .collect(Collectors.toList());
  }
}
