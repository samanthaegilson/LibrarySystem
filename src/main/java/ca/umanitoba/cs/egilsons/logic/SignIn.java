package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;

public class SignIn {
    private final LibrarySystem librarySystem;

    public SignIn(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
    }

    public Member checkUser(String name, String password) {
        Member member = null;
        boolean userExists = false;
        int index = 0;
        while (!userExists && index < this.librarySystem.getMembers().size()) {
            if (this.librarySystem.getMember(index).getName().equals(name)
                    && this.librarySystem.getMember(index).getPassword().equals(password)) {
                member = this.librarySystem.getMember(index);
                userExists = true;
            }
            index++;
        }
        return member;
    }

    public Member makeAccount(String name, String password) throws InvalidNameException, InvalidPasswordException {
        Member newAccount = new Member.MemberBuilder().name(name).password(password).build();
        this.librarySystem.addMember(newAccount);
        return newAccount;
    }
}
