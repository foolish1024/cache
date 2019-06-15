package cn.example.service;

import cn.example.service.common.CacheFactory;
import cn.example.service.common.PropertiesUtils;
import cn.example.service.common.ThreadObj;
import cn.example.service.memcachedCache.XMemcachedService;
import cn.example.service.redisCache.JedisService;

import java.util.concurrent.CountDownLatch;

public class App {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("start time: "+startTime);

        int threadNumber = PropertiesUtils.getThreadNumber();
        CountDownLatch count = new CountDownLatch(threadNumber);

        CacheFactory cache = new JedisService();
//        CacheFactory cache = new XMemcachedService();
//        CacheFactory cache = new SpyMemcachedService();
        cache.setStartTime(startTime);
        for(int i=0;i<threadNumber;i++){
            Thread t = new Thread(new ThreadObj(cache, count));
            t.start();
        }

        try {
            count.await();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            cache.closeCache();
            System.out.println("total end time:"+(System.currentTimeMillis()-startTime));
        }

//        List<Person> persons = new ArrayList<>();
//        for(int i = 0;i < 3;i++){
//            Person p = new Person((long)i, "name"+i, 10+i);
//            persons.add(p);
//        }
//        Person p = new Person(1, "n"+1, 10+1);
//        System.out.println(ObjectSizeCalculator.getObjectSize(p));
    }
}
