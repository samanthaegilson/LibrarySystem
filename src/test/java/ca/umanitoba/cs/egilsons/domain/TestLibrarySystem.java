package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestLibrarySystem {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testNewAddMember();
        testExistingAddMember();
        testInBoundsGetMember();
        testOutOfBoundsGetMember();

        return new TestResults(successes, failures);
    }

    public void testNewAddMember() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();

            if (librarySystem.addMember(member)) {
                if (librarySystem.getMember(0).equals(member)) {
                    pass("Member successfully added to library system.");
                } else {
                    fail("Member added is not the right member.");
                }
            } else {
                fail("Member was not added to library system.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testExistingAddMember() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            librarySystem.addMember(member);

            if (!librarySystem.addMember(member)) {
                if (librarySystem.getMembers().size() == 1) {
                    pass("Member successfully added to library system.");
                } else {
                    fail("Existing member was added a second time.");
                }
            } else {
                fail("Existing member was added again to library system.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInBoundsGetMember() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            librarySystem.addMember(member);
            Member retrieved = librarySystem.getMember(0);

            if (retrieved.equals(member)) {
                if (librarySystem.getMembers().size() == 1) {
                    pass("Member successfully retrieved.");
                } else {
                    fail("Member was removed from library system.");
                }
            } else {
                fail("Member retrieved is not the desired member.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testOutOfBoundsGetMember() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            librarySystem.addMember(member);
            Member retrieved = librarySystem.getMember(2);

            if (retrieved == null) {
                if (librarySystem.getMembers().size() == 1) {
                    pass("Member successfully not retrieved.");
                } else {
                    fail("Member was removed from library system.");
                }
            } else {
                fail("Member should not have been retrieved.");
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
