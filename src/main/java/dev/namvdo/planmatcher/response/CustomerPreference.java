package dev.namvdo.planmatcher.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPreference {
  private Integer voiceMinutes;
  private Integer dataGigabytes;
  private Integer smsCount;
  private String targetSegment;
  private Integer maxBudget;
  private Integer minContractMonths;
}
