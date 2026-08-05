package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.properties.AiProperties;
import com.atguigu.spzx.manager.service.AiChatService;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiChatServiceImpl implements AiChatService {

    @Autowired
    private AiProperties aiProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String chat(String prompt) {
        if (!StringUtils.hasText(prompt)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        if (!StringUtils.hasText(aiProperties.getBaseUrl()) || !StringUtils.hasText(aiProperties.getApiKey())) {
            return "请先在 application-dev.yml 中配置 spzx.ai.base-url 和 spzx.ai.api-key。";
        }

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        JSONArray messages = new JSONArray();
        if (StringUtils.hasText(aiProperties.getSystemPrompt())) {
            Map<String, Object> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", aiProperties.getSystemPrompt());
            messages.add(systemMessage);
        }
        messages.add(message);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", aiProperties.getModel());
        requestBody.put("messages", messages);
        requestBody.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(aiProperties.getApiKey());

        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(requestBody), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                aiProperties.getBaseUrl(),
                HttpMethod.POST,
                entity,
                String.class
        );

        String body = responseEntity.getBody();
        if (!StringUtils.hasText(body)) {
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }

        JSONObject jsonObject = JSON.parseObject(body);
        JSONArray choices = jsonObject.getJSONArray("choices");
        if (choices == null || choices.isEmpty()) {
            return jsonObject.getString("message");
        }

        JSONObject choice = choices.getJSONObject(0);
        JSONObject replyMessage = choice.getJSONObject("message");
        if (replyMessage != null && StringUtils.hasText(replyMessage.getString("content"))) {
            return replyMessage.getString("content");
        }
        return choice.getString("text");
    }

}
