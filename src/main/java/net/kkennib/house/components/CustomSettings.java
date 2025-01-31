package net.kkennib.house.components;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "custom.settings")
public class CustomSettings {

    private int pageCount;
}

