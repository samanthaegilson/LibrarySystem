package ca.umanitoba.cs.egilsons.domain.map;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Computer;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestMap {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateMap();
        testSetMediaCoordinates();
        testSetResourceCoordinates();

        return new TestResults(successes, failures);
    }

    public void testCreateMap() {
        try {
            Library library = new Library.LibraryBuilder().name("Test").build();
            Resource computer = new Computer();
            Resource room = new Room();
            library.addResource(computer);
            library.addResource(room);
            Map map = new Map(library);
            if (map.getDisplay().length == 43) {
                if (map.getDisplay()[0].length == 10) {
                    if (map.getKiosk().row() == 1) {
                        if (map.getKiosk().column() == 28) {
                            pass("Map created successfully.");
                        } else {
                            fail("Kiosk column was not set as expected, got " + map.getKiosk().column()
                                    + " expected 28.");
                        }
                    } else {
                        fail("Kiosk row was not set as expected, got " + map.getKiosk().row() + " expected 1.");
                    }
                } else {
                    fail("Map display column length was not set as expected, got " + map.getDisplay()[0].length
                            + " expected 10.");
                }
            } else {
                fail("Map display row length was not set as expected, got " + map.getDisplay().length + " expected 43.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testSetMediaCoordinates() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Map map = new Map(library);
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Map.setMediaCoordinates(media);
            Coordinates coordinates = media.getCoordinates();

            if (coordinates != null && coordinates.row() >= 0 && coordinates.row() < map.getDisplay().length
                    && coordinates.column() >= 0 && coordinates.column() < map.getDisplay()[0].length) {
                pass("Media coordinates successfully added.");
            } else {
                fail("Media coordinates were not properly added.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testSetResourceCoordinates() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Resource resource = new Room();
            library.addResource(resource);
            Map map = new Map(library);
            Map.setResourceCoordinates(resource);
            Coordinates coordinates = resource.getCoordinates();

            if (coordinates != null && coordinates.row() >= 0 && coordinates.row() < map.getDisplay().length
                    && coordinates.column() >= 0 && coordinates.column() < map.getDisplay()[0].length) {
                pass("Resource coordinates successfully added.");
            } else {
                fail("Resource coordinates were not properly added.");
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
