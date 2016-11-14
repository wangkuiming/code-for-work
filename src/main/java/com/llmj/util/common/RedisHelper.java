package com.llmj.util.common;

import com.llmj.common.redis.routing.RoutingXeRedis;

/**
 * redis缓存需要的key值在common包里定义
 * url:/xe-common/src/main/java/com/cy/constant/CyRedisKey.java
 * <P>File name : RedisHelper.java </P>
 * <P>Author : songbin </P> 
 * <P>Date : 2015-7-26 </P>
 */
public class RedisHelper {

    public static final RoutingXeRedis xeRedis = new RoutingXeRedis("xeRedis");
}
