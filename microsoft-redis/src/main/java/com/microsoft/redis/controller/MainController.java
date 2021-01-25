package com.microsoft.redis.controller;

import com.microsoft.redis.main.Entity;
import com.microsoft.redis.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

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
    private DataService dataService;

    @RequestMapping(value = "/batch")
    public void  batchAdd(){
        dataService.batchAdd();
    }

    @RequestMapping(value = "/list")
    public Set<Entity> list(){
        Set<Entity> entitySet = new HashSet<>();
        Set<ZSetOperations.TypedTuple<Entity>> typedTupleSet = dataService.list();
        if(!CollectionUtils.isEmpty(typedTupleSet)){
            typedTupleSet.forEach(entityTypedTuple -> {
                entitySet.add(entityTypedTuple.getValue());
            });
        }
        return entitySet;
    }

    @GetMapping("/cardinal")
    public long cardinal(){
        return  dataService.cardinal();
    }

    @GetMapping(value = "/count")
    public long totalCount(){
        return dataService.totalCount(0,5);
    }

}
