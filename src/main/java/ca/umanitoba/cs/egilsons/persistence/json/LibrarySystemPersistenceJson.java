package ca.umanitoba.cs.egilsons.persistence.json;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.exceptions.*;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.*;
import ca.umanitoba.cs.egilsons.domain.resource.*;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import com.google.common.base.Preconditions;

import javax.json.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * A JSON-specific implementation of {@link LibrarySystemPersistence}.
 */
public class LibrarySystemPersistenceJson implements LibrarySystemPersistence {
    private final Path librarySystemStorage;
    private final List<Media> hardCodedMedia;
    private final List<Resource> hardCodedResources;

    /**
     * Invariant properties for LibrarySystemPersistenceJson
     */
    private void checkLibrarySystemPersistenceJson() {
        Preconditions.checkNotNull(librarySystemStorage, "Storage should never be null.");
        Preconditions.checkNotNull(hardCodedMedia, "Hard coded media should never be null.");
    }

    /**
     * A constructor for LibrarySystemPersistenceJson. Receives the path to the persistence file
     *
     * @param librarySystemStorage the path to the storage
     */
    public LibrarySystemPersistenceJson(Path librarySystemStorage) {
        Preconditions.checkNotNull(librarySystemStorage, "Must pass a file reference!");
        this.librarySystemStorage = librarySystemStorage;
        this.hardCodedMedia = new ArrayList<>();
        this.hardCodedResources = new ArrayList<>();
        hardCodeMedia();
        hardCodeResources();
        checkLibrarySystemPersistenceJson();
    }

    /**
     * Persists a library system
     *
     * @param librarySystem the library system to persist
     */
    public void saveLibrarySystem(LibrarySystem librarySystem) {
        Preconditions.checkNotNull(librarySystem, "Library system should not be null.");
        checkLibrarySystemPersistenceJson();
        try {
            // Writes the library system to the file
            JsonWriter writer = Json.createWriter(Files.newOutputStream(librarySystemStorage));
            JsonObjectBuilder librarySystemJson = Json.createObjectBuilder();
            librarySystemJson.add("libraries", librariesToJson(librarySystem.getLibraries()));
            librarySystemJson.add("members", membersToJson(librarySystem.getMembers()));
            writer.writeObject(librarySystemJson.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        checkLibrarySystemPersistenceJson();
    }

    /**
     * Loads the library system
     *
     * @return the library system
     */
    public LibrarySystem loadLibrarySystem() {
        checkLibrarySystemPersistenceJson();
        final String LIBRARY_NAME = "Shakespeare";
        LibrarySystem librarySystem = new LibrarySystem();
        if (Files.exists(librarySystemStorage)) {
            try {
                // Loads the library system from the storage
                JsonReader reader = Json.createReader(Files.newInputStream(librarySystemStorage));
                JsonObject librarySystemJson = reader.readObject();

                // Loads the members
                for (JsonValue memberJson : librarySystemJson.getJsonArray("members")) {
                    try {
                        librarySystem.addMember(memberFromJson(memberJson.asJsonObject()));
                    } catch (InvalidNameException | InvalidPasswordException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Loads the libraries
                for (JsonValue libraryJson : librarySystemJson.getJsonArray("libraries")) {
                    try {
                        librarySystem.addLibrary(libraryFromJson(libraryJson.asJsonObject(), librarySystem));
                    } catch (InvalidNameException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Creates a default library system
            try {
                Library library = new Library.LibraryBuilder().name(LIBRARY_NAME).build();
                for (Media media : this.hardCodedMedia) {
                    library.addMedia(media);
                }
                for (Resource resource : this.hardCodedResources) {
                    library.addResource(resource);
                }
                librarySystem.addLibrary(library);
            } catch (InvalidNameException e) {
                throw new RuntimeException(e);
            }
        }
        checkLibrarySystemPersistenceJson();
        return librarySystem;
    }

    /**
     * Creates hardcoded media
     */
    private void hardCodeMedia() {
        checkLibrarySystemPersistenceJson();
        this.hardCodedMedia.add(new Book("Harry Potter", "J.K. Rowling", 560, MediaCategory.FANTASY));
        this.hardCodedMedia.add(new Book("The Hunger Games", "Suzanne Collins", 384, MediaCategory.YOUNG_ADULT));
        this.hardCodedMedia.add(new Book("The Missing Page", "HTTP", 404, MediaCategory.MYSTERY));
        this.hardCodedMedia.add(new Book("Gardening 101", "Mikayla Goncalves", 403, MediaCategory.NON_FICTION));
        this.hardCodedMedia.add(new Book("Hollow Echoes", "Kristine Fisher", 963, MediaCategory.HORROR));
        this.hardCodedMedia.add(new Book("Alien Invasion", "Oliver White", 668, MediaCategory.SCIENCE_FICTION));
        this.hardCodedMedia.add(new DVD("How to Train Your Cat", "Cat Sanders", 98, MediaCategory.CHILDREN));
        this.hardCodedMedia.add(new DVD("Fading Colours", "Julia Winters", 82, MediaCategory.ROMANCE));
        this.hardCodedMedia.add(new DVD("The Wishing Well", "Peter Callow", 103, MediaCategory.FANTASY));
        this.hardCodedMedia.add(new DVD("The Red Road", "Steven Anderson", 114, MediaCategory.HISTORICAL_FICTION));
        this.hardCodedMedia.add(new DVD("Starship", "Jamie Jones", 200, MediaCategory.SCIENCE_FICTION));
        checkLibrarySystemPersistenceJson();
    }

    /**
     * Creates hardcoded resources
     */
    private void hardCodeResources() {
        checkLibrarySystemPersistenceJson();
        final int COMPUTERS = 9;
        final int ROOMS = 3;
        for (int i = 0; i < COMPUTERS; i++) {
            this.hardCodedResources.add(new Computer());
        }
        for (int i = 0; i < ROOMS; i++) {
            this.hardCodedResources.add(new Room());
        }
        checkLibrarySystemPersistenceJson();
    }

    /**
     * Saves libraries to Json
     *
     * @param libraries the libraries to persist
     * @return the libraries in Json
     */
    private JsonArray librariesToJson(List<Library> libraries) {
        Preconditions.checkNotNull(libraries, "Libraries should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonArrayBuilder librariesBuilder = Json.createArrayBuilder();
        for (Library library : libraries) {
            Preconditions.checkNotNull(library, "Library in libraries should not be null.");
            JsonObjectBuilder libraryBuilder = Json.createObjectBuilder().add("name", library.getName());

            // Saves the media
            JsonArrayBuilder mediaBuilder = Json.createArrayBuilder();
            for (Media media : library.getMedia()) {
                mediaBuilder.add(mediaToJson(media));
            }
            libraryBuilder.add("media", mediaBuilder.build());

            // Saves the resources
            JsonArrayBuilder resourcesBuilder = Json.createArrayBuilder();
            for (Resource resource : library.getResources()) {
                resourcesBuilder.add(resourceToJson(resource));
            }
            libraryBuilder.add("resources", resourcesBuilder.build());
            librariesBuilder.add(libraryBuilder.build());
        }

        checkLibrarySystemPersistenceJson();
        return librariesBuilder.build();
    }

    /**
     * Saves media to Json
     *
     * @param media the media to persist
     * @return the media in Json
     */
    private JsonObject mediaToJson(Media media) {
        Preconditions.checkNotNull(media, "Media should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonObjectBuilder mediaBuilder = Json.createObjectBuilder()
                .add("title", media.getTitle())
                .add("copies", media.getCopies());

        // Saves the reviews
        JsonArrayBuilder reviewsBuilder = Json.createArrayBuilder();
        for (Review review : media.getReviews()) {
            reviewsBuilder.add(reviewToJson(review));
        }
        mediaBuilder.add("reviews", reviewsBuilder.build());

        // Saves the waitlist
        JsonArrayBuilder waitlistBuilder = Json.createArrayBuilder();
        for (Member member : media.getWaitlist()) {
            waitlistBuilder.add(member.getName());
        }
        mediaBuilder.add("waitlist", waitlistBuilder.build());

        checkLibrarySystemPersistenceJson();
        return mediaBuilder.build();
    }

    /**
     * Saves a review to Json
     *
     * @param review the review to persist
     * @return the review in Json
     */
    private JsonObject reviewToJson(Review review) {
        Preconditions.checkNotNull(review, "Review should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonObjectBuilder reviewBuilder = Json.createObjectBuilder()
                .add("member", review.getMember().getName())
                .add("media", review.getMedia().getTitle())
                .add("text", review.getText())
                .add("stars", review.getStars());

        checkLibrarySystemPersistenceJson();
        return reviewBuilder.build();
    }

    /**
     * Saves a resource to Json
     *
     * @param resource the resource to persist
     * @return the resource in Json
     */
    private JsonObject resourceToJson(Resource resource) {
        Preconditions.checkNotNull(resource, "Resource should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonObjectBuilder resourceBuilder = Json.createObjectBuilder()
                .add("type", resource.getClass().getSimpleName())
                .add("number", resource.getNumber());
        // Saves the time slots that are booked
        TimeSlot[][][] bookings = resource.getMonthBookings().getMonthBookings();
        JsonArrayBuilder bookingsBuilder = Json.createArrayBuilder();
        for (int i = 0; i < bookings.length; i++) {
            for (int j = 0; j < bookings[i].length; j++) {
                for (int k = 0; k < bookings[i][j].length; k++) {
                    if (bookings[i][j][k].isBooked()) {
                        bookingsBuilder.add(timeSlotToJson(bookings[i][j][k]));
                    }
                }
            }
        }
        resourceBuilder.add("bookings", bookingsBuilder.build());

        checkLibrarySystemPersistenceJson();
        return resourceBuilder.build();
    }

    /**
     * Saves members to Json
     *
     * @param members the members to persist
     * @return the members in Json
     */
    private JsonArray membersToJson(TreeSet<Member> members) {
        Preconditions.checkNotNull(members, "Members should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonArrayBuilder membersBuilder = Json.createArrayBuilder();
        for (Member member : members) {
            Preconditions.checkNotNull(member, "Member in members should not be null.");
            JsonObjectBuilder memberBuilder = Json.createObjectBuilder()
                    .add("name", member.getName())
                    .add("password", member.getPassword());

            // Saves the loans
            JsonArrayBuilder loansBuilder = Json.createArrayBuilder();
            for (Loan loan : member.getTakenOut()) {
                loansBuilder.add(loanToJson(loan));
            }
            memberBuilder.add("loans", loansBuilder.build());

            // Saves the bookings
            JsonArrayBuilder bookingsBuilder = Json.createArrayBuilder();
            for (TimeSlot slot : member.getBookings()) {
                bookingsBuilder.add(timeSlotToJson(slot));
            }
            memberBuilder.add("bookings", bookingsBuilder.build());

            // Saves the announcements
            JsonArrayBuilder announcementsBuilder = Json.createArrayBuilder();
            for (String announcement : member.getAnnouncements()) {
                announcementsBuilder.add(announcement);
            }
            memberBuilder.add("announcements", announcementsBuilder.build());
            membersBuilder.add(memberBuilder.build());
        }

        checkLibrarySystemPersistenceJson();
        return membersBuilder.build();
    }

    /**
     * Saves a loan to Json
     *
     * @param loan the loan to persist
     * @return the loan in Json
     */
    private JsonObject loanToJson(Loan loan) {
        Preconditions.checkNotNull(loan, "Loan should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonObjectBuilder loanBuilder = Json.createObjectBuilder()
                .add("media", loan.getMedia().getTitle())
                .add("year", loan.getReturnDate().getYear())
                .add("month", loan.getReturnDate().getMonthValue())
                .add("day", loan.getReturnDate().getDayOfMonth())
                .add("hour", loan.getReturnDate().getHour())
                .add("minute", loan.getReturnDate().getMinute())
                .add("second", loan.getReturnDate().getSecond());

        checkLibrarySystemPersistenceJson();
        return loanBuilder.build();
    }

    /**
     * Saves a time slot to Json
     *
     * @param slot the time slot to persist
     * @return the time slot in Json
     */
    private JsonObject timeSlotToJson(TimeSlot slot) {
        Preconditions.checkNotNull(slot, "Time slot should not be null.");
        checkLibrarySystemPersistenceJson();

        JsonObjectBuilder timeSlotBuilder = Json.createObjectBuilder()
                .add("start hour", slot.getStartHour())
                .add("end hour", slot.getEndHour())
                .add("day", slot.getDay())
                .add("week", slot.getWeek())
                .add("booked", slot.isBooked());

        checkLibrarySystemPersistenceJson();
        return timeSlotBuilder.build();
    }

    /**
     * Loads a library from Json
     *
     * @param libraryJson the library in Json
     * @param librarySystem the library system of the library
     * @return the library
     * @throws InvalidNameException if the library name is empty
     */
    private Library libraryFromJson(JsonObject libraryJson, LibrarySystem librarySystem) throws InvalidNameException {
        Preconditions.checkNotNull(libraryJson, "Library from Json should not be null.");
        Preconditions.checkNotNull(librarySystem, "Library system should not be null.");
        checkLibrarySystemPersistenceJson();
        Library library = new Library.LibraryBuilder().name(libraryJson.getString("name")).build();

        // Load media
        for (JsonValue mediaJson : libraryJson.getJsonArray("media")) {
            Media media = mediaFromJson(mediaJson.asJsonObject(), librarySystem);
            int copies = mediaJson.asJsonObject().getInt("copies");
            for (int i = 0; i < copies; i++) {
                library.addMedia(media);
            }
        }

        // Load resources
        for (JsonValue resourceJson : libraryJson.getJsonArray("resources")) {
            library.addResource(resourceFromJson(resourceJson.asJsonObject()));
        }

        checkLibrarySystemPersistenceJson();
        return library;
    }

    /**
     * Matches a title to a hardcoded media
     *
     * @param title the title of the desired media
     * @return the media that matches the title
     */
    private Media getMediaFromTitle(String title) {
        Preconditions.checkNotNull(title, "Title should not be null.");
        checkLibrarySystemPersistenceJson();
        boolean found = false;
        int index = 0;
        while (!found && index < this.hardCodedMedia.size()) {
            if (title.equals(this.hardCodedMedia.get(index).getTitle())) {
                found = true;
            } else {
                index++;
            }
        }

        checkLibrarySystemPersistenceJson();
        return this.hardCodedMedia.get(index);
    }

    /**
     * Matches a member in the library system to a name
     *
     * @param name the name of the desired member
     * @param librarySystem the library system if the member
     * @return the member
     */
    private Member getMemberFromName(String name, LibrarySystem librarySystem) {
        Preconditions.checkNotNull(name, "Name should not be null.");
        Preconditions.checkNotNull(librarySystem, "Library system should not be null.");
        checkLibrarySystemPersistenceJson();

        Member memberByName = null;
        for (Member member : librarySystem.getMembers()) {
            if (member.getName().equals(name)) {
                memberByName = member;
            }
        }

        checkLibrarySystemPersistenceJson();
        return memberByName;
    }

    /**
     * Loads a media from Json
     *
     * @param mediaJson the media in Json
     * @param librarySystem the library system of the media
     * @return the media
     */
    private Media mediaFromJson(JsonObject mediaJson, LibrarySystem librarySystem) {
        Preconditions.checkNotNull(mediaJson, "Media from Json should not be null.");
        Preconditions.checkNotNull(librarySystem, "Library system should not be null.");
        checkLibrarySystemPersistenceJson();
        // Match media to hardcoded media
        Media media = getMediaFromTitle(mediaJson.getString("title"));

        // Load reviews
        for (JsonValue reviewJson : mediaJson.getJsonArray("reviews")) {
            try {
                media.addReview(reviewFromJson(reviewJson.asJsonObject(), media, librarySystem));
            } catch (InvalidReviewTextException | InvalidReviewStarsException e) {
                throw new RuntimeException(e);
            }
        }

        // Load waitlist
        for (JsonValue waitlistJson : mediaJson.getJsonArray("waitlist")) {
            String name = waitlistJson.toString().substring(1, waitlistJson.toString().length() - 1);
            media.addToWaitlist(getMemberFromName(name, librarySystem));
        }

        checkLibrarySystemPersistenceJson();
        return media;
    }

    /**
     * Loads a review from Json
     *
     * @param reviewJson the review in Json
     * @param media the media of the review
     * @param librarySystem the library system of the review
     * @return the review
     * @throws InvalidReviewTextException if the review text is empty
     * @throws InvalidReviewStarsException if the review stars are out of bounds
     */
    private Review reviewFromJson(JsonObject reviewJson, Media media, LibrarySystem librarySystem) throws
            InvalidReviewTextException, InvalidReviewStarsException {
        Preconditions.checkNotNull(reviewJson, "Review from Json should not be null.");
        Preconditions.checkNotNull(media, "Media should not be null.");
        Preconditions.checkNotNull(librarySystem, "Library system should not be null.");
        checkLibrarySystemPersistenceJson();

        Review.ReviewBuilder reviewBuilder = new Review.ReviewBuilder();
        reviewBuilder.media(media).text(reviewJson.getString("text")).stars(reviewJson.getInt("stars"));
        reviewBuilder.member(getMemberFromName(reviewJson.getString("member"), librarySystem));

        checkLibrarySystemPersistenceJson();
        return reviewBuilder.build();
    }

    /**
     * Loads a resource from Json
     *
     * @param resourceJson the resource in Json
     * @return the resource
     */
    private Resource resourceFromJson(JsonObject resourceJson) {
        Preconditions.checkNotNull(resourceJson, "Resource from Json should not be null.");
        checkLibrarySystemPersistenceJson();

        boolean found = false;
        int index = 0;
        // Match resource to hardcoded resource
        while (!found && index < this.hardCodedResources.size()) {
            String type = this.hardCodedResources.get(index).getClass().getSimpleName();
            int number = this.hardCodedResources.get(index).getNumber();
            if (resourceJson.getString("type").equals(type) && resourceJson.getInt("number") == number) {
                found = true;
            } else {
                index++;
            }
        }

        Resource resource = this.hardCodedResources.get(index);
        // Load bookings
        for (JsonValue timeSlotJson : resourceJson.getJsonArray("bookings")) {
            try {
                TimeSlot timeSlot = timeSlotFromJson(timeSlotJson.asJsonObject());
                resource.getMonthBookings().book(timeSlot.getWeek(), timeSlot.getDay(), timeSlot.getStartHour());
            } catch (InvalidStartHourException | InvalidEndHourException | InvalidDayException | InvalidWeekException e) {
                throw new RuntimeException();
            }
        }

        checkLibrarySystemPersistenceJson();
        return resource;
    }

    /**
     * Loads a time slot from Json
     *
     * @param timeSlotJson the time slot in Json
     * @return the time slot
     * @throws InvalidStartHourException if the time slot start hour is out of bounds
     * @throws InvalidEndHourException if the time slot end hour is out of bounds
     * @throws InvalidDayException if the time slot day is out of bounds
     * @throws InvalidWeekException if the time slot week is out of bounds
     */
    private TimeSlot timeSlotFromJson(JsonObject timeSlotJson) throws InvalidStartHourException, InvalidEndHourException,
            InvalidDayException, InvalidWeekException {
        Preconditions.checkNotNull(timeSlotJson, "Time slot from Json should not be null.");
        checkLibrarySystemPersistenceJson();
        return new TimeSlot.TimeSlotBuilder().startHour(timeSlotJson.getInt("start hour"))
                .endHour(timeSlotJson.getInt("end hour"))
                .day(timeSlotJson.getInt("day"))
                .week(timeSlotJson.getInt("week"))
                .build();
    }

    /**
     * Loads a member from Json
     *
     * @param memberJson the member in Json
     * @return the member
     * @throws InvalidNameException if the member name is empty
     * @throws InvalidPasswordException if the member password is empty
     */
    private Member memberFromJson(JsonObject memberJson) throws InvalidNameException, InvalidPasswordException {
        Preconditions.checkNotNull(memberJson, "Member from Json should not be null.");
        checkLibrarySystemPersistenceJson();
        Member member = new Member.MemberBuilder().name(memberJson.getString("name"))
                .password(memberJson.getString("password")).build();

        // Load loans
        for (JsonValue loanJson : memberJson.getJsonArray("loans")) {
            try {
                member.getTakenOut().add(loanFromJson(loanJson.asJsonObject()));
            } catch (InvalidReturnDateException e) {
                throw new RuntimeException(e);
            }
        }

        // Load bookings
        for (JsonValue bookingJson : memberJson.getJsonArray("bookings")) {
            try {
                member.getBookings().add(timeSlotFromJson(bookingJson.asJsonObject()));
            } catch (InvalidStartHourException | InvalidEndHourException | InvalidDayException | InvalidWeekException e) {
                throw new RuntimeException(e);
            }
        }

        // Load announcements
        for (JsonValue announcementJson : memberJson.getJsonArray("announcements")) {
            member.addAnnouncement(announcementJson.toString());
        }

        checkLibrarySystemPersistenceJson();
        return member;
    }

    /**
     * Loads a loan from Json
     *
     * @param loanJson the loan in Json
     * @return the loan
     * @throws InvalidReturnDateException if the loan return date is out of bounds
     */
    private Loan loanFromJson(JsonObject loanJson) throws InvalidReturnDateException {
        Preconditions.checkNotNull(loanJson, "Loan from Json should not be null.");
        checkLibrarySystemPersistenceJson();
        Loan.LoanBuilder loanBuilder = new Loan.LoanBuilder();

        loanBuilder.media(getMediaFromTitle(loanJson.getString("media")));
        LocalDateTime returnDate = LocalDateTime.of(loanJson.getInt("year"), loanJson.getInt("month"),
                loanJson.getInt("day"), loanJson.getInt("hour"), loanJson.getInt("minute"),
                loanJson.getInt("second"));
        loanBuilder.returnDate(returnDate);

        checkLibrarySystemPersistenceJson();
        return loanBuilder.build();
    }
}
