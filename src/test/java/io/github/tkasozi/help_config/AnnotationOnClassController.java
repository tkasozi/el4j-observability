package io.github.tkasozi.help_config;

import io.github.tkasozi.annotation.TimeProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/classAnnotation")
@Controller
@TimeProfile("annotation_on_class")
public class AnnotationOnClassController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOnClassController.class);

	@GetMapping
	@ResponseBody
	public void testMethodAnnotation() {
		LOGGER.info("log in testMethodAnnotation");
	}
}
