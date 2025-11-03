package ca.umanitoba.cs.egilsons;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.exceptions.*;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Computer;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import ca.umanitoba.cs.egilsons.ui.SignInDisplay;

public class SignInMain {
    private static LibrarySystem librarySystem;

    public static void main(String[] args) {
        setUpLibrarySystem();

        // our entry point is really only responsible for initializing our display class.
        SignInDisplay display = new SignInDisplay(librarySystem);
        display.startSignIn();
    }

    public static void setUpLibrarySystem() {
        // Library System
        librarySystem = new LibrarySystem();
        // Libraries

        Library library1 = new Library.LibraryBuilder().name("Westwood").build();
//        Library library2 = new Library.LibraryBuilder().name("Bedson").build();
//        Library library3 = new Library.LibraryBuilder().name("Shakespeare").build();
//        Library library4 = new Library.LibraryBuilder().name("Elaine").build();
        // Media
        try {
            Book book1 = new Book.BookBuilder().title("Harry Potter").author("J.K. Rowling").pages(560).category(MediaCategory.FANTASY).build();
            Book book2 = new Book.BookBuilder().title("The Hunger Games").author("Suzanne Collins").pages(384).category(MediaCategory.YOUNG_ADULT).build();
            Book book3 = new Book.BookBuilder().title("The Missing Page").author("HTTP").pages(404).category(MediaCategory.MYSTERY).build();
            Book book4 = new Book.BookBuilder().title("Gardening 101").author("Mikayla Goncalves").pages(403).category(MediaCategory.NON_FICTION).build();
            Book book5 = new Book.BookBuilder().title("Hollow Echoes").author("Kristine Fisher").pages(963).category(MediaCategory.HORROR).build();
            Book book6 = new Book.BookBuilder().title("Alien Invasion").author("Oliver White").pages(668).category(MediaCategory.SCIENCE_FICTION).build();


            DVD dvd1 = new DVD.DVDBuilder().title("How to Train Your Cat").director("Cat Sanders").runTime(98).category(MediaCategory.CHILDREN).build();
            DVD dvd2 = new DVD.DVDBuilder().title("Fading Colours").director("Julia Winters").runTime(82).category(MediaCategory.ROMANCE).build();
            DVD dvd3 = new DVD.DVDBuilder().title("The Wishing Well").director("Peter Callow").runTime(103).category(MediaCategory.FANTASY).build();
            DVD dvd4 = new DVD.DVDBuilder().title("The Red Road").director("Steven Anderson").runTime(114).category(MediaCategory.HISTORICAL_FICTION).build();
            DVD dvd5 = new DVD.DVDBuilder().title("Starship").director("Jamie Jones").runTime(200).category(MediaCategory.SCIENCE_FICTION).build();

            for (int i = 0; i < 6; i++) {
                library1.addMedia(book2);
//                library2.addMedia(book3);
//                library2.addMedia(dvd4);
            }
        } catch (InvalidTitleException | InvalidAuthorException | InvalidBookPagesException | InvalidDirectorException |
                 InvalidDVDRunTimeException e) {
            throw new RuntimeException(e);
        }

        // Resources
        library1.addResource(new Room());
        library1.addResource(new Room());
//        library2.addResource(new Room());
//        library3.addResource(new Room());
//        library3.addResource(new Room());
//        library3.addResource(new Room());

        for (int i = 0; i < 8; i++) {
            library1.addResource(new Computer());
//            library2.addResource(new Computer());
//            library3.addResource(new Computer());
//            library4.addResource(new Computer());
//            library4.addResource(new Computer());
        }
//        library2.addResource(new Computer());
//        library3.addResource(new Computer());
//        library3.addResource(new Computer());
//        library3.addResource(new Computer());
        // Add together


        librarySystem.addLibrary(library1);
//        librarySystem.addLibrary(library2);
//        librarySystem.addLibrary(library3);
//        librarySystem.addLibrary(library4);
    }
}
