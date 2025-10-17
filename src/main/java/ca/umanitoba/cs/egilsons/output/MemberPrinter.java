package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Media;
import ca.umanitoba.cs.egilsons.domain.Member;

public class MemberPrinter {
    private Member member;

    public MemberPrinter(final Member member) {
        this.member = member;
    }

    public void print() {
        System.out.println("Member name: " + this.member.getName());
        System.out.println("Contact information : ");
        for (int i = 0; i < this.member.getContactInfo().size(); i++) {
            System.out.println(this.member.getContactInfo().get(i));
        }
        System.out.println("Constraints: ");
        for (int i = 0; i < this.member.getConstraints().size(); i++) {
            System.out.println(this.member.getConstraints().get(i));
        }
        System.out.println("Current media: ");
        for (int i = 0; i < this.member.getCurrentMedia().size(); i++) {
            System.out.println(this.member.getCurrentMedia().get(i).getName());
        }
        System.out.println("Reviews: ");
        for (int i = 0; i < this.member.getReviews().size(); i++) {
            System.out.println(this.member.getReviews().get(i).getStars() + ", "
                    + this.member.getReviews().get(i).getMedia().getName());
        }
    }
}
