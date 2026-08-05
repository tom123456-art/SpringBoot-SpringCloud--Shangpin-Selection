package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.dto.ai.AiChatDto;
import com.atguigu.spzx.manager.service.AiChatService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ai")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping("/chat")
    public Result<String> chat(@RequestBody AiChatDto aiChatDto) {
        String answer = aiChatService.chat(aiChatDto.getPrompt());
        return Result.build(answer, ResultCodeEnum.SUCCESS);
    }

}
