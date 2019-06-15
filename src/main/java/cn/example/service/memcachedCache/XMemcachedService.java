package cn.example.service.memcachedCache;

import cn.example.service.common.CacheFactory;
import cn.example.service.common.Person;
import cn.example.service.common.PropertiesUtils;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class XMemcachedService extends CacheFactory {
    private static String host = PropertiesUtils.getHost();
    private static int port = PropertiesUtils.getMemcachedPort();
    private static int cacheValueNumber = PropertiesUtils.getCacheValueNumber();
    private static int cacheValueSize = PropertiesUtils.getCacheValueSize()/100;

    private static MemcachedClientBuilder builder;
    private static MemcachedClient c;
    static {
        builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(host+":"+port));
//        builder.setConnectionPoolSize(10);
        builder.setOpTimeout(10000);
        try {
            c = builder.build();
            c.setOpTimeout(10000);
//            c.setConnectionPoolSize(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void operationCache(Object threadId, Long startTime){
        String key = threadId+this.getClass().getPackage().getName() +
                this.getClass().getName();
        List<Person> persons = new ArrayList<>();
        for(int i = 1;i < cacheValueSize;i++){
            persons.add(new Person(i, "n"+i, 10+i));
        }

        try {
            for(int i=0;i<cacheValueNumber;i++){
                c.set(key+i, 10000, persons.toString());
            }
            c.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                for(int i=0;i<cacheValueNumber;i++){
                    c.delete(key+i);
                }
                long endTime = System.currentTimeMillis();
                System.out.println("XMemcache total time: "+(endTime-startTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeCache() {
        try {
            if(null != c){
                c.shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
