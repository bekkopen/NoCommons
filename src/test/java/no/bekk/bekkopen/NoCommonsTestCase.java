package no.bekk.bekkopen;

import static org.junit.Assert.assertTrue;

/**
 * Base TestCase class that provides a helper method for asserting error message in Exception.
 */
public abstract class NoCommonsTestCase {

	protected void assertMessageContains(IllegalArgumentException e, String message) {
		assertTrue(e.getMessage().contains(message));
	}

}
