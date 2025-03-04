package dev.namvdo.planmatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.namvdo.planmatcher.model.ComponentOptions;
import dev.namvdo.planmatcher.model.DataOption;
import dev.namvdo.planmatcher.model.SliderConfiguration;
import dev.namvdo.planmatcher.model.SmsOption;
import dev.namvdo.planmatcher.model.VoiceOption;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComponentService {

  private final ObjectMapper objectMapper;
  private ComponentOptions componentOptions;

  @PostConstruct
  public void loadComponentOptions() {
    try {
      InputStream inputStream = getClass().getResourceAsStream("data/options.json");
      componentOptions = objectMapper.readValue(inputStream, ComponentOptions.class);
      log.info("Loaded component options: {} voice, {} data, {} SMS options",
          componentOptions.getVoiceOptions().size(),
          componentOptions.getDataOptions().size(),
          componentOptions.getSmsOptions().size());
    } catch (IOException exception) {
      log.error("Fail to load component options: {}", exception.getMessage(), exception);
      throw new RuntimeException("Fail to load component options");
    }
  }



  @Cacheable("slideConfigurations")
  public Map<String, SliderConfiguration> getSlideConfigurations() {
    return componentOptions.getSliderConfigurations();
  }

  @Cacheable("voiceOptions")
  public List<VoiceOption> getVoiceOptions() {
    return componentOptions.getVoiceOptions();
  }

  @Cacheable("dataOptions")
  public List<DataOption> getDataOptions() {
    return componentOptions.getDataOptions();
  }


  @Cacheable(value = "smsOptions")
  public List<SmsOption> getSmsOptions() {
    return componentOptions.getSmsOptions();
  }

  @Cacheable(value = "voiceOptionById", key = "#id")
  public Optional<VoiceOption> getVoiceOptionById(String id) {
    return getVoiceOptions().stream()
        .filter(option -> option.getId().equals(id))
        .findFirst();
  }

  @Cacheable(value = "dataOptionById", key = "#id")
  public Optional<DataOption> getDataOptionById(String id) {
    return getDataOptions().stream()
        .filter(option -> option.getId().equals(id))
        .findFirst();
  }

  @Cacheable(value = "smsOptionById", key = "#id")
  public Optional<SmsOption> getSmsOptionById(String id) {
    return getSmsOptions().stream()
        .filter(option -> option.getId().equals(id))
        .findFirst();
  }

  @Cacheable(value = "voiceOptionsByServiceType", key = "#serviceType")
  public List<VoiceOption> getVoiceOptionsByServiceType(Integer serviceType) {
    return getVoiceOptions().stream()
        .filter(option -> option.getServiceType().equals(serviceType))
        .collect(Collectors.toList());
  }

  @Cacheable(value = "dataOptionsByServiceType", key = "#serviceType")
  public List<DataOption> getDataOptionsByServiceType(Integer serviceType) {
    return getDataOptions().stream()
        .filter(option -> option.getServiceType().equals(serviceType))
        .collect(Collectors.toList());
  }

  @Cacheable(value = "smsOptionsByServiceType", key = "#serviceType")
  public List<SmsOption> getSmsOptionsByServiceType(Integer serviceType) {
    return getSmsOptions().stream()
        .filter(option -> option.getServiceType().equals(serviceType))
        .collect(Collectors.toList());
  }

  public List<Integer> getAvailableVoiceValues() {
    return getVoiceOptions().stream()
        .map(option -> option.getSliderInfo().getValue())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }

  public List<Integer> getAvailableDataValues() {
    return getDataOptions().stream()
        .map(option -> option.getSliderInfo().getValue())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }

  public List<Integer> getAvailableSmsValues() {
    return getSmsOptions().stream()
        .map(option -> option.getSliderInfo().getValue())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }

}
