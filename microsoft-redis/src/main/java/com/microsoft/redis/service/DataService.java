package com.microsoft.redis.service;

import com.microsoft.redis.main.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import com.alibaba.fastjson.JSON;

/**
 * Copyright 2013-2033 Estee Lauder(zgq65751348@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.ydm01.com/index.do
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * <p>
 *
 * @Auth Estee Lauder
 * @See {@code  }.
 * @Date 2021/1/25  15:01
 * </p>
 */
@Service
public class DataService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public final  static  String SCORE_RANK = "score_rank";

    public void batchAdd(){
        Set<ZSetOperations.TypedTuple<Entity> > typedTupleSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Entity entity = new Entity();
            entity.setCustomerId(i);
            entity.setScore((int)Math.round((Math.random()+1) * 1000));
            DefaultTypedTuple<Entity> tuple = new DefaultTypedTuple<>(entity,(double)Math.round((Math.random()+1) * 1000));
            typedTupleSet.add(tuple);
        }
        Long number = redisTemplate.opsForZSet().add(SCORE_RANK,typedTupleSet);
        System.out.println("受影响行数：" + number);
    }

    public Set<ZSetOperations.TypedTuple<Entity>> list(){
        Set<ZSetOperations.TypedTuple<Entity>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 10);
        System.out.println("获取到的排行和分数列表:" + JSON.toJSONString(rangeWithScores));
        return  rangeWithScores;
    }

    public boolean add(Entity entity){
      return  redisTemplate.opsForZSet().add(SCORE_RANK,entity.getCustomerId(),entity.getScore());
    }

    public Long totalCount(Integer a,Integer b){
        return  redisTemplate.opsForZSet().count(SCORE_RANK,a,b);
    }

    public Long  cardinal(){
        return redisTemplate.opsForZSet().zCard(SCORE_RANK);
    }

    public Double incrementScore(Entity entity){
        return redisTemplate.opsForZSet().incrementScore(SCORE_RANK,entity.getCustomerId(),entity.getScore());
    }

}
