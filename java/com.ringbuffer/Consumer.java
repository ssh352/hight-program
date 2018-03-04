package com.ringbuffer;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements WorkHandler<Order> {

    private String index;

    private static AtomicInteger cont = new AtomicInteger(0);

    public Consumer(String index){
         this.index = index;
    }



    @Override
    public void onEvent(Order o) throws Exception {
        System.out.println("当前消者"+this.index+"消费信息"+o.getId());
       cont.incrementAndGet();
    }

    public int getCount(){
        return cont.get();
    }
}
