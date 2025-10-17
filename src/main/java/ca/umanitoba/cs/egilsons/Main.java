package ca.umanitoba.cs.egilsons;

import ca.umanitoba.cs.egilsons.domain.*;
import ca.umanitoba.cs.egilsons.output.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu() {
        final int HIGH_BOUND = 15;
        LibrarySystem librarySystem = new LibrarySystem();
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        while (running) {
            displayMenu();
            int choice = getChoice(0, HIGH_BOUND, sc);
            if (choice == 1) {
                ArrayList<String> contactInfo = new ArrayList<>();

                System.out.println("Please enter a name");
                String name = sc.nextLine();

                System.out.println("Please enter a form of contact");
                String contact = sc.nextLine();
                contactInfo.add(contact);

                boolean moreInfo = true;
                while (moreInfo) {
                    System.out.println("Do you have another form of contact you wish to add?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int yesOrNo = getChoice(0, 3, sc);
                    if (yesOrNo == 1) {
                        System.out.println("Please enter another form of contact");
                        String contact2 = sc.nextLine();
                        contactInfo.add(contact2);
                    } else {
                        moreInfo = false;
                    }
                }
                Member newMember = new Member(name, contactInfo);
                boolean added = librarySystem.addMember(newMember);
                if (added) {
                    System.out.println("Member added");
                } else {
                    System.out.println("Member already exists. Could not add.");
                }
            } else if (choice == 2) {
                // print member
                System.out.println("Please choose the member to show: ");
                int count = 1;
                for (Member m : librarySystem.getMembers()) {
                    System.out.println((count + 1) + ". " + m.getName());
                }
                int memberChoice = getChoice(0, librarySystem.getMembers().size(), sc) - 1;
                Member member = librarySystem.getMember(memberChoice);

                MemberPrinter memberPrinter = new MemberPrinter(member);
                memberPrinter.print();
            } else if (choice == 3) {
                // remove member
                System.out.println("Please choose a member to remove: ");
                int count = 1;
                for (Member m : librarySystem.getMembers()) {
                    System.out.println((count + 1) + ". " + m.getName());
                }
                int memberChoice = getChoice(0, librarySystem.getMembers().size(), sc) - 1;
                Member member = librarySystem.getMember(memberChoice);

                if (librarySystem.removeMember(member)) {
                    System.out.println("Member removed.");
                } else {
                    System.out.println("Failed to remove member. Member not found.");
                }
            } else if (choice == 4) {
                // add library
                System.out.println("Please enter a name for the library:");
                String name = sc.nextLine();
                librarySystem.addLibrary(name);
                System.out.println(name + "library created.");
            } else if (choice == 5) {
                // show library
                System.out.println("Please choose a library to show:");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;
                LibraryPrinter libraryPrinter = new LibraryPrinter(librarySystem.getLibraries().get(libraryChoice));
                libraryPrinter.print();
            } else if (choice == 6) {
                // add media
                System.out.println("Please choose a library to add media to: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;
                System.out.println("Please enter the media name: ");
                String name = sc.nextLine();
                System.out.println("Please enter the creator of this media: ");
                String creator = sc.nextLine();
                System.out.println("Please choose the media format: ");
                int counter = 1;
                for (MediaFormat format : MediaFormat.values()) {
                    System.out.println(counter + ". " + format);
                    counter++;
                }
                int formatChoice = getChoice(0, MediaFormat.values().length, sc);
                MediaFormat format = null;
                counter = 1;
                for (MediaFormat f : MediaFormat.values()) {
                    if (formatChoice == counter) {
                        format = f;
                    }
                    counter++;
                }
                System.out.println("Please choose a media category: ");
                counter = 1;
                for (MediaCategory category : MediaCategory.values()) {
                    System.out.println(counter + ". " + category);
                    counter++;
                }
                int categoryChoice = getChoice(0, MediaCategory.values().length, sc);
                MediaCategory category = null;
                counter = 1;
                for (MediaCategory c : MediaCategory.values()) {
                    if (categoryChoice == counter) {
                        category = c;
                    }
                    counter++;
                }
                Media newMedia = new Media(name, creator, format, category);
                librarySystem.getLibraries().get(libraryChoice).addMedia(newMedia);
                System.out.println(name + " added to " + librarySystem.getLibraries().get(libraryChoice).getName());
            } else if (choice == 7) {
                // show media
                System.out.println("Please choose a library to show media from: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;

                List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
                System.out.println("Please choose a piece of media to show:");
                for (int i = 0; i < media.size(); i++) {
                    System.out.println((i + 1) + ". " + media.get(i).getName() + ", " + media.get(i).getFormat());
                }
                int mediaChoice = getChoice(0, media.size(), sc) - 1;

                MediaPrinter mediaPrinter = new MediaPrinter(media.get(mediaChoice));
                mediaPrinter.print();
            } else if (choice == 8) {
                // remove media
                System.out.println("Please choose a library to remove media from: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;

                List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
                System.out.println("Please choose a piece of media to remove:");
                for (int i = 0; i < media.size(); i++) {
                    System.out.println((i + 1) + ". " + media.get(i).getName() + ", " + media.get(i).getFormat());
                }
                int mediaChoice = getChoice(0, media.size(), sc) - 1;

                media.remove(mediaChoice);
                System.out.println(media + " removed from " + librarySystem.getLibraries().get(libraryChoice).getName());
            } else if (choice == 9) {
                // add resource
                System.out.println("Please choose a library to add a resource to: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;
                // number the rooms
                System.out.println("Please choose a resource type: ");
                int counter = 1;
                for (ResourceType type : ResourceType.values()) {
                    System.out.println(counter + ". " + type);
                    counter++;
                }
                int typeChoice = getChoice(0, ResourceType.values().length, sc);
                if (typeChoice == 1) {
                    // Room
                    Room newRoom = new Room();
                    librarySystem.getLibraries().get(libraryChoice).addResource(newRoom);
                } else {
                    // Computer
                    Computer newComputer = new Computer();
                    librarySystem.getLibraries().get(libraryChoice).addResource(newComputer);
                }

                System.out.println("Resource added.");
            } else if (choice == 10) {
                // show resource
                System.out.println("Please choose the library the resource belongs to: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;

                List<Resource> resources = librarySystem.getLibraries().get(libraryChoice).getResources();
                System.out.println("Please choose the resource to show: ");
                for (int i = 0; i < resources.size(); i++) {
                    System.out.println((i + 1) + ". " + resources.get(i).getType() + resources.get(i).getNumber());
                }
                int resourceChoice = getChoice(0, resources.size(), sc) - 1;

                ResourcePrinter resourcePrinter = new ResourcePrinter(resources.get(resourceChoice));
                resourcePrinter.print();
            } else if (choice == 11) {
                // add review
                System.out.println("Please choose the member making the review");
                int count = 1;
                for (Member m : librarySystem.getMembers()) {
                    System.out.println((count + 1) + ". " + m.getName());
                }
                int memberChoice = getChoice(0, librarySystem.getMembers().size(), sc) - 1;
                Member member = librarySystem.getMember(memberChoice);

                System.out.println("Please choose the library the media belongs to: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;

                List<Media> media = librarySystem.getLibraries().get(libraryChoice).getMedia();
                System.out.println("Please choose a piece of media to add a review to: ");
                for (int i = 0; i < media.size(); i++) {
                    System.out.println((i + 1) + ". " + media.get(i).getName() + ", " + media.get(i).getFormat());
                }
                int mediaChoice = getChoice(0, media.size(), sc) - 1;

                System.out.println("Please enter a star rating (1-5): ");
                int starChoice = getChoice(0, 6, sc);

                System.out.println("Please type out your review: ");
                String review = sc.nextLine();

                Review newReview = new Review(member, media.get(mediaChoice), review, starChoice);
                member.addReview(newReview);
                System.out.println("Review added.");
            } else if (choice == 12) {
                // show review
                System.out.println("Please choose the member who made the review: ");
                int count = 1;
                for (Member m : librarySystem.getMembers()) {
                    System.out.println((count + 1) + ". " + m.getName());
                }
                int memberChoice = getChoice(0, librarySystem.getMembers().size(), sc) - 1;

                List<Review> reviews = librarySystem.getMember(memberChoice).getReviews();
                for (int i = 0; i < reviews.size(); i++) {
                    System.out.println((i + 1) + ". " + reviews.get(i).getStars() + ", "
                            + reviews.get(i).getMedia().getName());
                }
                int reviewChoice = getChoice(0, reviews.size(), sc) - 1;

                ReviewPrinter reviewPrinter = new ReviewPrinter(reviews.get(reviewChoice));
                reviewPrinter.print();
            } else if (choice == 13) {
                // show map
                System.out.println("Please choose a library to add a resource to: ");
                for (int i = 0; i < librarySystem.getLibraries().size(); i++) {
                    System.out.println((i + 1) + ". " + librarySystem.getLibraries().get(i).getName());
                }
                int libraryChoice = getChoice(0, librarySystem.getLibraries().size(), sc) - 1;
                MapPrinter mapPrinter = new MapPrinter(new Map(librarySystem.getLibraries().get(libraryChoice)));
                mapPrinter.print();
            } else {
                // Quit
                running = false;
                sc.close();
                System.out.println("Goodbye.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("""
                Welcome to the Library System!
                Please enter a number corresponding to one of the following:
                1. ADD MEMBER
                2. SHOW MEMBER
                3. REMOVE MEMBER
                4. ADD LIBRARY
                5. SHOW LIBRARY
                6. ADD MEDIA
                7. SHOW MEDIA
                8. REMOVE MEDIA
                9. ADD RESOURCE
                10. SHOW RESOURCE
                11. ADD REVIEW
                12. SHOW REVIEW
                13. SHOW MAP
                14. QUIT
                """);
    }

    public static int getChoice(int low, int high, Scanner sc) {
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            String input = sc.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice > low && choice < high) {
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