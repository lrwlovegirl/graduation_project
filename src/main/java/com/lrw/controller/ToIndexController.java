package com.lrw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToIndexController {
    @GetMapping("/index")
	public String toIndex() {
		return "/index.html";
	}
	
	
}
