package app.utilities;

import java.util.Optional;
import java.util.UUID;

/**	This class serves as utility class for shared reusable methods. */
public final class Utilities {

	/**
	 * This method handles possible exceptions for invalid UUID string representations.
	 * @param uuid Defines the raw UUID string representation.
	 * @return an <b>optional</b> {@link UUID identifier}
	 */
	public static final Optional<UUID> normalizeUUIDString(final String uuid) {
		try {
			return Optional.of(UUID.fromString(uuid));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}
}
