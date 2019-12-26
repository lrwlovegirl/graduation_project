package com.lrw.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.util.WriteDoc;

@RestController
@RequestMapping("/export")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ExportController {

	@GetMapping("/exportQuestionToDoc")
	public void exportQuestionToDoc() throws IOException {
		System.out.println("start");
		WriteDoc.createWord("test.doc");
		System.out.println("end");
	}
	
}
