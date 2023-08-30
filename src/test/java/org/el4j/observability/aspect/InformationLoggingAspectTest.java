package org.el4j.observability.aspect;

import java.util.List;

import org.el4j.observability.domain.redis.EventLog;
import org.el4j.observability.help_config.AnnotationOnClassController;
import org.el4j.observability.help_config.DemoController;
import org.el4j.observability.help_config.UtilityClass;
import org.el4j.observability.repository.redis.EventLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/* default */ class InformationLoggingAspectTest {

	private static final String TEST_USER_NAME = "tkasozi";
	private static final Long TEST_TTL = 30L;

	/* default */ EventLogRepository eventLogRepository;
	/* default */ InformationLoggingAspect informationLoggingAspect;
	/* default */ MockMvc mockMvc;


	@BeforeEach
		/* default */ void setUp() {
		TestSecurityContextHolder.setAuthentication(new UsernamePasswordAuthenticationToken(TEST_USER_NAME, List.of()));
		eventLogRepository = Mockito.mock(EventLogRepository.class);
		informationLoggingAspect = new InformationLoggingAspect(eventLogRepository, TEST_TTL);
	}

	@Test
		/* default */ void logExecutionTimeType() throws Exception {
		final DemoController controllerProxy =
				(DemoController) UtilityClass.getAopProxy(informationLoggingAspect, new DemoController())
						.getProxy();
		mockMvc = MockMvcBuilders
				.standaloneSetup(controllerProxy)
				.build();
		mockMvc.perform(MockMvcRequestBuilders.get("/test/annotationOnMethod"));
		final ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
		Mockito.verify(eventLogRepository, Mockito.times(1)).save(captor.capture());

		final EventLog actualEvent = captor.getValue();
		Assertions.assertEquals("TIME_PROFILE", actualEvent.getEventName());
		Assertions.assertEquals(TEST_TTL, actualEvent.getTtl());
		Assertions.assertTrue(actualEvent.getDescription()
				.contains("[Method \"testMethodAnnotation\" in class \"DemoController\" executed in"));
		Assertions.assertEquals(TEST_USER_NAME, actualEvent.getUsername());
		Assertions.assertEquals("[]", actualEvent.getUserAccess());
	}

	@Test
		/* default */ void logExecutionTimeMethod() throws Exception {
		final AnnotationOnClassController controllerProxy =
				(AnnotationOnClassController) UtilityClass.getAopProxy(informationLoggingAspect, new AnnotationOnClassController())
						.getProxy();
		mockMvc = MockMvcBuilders
				.standaloneSetup(controllerProxy)
				.build();
		mockMvc.perform(MockMvcRequestBuilders.get("/classAnnotation"));
		final ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
		Mockito.verify(eventLogRepository, Mockito.times(1)).save(captor.capture());

		final EventLog actualEvent = captor.getValue();
		Assertions.assertEquals("ANNOTATION_ON_CLASS", actualEvent.getEventName());
	}
}
