package ca.umanitoba.cs.egilsons.domain;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * The library system. The library system contains all the {@link Library} and {@link Member}.
 */
public class LibrarySystem {
    private List<Library> libraries;
    private TreeSet<Member> members;

    /**
     * Invariant properties for LibrarySystem
     */
    private void checkLibrarySystem() {
        Preconditions.checkNotNull(libraries, "Libraries should never be null.");
        Preconditions.checkNotNull(members, "Members should never be null.");

        for (Library library : libraries) {
            Preconditions.checkNotNull(library, "Libraries in libraries should never be null.");
        }

        for (Member member : members) {
            Preconditions.checkNotNull(member, "Members in members should never be null.");
        }
    }

    /**
     * Default constructor for the Library System
     */
    public LibrarySystem() {
        this.libraries = new ArrayList<>();
        this.members = new TreeSet<>();
        checkLibrarySystem();
    }

    public List<Library> getLibraries() {
        return this.libraries;
    }

    public TreeSet<Member> getMembers() {
        return this.members;
    }

    /**
     * Adds a library to the library system
     *
     * @param library the library being added
     */
    public void addLibrary(Library library) {
        this.libraries.add(library);
    }

    /**
     * Adds a member to the library system
     *
     * @param member the member being added
     * @return if the member has been added or not
     */
    public boolean addMember(Member member) {
        return this.members.add(member);
    }

    /**
     * Gets a member from the library system's members
     *
     * @param index the index of the member
     * @return the member from the index
     */
    public Member getMember(int index) {
        Member member = null;
        int count = 0;
        // Goes through members until the count matches the index
        for (Member m : this.members) {
            if (count == index) {
                member = m;
            }
            count++;
        }
        return member;
    }
}
