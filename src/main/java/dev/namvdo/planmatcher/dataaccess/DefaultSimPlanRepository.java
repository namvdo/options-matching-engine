package dev.namvdo.planmatcher.dataaccess;

import java.util.List;

public class DefaultSimPlanRepository implements SimPlanRepository {
  @Override
  public List<Plan> getAllPlansForClient(Long client) {
    return List.of();
  }

  @Override
  public <T> List<PlanOption> getPlanOptions(T planId) {
    return List.of();
  }

  @Override
  public <T> List<PlanOption> getPlanOptions(T planId, OptionType type) {
    return List.of();
  }
}
