package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Member;

/**
 * Prints the information of a {@link Member}.
 */
public class MemberPrinter {
    private Member member;

    public MemberPrinter(final Member member) {
        this.member = member;
    }

    public void print() {
        System.out.println("Member name: " + this.member.getName());
    }
}
