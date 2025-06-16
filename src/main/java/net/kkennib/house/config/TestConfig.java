package net.kkennib.house.config;

import net.kkennib.house.service.JwtService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
  @Bean
  public JwtService jwtService() {
    return Mockito.mock(JwtService.class);
  }
}