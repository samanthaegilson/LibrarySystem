package ca.umanitoba.cs.egilsons.domain;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Media {
    private final String name;
    private final String creator;
    private final MediaFormat format;
    private final MediaCategory category;
    private List<Member> waitlist;
    private int copies;

    private void checkMedia() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1,"Media name should have at least 1 symbol.");
        Preconditions.checkNotNull(creator, "Creator should never be null.");
        Preconditions.checkState(creator.length() >= 1,"Creator name should have at least 1 symbol.");
        Preconditions.checkNotNull(format, "Format should never be null.");
        Preconditions.checkNotNull(category, "Category should never be null.");
        Preconditions.checkNotNull(waitlist, "Waitlist should never be null.");
        Preconditions.checkState(copies >= 0, "Copies should never go below 0.");

        for(Member m:waitlist) {
            Preconditions.checkNotNull(m, "Members in waitlist should never be null");
        }
    }

    public Media(String name, String creator, MediaFormat format, MediaCategory category) {
        this.name = name;
        this.creator = creator;
        this.format = format;
        this.category = category;
        this.waitlist = new ArrayList<>();
        this.copies++;
        checkMedia();
    }

    public String getName() {
        return this.name;
    }

    public String getCreator() {
        return this.creator;
    }

    public MediaFormat getFormat() {
        return this.format;
    }

    public MediaCategory getCategory() {
        return this.category;
    }

    public List<Member> getWaitlist() {
        return this.waitlist;
    }

    public int getCopies() {
        return this.copies;
    }

    public boolean equals(Media m) {
        boolean equal = false;
        if (m.name.equals(this.name) && m.creator.equals(this.creator) && m.format == this.format
                && m.category == this.category) {
            equal = true;
        }
        return equal;
    }

    public void addCopy() {
        this.copies++;
    }

    public boolean takeOut() {
        checkMedia();
        boolean success = false;
        if (this.copies > 0) {
            this.copies--;
            success = true;
        }
        // Should have time left to return somewhere
        checkMedia();
        return success;
    }

    public void returning() {
        checkMedia();
        this.copies++;
        checkMedia();
    }

    public void addToWaitlist(Member member) {
        checkMedia();
        this.waitlist.add(member);
        checkMedia();
    }

}
