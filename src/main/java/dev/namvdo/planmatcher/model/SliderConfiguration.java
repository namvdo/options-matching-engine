package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SliderConfiguration {
  private Integer min;
  private Integer max;
  private Integer step;
  private Integer defaultValue;
  private String unit;
  private String displayName;
  private List<Integer> snapPoints;
  private List<SpecialValue> specialValues;
}
