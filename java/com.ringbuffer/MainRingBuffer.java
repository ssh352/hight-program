package com.ringbuffer;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainRingBuffer {

    public String getadd() throws Exception {
        RingBuffer<Order> ringBuffer =
                RingBuffer.create(ProducerType.MULTI, new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return null;
                    }
                }, 1024 * 1024, new YieldingWaitStrategy());


        SequenceBarrier barrier = ringBuffer.newBarrier();

        Consumer[] consumers = new Consumer[3];

        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("c" + i);
        }

        WorkerPool<Order> workerPool =
                new WorkerPool<Order>(ringBuffer, barrier, new IgnoreExceptionHandler(), consumers);

        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 100; j++) {
                     producer.onDate(UUID.randomUUID().toString());
                    }
                }


            }).start();
        }

        Thread.sleep(2000);
        System.out.println("==========开始生产==================");
        latch.countDown();
        Thread.sleep(5000);
        System.out.println("总数"+consumers[0]);

        return null;
    }
}
