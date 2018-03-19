package com.baomidou.kisso;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.listener.WebApplicationContextHelper;
import com.doctorai.kisso.SSOCache;
import com.doctorai.kisso.security.token.SSOToken;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhaoShihao
 * @version 1.0
 * @create 2018-03-01 12:21
 */
public class RedisSSOCache implements SSOCache {
    private static Logger logger = Logger.getLogger(RedisSSOCache.class);
    //private JedisPool pool = WebApplicationContextHelper.getBean("jedisPool", JedisPool.class);
    StringRedisTemplate template = WebApplicationContextHelper.getBean("stringRedisTemplate", StringRedisTemplate.class);

    @PostConstruct
    public void init() {
        RedisSSOCache ssoCache = this;
        ssoCache.template = this.template;
    }

    @Override
    public SSOToken get(String key, int expires) {
        String s = template.opsForValue().get(key);
        if (expires > 0) {
            template.expire(key, expires, TimeUnit.SECONDS);
        }
        return JSONObject.parseObject(s, SSOToken.class);
    }

    @Override
    public boolean set(String key, SSOToken token, int expires) {
        try {
            String s = JSON.toJSONString(token);
            template.opsForValue().set(key, s);
            if (expires > 0) {
                template.expire(key, expires, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error("缓存设置失败", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String key) {
        try {
            template.delete(key);
        } catch (Exception e) {
            logger.error("缓存删除失败", e);
            return false;
        }
        return true;
    }
}
