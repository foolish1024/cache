package cn.example.service.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    static Properties p;
    static {
        p = new Properties();
        InputStream is = PropertiesUtils.class.getClassLoader().
                getResourceAsStream("applicationContext.properties");
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHost(){
        return p.getProperty("host");
    }

    public static Integer getRedisPort(){
        return Integer.valueOf(p.getProperty("redis.port"));
    }

    public static Integer getMemcachedPort(){
        return Integer.valueOf(p.getProperty("memcached.port"));
    }

    public static Integer getThreadNumber(){
        return Integer.valueOf(p.getProperty("thread.number"));
    }

    public static Integer getCacheValueNumber(){
        return Integer.valueOf(p.getProperty("cache.value.number"));
    }

    public static Integer getCacheValueSize(){
        return Integer.valueOf(p.getProperty("cache.value.size"));
    }
}
