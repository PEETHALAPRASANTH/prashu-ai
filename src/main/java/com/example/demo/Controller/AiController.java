package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.AiService;
import com.example.demo.dto.ChatInputData;

@RestController
public class AiController {
	
	
	@Autowired
	public AiService aiService;
	
	@CrossOrigin("*")
	@PostMapping("/ai/chat")
	public Object chat(@RequestBody ChatInputData chatInputData) {

	    return aiService.chat(chatInputData.getMessage());

	}
	
}
