package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;

import java.util.ArrayList;
import java.util.List;

public class BorrowMedia {
    private final Library library;
    private final Member member;

    public BorrowMedia(Library library, Member member) {
        this.library = library;
        this.member = member;
    }

    public List<Media> filterMedia(String name, boolean isBook) {
        // Should i check name and category are not null??
        List<Media> filtered = new ArrayList<>();
        if (isBook) {
            for (Media media : this.library.getMedia()) {
                if (media.getTitle().toLowerCase().contains(name) && media instanceof Book) {
                    filtered.add(media);
                }
            }
        } else {
            for (Media media : this.library.getMedia()) {
                if (media.getTitle().toLowerCase().contains(name) && media instanceof DVD) {
                    filtered.add(media);
                }
            }
        }

        return filtered;
    }

    public boolean borrowMedia(Media media) {
        return this.member.borrowMedia(media);
    }

    // Need some type of time system for overdue media
}
