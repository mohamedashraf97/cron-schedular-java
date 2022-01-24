package com.example.schedular.cronservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import com.example.schedular.model.Job;

public class ProducerThread implements Runnable {

  @Autowired
  private Job job ;
  @Autowired
  private QueueConsumerThread queueConsumerThread;

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
      if (newJob == QueueConsumerThread.jobQueue.peek()) {
        // The new job is the one that will be scheduled next.
        // So wakeup consumer thread so that it does not oversleep.
        queueConsumerThread.interrupt(newJob);
      }
    }
  }

  private Job getNewJobFromCrontabFile() throws java.text.ParseException {
    //iterate through file to read expressions
    //Or here is a demo example now
    return getJob(job);
  }

  public Job getJob(Job job) throws ParseException {
    final String cronExpression = "0 45 23 * * *";

    final CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
    final Date nextExecutionDate = generator.next(new Date());
    SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
    Date d = f.parse(nextExecutionDate.toString());
    long milliseconds = d.getTime();
    job.setExecutionInterval(milliseconds);
    return job;
  }
}
