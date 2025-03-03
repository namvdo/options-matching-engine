package dev.namvdo.planmatcher.dataaccess;

import java.util.List;

public interface SimPlanRepository {
  List<Plan> getAllPlansForClient(Long client);
  <T> List<PlanOption> getPlanOptions(T planId);
  <T> List<PlanOption> getPlanOptions(T planId, OptionType type);
}
