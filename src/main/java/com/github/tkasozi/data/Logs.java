package com.github.tkasozi.data;


import lombok.Getter;

/**
 * List wrapper.
 *
 * @param <T> any {@link java.lang.Iterable}
 */
public class Logs<T> {
	/**
	 * Wrapper to implement SDE.
	 *
	 * @return an object wrapping a collection.
	 */
	@Getter
	private final Iterable<T> items;

	/**
	 * Constructor.
	 *
	 * @param items collection to wrap.
	 */
	public Logs(final Iterable<T> items) {
		this.items = items;
	}
}
