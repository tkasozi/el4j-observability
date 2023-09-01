package com.github.tkasozi.endpoint.v1;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tkasozi.repository.redis.EventLogRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
		/* default */ class MetricsEndpointsTest {
	public static final String OBSERVER_V_1_UTILIZATION_USER = "/observer/v1/utilization/user";

	@Autowired
	/* default */ MockMvc mvc;

	@MockBean
	/* default */ EventLogRepository mockEventLogRepository;

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */ void allEventsEndpointTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UTILIZATION_USER))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.items").isEmpty());
	}

	@Test
	@WithMockUser
		/* default */ void allEventsEndpointTest403() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UTILIZATION_USER))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */ void deleteEndpointTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.delete(OBSERVER_V_1_UTILIZATION_USER)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(List.of())))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.items").isEmpty());
	}
}
