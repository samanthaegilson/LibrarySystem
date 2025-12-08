package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.*;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import ca.umanitoba.cs.egilsons.tests.TestResults;

import java.util.List;

/**
 * Testing of {@link BorrowMedia}.
 */
public class TestBorrowMedia {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testFilterMedia();
        testAvailableBorrowMedia();
        testUnavailableBorrowMedia();
        testAddToWaitlist();

        return new TestResults(successes, failures);
    }

    private void testFilterMedia() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media1 = new Book("Book Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            Media media2 = new DVD("DVD Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            Media media3 = new Book("Book 3", "Test", 100, MediaCategory.SCIENCE_FICTION);
            library.addMedia(media1);
            library.addMedia(media2);
            library.addMedia(media3);
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            LibrarySystem librarySystem = new LibrarySystem();
            librarySystem.addLibrary(library);
            BorrowMedia borrowMedia = new BorrowMedia(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            List<Media> filtered = borrowMedia.filterMedia("Test", true);
            if (filtered.size() == 1) {
                if (filtered.get(0).equals(media1)) {
                    pass("Media filtered successfully.");
                } else {
                    fail("Filtered media is not what was expected, got " + filtered.get(0).getTitle()
                            + " expected Book Test.");
                }
            } else {
                fail("Filtered amount is not what was expected, got " + filtered.size() + " expected 1.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testAvailableBorrowMedia() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            library.addMedia(media);
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            LibrarySystem librarySystem = new LibrarySystem();
            librarySystem.addLibrary(library);
            BorrowMedia borrowMedia = new BorrowMedia(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            if (borrowMedia.borrowMedia(media)) {
                if (media.getCopies() == 0) {
                    pass("Media successfully borrowed.");
                } else {
                    fail("Media copies is not what was expected, got " + media.getCopies() + " expected 0.");
                }
            } else {
                fail("Media should have been available to be borrowed.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testUnavailableBorrowMedia() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            library.addMedia(media);
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Member first = new Member.MemberBuilder().name("First").password("pass").build();
            media.addToWaitlist(first);
            LibrarySystem librarySystem = new LibrarySystem();
            librarySystem.addLibrary(library);
            BorrowMedia borrowMedia = new BorrowMedia(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            if (!borrowMedia.borrowMedia(media)) {
                if (media.getCopies() == 1) {
                    pass("Media successfully not borrowed.");
                } else {
                    fail("Media copies is not what was expected, got " + media.getCopies() + " expected 1.");
                }
            } else {
                fail("Media should not have been available to be borrowed.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testAddToWaitlist() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            LibrarySystem librarySystem = new LibrarySystem();
            librarySystem.addLibrary(library);
            BorrowMedia borrowMedia = new BorrowMedia(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            int spot = borrowMedia.addToWaitlist(media);
            if (spot == 1) {
                Member first = media.getWaitlist().element();
                if (first.compareTo(member) == 0) {
                    pass("Member successfully added to waitlist.");
                } else {
                    fail("Waitlist member is not what was expected, got " + first.getName() + " expected Sam.");
                }
            } else {
                fail("Waitlist spot is not what was expected, got " + spot + " expected 1.");
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
