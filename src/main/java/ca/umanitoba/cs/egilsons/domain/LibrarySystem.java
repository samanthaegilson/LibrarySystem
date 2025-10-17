package ca.umanitoba.cs.egilsons.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class LibrarySystem {
    private List<Library> libraries;
    private TreeSet<Member> members;

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

    public void addLibrary(String name) {
        this.libraries.remove(0);
        this.libraries.add(new Library(name));
    }

    public boolean addMember(Member member) {
        boolean added = false;
        if (this.members.add(member)) {
            // !this.members.contains(member)
            added = true;
        }
        return added;
    }

    public boolean removeMember(Member m) {
        return this.members.remove(m);
    }

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
