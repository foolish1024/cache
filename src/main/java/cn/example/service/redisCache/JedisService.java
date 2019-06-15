package cn.example.service.redisCache;

import cn.example.service.common.CacheFactory;
import cn.example.service.common.Person;
import cn.example.service.common.PropertiesUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisService extends CacheFactory {
    private static String host = PropertiesUtils.getHost();
    private static int port = PropertiesUtils.getRedisPort();
    private static int cacheValueNumber = PropertiesUtils.getCacheValueNumber();
    private static int cacheValueSize = PropertiesUtils.getCacheValueSize()/100;

    private static JedisPool jedisPool;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        jedisPool = new JedisPool(config, host, port, 3000);
    }

    @Override
    public void operationCache(Object threadId, Long startTime){
        String key = threadId+this.getClass().getPackage().getName() +
                this.getClass().getClass().getName();
        List<Person> persons = new ArrayList<>();
        for(int i = 0;i < cacheValueSize;i++){
            persons.add(new Person(i, "n"+i, 10+i));
        }

        Jedis j = null;
        try{
            j = jedisPool.getResource();

//            this.StringOp(j, key, persons.toString());
            this.HashOp(j, key, persons.toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != j){
                j.close();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("redis total time: "+(endTime-startTime));
        }
    }

    @Override
    public void closeCache() {
        jedisPool.destroy();
    }

    private void StringOp(Jedis j, String key, String value){
        for(int i = 0;i<cacheValueNumber;i++){
                j.set(key+i, value);
        }

        for(int i = 0;i<cacheValueNumber;i++){
                j.get(key+i);
                j.del(key+i);
        }
    }

    private void HashOp(Jedis j, String key, String value){
        Map<String, String> map = new HashMap<>();
        String hKey = "h"+key;
        for(int i = 0;i<cacheValueNumber;i++){
            map.put((key+i), value);
        }
        j.hset(hKey, map);

        for(int i = 0;i<cacheValueNumber;i++){
            j.hget(hKey, (key+i));
            j.hdel(hKey, (key+i));
        }
    }
}
