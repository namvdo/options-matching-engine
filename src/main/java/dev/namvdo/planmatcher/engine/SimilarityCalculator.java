package dev.namvdo.planmatcher.engine;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimilarityCalculator {
  public double calculateVoiceSimilarity(Integer preferred, Integer actual, List<Integer> availableValues) {
    if (actual == null) {
      return preferred >= 2000 ? 0.95 : Math.min(0.9, preferred / 2000.0);
    }
    if (preferred < 200) {
      double diff = Math.abs(preferred - actual);
      return Math.max(0, 1 - (diff / 200));
    } else {
      double ratio = Math.min((double) preferred, (double) actual) / Math.max((double) preferred, (double) actual);

      double extraFactor = actual >= preferred ? 0.05 : 0;

      return Math.max(0.7, ratio) + extraFactor;
    }
  }

  public double calculateDataSimilarity(Integer preferred, Integer actual, List<Integer> availableValues) {
    if (actual == null) {
      return preferred >= 50 ? 0.95 : Math.min(0.9, preferred / 50.0);
    }

    double logPreferred = Math.log(preferred + 1);
    double logActual = Math.log(actual + 1);

    Integer maxValue = availableValues.stream().max(Integer::compare).orElse(100);
    double logMax = Math.log(maxValue + 1);

    double logDiff = Math.abs(logPreferred - logActual);

    double similarity = 1 - (logDiff / logMax);

    double bonusFactor = actual >= preferred ? 0.05 : 0;

    return Math.min(1, similarity + bonusFactor);
  }

  public double calculateSmsSimilarity(Integer preferred, Integer actual, List<Integer> availableValues) {
    if (actual == null) {
      return preferred >= 500 ? 0.95 : Math.min(0.9, preferred / 500.0);
    }

    if (preferred >= 500 && actual >= 500) {
      return 0.95;
    }

    double diff = Math.abs(preferred - actual);
    return Math.max(0, 1 - (diff / 500.0));
  }

  public Integer findClosestValue(Integer preferred, List<Integer> availableValues) {
    if (availableValues == null || availableValues.isEmpty()) {
      return preferred;
    }
    return availableValues.stream().min(
        (a, b) -> {
          int diffA = Math.abs(a - preferred);
          int diffB = Math.abs(b - preferred);
          return Integer.compare(diffA, diffB);
        }
    ).orElse(preferred);
  }
}
