package com.example.schedular.model;

import org.springframework.stereotype.Component;

@Component
public class Job {

  long nextExecutionTime;
  long executionInterval;

  public long getNextExecutionTime() {
    return nextExecutionTime;
  }

  public void setNextExecutionTime(long nextExecutionTime) {
    this.nextExecutionTime = nextExecutionTime;
  }

  public void setExecutionInterval(long executionInterval) {
    this.executionInterval = executionInterval;
  }

  public long getExecutionInterval() {
    return executionInterval;
  }

  public Job copy() {
    Job job = new Job();
    job.setNextExecutionTime(getNextExecutionTime());
    job.setExecutionInterval(getExecutionInterval());
    return job;
  }

  public void execute() {
    System.out.println("Job executed...");
  }
}
