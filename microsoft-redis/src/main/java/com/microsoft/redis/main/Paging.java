package com.microsoft.redis.main;

import lombok.Data;

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
 * @Date 2021/1/26  11:32
 * </p>
 */
@Data
public class Paging {
    private int index;
    private int size;

    private static ThreadLocal<Integer> offSet = new ThreadLocal<Integer>();
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();

    public static int getOffSet() {
        Integer os =offSet.get();
        if (os == null) {
            return 0;
        }
        return os;
    }
    public static void setOffSet(int offSetValue) {
        offSet.set(offSetValue);
    }
    public static void removeOffSet(){
        offSet.remove();
    }
    /*
     * pageSize ：get、set、remove
     */
    public static int getPageSize() {
        Integer ps = pageSize.get();
        if (ps == null) {
            return 10;
        }
        return ps;
    }
    public static void setPageSize(int pageSizeValue) {
        pageSize.set(pageSizeValue);
    }
    public static void removePageSize(){
        pageSize.remove();
    }
}
