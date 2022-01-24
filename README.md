# cron-schedular-java
Simple algorithm for cron schedular


# The Solution:

1-Using PriorityBlockingQueue which is safe to use with threads and prioritized by job.nextExecutionTime().

2-thread-pool is used to pop the job from the queue.

3- Job: this is the model for our job in the solution to get the nextExecutionTime or nextExecutionInterval ..etc.
    
3- by following the standard threadpool producer-consumer pattern.

4- QueueConsumerThread : thread which will be running in an infinite loop and submitting new jobs to the thread pool after consuming them from the queue. Lets call it QueueConsumerThread:

5- QueueProducerThread: One more thread is used to fire the jobs or get them from file

```bash
A problem here appears which is if a thread is sleeping for an hour waiting for its turn and a new task appears which will run every minute,
we will need something to wake it up to run the new task which is ProducerThread
```
----------------------------------------------------------------------------------------------------------------------------------

## Notes
```python
This is solution is made using java spring boot just to use speing functionalities and for grouping our folders and solution

Just to know the approach and the flow of the code and algorithm

unfortunately tests is not implemented (it might be made using scheduled or Cron in spring and may be by using fake timers)
```
