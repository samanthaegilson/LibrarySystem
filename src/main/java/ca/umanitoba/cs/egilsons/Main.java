//package ca.umanitoba.cs.egilsons;
//
//import ca.umanitoba.cs.egilsons.domain.*;
//import ca.umanitoba.cs.egilsons.domain.media.*;
//import ca.umanitoba.cs.egilsons.domain.Review;
//import ca.umanitoba.cs.egilsons.domain.resource.Computer;
//import ca.umanitoba.cs.egilsons.domain.resource.Resource;
//import ca.umanitoba.cs.egilsons.domain.resource.Room;
//import ca.umanitoba.cs.egilsons.domain.media.Book;
//import ca.umanitoba.cs.egilsons.domain.media.DVD;
//import ca.umanitoba.cs.egilsons.output.*;
//
//import java.util.List;
//import java.util.Scanner;
//import java.util.TreeSet;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main {
//    public static void main(String[] args) {
//        runMenu();
//    }
//
//    /**
//     * Keeps the menu running with available choices until the user quits.
//     */
//    public static void runMenu() {
//        final int HIGH_BOUND = 14; // Total amount of choices
//        LibrarySystem librarySystem = new LibrarySystem();
//        boolean running = true;
//        Scanner keyboard = new Scanner(System.in);
//        while (running) {
//            displayMenu();
//            int choice = getChoice(1, HIGH_BOUND, keyboard);
//            if (choice == 1) {
//                // Add member
//                addMember(librarySystem, keyboard);
//            } else if (choice == 2) {
//                // Show member
//                showMember(librarySystem, keyboard);
//            } else if (choice == 3) {
//                // Remove member
//                removeMember(librarySystem, keyboard);
//            } else if (choice == 4) {
//                // Add library
//                addLibrary(librarySystem, keyboard);
//            } else if (choice == 5) {
//                // Show library
//                showLibrary(librarySystem, keyboard);
//            } else if (choice == 6) {
//                // Add media
//                addMedia(librarySystem, keyboard);
//            } else if (choice == 7) {
//                // Show media
//                showMedia(librarySystem, keyboard);
//            } else if (choice == 8) {
//                // Remove media
//                removeMedia(librarySystem, keyboard);
//            } else if (choice == 9) {
//                // Add resource
//                addResource(librarySystem, keyboard);
//            } else if (choice == 10) {
//                // Show resource
//                showResource(librarySystem, keyboard);
//            } else if (choice == 11) {
//                // Add review
//                addReview(librarySystem, keyboard);
//            } else if (choice == 12) {
//                // Show review
//                showReview(librarySystem, keyboard);
//            } else if (choice == 13) {
//                // Show map
//                showMap(librarySystem, keyboard);
//            } else {
//                // Quit
//                running = false;
//                keyboard.close();
//                System.out.println("Goodbye.");
//            }
//        }
//    }
//
//    /**
//     * Prints the menu of choices
//     */
//    public static void displayMenu() {
//        System.out.println("""
//
//                Welcome to the Library System!
//                Please enter a number corresponding to one of the following:
//                1. ADD MEMBER
//                2. SHOW MEMBER
//                3. REMOVE MEMBER
//                4. ADD LIBRARY
//                5. SHOW LIBRARY
//                6. ADD MEDIA
//                7. SHOW MEDIA
//                8. REMOVE MEDIA
//                9. ADD RESOURCE
//                10. SHOW RESOURCE
//                11. ADD REVIEW
//                12. SHOW REVIEW
//                13. SHOW MAP
//                14. QUIT
//                """);
//    }
//
//    /**
//     * Adds a member to the library system
//     *
//     * @param librarySystem the library system the member is being added to
//     * @param keyboard the scanner to receive input
//     */
//    public static void addMember(LibrarySystem librarySystem, Scanner keyboard) {
//        System.out.println("Please enter a name: ");
//        String name = keyboard.nextLine();
//
//        boolean added = librarySystem.addMember(new Member(name));
//        if (added) {
//            System.out.println("Member " + name + " added.");
//        } else {
//            System.out.println("Member already exists. Could not add.");
//        }
//    }
//
//    /**
//     * Shows a member from the library system
//     *
//     * @param librarySystem the library system of the member
//     * @param keyboard the scanner to receive input
//     */
//    public static void showMember(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getMembers().isEmpty()) {
//            System.out.println("Please choose the member to show: ");
//            int memberChoice = chooseMember(librarySystem.getMembers(), keyboard);
//
//            MemberPrinter memberPrinter = new MemberPrinter(librarySystem.getMember(memberChoice));
//            memberPrinter.print();
//        } else {
//            System.out.println("Please add a member first.");
//        }
//    }
//
//    /**
//     * Removes a member from the library system
//     *
//     * @param librarySystem the library system of the member
//     * @param keyboard the scanner to receive input
//     */
//    public static void removeMember(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getMembers().isEmpty()) { // Checks there is a member to choose from
//            System.out.println("Please choose a member to remove: ");
//            int memberChoice = chooseMember(librarySystem.getMembers(), keyboard);
//
//            if (librarySystem.removeMember(librarySystem.getMember(memberChoice))) {
//                System.out.println("Member removed.");
//            } else {
//                System.out.println("Failed to remove member. Member not found.");
//            }
//        } else {
//            System.out.println("Please add a member first.");
//        }
//    }
//
//    /**
//     * Adds a library to the library system
//     *
//     * @param librarySystem the library system the library is being added to
//     * @param keyboard the scanner to receive input
//     */
//    public static void addLibrary(LibrarySystem librarySystem, Scanner keyboard) {
//        System.out.println("Please enter a name for the library:");
//        String name = keyboard.nextLine();
//
//        librarySystem.addLibrary(new Library(name));
//        System.out.println(name + " library created.");
//    }
//
//    /**
//     * Shows a library from the library system
//     *
//     * @param librarySystem the library system of the library
//     * @param keyboard the scanner to receive input
//     */
//    public static void showLibrary(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose a library to show:");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            LibraryPrinter libraryPrinter = new LibraryPrinter(librarySystem.getLibraries().get(libraryChoice));
//            libraryPrinter.print();
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Adds media to a library in the library system
//     *
//     * @param librarySystem the library system the media is being added to
//     * @param keyboard the scanner to receive input
//     */
//    public static void addMedia(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose a library to add media to: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            System.out.println("Please choose a type of media: ");
//            System.out.println("""
//                    1. Book
//                    2. DVD
//                    """);
//            int mediaType = getChoice(1, 2, keyboard);
//
//            // There are only 2 choices
//            if (mediaType == 1) {
//                Book book = createBook(keyboard);
//                librarySystem.getLibraries().get(libraryChoice).addMedia(book);
//            } else {
//                DVD dvd = createDVD(keyboard);
//                librarySystem.getLibraries().get(libraryChoice).addMedia(dvd);
//            }
//
//            System.out.println("Media added to " + librarySystem.getLibraries().get(libraryChoice).getName());
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Creates a book object with input from the user
//     *
//     * @param keyboard the scanner to receive input
//     * @return the book created
//     */
//    public static Book createBook(Scanner keyboard) {
//        System.out.println("Please enter the book title: ");
//        String title = keyboard.nextLine();
//
//        System.out.println("Please enter the author of this book: ");
//        String author = keyboard.nextLine();
//
//        System.out.println("Please enter the amount of pages in the book: ");
//        int pages = getChoice(0, Integer.MAX_VALUE, keyboard); // Can have any positive number of pages
//
//        MediaCategory category = chooseCategory(keyboard);
//
//        return new Book(title, author, pages, category);
//    }
//
//    /**
//     * Creates a DVD object with input from the user
//     *
//     * @param keyboard the scanner to receive input
//     * @return the DVD created
//     */
//    public static DVD createDVD(Scanner keyboard) {
//        System.out.println("Please enter the DVD title: ");
//        String title = keyboard.nextLine();
//
//        System.out.println("Please enter the director of this movie: ");
//        String director = keyboard.nextLine();
//
//        System.out.println("Please enter the run time of this movie (in minutes): ");
//        int runTime = getChoice(0, Integer.MAX_VALUE, keyboard); // Can have any positive number for the run time
//
//        MediaCategory category = chooseCategory(keyboard);
//
//        return new DVD(title, director, runTime, category);
//    }
//
//    /**
//     * Shows media from a library in the library system
//     *
//     * @param librarySystem the library system of the media
//     * @param keyboard the scanner to receive input
//     */
//    public static void showMedia(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose a library to show media from: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
//            if (!media.isEmpty()) { // Checks there is media to choose from
//                System.out.println("Please choose a piece of media to show:");
//                int mediaChoice = chooseMedia(media, keyboard);
//
//                // There are only 2 options for media
//                if (media.get(mediaChoice) instanceof Book) {
//                    BookPrinter bookPrinter = new BookPrinter((Book) media.get(mediaChoice));
//                    bookPrinter.print();
//                } else {
//                    DVDPrinter dvdPrinter = new DVDPrinter((DVD) media.get(mediaChoice));
//                    dvdPrinter.print();
//                }
//            } else {
//                System.out.println("Please add media first.");
//            }
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Removes media from a library in the library system
//     *
//     * @param librarySystem the library system of the media
//     * @param keyboard the scanner to receive input
//     */
//    public static void removeMedia(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose a library to remove media from: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
//            if (!media.isEmpty()) { // Checks there is media to choose from
//                System.out.println("Please choose a piece of media to remove:");
//                int mediaChoice = chooseMedia(media, keyboard);
//
//                librarySystem.getLibraries().get(libraryChoice).removeMedia(media.get(mediaChoice));
//                System.out.println("Media removed from "
//                        + librarySystem.getLibraries().get(libraryChoice).getName());
//            } else {
//                System.out.println("Please add media first.");
//            }
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Adds a resource to a library in the library system
//     *
//     * @param librarySystem the library system the resource is being added to
//     * @param keyboard the scanner to receive input
//     */
//    public static void addResource(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose a library to add a resource to: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            System.out.println("Please choose a resource type: ");
//            // Prints both resource types
//            System.out.println("""
//                    1. Room
//                    2. Computer
//                    """);
//            int typeChoice = getChoice(1, 2, keyboard); // Only 2 choices of resources
//
//            // There are only 2 choices
//            if (typeChoice == 1) { // Room
//                Room newRoom = new Room();
//                librarySystem.getLibraries().get(libraryChoice).addResource(newRoom);
//            } else { // Computer
//                Computer newComputer = new Computer();
//                librarySystem.getLibraries().get(libraryChoice).addResource(newComputer);
//            }
//
//            System.out.println("Resource added to "
//                    + librarySystem.getLibraries().get(libraryChoice).getName() + " library.");
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Shows a resource from a library in the library system
//     *
//     * @param librarySystem the library system of the resource
//     * @param keyboard the scanner to receive input
//     */
//    public static void showResource(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose the library the resource belongs to: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            List<Resource> resources = librarySystem.getLibraries().get(libraryChoice).getResources();
//            if (!resources.isEmpty()) { // Checks there is a resource to choose from
//                System.out.println("Please choose the resource to show: ");
//                int resourceChoice = chooseResource(resources, keyboard);
//
//                // There are only 2 options for resources
//                if (resources.get(resourceChoice) instanceof Room) {
//                    RoomPrinter roomPrinter = new RoomPrinter((Room) resources.get(resourceChoice));
//                    roomPrinter.print();
//                } else {
//                    ComputerPrinter computerPrinter = new ComputerPrinter((Computer) resources.get(resourceChoice));
//                    computerPrinter.print();
//                }
//            } else {
//                System.out.println("Please add a resource first.");
//            }
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Adds a review to a media in a library in the library system
//     *
//     * @param librarySystem the library system the review is being added to
//     * @param keyboard the scanner to receive input
//     */
//    public static void addReview(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getMembers().isEmpty()) { // Checks there is a member to choose from
//            System.out.println("Please choose the member making the review");
//            int memberChoice = chooseMember(librarySystem.getMembers(), keyboard);
//            Member member = librarySystem.getMember(memberChoice);
//
//            if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//                System.out.println("Please choose the library the media belongs to: ");
//                int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//                List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
//                if (!media.isEmpty()) { // Checks there is media to choose from
//                    System.out.println("Please choose a piece of media to add a review to: ");
//                    int mediaChoice = chooseMedia(media, keyboard);
//
//                    System.out.println("Please enter a star rating (1-5): ");
//                    int starChoice = getChoice(1, 6, keyboard);
//
//                    System.out.println("Please type out your review: ");
//                    String text = keyboard.nextLine();
//
//                    Review review = new Review(member, media.get(mediaChoice), text, starChoice);
//                    media.get(mediaChoice).addReview(review);
//                    System.out.println("Review added.");
//                } else {
//                    System.out.println("Please add media first.");
//                }
//            } else {
//                System.out.println("Please add a library first.");
//            }
//        } else {
//            System.out.println("Please add a member first.");
//        }
//    }
//
//    /**
//     * Shows a review of a media in a library in the library system
//     *
//     * @param librarySystem the library system of the review
//     * @param keyboard the scanner to receive input
//     */
//    public static void showReview(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose the library the media of the review belongs to: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
//            if (!media.isEmpty()) { // Checks there is media to choose from
//                System.out.println("Please choose the media of the review: ");
//                int mediaChoice = chooseMedia(media, keyboard);
//
//                List<Review> reviews = media.get(mediaChoice).getReviews();
//                if (!reviews.isEmpty()) { // Checks there is a review to choose from
//                    System.out.println("Please choose a review to show: ");
//                    int reviewChoice = chooseReview(reviews, keyboard);
//
//                    ReviewPrinter reviewPrinter = new ReviewPrinter(reviews.get(reviewChoice));
//                    reviewPrinter.print();
//                } else {
//                    System.out.println("Please add a review first.");
//                }
//            } else {
//                System.out.println("Please add media first.");
//            }
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Shows a map of a library in the library system
//     *
//     * @param librarySystem the library system of the map
//     * @param keyboard the scanner to receive input
//     */
//    public static void showMap(LibrarySystem librarySystem, Scanner keyboard) {
//        if (!librarySystem.getLibraries().isEmpty()) { // Checks there is a library to choose from
//            System.out.println("Please choose a library to show the map of: ");
//            int libraryChoice = chooseLibrary(librarySystem.getLibraries(), keyboard);
//
//            MapPrinter mapPrinter = new MapPrinter(librarySystem.getLibraries().get(libraryChoice).getMap());
//            mapPrinter.print();
//        } else {
//            System.out.println("Please add a library first.");
//        }
//    }
//
//    /**
//     * Gets an integer from the user between a low and high bound.
//     *
//     * @param low the lowest the choice can be
//     * @param high the highest the choice can be
//     * @param keyboard the scanner to receive input
//     * @return the integer representing the choice of the user
//     */
//    public static int getChoice(int low, int high, Scanner keyboard) {
//        boolean valid = false;
//        int choice = -1;
//        while (!valid) {
//            String input = keyboard.nextLine();
//            try {
//                choice = Integer.parseInt(input);
//                // The choice is only valid if it is an integer between the high and low bounds
//                if (choice >= low && choice <= high) {
//                    valid = true;
//                } else {
//                    System.out.println("Not a valid choice, please try again.");
//                }
//            } catch (NumberFormatException nfe) {
//                System.out.println("Not a number, please try again.");
//            }
//        }
//        return choice;
//    }
//
//    /**
//     * Gets the user's choice of library from the list it prints
//     *
//     * @param libraries the list of libraries to choose from
//     * @param keyboard the scanner to receive input
//     * @return the index of the choice of library
//     */
//    public static int chooseLibrary(List<Library> libraries, Scanner keyboard) {
//        for (int i = 0; i < libraries.size(); i++) {
//            System.out.println((i + 1) + ". " + libraries.get(i).getName());
//        }
//        return getChoice(1, libraries.size(), keyboard) - 1; // Adjusts for the index
//    }
//
//    /**
//     * Gets the user's choice of member from the list it prints
//     *
//     * @param members the list of members to choose from
//     * @param keyboard the scanner to receive input
//     * @return the index of the choice of member
//     */
//    public static int chooseMember(TreeSet<Member> members, Scanner keyboard) {
//        int count = 1;
//        for (Member member : members) {
//            System.out.println(count + ". " + member.getName());
//            count++;
//        }
//        return getChoice(1, members.size(), keyboard) - 1; // Adjusts for the index
//    }
//
//    /**
//     * Gets the user's choice of media from the list it prints
//     *
//     * @param media the list of media to choose from
//     * @param keyboard the scanner to receive input
//     * @return the index of the choice of media
//     */
//    public static int chooseMedia(List<Media> media, Scanner keyboard) {
//        for (int i = 0; i < media.size(); i++) {
//            System.out.println((i + 1) + ". " + media.get(i).getTitle() + ", " + media.get(i).getClass().getSimpleName());
//        }
//        return getChoice(1, media.size(), keyboard) - 1; // Adjusts for the index
//    }
//
//    /**
//     * Gets the user's choice of media category or genre from the list it prints
//     *
//     * @param keyboard the scanner to receive input
//     * @return the choice of media category
//     */
//    public static MediaCategory chooseCategory(Scanner keyboard) {
//        System.out.println("Please choose a media category: ");
//        // Cycles through the enum MediaCategory once to display the choices, then cycles through again
//        // to match the choice to a value
//        int counter = 1;
//        for (MediaCategory category : MediaCategory.values()) {
//            System.out.println(counter + ". " + category);
//            counter++;
//        }
//        int categoryChoice = getChoice(1, MediaCategory.values().length, keyboard);
//        MediaCategory category = null;
//        counter = 1;
//        for (MediaCategory c : MediaCategory.values()) {
//            if (categoryChoice == counter) {
//                category = c;
//            }
//            counter++;
//        }
//        return category;
//    }
//
//    /**
//     * Gets the user's choice of resource from the list it prints
//     *
//     * @param resources the list of resources to choose from
//     * @param keyboard the scanner to receive input
//     * @return the index of the choice of resource
//     */
//    public static int chooseResource(List<Resource> resources, Scanner keyboard) {
//        for (int i = 0; i < resources.size(); i++) {
//            System.out.println((i + 1) + ". " + resources.get(i).getClass().getSimpleName() + " " + resources.get(i).getNumber());
//        }
//        return getChoice(1, resources.size(), keyboard) - 1; // Adjusts for the index
//    }
//
//    /**
//     * Gets the user's choice of review from the list it prints
//     *
//     * @param reviews the list of reviews to choose from
//     * @param keyboard the scanner to receive input
//     * @return the index of the choice of review
//     */
//    public static int chooseReview(List<Review> reviews, Scanner keyboard) {
//        for (int i = 0; i < reviews.size(); i++) {
//            System.out.println((i + 1) + ". " + reviews.get(i).stars() + " stars by "
//                    + reviews.get(i).member().getName());
//        }
//        return getChoice(1, reviews.size(), keyboard) - 1; // Adjusts for the index
//    }
//}