package dev.namvdo.planmatcher.dataaccess;

public record SmsPlanComponent(OptionType type, String id, Integer count, String description) {
  public static  SmsPlanComponent of(String id, Integer count, String description) {
    return new SmsPlanComponent(OptionType.SMS, id, count, description);
  }
}
