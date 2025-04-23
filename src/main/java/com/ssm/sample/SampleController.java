package com.ssm.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api")
@Tag(name="sample",description = "sample-Controller")
public class SampleController {
	
    @GetMapping("/sample")
	@Operation(summary = "sample",description = "sample")
    public String doGetHelloWorldDemo() {
        return "Hello World (Demo)";
    }
	
}
