package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataOption {
  private String id;
  private Integer gigabytes;
  private String displayName;
  private String description;
  private BigDecimal basePrice;
  private Integer serviceType;
  private boolean isAvailableStandalone;
  private List<String> compatiblePlans;
  private SliderInfo sliderInfo;
}
