package io.github.tkasozi.endpoint.v1;

import io.github.tkasozi.domain.redis.EventLog;
import io.github.tkasozi.repository.redis.EventLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
		/* default */class UiPerformanceEndpointsTest {
	public static final String OBSERVER_V_1_UI_PERFORMANCE = "/observer/v1/ui/performance";

	@Autowired
	/* default */ MockMvc mvc;

	@MockBean
	/* default */ EventLogRepository eventLogRepository;

	@Test
	@WithMockUser
		/* default */void logPerformance() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UI_PERFORMANCE)
						.queryParam("pathname", "/aboutUs")
						.queryParam("time", "300"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		final ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
		Mockito.verify(eventLogRepository, Mockito.times(1)).save(captor.capture());
		final var actualEvent = captor.getValue();
		Assertions.assertEquals("UI_METRICS", actualEvent.getEventName());
		Assertions.assertEquals("\"/aboutUs\" took 300 ms to execute", actualEvent.getDescription());
	}

	@Test
		/* default */void logPerformance401() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UI_PERFORMANCE)
						.queryParam("pathname", "/aboutUs")
						.queryParam("time", "300"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
