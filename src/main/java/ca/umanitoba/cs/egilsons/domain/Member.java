package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;
import ca.umanitoba.cs.egilsons.domain.media.Loan;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A member. Members belong to the {@link LibrarySystem} and can interact with {@link Media} and
 * {@link ca.umanitoba.cs.egilsons.domain.resource.Resource} through a {@link Library}.
 */
public class Member implements Comparable<Member> {
    private final String name;
    private final String password;
    private List<Loan> takenOut;
    private List<TimeSlot> bookings;
    private List<String> announcements;

    /**
     * Invariant properties for Member
     */
    private void checkMember() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1,"Member name should have at least 1 symbol.");
        Preconditions.checkNotNull(password, "Password should never be null.");
        Preconditions.checkState(password.length() >= 1,"Password should have at least 1 symbol.");
        Preconditions.checkNotNull(takenOut, "Taken out should never be null.");
        Preconditions.checkNotNull(bookings, "Bookings should never be null.");
        Preconditions.checkNotNull(announcements, "Announcements should never be null.");

        for (Loan loan : takenOut) {
            Preconditions.checkNotNull(loan, "Loans in taken out should never be null.");
        }

        for (TimeSlot slot : bookings) {
            Preconditions.checkNotNull(slot, "Time slots in bookings should never be null.");
        }

        for (String announcement : announcements) {
            Preconditions.checkNotNull(announcement, "Announcements in announcements should never be null.");
        }
    }

    /**
     * Constructor for a member. Receives input for the name and contact information
     *
     * @param name the name of the member
     */
    private Member(String name, String password) {
        this.name = name;
        this.password = password;
        this.takenOut = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.announcements = new ArrayList<>();
        checkMember();
    }

    /**
     * A builder class for a member
     */
    public static class MemberBuilder {
        private String name;
        private String password;

        /**
         * Checks that a name for the member is valid
         *
         * @param name the name of the member
         * @return the member builder
         * @throws InvalidNameException if the name is empty
         */
        public MemberBuilder name(String name) throws InvalidNameException {
            Preconditions.checkNotNull(name, "Name should not be null.");

            if (name.isEmpty()) {
                throw new InvalidNameException();
            }

            this.name = name;
            return this;
        }

        /**
         * Checks that a password for the member is valid
         *
         * @param password the password of the member
         * @return the member builder
         * @throws InvalidPasswordException if the password is empty
         */
        public MemberBuilder password(String password) throws InvalidPasswordException {
            Preconditions.checkNotNull(password, "Password should not be null.");

            if (password.isEmpty()) {
                throw new InvalidPasswordException();
            }

            this.password = password;
            return this;
        }

        /**
         * Creates a member
         *
         * @return the member
         */
        public Member build() {
            return new Member(this.name, this.password);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Loan> getTakenOut() {
        return this.takenOut;
    }

    public List<String> getAnnouncements() {
        return this.announcements;
    }

    /**
     * Checks if the member has overdue media
     *
     * @return if the member has overdue media or not
     */
    public boolean hasOverdueMedia() {
        checkMember();
        boolean overdueMedia = false;
        for (Loan loan : this.takenOut) {
            if (loan.isOverdue()) {
                overdueMedia = true;
            }
        }
        checkMember();
        return overdueMedia;
    }

    /**
     * Compares a member to another member
     *
     * @param other the member being compared
     * @return 0 if the members are equal, 1 if the members are different
     */
    public int compareTo(Member other) {
        checkMember();
        int equal = 1;
        if (this.name.equals(other.getName())) {
            equal = 0;
        }
        checkMember();
        return equal;
    }

    /**
     * Borrows media from the library
     *
     * @param media the media to borrow
     * @return if the media is available or not
     */
    public boolean borrowMedia(Media media) {
        checkMember();
        boolean borrowed = false;
        if (media.takeOutCopy() != null) {
            this.takenOut.add(new Loan(media));
            borrowed = true;
        }
        checkMember();
        return borrowed;
    }

    /**
     * Returns media to the library
     *
     * @param media the media to return
     */
    public void returnMedia(Media media) {
        checkMember();
        boolean found = false;
        int index = 0;
        while (!found && index < this.takenOut.size()) {
            if (this.takenOut.get(0).getMedia() == media) {
                found = true;
                this.takenOut.remove(index);
                media.addCopy();
            }
            index++;
        }
        checkMember();
    }

    /**
     * Books a time slot of a resource
     *
     * @param slot the time slot to book
     * @param resource the resource to book
     */
    public void bookResource(TimeSlot slot, Resource resource) {
        checkMember();
        final int START_HOUR = resource.getMonthBookings().getStartHour();
        if (resource.getMonthBookings().getMonthBookings()[slot.getWeek() - 1][slot.getDay() - 1][slot.getStartHour() - START_HOUR].book()) {
            this.bookings.add(slot);
        }
        checkMember();
    }

    /**
     * Adds a title to announcements
     *
     * @param title the title to add
     */
    public void addAnnouncement(String title) {
        checkMember();
        this.announcements.add(title);
        checkMember();
    }

    /**
     * Removes a title from announcements
     *
     * @param title the title to remove
     */
    public void removeAnnouncement(String title) {
        checkMember();
        this.announcements.remove(title);
        checkMember();
    }
}
