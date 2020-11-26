package no.bekk.bekkopen;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Base class that provides a helper method for asserting error message in Exception.
 */
public abstract class NoCommonsBase {

	protected void assertMessageContains(IllegalArgumentException e, String message) {
		assertTrue(e.getMessage().contains(message));
	}

}
