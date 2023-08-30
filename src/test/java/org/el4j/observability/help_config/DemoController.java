package org.el4j.observability.help_config;

import java.io.Serializable;

import org.el4j.observability.annotation.TimeProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class DemoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

	@GetMapping("/throwException")
	@ResponseBody
	public void testMethod() {
		throw new DemoException("Test Exception Aspect works.");
	}

	@GetMapping("/annotationOnMethod")
	@ResponseBody
	@TimeProfile
	public void testMethodAnnotation() {
		LOGGER.info("log in testMethodAnnotation");
	}

	public static class DemoException extends RuntimeException implements Serializable {
		public static final long serialVersionUID = 4328743;

		/* default */ DemoException(final String m) {
			super(m);
		}
	}
}
