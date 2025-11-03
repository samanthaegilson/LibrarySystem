package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.logic.SignIn;

import java.util.Scanner;

public class SignInDisplay {
    private final SignIn signIn;
    private final LibrarySystem librarySystem;
    private final Scanner keyboard;

    public SignInDisplay(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
        this.signIn = new SignIn(librarySystem);
        this.keyboard = new Scanner(System.in);
    }

    public void startSignIn() {
        String task;
        boolean validInput = false;
        System.out.println("Sign in:");

        while (!validInput) {
            task = signInScreenOptions();
            switch (task) {
                case "yes":
                    validInput = true;
                    loginToAccount();
                    break;
                case "no":
                    validInput = true;
                    makeAccount();
                    break;
                default:
                    System.out.println("I don't know what that means.");
            }
        }

    }

    private String signInScreenOptions() {
        String signInScreenSubtask;
        System.out.println("""
                Do you have an account?
                YES
                NO
                """);
        signInScreenSubtask = keyboard.nextLine().toLowerCase();
        return signInScreenSubtask;
    }

    private void loginToAccount() {

    }

    private void makeAccount() {

    }
}
