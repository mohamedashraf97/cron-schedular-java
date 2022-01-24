package com.example.schedular.cronservices;

import static java.lang.System.currentTimeMillis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.schedular.model.Job;

@Component
public class QueueConsumerThread implements Runnable {

  @Autowired
  private Job job ;

  //Heap safe solution
  public static PriorityBlockingQueue<Job> jobQueue = new PriorityBlockingQueue<>();
  //N threads for example 4
  private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

  public  void interrupt(Job newJob) {
    //should interrupt the thread here
    newJob.execute();
  }

  // Sleep for the next period until next execution
  void goToSleep(Job job, PriorityBlockingQueue jobQueue) throws InterruptedException {
    jobQueue.add(job);
    Thread.sleep(job.getNextExecutionTime());
  }

  void executeJob(Job job, PriorityBlockingQueue jobQueue) {
    threadPool.submit((Runnable) job); // async call
    job = job.copy();
    job.setNextExecutionTime(currentTimeMillis() + job.getExecutionInterval());
    jobQueue.add(job);
  }

  @Override
  public void run() {
    while (true) {
      job = jobQueue.peek();
      //if the next time didn`t come yet
      if (job.getNextExecutionTime() > currentTimeMillis()) {
        // Nothing to do
        try {
          goToSleep(job, jobQueue);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        executeJob(job, jobQueue);
      }
    }
  }
}
