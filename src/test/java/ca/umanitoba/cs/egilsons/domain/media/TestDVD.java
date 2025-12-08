package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.tests.TestResults;

/**
 * Testing of a {@link DVD}.
 */
public class TestDVD {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testEmptyFrontOfWaitlist();
        testFrontFrontOfWaitlist();
        testNotFrontFrontOfWaitlist();
        testDifferentEquals();
        testSameEquals();
        testTakeOutCopy();
        testZeroTakeOutCopy();
        testEmptyAddToWaitlist();
        testExistingAddToWaitlist();

        return new TestResults(successes, failures);
    }

    public void testEmptyFrontOfWaitlist() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);

            if (dvd.frontOfWaitlist(member)) {
                pass("Member is correctly front of empty waitlist.");
            } else {
                fail("Member should be the front of an empty waitlist.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testFrontFrontOfWaitlist() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            dvd.addToWaitlist(member);

            if (dvd.frontOfWaitlist(member)) {
                pass("Member is correctly front of waitlist.");
            } else {
                fail("Member should be the front of waitlist.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testNotFrontFrontOfWaitlist() {
        try {
            Member first = new Member.MemberBuilder().name("First").password("pass").build();
            Member second = new Member.MemberBuilder().name("Second").password("pass").build();
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            dvd.addToWaitlist(first);
            dvd.addToWaitlist(second);

            if (!dvd.frontOfWaitlist(second)) {
                pass("Member is correctly not at the front of waitlist.");
            } else {
                fail("Member should not be the front of waitlist.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testDifferentEquals() {
        try {
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            DVD other = new DVD("Other", "Test", 100, MediaCategory.NON_FICTION);

            if (!dvd.equals(other)) {
                pass("DVD is correctly different from other dvd.");
            } else {
                fail("The dvds should be different.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testSameEquals() {
        try {
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            DVD other = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);

            if (dvd.equals(other)) {
                pass("DVD is correctly equal to other dvd.");
            } else {
                fail("The dvds should be the same.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testTakeOutCopy() {
        try {
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            Media retrieved = dvd.takeOutCopy();

            if (retrieved.equals(dvd)) {
                int copies = dvd.getCopies();
                if (copies == 0) {
                    pass("DVD copy is successfully taken out.");
                } else {
                    fail("DVD copies is not what was expected, got " + copies + " expected 0.");
                }
            } else {
                fail("Retrieved dvd is not the same as the dvd taken out.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testZeroTakeOutCopy() {
        try {
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            dvd.takeOutCopy();
            Media retrieved = dvd.takeOutCopy();

            if (retrieved == null) {
                int copies = dvd.getCopies();
                if (copies == 0) {
                    pass("DVD copy is successfully not taken out.");
                } else {
                    fail("DVD copies is not what was expected, got " + copies + " expected 0.");
                }
            } else {
                fail("Should not have been able to get a copy of a dvd with no copies.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testEmptyAddToWaitlist() {
        try {
            Member first = new Member.MemberBuilder().name("First").password("pass").build();
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            int spot = dvd.addToWaitlist(first);

            if (spot == 1) {
                if (dvd.frontOfWaitlist(first)) {
                    pass("Member is correctly not at the front of waitlist.");
                } else {
                    fail("Member should have been first in empty waitlist.");
                }
            } else {
                fail("Waitlist spot is not what was expected, got " + spot + " expected 1.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testExistingAddToWaitlist() {
        try {
            Member first = new Member.MemberBuilder().name("First").password("pass").build();
            Member second = new Member.MemberBuilder().name("Second").password("pass").build();
            DVD dvd = new DVD("Test", "Test", 100, MediaCategory.NON_FICTION);
            dvd.addToWaitlist(first);
            int spot = dvd.addToWaitlist(second);

            if (spot == 2) {
                Member front = dvd.getWaitlist().remove();
                if (front.compareTo(first) == 0) {
                    Member next = dvd.getWaitlist().remove();
                    if (next.compareTo(second) == 0) {
                        pass("Member is correctly added to the back of the waitlist.");
                    } else {
                        fail("Second of waitlist is not what was expected, got " + next.getName() + " expected Second.");
                    }
                } else {
                    fail("Front of waitlist is not what was expected, got " + front.getName() + " expected First.");
                }
            } else {
                fail("Waitlist spot is not what was expected, got " + spot + " expected 2.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void pass(String message) {
        successes++;
        System.out.println("PASS: " + message);
    }

    private void fail(String message) {
        failures++;
        System.out.println("FAIL: " + message);
    }
}
