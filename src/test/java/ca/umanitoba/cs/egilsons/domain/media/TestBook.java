package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestBook {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateBook();
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

    public void testCreateBook() {
        try {
            Book book = new Book("Title", "Author", 100, MediaCategory.NON_FICTION);

            if (book.getTitle().equals("Title")) {
                if (book.getAuthor().equals("Author")) {
                    if (book.getPages() == 100) {
                        if (book.getCategory() == MediaCategory.NON_FICTION) {
                            if (book.getCopies() == 1) {
                                pass("Book created successfully.");
                            } else {
                                fail("Book copies is not what was expected, got " + book.getCopies() + " expected 1.");
                            }
                        } else {
                            fail("Book category is not what was expected, got " + book.getCategory()
                                    + " expected NON-FICTION.");
                        }
                    } else {
                        fail("Book pages is not what was expected, got " + book.getPages() + " expected 100.");
                    }
                } else {
                    fail("Book author is not what was expected, got " + book.getAuthor() + " expected Author.");
                }
            } else {
                fail("Book title is not what was expected, got " + book.getTitle() + " expected Title.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testEmptyFrontOfWaitlist() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);

            if (book.frontOfWaitlist(member)) {
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
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            book.addToWaitlist(member);

            if (book.frontOfWaitlist(member)) {
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
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            book.addToWaitlist(first);
            book.addToWaitlist(second);

            if (!book.frontOfWaitlist(second)) {
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
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Book other = new Book("Other", "Test", 100, MediaCategory.NON_FICTION);

            if (!book.equals(other)) {
                pass("Book is correctly different from other book.");
            } else {
                fail("The books should be different.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testSameEquals() {
        try {
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Book other = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);

            if (book.equals(other)) {
                pass("Book is correctly equal to other book.");
            } else {
                fail("The books should be the same.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testTakeOutCopy() {
        try {
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Media retrieved = book.takeOutCopy();

            if (retrieved.equals(book)) {
                int copies = book.getCopies();
                if (copies == 0) {
                    pass("Book copy is successfully taken out.");
                } else {
                    fail("Book copies is not what was expected, got " + copies + " expected 0.");
                }
            } else {
                fail("Retrieved book is not the same as the book taken out.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testZeroTakeOutCopy() {
        try {
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            book.takeOutCopy();
            Media retrieved = book.takeOutCopy();

            if (retrieved == null) {
                int copies = book.getCopies();
                if (copies == 0) {
                    pass("Book copy is successfully not taken out.");
                } else {
                    fail("Book copies is not what was expected, got " + copies + " expected 0.");
                }
            } else {
                fail("Should not have been able to get a copy of a book with no copies.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testEmptyAddToWaitlist() {
        try {
            Member first = new Member.MemberBuilder().name("First").password("pass").build();
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            int spot = book.addToWaitlist(first);

            if (spot == 1) {
                if (book.frontOfWaitlist(first)) {
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
            Book book = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            book.addToWaitlist(first);
            int spot = book.addToWaitlist(second);

            if (spot == 2) {
                Member front = book.getWaitlist().remove();
                if (front.compareTo(first) == 0) {
                    Member next = book.getWaitlist().remove();
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