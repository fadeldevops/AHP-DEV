package com.example.learn.api.master.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import com.example.learn.api.master.security.RestAuthenticationEntryPoint;
import com.example.learn.api.master.service.UserDetailService;
import com.example.learn.api.master.service.impl.UserDetailServiceImpl;
import com.example.learn.api.master.util.JwtAuthenticationFilter;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   RestAuthenticationEntryPoint restAuthenticationEntryPoint;

   @Autowired
   JwtAuthenticationFilter jwtAuthenticationFilter;

   @Autowired
   private UserDetailsService userDetailsService;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().cors().and().logout().disable().exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint).and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            // .antMatchers(HttpMethod.GET, "/generate-error", "/hello-world").permitAll()
            .anyRequest().authenticated();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return NoOpPasswordEncoder.getInstance();
   }

   @Override
   public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/h2/**", "/v2/api-docs", "/v3/api-docs/**", "/configuration/**",
            "/swagger-resources/**", "/swagger-resources", "/swagger-ui/**", "/webjars/**", "/api-docs/**");
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {

      auth.userDetailsService(userDetailsService);
   }

   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   @Bean
   public WebMvcConfigurer configurer() {
      return new WebMvcConfigurer() {
         @Override
         public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/*").allowedOrigins("*");
         }
      };
   }

   @Bean(name = "restTemplateByPassSSL")
   public RestTemplate restTemplateByPassSSL()
         throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
      TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
      HostnameVerifier hostnameVerifier = (s, sslSession) -> true;
      SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
      SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
      CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      requestFactory.setHttpClient(httpClient);

      return new RestTemplate(requestFactory);
   }

}
