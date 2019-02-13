package com.bling.dab.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: hxp
 * @date: 2019/1/30 13:41
 * @description:
 */
@Component
public class RedisApiService implements RedisApi{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * redis对象实例
     */
    private static RedisApiService redisApiService;

    /**
     * 写入成功redis返回
     */
    private final String OK = "OK";


//    private static GenericObjectPoolConfig config = new GenericObjectPoolConfig();

    private JedisPool jedisPool;

    private Integer expiredInSeconds = DEFAULT_EXPIRED_IN_SECONDS;


    private void init(RedisConfig configRead) {
        String host = configRead.getHost();
        String port = configRead.getPort();
        String pwd = configRead.getPwd();
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(200);
            config.setMinIdle(100);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(150);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(20*1000);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            config.setTestOnReturn(false);
            config.setTestWhileIdle(false);
            config.setBlockWhenExhausted(false);
            logger.info("开始初始化JedisPool!");
            setJedisPool(new JedisPool(config, host, Integer.parseInt(port), 30000, null));
            logger.info("结束初始化JedisPool!-------------初始化成功！");
        } catch (Exception e) {
            logger.error("初始化JedisPool出错", e);
        }
    }


    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setExpiredInSeconds(Integer expiredInSeconds) {
        this.expiredInSeconds = expiredInSeconds;
    }

    private RedisApiService(RedisConfig configRead) {
        init(configRead);
    }

    public RedisApiService() {

    }

    /**
     * 获取单例redis(单例双重锁)
     *
     * @return
     */
    public static RedisApiService getInstance(RedisConfig configRead) {

        if (redisApiService == null) {
            synchronized (RedisApiService.class) {
                if (redisApiService == null){
                    redisApiService = new RedisApiService(configRead);
                }
            }
        }
        return redisApiService;
    }


    /**
     * 获得一个连接实例
     *
     * @return
     */
    public Jedis getResource() {
        return jedisPool.getResource();
    }

    /**
     * 归还连接实例
     */
    private void returnResource(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

    /**
     * 当系统关闭时调用，可在spring中配置
     */
    public void destroy() {
        jedisPool.destroy();
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("获取缓存值失败, key, " + key);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean set(String key, String value) throws Exception {
        return set(key, value, expiredInSeconds);
    }
    @Override
    public boolean set(String key, String value, int expiredInSeconds) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return OK.equals(jedis.setex(key, expiredInSeconds, value));
        } catch (Exception e) {
            logger.error("设置缓存值失败, key: " + key + ", value: " + value);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }
    @Override
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("判断key是否存在失败，key:" + key);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }

    }

    @Override
    public boolean remove(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.del(key) > 0;
        } catch (Exception e) {
            logger.error("移除缓存值失败, key: " + key,e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Long hset(String key, String field, String value) throws Exception {
        Jedis  jedis = null;
        try {
            jedis= getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error("设置键值对失败,key:"+key,e);
            throw new RuntimeException(e);
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置key的过期时间
     * @param key
     * @param expiredInSeconds
     * @return
     */
    @Override
    public Long expire(String key, int expiredInSeconds){
        Jedis  jedis = null;
        try {
            jedis= getResource();
            return jedis.expire(key, expiredInSeconds);
        } catch (Exception e) {
            logger.error("设置键值对失败,key:"+key,e);
            throw new RuntimeException(e);
        }finally {
            returnResource(jedis);
        }
    }
    @Override
    public String hget(String key, String field) throws Exception {

        Jedis  jedis = null;
        try {
            jedis= getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("设置键值对失败,key:"+key,e);
            throw new RuntimeException(e);
        }finally {
            returnResource(jedis);
        }
    }
    @Override
    public Long hdel(String key, String... fields) throws Exception {
        Jedis  jedis = null;
        try {
            jedis= getResource();
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            logger.error("设置键值对失败,key:"+key,e);
            throw new RuntimeException(e);
        }finally {
            returnResource(jedis);
        }
    }


}
