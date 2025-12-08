package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReviewStarsException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReviewTextException;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestReview {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateReview();
        testInvalidText();
        testInvalidLowerBoundsStars();
        testInvalidUpperBoundsStars();

        return new TestResults(successes, failures);
    }

    // this is my happy path:
    public void testCreateReview() {
        Review.ReviewBuilder builder = new Review.ReviewBuilder();

        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Review review = builder.member(member).media(media).text("Great").stars(4).build();

            if (!review.getMember().equals(member)) {
                fail("Member was not set as expected, got " + review.getMember().getName() + " expected Sam");
            } else if (!review.getMedia().equals(media)) {
                fail("Media was not set as expected, got " + review.getMedia().getTitle() + " expected Test.");
            } else if (!review.getText().equals("Great")) {
                fail("Text was not set as expected, got " + review.getText() + " expected Great.");
            } else if (review.getStars() != 4) {
                fail("Stars was not set as expected, got " + review.getStars() + " expected 4.");
            } else {
                pass("All properties in the happy path were set as expected.");
            }
        } catch (Exception e) {
            fail("Exception thrown during happy path inputs.");
            e.printStackTrace();
        }
    }

    public void testInvalidText() {
        Review.ReviewBuilder builder = new Review.ReviewBuilder();

        try {
            builder.text("");
            fail("Should not have succeeded in setting empty string as text.");
        } catch (InvalidReviewTextException e) {
            pass("Successfully rejected empty string.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidLowerBoundsStars() {
        Review.ReviewBuilder builder = new Review.ReviewBuilder();

        try {
            builder.stars(0);
            fail("Should not have succeeded in setting 0 as stars.");
        } catch (InvalidReviewStarsException e) {
            pass("Successfully rejected 0 for stars.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidUpperBoundsStars() {
        Review.ReviewBuilder builder = new Review.ReviewBuilder();

        try {
            builder.stars(6);
            fail("Should not have succeeded in setting 6 as stars.");
        } catch (InvalidReviewStarsException e) {
            pass("Successfully rejected 6 for stars.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
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
