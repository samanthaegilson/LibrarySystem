package ca.umanitoba.cs.egilsons.domain;

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
     * Default constructor for the Library System
     */
    public LibrarySystem() {
        this.libraries = new ArrayList<>();
        this.members = new TreeSet<>();
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
        if (this.libraries.size() > 0) {
            this.libraries.remove(0);
        }
        this.libraries.add(library);
    }

    /**
     * Adds a member to the library system
     *
     * @param member the member being added
     * @return
     */
    public boolean addMember(Member member) {
        boolean added = false;
        if (this.members.add(member)) {
            // !this.members.contains(member)
            added = true;
        }
        return added;
    }

    /**
     * Removes a member from the library system's members
     *
     * @param member the member being removed
     * @return if the member has been removed or not
     */
    public boolean removeMember(Member member) {
        return this.members.remove(member);
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
        for (Member m : this.members) {
            if (count == index) {
                member = m;
            }
            count++;
        }
        return member;
    }
}
