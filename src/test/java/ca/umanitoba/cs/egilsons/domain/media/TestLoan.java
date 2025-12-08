package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReturnDateException;
import ca.umanitoba.cs.egilsons.tests.TestResults;

import java.time.LocalDateTime;

/**
 * Testing of a {@link Loan}.
 */
public class TestLoan {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateLoan();
        testInvalidReturnDate();
        testOverdueIsOverdue();
        testNotOverdueIsOverdue();

        return new TestResults(successes, failures);
    }

    public void testCreateLoan() {
        Loan.LoanBuilder loanBuilder = new Loan.LoanBuilder();

        try {
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Loan loan = loanBuilder.media(media).build();

            if (loan.getMedia().equals(media)) {
                if (loan.getReturnDate().isBefore(LocalDateTime.now().plusSeconds(30))) {
                    pass("Loan created successfully.");
                } else {
                    System.out.println(loan.getReturnDate());
                    fail("Loan should return date should be 30 seconds from now.");
                }
            } else {
                fail("Loan media was not set as expected, got " + loan.getMedia().getTitle() + " expected Test.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidReturnDate() {
        Loan.LoanBuilder builder = new Loan.LoanBuilder();

        try {
            builder.returnDate(LocalDateTime.now().plusSeconds(31));
            fail("Should not have succeeded in setting return date past 30 seconds.");
        } catch (InvalidReturnDateException e) {
            pass("Successfully rejected return date past 30 seconds.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testOverdueIsOverdue() {
        try {
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Loan loan = new Loan.LoanBuilder().media(media).returnDate(LocalDateTime.now().minusSeconds(1)).build();
            if (loan.isOverdue()) {
                pass("Loan is successfully overdue.");
            } else {
                fail("Loan should be overdue.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testNotOverdueIsOverdue() {
        try {
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Loan loan = new Loan.LoanBuilder().media(media).returnDate(LocalDateTime.now().plusSeconds(20)).build();
            if (!loan.isOverdue()) {
                pass("Loan is successfully not overdue.");
            } else {
                fail("Loan should not be overdue.");
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
