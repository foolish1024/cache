package cn.example.service.common;

import lombok.Data;

@Data
public class CacheFactory {
    private Long startTime;

    public void operationCache(Object threadId, Long startTime){
        this.startTime = startTime;
    }

    public void closeCache(){

    }
}
