package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidPasswordException;
import ca.umanitoba.cs.egilsons.logic.SignIn;

import java.util.Scanner;

public class SignInDisplay {
    private final SignIn signIn;
    private final Scanner keyboard;

    public SignInDisplay(LibrarySystem librarySystem) {
        this.signIn = new SignIn(librarySystem);
        this.keyboard = new Scanner(System.in);
    }

    public Member startSignIn() {
        Member account = null;
        System.out.println("Do you have an account?");
        boolean haveAccount = yesNo();
        if (haveAccount) {
            account = loginToAccount();
        } else {
            account = makeAccount();
        }

        // go to AccountCentre
        // through main?
        // this.keyboard.close();
        return account;
    }

    public void printAnnouncements(Member member) {
        System.out.println("Announcements: ");
        for (String announcement : member.getAnnouncements()) {
            System.out.println(announcement + " is available to take out!");
        }
    }

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

    private Member loginToAccount() {
        Member account = null;
        while (account == null) {
            System.out.println("Please enter your member name: ");
            String name = keyboard.nextLine();
            System.out.println("Please enter your account's password: ");
            String password = keyboard.nextLine();
            account = signIn.checkUser(name, password);
            if (account == null) {
                System.out.println("Account name or password incorrect. Would you like to make a new account?");
                boolean makeNewAccount = yesNo();
                if (makeNewAccount) {
                    account = makeAccount();
                }
            }
        }
        return account;
    }

    private Member makeAccount() {
        Member account = null;
        String name = null;
        String password = null;
        while (name == null || password == null) {
            System.out.println("Please enter a name for your account: ");
            name = keyboard.nextLine();
            System.out.println("Please enter a password for your account: ");
            password = keyboard.nextLine();
            try {
                account = signIn.makeAccount(name, password);
            } catch (InvalidNameException e) {
                System.out.println("Name should have at least one letter, e.g., bob");
                name = null;
            } catch (InvalidPasswordException e) {
                System.out.println("Password should have at least one letter, e.g., nu3586l");
                password = null;
            }
        }
        return account;
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
                    System.out.println("Not a valid choice, please try again.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number, please try again.");
            }
        }
        return choice;
    }
}
