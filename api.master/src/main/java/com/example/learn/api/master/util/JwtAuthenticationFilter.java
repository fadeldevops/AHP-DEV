package com.example.learn.api.master.util;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.learn.api.master.vo.AuthInfoVO;
import com.example.learn.api.master.vo.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   @Autowired
   JwtUtil util;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {

      try {
         String token = util.getAccessToken(request);

         if (token != null) {
            Claims claims = util.parseAccessToken(token);
            setupSpringAuthentication(claims);
         } else {
            SecurityContextHolder.clearContext();
         }
      } catch (ExpiredJwtException e) {
         SecurityContextHolder.clearContext();
      } catch (UnsupportedJwtException e) {
         SecurityContextHolder.clearContext();
      } catch (MalformedJwtException e) {
         SecurityContextHolder.clearContext();
      } catch (Exception e) {
         SecurityContextHolder.clearContext();
      }
      filterChain.doFilter(request, response);

   }

   /**
    * Authentication method in Spring flow
    *
    * @param claims
    */
   private void setupSpringAuthentication(Claims claims) {
      AuthInfoVO authorizationInfo = (AuthInfoVO) util.getAuthorizationInfo(claims);
      UserInfo userInfo = new UserInfo(claims.getSubject());

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userInfo, null,
            authorizationInfo.getRoleCds().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
      SecurityContextHolder.getContext().setAuthentication(auth);
   }

   // private Boolean isUrlPermitted(HttpServletRequest request, AuthInfoVO
   // authorizationInfo) {

   // if (authorizationInfo != null) {
   // if (authorizationInfo.getIsAdminFlag() ||
   // !authorizationInfo.getIsAdminFlag()) {
   // return true;
   // }

   // // return authorizationInfo.getPermittedUrls().stream().anyMatch(urlInfo -> {
   // // boolean hasTrailingQuestionMark =
   // // "GET".equals(request.getMethod()) &&
   // // request.getParameterNames().hasMoreElements();

   // // return (request.getRequestURI() + (hasTrailingQuestionMark ? "?" :
   // // StringUtils.EMPTY)).startsWith(
   // // urlInfo.getUrl()) && request.getMethod().equals(urlInfo.getReqMthd());
   // // });
   // }

   // return false;
   // }

}
