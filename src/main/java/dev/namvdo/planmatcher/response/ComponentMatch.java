package dev.namvdo.planmatcher.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ComponentMatch {
  private String componentId;
  private Double matchScore;
  private boolean exactMatch;
}
