package com.f4w.utils;

import com.f4w.entity.SysUser;
import com.f4w.mapper.SysUserMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JWTUtils {
    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.exp}")
    private Integer exp;
    @Resource
    private SysUserMapper sysUserMapper;

    public String creatKey(Map map) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key keySpec = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        JwtBuilder builder = Jwts.builder();
        builder.setClaims(map);
        builder.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        builder.setExpiration(DateTime.now().plusHours(exp).toDate());
        builder.setIssuedAt(new Date());
        builder.setNotBefore(new Date());
        builder.signWith(keySpec);
        String jws = builder.compact();
        return jws;
    }

    public String creatKey(SysUser sysUser) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key keySpec = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        JwtBuilder builder = Jwts.builder();
        Map map = new HashedMap();
        map.put("uid", sysUser.getId());
        builder.setClaims(map);
        builder.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        builder.setExpiration(DateTime.now().plusHours(exp).toDate());
        builder.setIssuedAt(new Date());
        builder.setNotBefore(new Date());
        builder.signWith(keySpec);
        String jws = builder.compact();
        return jws;
    }

    public SysUser getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getHeader("X-Token");
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            Object uid = parseBody(token).get("uid");
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(uid);
            return sysUser;
        } catch (Exception e) {
            log.error("解析token 失败");
        }
        return null;
    }

    public SysUser getRequiredUserInfo() throws ShowException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getHeader("X-Token");
        }
        if (StringUtils.isBlank(token)) {
            throw new ShowException(SystemErrorEnum.AUTH_TOKEN);
        }
        try {
            Object uid = parseBody(token).get("uid");
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(uid);
            if (sysUser == null) {
                throw new ShowException(SystemErrorEnum.AUTH_EXP);
            }
            return sysUser;
        } catch (Exception e) {
            throw new ShowException(SystemErrorEnum.AUTH_EXP);
        }
    }


    public Claims parseBody(String jwsString) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsString).getBody();
        return claims;
    }
}
