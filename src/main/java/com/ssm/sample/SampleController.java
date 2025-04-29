package com.ssm.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssm.sample.service.SampleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api")
@Tag(name="sample",description = "sample-Controller")
public class SampleController {
	
	@Autowired
	private SampleService sampleService;
	
    @GetMapping("/sample")
	@Operation(summary = "sample",description = "sample")
    public String doGetHelloWorldDemo() {
        return "Hello World (Demo)";
    }
    
    @GetMapping("/sampledb")
	@Operation(summary = "sampledb",description = "sampledb")
    public int doGetHelloWorldDemodb() {
        return sampleService.selectId();
    }
	
}
