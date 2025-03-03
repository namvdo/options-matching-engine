package dev.namvdo.planmatcher.dataaccess;

public record DataPlanComponent(OptionType type, String id, String gigabytes, String description) {
  public static DataPlanComponent of(String id, String minutes, String description) {
    return new DataPlanComponent(OptionType.DATA, id, minutes, description);
  }
}
