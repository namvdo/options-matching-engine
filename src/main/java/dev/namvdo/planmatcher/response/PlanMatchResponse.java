package dev.namvdo.planmatcher.response;

import dev.namvdo.planmatcher.model.SliderConfiguration;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Builder
@Getter
public class PlanMatchResponse {
  private List<PlanMatch> matches;
  private Map<String, SliderConfiguration> sliderConfigurations;
}

