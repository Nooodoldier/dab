package com.bling.dab.common.util;

import com.alibaba.fastjson.JSON;
import com.bling.dab.common.model.SignInReq;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: hxp
 * @date: 2019/5/10 14:30
 * @description:
 */
@Slf4j
public class JwtTokenUtil {

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param ttlMillis jwt过期时间
     * @param sign      登录成功的user对象
     * @return
     */
    public static String createJWT(long ttlMillis, SignInReq sign) {
        log.info("开始创建token令牌,expt:"+ttlMillis);
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", sign.getId());
        claims.put("username", sign.getUserName());
        claims.put("password", sign.getPassword());

        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = sign.getPassword();

        //生成签发人
        String subject = sign.getUserName();



        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        Date exp = null;
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        String token = builder.compact();
        String dateStr = DateUtil.parseDateToStr(exp, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        log.info("结束创建token令牌:"+token+"\n"+"token令牌过期时间为:"+dateStr);
        return token;
    }



    /**
     * Token的解密
     * @param token 加密后的token
     * @param sign  用户的对象
     * @return
     */
    public static Claims parseJWT(String token, SignInReq sign) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = sign.getPassword();

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        log.info("解密token令牌："+ JSON.toJSONString(claims));
        return claims;
    }


    /**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     * @param token
     * @param sign
     * @return
     */
    public static Boolean isVerify(String token, SignInReq sign) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = sign.getPassword();

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();

        if (claims.get("password").equals(sign.getPassword())) {
            log.info("验证token令牌通过！");
            return true;
        }
        log.info("验证token令牌未通过！");
        return false;
    }


}
