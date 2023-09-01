package com.github.tkasozi.data;

import lombok.Getter;
import lombok.Setter;

/**
 * SimpleUser details attach to each log saved.
 */
public class LoggedUserDetails {
	/**
	 * The username extracted from authenticated user details.
	 *
	 * @param username the extracted name
	 * @return string user name.
	 */
	@Getter
	@Setter
	private String username;
	/**
	 * The user authorities extracted from authenticated user details.
	 *
	 * @param userAccess type string
	 * @return list of authorities converted to string
	 */
	@Getter
	@Setter
	private String userAccess;
}
