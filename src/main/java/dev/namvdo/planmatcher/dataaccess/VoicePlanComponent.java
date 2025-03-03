package dev.namvdo.planmatcher.dataaccess;

public record VoicePlanComponent(OptionType type, String id, String minutes, String description) {
  public static VoicePlanComponent of(String id, String minutes, String description)   {
    return new VoicePlanComponent(OptionType.VOICE, id, minutes, description);
  }
}
