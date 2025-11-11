package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;
import ca.umanitoba.cs.egilsons.domain.media.Media;
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
    private List<Media> takenOut;
    private List<TimeSlot> bookings;
    private List<String> announcements; // Add to class invariants!!!
    // private List<String> constraints;
    // contact info?

    /**
     * Invariant properties for Member
     */
    private void checkMember() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1,"Member name should have at least 1 symbol.");
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
        this.announcements = new ArrayList<>();
        checkMember();
    }

    public static class MemberBuilder {
        private String name;
        private String password;
        private List<Media> takenOut;

        public MemberBuilder name(String name) throws InvalidNameException {
            Preconditions.checkNotNull(name, "Name should not be null.");

            if (name.isEmpty()) {
                throw new InvalidNameException();
            }

            this.name = name;
            return this;
        }

        public MemberBuilder password(String password) throws InvalidPasswordException {
            Preconditions.checkNotNull(password, "Password should not be null.");

            if (password.isEmpty()) {
                throw new InvalidPasswordException();
            }

            this.password = password;
            return this;
        }

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

    public List<Media> getTakenOut() {
        return this.takenOut;
    }

    public List<String> getAnnouncements() {
        return this.announcements;
    }

    /**
     * Compares a member to another member
     *
     * @param other the member being compared
     * @return 0 if the members are equal, 1 if the members are different
     */
    public int compareTo(Member other) {
        int equal = 1;
        if (this.name.equals(other.getName())) {
            equal = 0;
        }
        return equal;
    }

    public boolean borrowMedia(Media media) {
        checkMember();
        boolean borrowed = false;
        if (media.takeOutCopy() != null) {
            this.takenOut.add(media);
            borrowed = true;
        }
        checkMember();
        return borrowed;
    }

    public void returnMedia(Media media) {
        checkMember();
        media.returnCopy();
        this.takenOut.remove(media); // Should i check this media is in takenOut???
        checkMember();
    }

    public void addAnnouncement(String title) {
        this.announcements.add(title);
    }
}
