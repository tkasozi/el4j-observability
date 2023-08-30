package org.el4j.observability;

import lombok.experimental.UtilityClass;
import org.el4j.observability.data.LoggedUserDetails;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class ResourceUtility {
	/**
	 * This is the default view or the html file generated from the ui.
	 */
	public static final String DEFAULT_VIEW = "dashboard";

	/**
	 * The UI assets are stored in this location.
	 */
	public static final String STATIC_RESOURCE_LOCATION = "el4j-logger";

	/**
	 * All the rest endpoints in v1 will be prepended by this pattern.
	 */
	public static final String V1_API_PATTERN = "/observer/v1/**";

	/**
	 * Prefix to be use in logs.
	 */
	public static final String TIME_PROFILE_PREFIX = "TIME_PROFILE";

	/**
	 * Special access observer endpoint to allow non admin authenticated user to have their events tracked.
	 */
	public static final String OBSERVER_V_1_UI_PERFORMANCE = "/observer/v1/ui/performance";

	/**
	 * Creates a simple user detail based on the authenticated user.
	 * @return Simple user detail.
	 */
	public static LoggedUserDetails getLoggedUserDetails() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final LoggedUserDetails loggedUserDetails = new LoggedUserDetails();

		// This should handle all types of authentication (most likely it won't match other authentication objects)
		if (authentication instanceof UsernamePasswordAuthenticationToken) {
			loggedUserDetails.setUsername(authentication.getName());
			loggedUserDetails.setUserAccess(authentication.getAuthorities().stream()
					.map(org.springframework.security.core.GrantedAuthority::getAuthority).toList()
					.toString());
		}
		return loggedUserDetails;
	}
}
