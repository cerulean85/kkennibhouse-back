package net.kkennib.house.config;

import net.kkennib.house.util.JwtUtil;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
  @Bean
  public JwtUtil jwtService() {
    return Mockito.mock(JwtUtil.class);
  }
}