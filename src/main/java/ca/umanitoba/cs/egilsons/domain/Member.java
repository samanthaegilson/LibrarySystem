package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.media.Media;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A member. Members belong to the {@link LibrarySystem} and can interact with {@link Media} and
 * {@link ca.umanitoba.cs.egilsons.domain.resource.Resource} through a {@link Library}.
 */
public class Member implements Comparable<Member> {
    private final String name;
    private List<Media> currentMedia;
    private List<String> contactInfo;
    private List<String> constraints;

    /**
     * Invariant properties for a member
     */
    private void checkMember() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1,"Member name should have at least 1 symbol.");
        Preconditions.checkNotNull(currentMedia, "Current Media should never be null.");
        Preconditions.checkNotNull(contactInfo, "Contact Info should never be null.");
        Preconditions.checkNotNull(constraints, "Constraints should never be null.");

        for (Media m:currentMedia) {
            Preconditions.checkNotNull(m, "Media in currentMedia should never be null.");
        }
    }

    /**
     * Constructor for a member. Receives input for the name and contact information
     *
     * @param name the name of the member
     * @param contactInfo the contact information of the member
     */
    public Member(String name, List<String> contactInfo) {
        this.name = name;
        this.currentMedia = new ArrayList<>();
        this.contactInfo = contactInfo;
        this.constraints = new ArrayList<>();
        checkMember();
    }

    public String getName() {
        return this.name;
    }

    public List<Media> getCurrentMedia() {
        return this.currentMedia;
    }

    public List<String> getContactInfo() {
        return this.contactInfo;
    }

    public List<String> getConstraints() {
        return this.constraints;
    }

    public boolean equals(Member other) {
        boolean equal = false;
        if (this.name.equals(other.getName())) {
            equal = true;
        }
        return equal;
    }

    @Override
    public int compareTo(Member other) {
        int equal = 1;
        if (this.name.equals(other.getName())) {
            equal = 0;
        }
        return equal;
    }
}
