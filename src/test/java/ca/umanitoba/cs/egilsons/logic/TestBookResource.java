package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidDayException;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidHourException;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidSlotAmountException;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidWeekException;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import ca.umanitoba.cs.egilsons.tests.TestResults;

import java.util.List;

/**
 * Testing of {@link BookResource}.
 */
public class TestBookResource {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testInvalidWeekFilterAmount();
        testInvalidDayFilterAmount();
        testInvalidHourFilterAmount();
        testInvalidSlotFilterAmount();
        testValidFilterAmount();
        testInvalidWeekFilterWeek();
        testValidFilterWeek();
        testInvalidWeekFilterDay();
        testInvalidDayFilterDay();
        testValidFilterDay();
        testBookResource();

        return new TestResults(successes, failures);
    }

    private void testInvalidWeekFilterAmount() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterAmount(0, 1, 8, 60, resource);
            } catch (InvalidWeekException e) {
                pass("Not able to filter a week 0.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testInvalidDayFilterAmount() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterAmount(1, 0, 8, 60, resource);
            } catch (InvalidDayException e) {
                pass("Not able to filter a day 0.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testInvalidHourFilterAmount() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterAmount(1, 1, 5, 60, resource);
            } catch (InvalidHourException e) {
                pass("Not able to filter a start hour smaller than 8 or bigger than 19.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testInvalidSlotFilterAmount() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterAmount(1, 1, 8, 0, resource);
            } catch (InvalidSlotAmountException e) {
                pass("Not able to filter 0 time slots.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testValidFilterAmount() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            List<TimeSlot> slots = bookResource.filterAmount(1, 1, 8, 10, resource);
            if (slots.size() <= 10) {
                if (slots.get(0).getWeek() == 1) {
                    if (slots.get(0).getDay() == 1) {
                        if (slots.get(0).getStartHour() == 8) {
                            if (slots.get(0).getEndHour() == 9) {
                                pass("Slots are successfully filtered.");
                            } else {
                                fail("First slot end hour is not what was expected, got " + slots.get(0).getEndHour()
                                        + " expected 9.");
                            }
                        } else {
                            fail("First slot start hour is not what was expected, got " + slots.get(0).getStartHour()
                                    + " expected 8.");
                        }
                    } else {
                        fail("First slot day is not what was expected, got " + slots.get(0).getDay() + " expected 1.");
                    }
                } else {
                    fail("First slot week is not what was expected, got " + slots.get(0).getWeek() + " expected 1.");
                }
            } else {
                fail("Amount of slots is not what was expected, got " + slots.size() + " expected 10.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testInvalidWeekFilterWeek() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterWeek(0, resource);
            } catch (InvalidWeekException e) {
                pass("Not able to filter a week 0.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testValidFilterWeek() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            List<TimeSlot> slots = bookResource.filterWeek(1, resource);

            if (slots.size() <= 84) {
                if (slots.get(0).getWeek() == 1) {
                    if (slots.get(0).getDay() == 1) {
                        if (slots.get(0).getStartHour() == 8) {
                            if (slots.get(0).getEndHour() == 9) {
                                pass("Slots are successfully filtered.");
                            } else {
                                fail("First slot end hour is not what was expected, got " + slots.get(0).getEndHour()
                                        + " expected 9.");
                            }
                        } else {
                            fail("First slot start hour is not what was expected, got " + slots.get(0).getStartHour()
                                    + " expected 8.");
                        }
                    } else {
                        fail("First slot day is not what was expected, got " + slots.get(0).getDay() + " expected 1.");
                    }
                } else {
                    fail("First slot week is not what was expected, got " + slots.get(0).getWeek() + " expected 1.");
                }
            } else {
                fail("Amount of slots is not what was expected, got " + slots.size() + " expected 84.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testInvalidWeekFilterDay() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterDay(0, 1, resource);
            } catch (InvalidWeekException e) {
                pass("Not able to filter a week 0.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testInvalidDayFilterDay() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            try {
                bookResource.filterDay(1, 0, resource);
            } catch (InvalidDayException e) {
                pass("Not able to filter a day 0.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testValidFilterDay() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });
            List<TimeSlot> slots = bookResource.filterDay(1, 1, resource);
            if (slots.size() <= 12) {
                if (slots.get(0).getWeek() == 1) {
                    if (slots.get(0).getDay() == 1) {
                        if (slots.get(0).getStartHour() == 8) {
                            if (slots.get(0).getEndHour() == 9) {
                                pass("Slots are successfully filtered.");
                            } else {
                                fail("First slot end hour is not what was expected, got " + slots.get(0).getEndHour()
                                        + " expected 9.");
                            }
                        } else {
                            fail("First slot start hour is not what was expected, got " + slots.get(0).getStartHour()
                                    + " expected 8.");
                        }
                    } else {
                        fail("First slot day is not what was expected, got " + slots.get(0).getDay() + " expected 1.");
                    }
                } else {
                    fail("First slot week is not what was expected, got " + slots.get(0).getWeek() + " expected 1.");
                }
            } else {
                fail("Amount of slots is not what was expected, got " + slots.size() + " expected 12.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void testBookResource() {
        try {
            LibrarySystem librarySystem = new LibrarySystem();
            Member member = new Member.MemberBuilder().name("Sam").password("pass").build();
            Resource resource = new Room();
            TimeSlot timeSlot = resource.getMonthBookings().getTimeSlot(1, 1, 8);
            BookResource bookResource = new BookResource(librarySystem, member, new LibrarySystemPersistence() {
                @Override
                public void saveLibrarySystem(LibrarySystem librarySystem) {
                }

                @Override
                public LibrarySystem loadLibrarySystem() {
                    return null;
                }
            });

            bookResource.bookResource(timeSlot, resource);
            if (timeSlot.isBooked()) {
                pass("Time slot successfully booked.");
            } else {
                fail("Time slot was not booked.");
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
