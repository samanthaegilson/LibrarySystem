package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;

import java.util.List;

public class BookResource {
    private final Library library;
    private final Member member;

    public BookResource(Library library, Member member) {
        this.library = library;
        this.member = member;
    }

    public List<Resource> filterResources() {
        // filter by week day and time?
        // People should be able to manually view/search for available timeslots using filters.
        // For example, they could request to view available
        // timeslots for the next week, or the next X available timeslots after 1:00pm
        // (where you can specify the value of X). They should also be able
        // to view a range of dates’ timeslots at a glance.

        // Options for filter by week, day of time

        return null;
    }

    public void bookResource(Resource resource) {

    }
}
