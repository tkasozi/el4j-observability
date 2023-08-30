package org.el4j.observability.domain;

import lombok.NonNull;

import org.springframework.data.annotation.Id;

/**
 * Base log data.
 */
public class BaseEntity {
	@Id
	private String id;

	/**
	 * All args constructor.
	 *
	 * @param id String formatted.
	 */
	public BaseEntity(final @NonNull String id) {
		this.id = id;
	}

	/**
	 * default constructor.
	 */
	public BaseEntity() {
		// default
	}

	/**
	 * persistence id.
	 *
	 * @return String persistence id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * set id.
	 *
	 * @param id string formatted.
	 */
	public void setId(final @NonNull String id) {
		this.id = id;
	}

	/**
	 * Builder constructor.
	 *
	 * @param builder builder to create from.
	 */
	public BaseEntity(final BaseEntityBuilder<?> builder) {
		this.id = builder.eventId;
	}

	/**
	 * Base builder.
	 *
	 * @param <T> type.
	 */
	public static class BaseEntityBuilder<T extends BaseEntityBuilder<T>> {
		private String eventId;

		/**
		 * id.
		 *
		 * @param id String formatted.
		 * @return Builder.
		 */
		@SuppressWarnings({"unchecked", "PMD.ShortMethodName"})
		public T id(final String id) {
			this.eventId = id;
			return (T) this;
		}
	}
}
