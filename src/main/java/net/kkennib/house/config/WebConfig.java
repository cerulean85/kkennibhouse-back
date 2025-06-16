package net.kkennib.house.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 프론트엔드 주소 http://localhost:3000
                .allowedMethods("*");
//                .allowCredentials(true);
    }
}