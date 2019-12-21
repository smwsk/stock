package com.smu.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController extends BaseController {

	@RequestMapping(value = "hello1")
	public String hello1(){
		return "hello1";
	}

	@RequestMapping(value = "hello")
	public String hello(){
		return "hello";
	}
}
