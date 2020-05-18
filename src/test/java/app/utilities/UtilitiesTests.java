package app.utilities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public final class UtilitiesTests {

	@Test
	public void testValidUUID() {
		final UUID expected = UUID.randomUUID();
		final Optional<UUID> actual = Utilities.normalizeUUIDString(expected.toString());
		assertTrue("Expected UUID to be present", actual.isPresent());
		assertEquals(expected, actual.get());
	}
	
	@Test
	public void testInValidUUID() {
		final Optional<UUID> actual = Utilities.normalizeUUIDString("INVALID_UUID");
		assertFalse("Expected UUID to be empty", actual.isPresent());
	}
}
