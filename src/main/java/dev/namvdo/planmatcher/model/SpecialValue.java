package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialValue {
  private Integer value;
  private String label;
}
