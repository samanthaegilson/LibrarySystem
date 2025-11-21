package ca.umanitoba.cs.egilsons.domain.media;

import com.google.common.base.Preconditions;

import java.time.LocalDateTime;

/**
 * A loan. A {@link ca.umanitoba.cs.egilsons.domain.Member} can loan a piece of {@link Media}.
 */
public class Loan {
    private final Media media;
    private LocalDateTime borrowDate;
    private final LocalDateTime returnDate;

    /**
     * Invariant properties for Loan
     */
    private void checkLoan() {
        Preconditions.checkNotNull(media, "Media should never be null.");
        Preconditions.checkNotNull(borrowDate, "Borrow date should never be null.");
        Preconditions.checkNotNull(returnDate, "Return date should never be null.");
    }

    /**
     * A constructor for Loan. Receives the media of the loan
     *
     * @param media the media of the loan
     */
    public Loan(Media media) {
        final long SECONDS = 30;
        this.media = media;
        this.borrowDate = LocalDateTime.now();
        LocalDateTime returnAdjustment = borrowDate.plusSeconds(SECONDS); // for the purposes of testing is only 30
                                                                          // seconds after taking out
        this.returnDate = LocalDateTime.of(returnAdjustment.getYear(), returnAdjustment.getMonth(),
                returnAdjustment.getDayOfMonth(), returnAdjustment.getHour(), returnAdjustment.getMinute(),
                returnAdjustment.getSecond());
        checkLoan();
    }

    public Media getMedia() {
        return media;
    }

    /**
     * Checks if the media is overdue
     *
     * @return if the media is overdue or not
     */
    public boolean isOverdue() {
        checkLoan();
        boolean overdue = false;
        this.borrowDate = LocalDateTime.now();
        if (this.borrowDate.isAfter(this.returnDate)) {
            overdue = true;
        }
        checkLoan();
        return overdue;
    }
}
