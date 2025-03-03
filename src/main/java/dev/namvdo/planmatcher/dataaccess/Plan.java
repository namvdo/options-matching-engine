package dev.namvdo.planmatcher.dataaccess;

import com.google.gson.JsonObject;

public record Plan(String name, String description, String monthlyPrice, JsonObject component){ }
