package ca.umanitoba.cs.egilsons.tests;

import ca.umanitoba.cs.egilsons.domain.TestLibrary;
import ca.umanitoba.cs.egilsons.domain.TestLibrarySystem;
import ca.umanitoba.cs.egilsons.domain.TestMember;
import ca.umanitoba.cs.egilsons.domain.TestReview;
import ca.umanitoba.cs.egilsons.domain.map.TestMap;
import ca.umanitoba.cs.egilsons.domain.media.TestBook;
import ca.umanitoba.cs.egilsons.domain.media.TestDVD;
import ca.umanitoba.cs.egilsons.domain.media.TestLoan;
import ca.umanitoba.cs.egilsons.domain.resource.TestBooking;
import ca.umanitoba.cs.egilsons.domain.resource.TestTimeSlot;
import ca.umanitoba.cs.egilsons.domain.stack.TestStack;
import ca.umanitoba.cs.egilsons.logic.*;

public class TestHarness {
    private static int successes;
    private static int failures;

    public static void main(String[] args) {
        System.out.println("Test Harness");

        // Domain tests
        System.out.println("Stack:");
        TestResults stackResults = new TestStack().runTests();
        successes += stackResults.successes();
        failures += stackResults.failures();
        System.out.println("Map:");
        TestResults mapResults = new TestMap().runTests();
        successes += mapResults.successes();
        failures += mapResults.failures();
        System.out.println("Book:");
        TestResults bookResults = new TestBook().runTests();
        successes += bookResults.successes();
        failures += bookResults.failures();
        System.out.println("DVD:");
        TestResults dvdResults = new TestDVD().runTests();
        successes += dvdResults.successes();
        failures += dvdResults.failures();
        System.out.println("Loan:");
        TestResults loanResults = new TestLoan().runTests();
        successes += loanResults.successes();
        failures += loanResults.failures();
        System.out.println("Booking:");
        TestResults bookingResults = new TestBooking().runTests();
        successes += bookingResults.successes();
        failures += bookingResults.failures();
        System.out.println("Time slot:");
        TestResults timeSlotResults = new TestTimeSlot().runTests();
        successes += timeSlotResults.successes();
        failures += timeSlotResults.failures();
        System.out.println("Library:");
        TestResults libraryResults = new TestLibrary().runTests();
        successes += libraryResults.successes();
        failures += libraryResults.failures();
        System.out.println("Library system:");
        TestResults librarySystemResults = new TestLibrarySystem().runTests();
        successes += librarySystemResults.successes();
        failures += librarySystemResults.failures();
        System.out.println("Member:");
        TestResults memberResults = new TestMember().runTests();
        successes += memberResults.successes();
        failures += memberResults.failures();
        System.out.println("Review:");
        TestResults reviewResults = new TestReview().runTests();
        successes += reviewResults.successes();
        failures += reviewResults.failures();
        // Logic layer tests
        System.out.println("Book resource:");
        TestResults bookResourceResults = new TestBookResource().runTests();
        successes += bookResourceResults.successes();
        failures += bookResourceResults.failures();
        System.out.println("Borrow media:");
        TestResults borrowMediaResults = new TestBorrowMedia().runTests();
        successes += borrowMediaResults.successes();
        failures += borrowMediaResults.failures();
        System.out.println("Find item:");
        TestResults findItemResults = new TestFindItem().runTests();
        successes += findItemResults.successes();
        failures += findItemResults.failures();
        System.out.println("Return media:");
        TestResults returnMediaResults = new TestReturnMedia().runTests();
        successes += returnMediaResults.successes();
        failures += returnMediaResults.failures();
        System.out.println("Sign in:");
        TestResults signInResults = new TestSignIn().runTests();
        successes += signInResults.successes();
        failures += signInResults.failures();

        System.out.printf("Total tests: %d\n", successes + failures);
        System.out.printf("\tSuccesses: %d\n", successes);
        System.out.printf("\tFailures: %d\n", failures);

        if (failures > 0) {
            System.out.println("There were test failures.");
        } else {
            System.out.println("All tests passed!");
        }
    }
}
