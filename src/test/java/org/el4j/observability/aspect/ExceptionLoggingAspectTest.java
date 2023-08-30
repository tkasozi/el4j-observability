package org.el4j.observability.aspect;

import java.util.List;

import jakarta.servlet.ServletException;
import org.el4j.observability.MetricsTypeEnum;
import org.el4j.observability.domain.redis.EventLog;
import org.el4j.observability.help_config.DemoController;
import org.el4j.observability.help_config.UtilityClass;
import org.el4j.observability.repository.redis.EventLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/* default */ class ExceptionLoggingAspectTest {

	@Test
		/* default */ void logExceptions() throws Exception {
		TestSecurityContextHolder.setAuthentication(new UsernamePasswordAuthenticationToken("tkasozi", List.of()));
		final EventLogRepository eventLogRepository = Mockito.mock(EventLogRepository.class);
		final ExceptionLoggingAspect exceptionLoggingAspect =
				new ExceptionLoggingAspect(eventLogRepository, 30L);

		final DemoController controllerProxy =
				(DemoController) UtilityClass.getAopProxy(exceptionLoggingAspect, new DemoController())
						.getProxy();

		final MockMvc mockMvc = MockMvcBuilders
				.standaloneSetup(controllerProxy)
				.build();
		// @formatter:off
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/test/throwException"));
		}
		catch (ServletException exception) {
			final ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
			Mockito.verify(eventLogRepository, Mockito.times(1)).save(captor.capture());

			final EventLog actualEvent = captor.getValue();
			Assertions.assertEquals(MetricsTypeEnum.EXCEPTION_EVENT.getValue(), actualEvent.getEventName());
			Assertions.assertEquals(30L, actualEvent.getTtl());
			Assertions.assertEquals("[Method: 'testMethod' failed with the following message: 'Test Exception Aspect works.']",
					actualEvent.getDescription());
			Assertions.assertEquals("tkasozi", actualEvent.getUsername());
			Assertions.assertEquals("[]", actualEvent.getUserAccess());
		}
		// @formatter:on
	}
}
