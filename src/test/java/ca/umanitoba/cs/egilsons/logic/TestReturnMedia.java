package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestReturnMedia {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testReturnMedia();
        testWriteReview();

        return new TestResults(successes, failures);
    }

    private void testReturnMedia() {
        try {
            Media media = new Book("Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Member next = new Member.MemberBuilder().name("Next").password("pass").build();
            media.addToWaitlist(next);
            LibrarySystem librarySystem = new LibrarySystem();
            ReturnMedia returnMedia = new ReturnMedia(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            returnMedia.returnMedia(media);
            if (media.getCopies() == 1) {
                if (next.getAnnouncements().get(0).equals(media.getTitle())) {
                    pass("Media successfully returned.");
                } else {
                    fail("Next member announcements is not what was expected, got " + next.getAnnouncements().get(0)
                            + " expected Test.");
                }
            } else {
                fail("Media copies is not what was expected, got " + media.getCopies() + " expected 1.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testWriteReview() {
        try {
            Media media = new Book("Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Review review = new Review.ReviewBuilder().member(member).media(media).text("text").stars(3).build();
            LibrarySystem librarySystem = new LibrarySystem();
            ReturnMedia returnMedia = new ReturnMedia(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            returnMedia.writeReview(media, review);
            Review retrieved = media.getReviews().get(0);
            if (retrieved != null) {
                if (retrieved.getMember().compareTo(member) == 0) {
                    if (retrieved.getMedia().equals(media)) {
                        if (retrieved.getText().equals("text")) {
                            if (retrieved.getStars() == 3) {
                                pass("Review successfully written.");
                            } else {
                                fail("Review stars is not what was expected, got " + retrieved.getStars()
                                        + " expected 3.");
                            }
                        } else {
                            fail("Review text is not what was expected, got " + retrieved.getText()
                                    + " expected text.");
                        }
                    } else {
                        fail("Review media is not what was expected, got " + retrieved.getMedia().getTitle()
                                + " expected Test.");
                    }
                } else {
                    fail("Review member is not what was expected, got " + retrieved.getMember().getName()
                            + " expected Sam.");
                }
            } else {
                fail("Review should not be null.");
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
