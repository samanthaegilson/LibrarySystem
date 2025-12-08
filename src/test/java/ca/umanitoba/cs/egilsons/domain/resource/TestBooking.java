package ca.umanitoba.cs.egilsons.domain.resource;

import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestBooking {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testValidGetTimeSlot();
        testInvalidGetTimeSlot();
        testBook();

        return new TestResults(successes, failures);
    }

    public void testValidGetTimeSlot() {
        try {
            Booking booking = new Booking();
            TimeSlot timeSlot = booking.getTimeSlot(1, 1, 10);

            if (timeSlot != null) {
                if (timeSlot.getStartHour() == 10) {
                    if (timeSlot.getEndHour() == 11) {
                        if (timeSlot.getDay() == 1) {
                            if (timeSlot.getWeek() == 1) {
                                pass("");
                            } else {
                                fail("Week is not what was expected, got " + timeSlot.getWeek() + " expected 1.");
                            }
                        } else {
                            fail("Day is not what was expected, got " + timeSlot.getDay() + " expected 1.");
                        }
                    } else {
                        fail("End hour is not what was expected, got " + timeSlot.getEndHour() + " expected 11.");
                    }
                } else {
                    fail("Start hour is not what was expected, got " + timeSlot.getStartHour() + " expected 10.");
                }
            } else {
                fail("Time slot should not be null.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidGetTimeSlot() {
        try {
            Booking booking = new Booking();
            TimeSlot timeSlot = booking.getTimeSlot(1, 1, 1);

            if (timeSlot == null) {
                pass("Invalid time slot successfully denied.");
            } else {
                fail("Time slot should not have been retrieved.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testBook() {
        try {
            Booking booking = new Booking();
            booking.book(1, 1, 10);
            TimeSlot timeSlot = booking.getTimeSlot(1, 1, 10);

            if (timeSlot != null) {
                if (timeSlot.isBooked()) {
                    pass("Time slot successfully booked.");
                } else {
                    fail("Time slot should have been booked.");
                }
            } else {
                fail("Time slot should not be null.");
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
