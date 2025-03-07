package com.yrh.ff_ticket.util;


import com.yrh.ff_ticket.dao.model.Users;
import io.jsonwebtoken.*;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Date;
import java.util.UUID;

public class TokenUtil {

    private static final long time=1000*60*60*24;

    private static final String signature="thisisrandomstringtocode114";

    public static String createUserJwt(Users user){
        return createUserJwt(user.getUser_id(),user.getUserType().getType());
    }
    public static String createUserJwt(String user_id,String user_type){
        JwtBuilder jwtBuilder= Jwts.builder();
        String token=jwtBuilder
                //头部header
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
                //荷载payload
                .claim("user_id",user_id)
                .claim("user_type",user_type)
//                .claim("user_name",user_name)
                .setSubject("user-token")//主题
                .setExpiration(new Date(System.currentTimeMillis()+time))//有效时间
                .setId(UUID.randomUUID().toString())
                //签名Signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();//拼接三部分
        return token;
    }

    public static Users parseUserJwt(String token){
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws=jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims=claimsJws.getBody();
        Users users=new Users();
        users.setUser_id((String) claims.get("user_id"));
        users.setUser_name((String) claims.get("user_name"));
        return users;
    }

    public static Pair<Boolean,String> verify(String token){
        try {
            JwtParser jwtParser=Jwts.parser();
            Jws<Claims> claimsJws=jwtParser.setSigningKey(signature).parseClaimsJws(token);
            Claims claims=claimsJws.getBody();
            return new Pair<>(true,(String) claims.get("user_type"));
        } catch (Exception e){
            return new Pair<>(false,null);
        }
    }
}
