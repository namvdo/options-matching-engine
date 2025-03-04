package dev.namvdo.planmatcher.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanMatchResponse {
  private List<PlanMatch> matches;
  private Map<String, SliderConfiguration> sliderConfigurations;
}

