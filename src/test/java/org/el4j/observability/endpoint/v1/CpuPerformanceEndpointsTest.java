package org.el4j.observability.endpoint.v1;

import org.el4j.observability.repository.redis.CpuEventLogRepository;
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
		/* default */class CpuPerformanceEndpointsTest {

	public static final String OBSERVER_V_1_UTILIZATION_CPU = "/observer/v1/utilization/cpu";

	@Autowired
	/* default */ MockMvc mvc;

	@MockBean
	/* default */ CpuEventLogRepository cpuEventLogRepository;

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */void allCpuEndpoint() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UTILIZATION_CPU))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
		/* default */void cpuUsageByTimeOffset() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(String.format("%s/2000", OBSERVER_V_1_UTILIZATION_CPU)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@WithMockUser
		/* default */void allCpuEndpoint403() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(OBSERVER_V_1_UTILIZATION_CPU))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
