package ca.umanitoba.cs.egilsons;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.exceptions.*;
import ca.umanitoba.cs.egilsons.logic.SignIn;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import ca.umanitoba.cs.egilsons.persistence.json.LibrarySystemPersistenceJson;
import ca.umanitoba.cs.egilsons.ui.AccountCentre;
import ca.umanitoba.cs.egilsons.ui.SignInDisplay;

import java.nio.file.Path;

public class SignInMain {
    public static void main(String[] args) {
        LibrarySystemPersistence persistence = new LibrarySystemPersistenceJson(Path.of("librarySystem.json"));
        LibrarySystem librarySystem = persistence.loadLibrarySystem();
        Library library = librarySystem.getLibraries().get(0);

        System.out.println("Welcome to " + library.getName() + " Library");

        // Start sign in
        SignIn signIn = new SignIn(librarySystem, persistence);
        SignInDisplay display = new SignInDisplay(signIn);
        Member account = display.signInScreen();
        // Enter the main menu
        AccountCentre accountCentre = new AccountCentre(librarySystem, account, persistence);
        accountCentre.menu();
    }
}
