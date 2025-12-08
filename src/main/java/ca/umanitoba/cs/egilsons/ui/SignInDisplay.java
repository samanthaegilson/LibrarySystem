package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;
import ca.umanitoba.cs.egilsons.logic.SignIn;
import com.google.common.base.Preconditions;

import java.util.Scanner;

/**
 * Signs a user into a {@link Member} account.
 */
public class SignInDisplay {
    private final SignIn signIn;
    private final Scanner keyboard;

    /**
     * A constructor for SignInDisplay. Receives the library system
     *
     * @param signIn the sign in to display
     */
    public SignInDisplay(SignIn signIn) {
        this.signIn = signIn;
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Gets the user the sign in or make a new account
     *
     * @return the member signed in
     */
    public Member signInScreen() {
        Member account;
        System.out.println("Do you have an account?");
        boolean haveAccount = yesNo();
        if (haveAccount) {
            account = loginToAccount();
        } else {
            account = makeAccount();
        }

        return account;
    }

    /**
     * Gets the user's choice of yes or no
     *
     * @return if the user chose yes or not
     */
    private boolean yesNo() {
        boolean isYes = false;
        System.out.println("""
            1. YES
            2. NO""");
        int yesNo = getChoice(2);
        if (yesNo == 1) {
            isYes = true;
        }
        return isYes;
    }

    /**
     * Checks if a given name and password match a current member
     *
     * @return the member account
     */
    private Member loginToAccount() {
        Member account = null;
        while (account == null) {
            System.out.println("Please enter your member name: ");
            String name = keyboard.nextLine();
            System.out.println("Please enter your account's password: ");
            String password = keyboard.nextLine();
            account = signIn.checkUser(name, password);
            if (account == null) {
                System.out.println("Account name and password do not match any current members. Would you like to make a new account?");
                boolean makeNewAccount = yesNo();
                if (makeNewAccount) {
                    account = makeAccount();
                }
            }
        }
        return account;
    }

    /**
     * Creates a member account
     *
     * @return the created account
     */
    private Member makeAccount() {
        boolean added = false;
        Member account = null;
        while (!added) {
            Member.MemberBuilder memberBuilder = new Member.MemberBuilder();
            getNameInput(memberBuilder);
            getPasswordInput(memberBuilder);
            account = memberBuilder.build();
            added = this.signIn.makeAccount(account);
            if (!added) {
                System.out.println("Member already exists. Please choose a different name.");
                account = null;
            }
        }
        return account;
    }

    /**
     * Receives the name for the account from the user
     *
     * @param memberBuilder the builder for the member
     */
    private void getNameInput(Member.MemberBuilder memberBuilder) {
        String name;
        Preconditions.checkNotNull(memberBuilder, "Builder should not be null");
        do {
            System.out.println("Please enter a name for your account: ");
            name = this.keyboard.nextLine();

            try {
                memberBuilder.name(name);
            } catch (InvalidNameException e) {
                System.out.println("Name should have at least one letter, e.g., bob");
                name = null;
            }
        } while (name == null);
        Preconditions.checkNotNull(name, "Name should not be null after it's been set.");
    }

    /**
     * Receives the password for the account from the user
     *
     * @param memberBuilder the builder for the member
     */
    private void getPasswordInput(Member.MemberBuilder memberBuilder) {
        String password;
        Preconditions.checkNotNull(memberBuilder, "Builder should not be null");
        do {
            System.out.println("Please enter a password for your account: ");
            password = this.keyboard.nextLine();

            try {
                memberBuilder.password(password);
            } catch (InvalidPasswordException e) {
                System.out.println("Password should have at least one letter, e.g., nu3586l");
                password = null;
            }
        } while (password == null);
        Preconditions.checkNotNull(password, "Name should not be null after it's been set.");
    }

    /**
     * Gets an integer from the user between 1 and a high bound.
     *
     * @param high the highest the choice can be
     * @return the integer representing the choice of the user
     */
    private int getChoice(int high) {
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                choice = Integer.parseInt(input);
                // The choice is only valid if it is an integer between the high and low bounds
                if (choice >= 1 && choice <= high) {
                    valid = true;
                } else {
                    System.out.println("Must be a number between 1 and " + high + ", e.g., 1.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number, please enter a number between 1 and " + high + ", e.g., 1.");
            }
        }
        return choice;
    }
}
