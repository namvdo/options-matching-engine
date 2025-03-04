package dev.namvdo.planmatcher.response;

import dev.namvdo.planmatcher.model.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class PlanMatch {
  private Plan plan;
  private Double matchScore;
  private Map<String, ComponentMatch> componentMatches;
}
