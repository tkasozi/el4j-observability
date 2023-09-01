package io.github.tkasozi.endpoint.v1;

import io.github.tkasozi.aspect.SystemMemoryMonitoring;
import io.github.tkasozi.repository.redis.MemoryEventRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true",
		"elf4j.metrics.logging.extra.enabled=true"
})
@AutoConfigureMockMvc
		/* default */class MemoryPerformanceEndpointsTest {
	public static final String OBSERVER_V_1_UTILIZATION_MEMORY = "/observer/v1/utilization/memory";

	@Autowired
	/* default */ MockMvc mvc;

	@MockBean
	/* default */ SystemMemoryMonitoring systemMemMonitoring;

	@MockBean
	/* default */ MemoryEventRepository memoryEventRepository;

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */void allMemoryEndpoint() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UTILIZATION_MEMORY))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */void memoryUsageByTimeOffset() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(String.format("%s/10", OBSERVER_V_1_UTILIZATION_MEMORY)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */void maxMemoryUtilizationEvents() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(String.format("%s/maxSize", OBSERVER_V_1_UTILIZATION_MEMORY)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@WithMockUser
		/* default */void maxMemoryUtilizationEvents403() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(String.format("%s/maxSize", OBSERVER_V_1_UTILIZATION_MEMORY)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
