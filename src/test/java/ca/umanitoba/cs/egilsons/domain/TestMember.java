package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.Loan;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;
import ca.umanitoba.cs.egilsons.tests.TestResults;

import java.time.LocalDateTime;

/**
 * This is a test suite for the {@link Member} class.
 */

public class TestMember {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateMember();
        testInvalidName();
        testInvalidPassword();
        testHasOverdueMedia();
        testDifferentCompareTo();
        testSameCompareTo();
        testAvailableBorrowMedia();
        testUnavailableBorrowMedia();
        testTakenOutReturnMedia();
        testNotTakenOutReturnMedia();
        testAvailableBookResource();
        testUnavailableBookResource();
        testAddAnnouncement();

        return new TestResults(successes, failures);
    }

    public void testCreateMember() {
        Member.MemberBuilder builder = new Member.MemberBuilder();

        try {
            Member member = builder.name("Sam").password("password").build();

            if (!member.getName().equals("Sam")) {
                fail("Name was not set as expected, got " + member.getName() + " expected Sam");
            } else if (!member.getPassword().equals("password")) {
                fail("Password was not set as expected, got " + member.getPassword() + " expected password");
            } else {
                pass("All properties in the happy path were set as expected.");
            }
        } catch (Exception e) {
            fail("Exception thrown during happy path inputs.");
            e.printStackTrace();
        }
    }

    public void testInvalidName() {
        Member.MemberBuilder builder = new Member.MemberBuilder();

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

    public void testInvalidPassword() {
        Member.MemberBuilder builder = new Member.MemberBuilder();

        try {
            builder.password("");
            fail("Should not have succeeded in setting empty string as password.");
        } catch (InvalidPasswordException e) {
            pass("Successfully rejected empty string.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testHasOverdueMedia() {
        try {
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            Loan loan = new Loan.LoanBuilder().media(media).returnDate(LocalDateTime.now().minusSeconds(1)).build();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            member.getTakenOut().add(loan);

            if (member.hasOverdueMedia()) {
                pass("Member successfully identified overdue media.");
            } else {
                fail("Member should have overdue media");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testDifferentCompareTo() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Member other = new Member.MemberBuilder().name("Other").password("pass").build();

            if (member.compareTo(other) == 1) {
                pass("Members successfully deemed different.");
            } else {
                fail("Members should have been deemed different.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testSameCompareTo() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Member same = new Member.MemberBuilder().name("Sam").password("pass").build();

            if (member.compareTo(same) == 0) {
                pass("Members successfully deemed equal.");
            } else {
                fail("Members should have been equal.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testAvailableBorrowMedia() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Media available = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);

            if (member.borrowMedia(available)) {
                if (member.getTakenOut().get(0).getMedia().equals(available)) {
                    pass("Member successfully taken out available media.");
                } else {
                    fail("Media was not actually taken out.");
                }
            } else {
                fail("Member failed to take out available media.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testUnavailableBorrowMedia() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Media available = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            available.takeOutCopy();

            if (!member.borrowMedia(available)) {
                if (member.getTakenOut().isEmpty()) {
                    pass("Member successfully did not take out unavailable media.");
                } else {
                    fail("Media was taken out despite being unavailable.");
                }
            } else {
                fail("Member took out unavailable media.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testTakenOutReturnMedia() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            member.borrowMedia(media);
            member.returnMedia(media);

            if (member.getTakenOut().isEmpty() && media.getCopies() == 1) {
                pass("Member successfully returned media.");
            } else {
                fail("Member failed to return media.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testNotTakenOutReturnMedia() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.NON_FICTION);
            member.returnMedia(media);

            if (member.getTakenOut().isEmpty() && media.getCopies() == 1) {
                pass("Member successfully did not return media that was not taken out.");
            } else {
                fail("Member returned media that had not been taken out.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testAvailableBookResource() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            TimeSlot timeSlot = new TimeSlot.TimeSlotBuilder().startHour(8).endHour(9).day(1).week(1).build();
            member.bookResource(timeSlot, resource);

            if (member.getBookings().size() == 1) {
                if (resource.getMonthBookings().getTimeSlot(1, 1, 8).isBooked()) {
                    pass("Member successfully booked time slot.");
                } else {
                    fail("Resource time slot was not actually booked.");
                }
            } else {
                fail("Booked time slot was not added to bookings.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testUnavailableBookResource() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            TimeSlot timeSlot = new TimeSlot.TimeSlotBuilder().startHour(8).endHour(9).day(1).week(1).build();
            resource.getMonthBookings().book(1, 1, 8);
            member.bookResource(timeSlot, resource);

            if (member.getBookings().isEmpty()) {
                if (resource.getMonthBookings().getTimeSlot(1, 1, 8).isBooked()) {
                    pass("Member successfully did not book time slot.");
                } else {
                    fail("Already booked resource time slot was booked.");
                }
            } else {
                fail("Already booked time slot was added to bookings.");
            }
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testAddAnnouncement() {
        try {
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            member.addAnnouncement("Title");

            if (member.getAnnouncements().get(0).equals("Title")) {
                pass("Member successfully added title to announcements.");
            } else {
                fail("Title was not added to announcements.");
            }
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
