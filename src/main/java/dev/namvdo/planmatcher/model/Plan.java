package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
  private String id;
  private String name;
  private String description;
  private BigDecimal monthlyPrice;
  private Map<String, PlanComponent> components;
  private List<String> features;
  private String targetSegment;
  private String promotionalOffer;
  private Integer minContractMonths;
}
