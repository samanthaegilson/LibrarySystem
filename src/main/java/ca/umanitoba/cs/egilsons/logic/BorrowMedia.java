package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic for borrowing {@link Media}.
 */
public class BorrowMedia {
    private LibrarySystem librarySystem;
    private final Library library;
    private final Member member;
    private LibrarySystemPersistence persistence;

    /**
     * Checking that BorrowMedia is in a valid state
     */
    private void checkBorrowMedia() {
        Preconditions.checkNotNull(library, "Library should never be null.");
        Preconditions.checkNotNull(member, "Member should never be null.");
    }

    /**
     * A constructor for BorrowMedina. Receives the library and the member
     *
     * @param librarySystem the library system of the media
     * @param member the member borrowing the media
     * @param persistence the persistence of the library system
     */
    public BorrowMedia(LibrarySystem librarySystem, Member member, LibrarySystemPersistence persistence) {
        this.librarySystem = librarySystem;
        this.library = librarySystem.getLibraries().get(0);
        this.member = member;
        this.persistence = persistence;
        checkBorrowMedia();
    }

    /**
     * Filters media by name and type
     *
     * @param name the name to filter by
     * @param isBook if the desired media is a book or dvd
     * @return a list of media
     */
    public List<Media> filterMedia(String name, boolean isBook) {
        checkBorrowMedia();
        Preconditions.checkNotNull(name, "Filter name should not be null.");
        List<Media> filtered = new ArrayList<>();
        if (isBook) {
            for (Media media : this.library.getMedia()) {
                if (media.getTitle().toLowerCase().contains(name.toLowerCase()) && media instanceof Book) {
                    filtered.add(media);
                }
            }
        } else {
            for (Media media : this.library.getMedia()) {
                if (media.getTitle().toLowerCase().contains(name.toLowerCase()) && media instanceof DVD) {
                    filtered.add(media);
                }
            }
        }

        checkBorrowMedia();
        return filtered;
    }

    /**
     * Borrows a media
     *
     * @param media the media to borrow
     * @return if the media was successfully borrowed or not
     */
    public boolean borrowMedia(Media media) {
        checkBorrowMedia();
        Preconditions.checkNotNull(media, "Media should not be null.");
        boolean borrowed = false;
        // Checks there's no one on the waitlist before them
        if (media.getWaitlist().isEmpty() || media.frontOfWaitlist(this.member)) {
            borrowed = this.member.borrowMedia(media);
        }

        // Removes the member from the waitlist if they borrowed the media
        if (borrowed && media.frontOfWaitlist(this.member)) {
            media.getWaitlist().remove(this.member);
            this.member.removeAnnouncement(media.getTitle());
        }

        this.persistence.saveLibrarySystem(this.librarySystem);
        checkBorrowMedia();
        return borrowed;
    }

    /**
     * Adds the member to a media waitlist
     *
     * @param media the media of the waitlist
     * @return the member's spot in the waitlist
     */
    public int addToWaitlist(Media media) {
        checkBorrowMedia();
        Preconditions.checkNotNull(media, "Media should not be null.");
        int spot = -1;

        // Will not add a member to the waitlist again
        if (!media.getWaitlist().contains(this.member)) {
            spot = media.addToWaitlist(this.member);
        }

        this.persistence.saveLibrarySystem(this.librarySystem);
        checkBorrowMedia();
        return spot;
    }
}
