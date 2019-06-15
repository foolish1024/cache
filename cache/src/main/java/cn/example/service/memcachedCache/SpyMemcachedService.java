//package cn.example.service.memcachedCache;
//
//import cn.example.service.common.CacheFactory;
//import cn.example.service.common.Person;
//import cn.example.service.common.PropertiesUtils;
//import net.spy.memcached.ConnectionFactory;
//import net.spy.memcached.ConnectionFactoryBuilder;
//import net.spy.memcached.DefaultConnectionFactory;
//import net.spy.memcached.MemcachedClient;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SpyMemcachedService extends CacheFactory {
//    private static String host = PropertiesUtils.getHost();
//    private static int port = PropertiesUtils.getMemcachedPort();
//    private static int cacheValueNumber = PropertiesUtils.getCacheValueNumber();
//    private static int cacheValueSize = PropertiesUtils.getCacheValueSize()/100;
//
//    private static MemcachedClient mc;
//    static {
//        ConnectionFactoryBuilder builder = new ConnectionFactoryBuilder();
//        builder.setOpTimeout(100000);
//        ConnectionFactory factory = builder.build();
//        InetSocketAddress address = new InetSocketAddress(host, port);
//        List<InetSocketAddress> addressList = new ArrayList<>();
//        addressList.add(address);
//        try {
//            mc = new MemcachedClient(factory, addressList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void operationCache(Object threadId, Long startTime){
//        String key = threadId+this.getClass().getPackage().getName() +
//                this.getClass().getClass().getName();
//        List<Person> persons = new ArrayList<>();
//        for(int i = 1;i < cacheValueSize;i++){
//            persons.add(new Person(i, "n"+i, 10+i));
//        }
//
//        for(int i=0;i<cacheValueNumber;i++){
//            mc.set(key+i, 100000, persons.toString());
//        }
//
//        for(int i=0;i<cacheValueNumber;i++){
//            mc.get(key+i);
//            mc.delete(key+i);
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println("memcached total time: "+(endTime-startTime));
//    }
//
//    @Override
//    public void closeCache() {
//        mc.shutdown();
//    }
//}
