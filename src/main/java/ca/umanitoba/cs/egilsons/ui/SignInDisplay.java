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

    private boolean yesNo() {
        boolean isYes = false;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                YES
                NO
                """);
            String yesNo = this.keyboard.nextLine().toLowerCase();
            switch (yesNo) {
                case "yes":
                    validInput = true;
                    isYes = true;
                    break;
                case "no":
                    validInput = true;
                    break;
                default:
                    System.out.println("Not an option.");
            }
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
}
