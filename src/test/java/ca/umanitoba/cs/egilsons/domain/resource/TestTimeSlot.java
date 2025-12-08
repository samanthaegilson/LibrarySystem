package ca.umanitoba.cs.egilsons.domain.resource;

import ca.umanitoba.cs.egilsons.domain.exceptions.*;
import ca.umanitoba.cs.egilsons.tests.TestResults;

/**
 * Testing of a {@link TimeSlot}.
 */
public class TestTimeSlot {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testCreateTimeSlot();
        testInvalidLowerBoundsStartHour();
        testInvalidUpperBoundsStartHour();
        testInvalidLowerBoundsEndHour();
        testInvalidUpperBoundsEndHour();
        testInvalidLowerBoundsDay();
        testInvalidUpperBoundsDay();
        testInvalidLowerBoundsWeek();
        testInvalidUpperBoundsWeek();
        testBook();

        return new TestResults(successes, failures);
    }

    public void testCreateTimeSlot() {
        TimeSlot.TimeSlotBuilder timeSlotBuilder = new TimeSlot.TimeSlotBuilder();

        try {
            TimeSlot timeSlot = timeSlotBuilder.startHour(8).endHour(9).day(1).week(1).build();

            if (timeSlot.getStartHour() == 8) {
                if (timeSlot.getEndHour() == 9) {
                    if (timeSlot.getDay() == 1) {
                        if (timeSlot.getWeek() == 1) {
                            if (!timeSlot.isBooked()) {
                                pass("Time slot created successfully.");
                            } else {
                                fail("New time slot should not be booked.");
                            }
                        } else {
                            fail("Time slot week was not set as expected, got " + timeSlot.getWeek() + " expected 1.");
                        }
                    } else {
                        fail("Time slot day was not set as expected, got " + timeSlot.getDay() + " expected 1.");
                    }
                } else {
                    fail("Time slot end hour was not set as expected, got " + timeSlot.getEndHour() + " expected 9.");
                }
            } else {
                fail("Time slot start hour was not set as expected, got " + timeSlot.getStartHour() + " expected 8.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidLowerBoundsStartHour() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.startHour(7);
            fail("Should not have succeeded in setting start hour smaller than 8.");
        } catch (InvalidStartHourException e) {
            pass("Successfully rejected start hour smaller than 8.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidUpperBoundsStartHour() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.startHour(20);
            fail("Should not have succeeded in setting start hour bigger than 19.");
        } catch (InvalidStartHourException e) {
            pass("Successfully rejected start hour bigger than 19.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidLowerBoundsEndHour() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.endHour(8);
            fail("Should not have succeeded in setting end hour smaller than 9.");
        } catch (InvalidEndHourException e) {
            pass("Successfully rejected end hour smaller than 9.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidUpperBoundsEndHour() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.endHour(21);
            fail("Should not have succeeded in setting end hour bigger than 20.");
        } catch (InvalidEndHourException e) {
            pass("Successfully rejected end hour bigger than 20.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidLowerBoundsDay() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.day(0);
            fail("Should not have succeeded in setting day smaller than 1.");
        } catch (InvalidDayException e) {
            pass("Successfully rejected day smaller than 1.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidUpperBoundsDay() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.day(8);
            fail("Should not have succeeded in setting day bigger than 7.");
        } catch (InvalidDayException e) {
            pass("Successfully rejected day bigger than 7.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidLowerBoundsWeek() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.week(0);
            fail("Should not have succeeded in setting week smaller than 1.");
        } catch (InvalidWeekException e) {
            pass("Successfully rejected week smaller than 1.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testInvalidUpperBoundsWeek() {
        TimeSlot.TimeSlotBuilder builder = new TimeSlot.TimeSlotBuilder();

        try {
            builder.week(5);
            fail("Should not have succeeded in setting week bigger than 4.");
        } catch (InvalidWeekException e) {
            pass("Successfully rejected week bigger than 4.");
        } catch (Exception e) {
            fail("Some other exception was thrown.");
            e.printStackTrace();
        }
    }

    public void testBook() {
        try {
            TimeSlot timeSlot = new TimeSlot.TimeSlotBuilder().startHour(8).endHour(9).day(1).week(1).build();
            if (timeSlot.book()) {
                if (timeSlot.isBooked()) {
                    pass("Time slot successfully booked.");
                } else {
                    fail("Time slot was not actually booked");
                }
            } else {
                fail("Time slot should be available to book.");
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
