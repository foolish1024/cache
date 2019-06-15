package cn.example.service.common;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ThreadObj implements Runnable {
    private CacheFactory factory;
    private CountDownLatch count;

    public ThreadObj(CacheFactory factory, CountDownLatch count){
        this.factory = factory;
        this.count = count;
    }

    @Override
    public void run() {
        factory.operationCache(
                UUID.randomUUID().toString(), factory.getStartTime());

        count.countDown();
    }
}
