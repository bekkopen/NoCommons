package no.bekk.bekkopen;

import static org.junit.Assert.assertTrue;

/**
 * Base TestCase class that provides helper methods.
 * 
 * @author per k. mengshoel
 */
public abstract class NoCommonsTestCase {

	protected void assertMessageContains(IllegalArgumentException e, String message) {
		assertTrue(e.getMessage().indexOf(message) != -1);
	}

}
