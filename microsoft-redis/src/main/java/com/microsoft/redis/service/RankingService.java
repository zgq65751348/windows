package com.microsoft.redis.service;

import cn.hutool.setting.SettingUtil;
import com.alibaba.fastjson.JSON;
import com.microsoft.redis.main.Paging;
import com.microsoft.redis.main.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
 * @Date 2021/1/26  9:49
 * </p>
 */

@Service
@Slf4j
public class RankingService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    public static final String KEY = "KEY";


    /*
     *  添加至缓存
     */
    public void setRedisUserRankInfo(UserInfo userInfo, Integer count) {
        try {
            Map<String, Object> userInfoMap = new HashMap<>();
            userInfoMap.put("nickName", userInfo.getUserName());
            userInfoMap.put("headImg", userInfo.getAvatar());
            redisTemplate.opsForValue().set(KEY + "_" + userInfo.getUserId(), JSON.toJSONString(userInfoMap));
            redisTemplate.opsForZSet().add(KEY, userInfo.getUserId(), count.doubleValue());
        } catch (Exception e) {
            log.error("信息存入redis异常", e);
        }
    }

    /**
     * 排行榜列表
     *
     * @return
     */
    public List<Map<String, Object>> getRankList(Paging paging) {
        List<Map<String, Object>> rank = new LinkedList<>();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(KEY, 0, 99999, (paging.getIndex()-1)*paging.getSize(), paging.getSize());
        assert typedTuples != null;
        int index = 0;
        for (ZSetOperations.TypedTuple typle : typedTuples) {
            Map<String, Object> userRankMap = new HashMap<>();
            userRankMap.put("userId", typle.getValue());
            userRankMap.put("count", typle.getScore().intValue());
            userRankMap.put("rank", ++index);
            String userInfo = redisTemplate.opsForValue().get(KEY + "_" + typle.getValue()).toString();
            if (!StringUtils.isEmpty(userInfo)) {
                Map userInfoMap = JSON.parseObject(userInfo, HashMap.class);
                userRankMap.put("nickName", userInfoMap.get("nickName"));
                userRankMap.put("headImg", userInfoMap.get("headImg"));
            }
            rank.add(userRankMap);
        }
        return rank;
    }

    /**
     * 查询我的排行
     *
     * @param userId
     * @return
     */
    public Map<String, Object> getUserRank(String userId) {
        Long userRank = redisTemplate.opsForZSet().reverseRank(KEY, userId);//我的排名
        Double count = redisTemplate.opsForZSet().score(KEY, userId);//我的分数
        Map<String, Object> userRankMap = new HashMap<>();
        userRankMap.put("userId", userId);
        if (userRank == null || KEY == null) {
            return null;
        }
        userRankMap.put("count", count.intValue());
        userRankMap.put("rank", userRank + 1);
        String userInfo = redisTemplate.opsForValue().get(KEY + "_" + userId).toString();
        if (!StringUtils.isEmpty(userInfo)) {
            Map userInfoMap = JSON.parseObject(userInfo, HashMap.class);
            userRankMap.put("nickName", userInfoMap.get("nickName"));
            userRankMap.put("headImg", userInfoMap.get("headImg"));
        }
        return userRankMap;
    }

    public boolean add(UserInfo userInfo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("nickName", userInfo.getUserName());
        userInfoMap.put("headImg", userInfo.getAvatar());
        redisTemplate.opsForValue().set(KEY + "_" + userInfo.getUserId(), JSON.toJSONString(userInfoMap));
        return  redisTemplate.opsForZSet().add(KEY,userInfo.getUserId(),userInfo.getScore());
    }

    public void batch() {
       Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserId(i);
                    userInfo.setUserName(randomString());
                    userInfo.setAvatar(UUID.randomUUID().toString());
                    int score = (int) Math.round((Math.random() + 1) * 1000);
                    setRedisUserRankInfo(userInfo,score);
                }
            }
        };
       new Thread(runnable).start();
    }



    private static String randomString() {
        return RandomStringUtils.random(2, 0x4e00, 0x9fa5, false, false);
    }

    // custom method
    private static class RandomString {
        private static final int BASE_RANDOM = 0x9fa5 - 0x4e00 + 1;
        private static Random random = new Random();

        private static char getRandomChar() {
            return (char) (0x4e00 + random.nextInt(BASE_RANDOM));
        }
    }
}
