package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spzx.ai")
public class AiProperties {

    private String baseUrl;

    private String apiKey;

    private String model;

    private String systemPrompt;

}
