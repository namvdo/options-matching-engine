package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataOption {
  private String id;
  private Integer gigabytes;
  private String displayName;
  private String description;
  private BigDecimal basePrice;
  private Integer serviceType;
  private Boolean isAvailableStandalone;
  private List<String> compatiblePlans;
  private SliderInfo sliderInfo;

  public boolean isAvailableStandalone() {
    return this.isAvailableStandalone;
  }
}
