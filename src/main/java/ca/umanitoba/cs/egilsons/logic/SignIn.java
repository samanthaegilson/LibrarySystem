package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import com.google.common.base.Preconditions;

/**
 * Logic for signing in to a {@link Member} account.
 */
public class SignIn {
    private final LibrarySystem librarySystem;

    /**
     * Checks that SignIn is in a valid state
     */
    private void checkSignIn() {
        Preconditions.checkNotNull(librarySystem, "Library system should never be null.");
    }

    /**
     * A constructor for SignIn. Receives the library system
     *
     * @param librarySystem the library system
     */
    public SignIn(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
        checkSignIn();
    }

    /**
     * Checks if a user exists with the given name and password
     *
     * @param name the name to check
     * @param password the password to check
     * @return the member with the name and password
     */
    public Member checkUser(String name, String password) {
        checkSignIn();
        Preconditions.checkNotNull(name, "Member name should not be null.");
        Preconditions.checkNotNull(password, "Password name should not be null.");
        Member member = null;
        boolean userExists = false;
        int index = 0;

        // Searches until a member matches the name and password
        while (!userExists && index < this.librarySystem.getMembers().size()) {
            if (this.librarySystem.getMember(index).getName().equals(name)
                    && this.librarySystem.getMember(index).getPassword().equals(password)) {
                member = this.librarySystem.getMember(index);
                userExists = true;
            }
            index++;
        }

        checkSignIn();
        return member;
    }

    /**
     * Adds an account to the library system
     *
     * @param account the account to add
     * @return if the account was successfully added or not
     */
    public boolean makeAccount(Member account) {
        checkSignIn();
        return this.librarySystem.addMember(account);
    }
}
