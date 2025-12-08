package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestSignIn {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCheckUser();
        testMakeAccount();

        return new TestResults(successes, failures);
    }

    public void testCheckUser() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            librarySystem.addMember(member);
            SignIn signIn = new SignIn(librarySystem, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            Member received = signIn.checkUser("Sam", "pass");
            if (received != null) {
                if (received.compareTo(member) == 0) {
                    pass("Member successfully found.");
                } else {
                    fail("Member is not what was expected, got " + received.getName() + " expected Sam.");
                }
            } else {
                fail("Account should have been found.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testMakeAccount() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            SignIn signIn = new SignIn(librarySystem, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            if (signIn.makeAccount(member)) {
                if (librarySystem.getMember(0).compareTo(member) == 0) {
                    pass("Account successfully made.");
                } else {
                    fail("Member created is not what was expected, got " + librarySystem.getMember(0).getName()
                            + " expected Sam.");
                }
            } else {
                fail("Account should have been found.");
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
