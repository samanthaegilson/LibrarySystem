package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;

/**
 * Logic for signing in to a {@link Member} account.
 */
public class SignIn {
    private final LibrarySystem librarySystem;

    /**
     * A constructor for SignIn. Receives the library system
     *
     * @param librarySystem the library system
     */
    public SignIn(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
    }

    /**
     * Checks if a user exists with the given name and password
     *
     * @param name the name to check
     * @param password the password to check
     * @return the member with the name and password
     */
    public Member checkUser(String name, String password) {
        Member member = null;
        boolean userExists = false;
        int index = 0;

        // Searches until a user matches the name and password
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

    /**
     * Adds an account to the library system
     *
     * @param account the account to add
     * @return if the account was successfully added or not
     */
    public boolean makeAccount(Member account) {
        return this.librarySystem.addMember(account);
    }
}
