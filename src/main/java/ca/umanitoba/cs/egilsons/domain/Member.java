package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.media.Media;
import com.google.common.base.Preconditions;

/**
 * A member. Members belong to the {@link LibrarySystem} and can interact with {@link Media} and
 * {@link ca.umanitoba.cs.egilsons.domain.resource.Resource} through a {@link Library}.
 */
public class Member implements Comparable<Member> {
    private final String name;

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
    public Member(String name) {
        this.name = name;
        checkMember();
    }

    public String getName() {
        return this.name;
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
}
