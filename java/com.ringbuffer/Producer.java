package com.ringbuffer;

import com.lmax.disruptor.RingBuffer;

public class Producer {

    private String process;

    private RingBuffer ringBuffer;

    public Producer(RingBuffer ringBuffer){
        this.ringBuffer =ringBuffer;
    }


    public void onDate(String uuid){
        long sequece = ringBuffer.next();

        try{

             Order order = (Order)ringBuffer.get(sequece);
             order.setId(uuid);
        }finally{
              ringBuffer.publish(sequece);
        }

    }
}
