package com.microsoft.redis.controller;


import com.microsoft.redis.main.Paging;
import com.microsoft.redis.main.UserInfo;
import com.microsoft.redis.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


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
 * @Date 2021/1/25  15:37
 * </p>
 */
@RestController
@RequestMapping("data")
public class MainController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/add")
    public void run(){
        rankingService.batch();
    }

    @PostMapping("/get")
    public List<Map<String, Object>> get(@RequestBody Paging paging){
        return rankingService.getRankList(paging);
    }

    @PutMapping("/create")
    public  boolean add(@RequestBody UserInfo userInfo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return rankingService.add(userInfo);
    }
}
