package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import ca.umanitoba.cs.egilsons.tests.TestResults;

/**
 * Testing of a {@link Library}.
 */
public class TestLibrary {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateLibrary();
        testInvalidName();
        testNewAddMedia();
        testExistingAddMedia();
        testAddResource();

        return new TestResults(successes, failures);
    }

    // this is my happy path:
    public void testCreateLibrary() {
        Library.LibraryBuilder builder = new Library.LibraryBuilder();

        try {
            Library library = builder.name("Westwood").build();

            if (!library.getName().equals("Westwood")) {
                fail("Name was not set as expected, got " + library.getName() + " expected Westwood");
            } else {
                pass("All properties in the happy path were set as expected.");
            }
        } catch (Exception e) {
            fail("Exception thrown during happy path inputs.");
            e.printStackTrace();
        }
    }

    public void testInvalidName() {
        Library.LibraryBuilder builder = new Library.LibraryBuilder();

        try {
            builder.name("");
            fail("Should not have succeeded in setting empty string as name.");
        } catch (InvalidNameException e) {
            pass("Successfully rejected empty string.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testNewAddMedia() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            library.addMedia(media);

            if (library.getMedia().get(0).equals(media)) {
                pass("Media successfully added to library.");
            } else {
                fail("Media was not added to library.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testExistingAddMedia() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            library.addMedia(media);
            library.addMedia(media);

            if (library.getMedia().size() == 1 && library.getMedia().get(0).equals(media)) {
                pass("Media successfully added to library.");
            } else {
                fail("Media was not added to existing media.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testAddResource() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Resource resource = new Room();
            library.addResource(resource);

            if (library.getResources().get(0).getNumber() == resource.getNumber()
                    && library.getResources().get(0).getClass() == resource.getClass()) {
                pass("Resource successfully added to library.");
            } else {
                fail("Resource was not added to library.");
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
