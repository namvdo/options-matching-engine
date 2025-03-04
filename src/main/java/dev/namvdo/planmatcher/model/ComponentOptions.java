package dev.namvdo.planmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentOptions {
  private List<VoiceOption> voiceOptions;
  private List<DataOption> dataOptions;
  private List<SmsOption> smsOptions;
  private Map<String, SliderConfiguration> sliderConfigurations;
}
