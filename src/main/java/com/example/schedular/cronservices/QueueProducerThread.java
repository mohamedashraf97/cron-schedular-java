package com.example.schedular.cronservices;


import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.schedular.model.Job;

public class QueueProducerThread implements Runnable {

 @Autowired
 private Job job;

 @Autowired
 private  ProducerThread producerThread;

  @Override
  public void run() {
    while (true) {
      Job newJob = null; // blocking call
      try {
        newJob = getNewJobFromCrontabFile();
      } catch (ParseException e) {
        e.printStackTrace();
      }
      QueueConsumerThread.jobQueue.add(newJob);
    }
  }

  public Job getNewJobFromCrontabFile() throws java.text.ParseException {

    return producerThread.getJob(job);
  }
}
