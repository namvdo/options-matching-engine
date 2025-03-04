package dev.namvdo.planmatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.namvdo")
public class PlanMatcherApplication {

  public static void main(String[] args) {
    SpringApplication.run(PlanMatcherApplication.class, args);
  }

}
