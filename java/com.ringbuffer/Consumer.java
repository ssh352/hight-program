package com.ringbuffer;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler {

    private String index;

    public Consumer(String index){
         this.index = index;
    }

    @Override
    public void onEvent(Object o) throws Exception {

    }
}
