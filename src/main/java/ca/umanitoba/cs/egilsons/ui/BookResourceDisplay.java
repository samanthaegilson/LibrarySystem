package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.logic.BookResource;

import java.util.Scanner;

public class BookResourceDisplay {
    private final BookResource bookResource;
    private final Library library;
    private final Member member;
    private final Scanner keyboard;

    public BookResourceDisplay(Library library, Member member) {
        this.bookResource = new BookResource(library, member);
        this.library = library;
        this.member = member;
        this.keyboard = new Scanner(System.in);
    }


}
