package boss.nocommons;

import junit.framework.TestCase;

/**
 * Base TestCase class that provides helper methods.
 * @author per k. mengshoel
 */
public abstract class NoCommonsTestCase extends TestCase {

    protected void assertMessageContains(IllegalArgumentException e,
            String message) {
        assertTrue(e.getMessage().indexOf(message) != -1);
    }

}
