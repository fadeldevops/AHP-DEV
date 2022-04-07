package com.example.learn.api.master.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.example.learn.api.master.service.RoleService;
import com.example.learn.api.master.vo.AuthInfoVO;
import com.example.learn.api.master.vo.JwtInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtUtil {

   public static final String CLAIM_KEY_AUTHORITIES = "authorities";
   @Value("${jwt.secret:#{null}}")
   private String jwtSecret;
   @Value("${jwt.expired:#{null}}")
   private Integer jwtExpired;
   // @Value("${jwt.expired:#{null}}")
   // private String jwtRefreshSecret;
   // @Value("${" + Constants.JWT_REFRESH_EXPIRED + ":#{null}}")
   // private Integer jwtRefreshExpired;
   @Value("${jwt.tokenType:#{null}}")
   private String jwtTokenType;

   @Autowired
   private RoleService roleService;

   @Autowired
   ObjectMapper objectMapper;

   public JwtInfo generateAccessToken(String username) {

      // List<GrantedAuthority> grantedAuthorities =
      // AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
      AuthInfoVO authorizationInfo = roleService.getAuthorizationInfo(username);

      Date issuedAt = new Date();
      Date expiration = new Date(issuedAt.getTime() + (jwtExpired));
      String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
            // .claim("authorities",
            // grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .claim(CLAIM_KEY_AUTHORITIES, authorizationInfo)
            .setIssuedAt(issuedAt).setExpiration(expiration).signWith(SignatureAlgorithm.HS512,
                  jwtSecret.getBytes())
            .compact();

      return new JwtInfo(token, username, issuedAt, expiration, jwtExpired);
   }

   public Claims parseAccessToken(String token) {
      return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
   }

   public String getAccessToken(String authorizationString) {
      if (authorizationString != null && authorizationString.startsWith(jwtTokenType)) {
         return authorizationString.replaceFirst(jwtTokenType + " ", org.apache.commons.lang3.StringUtils.EMPTY);
      }

      return null;
   }

   public String getAccessToken(HttpServletRequest request) {
      String authenticationString = request.getHeader("Authorization");

      return this.getAccessToken(authenticationString);
   }

   public String getTokenType() {
      return jwtTokenType;
   }

   public AuthInfoVO getAuthorizationInfo(Claims claims) {
      Map map = (Map) claims.get(JwtUtil.CLAIM_KEY_AUTHORITIES);

      return objectMapper.convertValue(map, AuthInfoVO.class);
   }

}
