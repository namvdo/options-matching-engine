package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanComponent {
  private String id;
  private Integer minutes;
  private Integer gigabytes;
  private Integer count;
  private String description;
}