package com.microsoft.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
 * @Date 2021/1/25  15:00
 * </p>
 */
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class,args);
    }
}
