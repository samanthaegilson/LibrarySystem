package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReturnDateException;
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
    private Loan(Media media, LocalDateTime returnDate) {
        this.media = media;
        this.borrowDate = LocalDateTime.now();
        this.returnDate = returnDate;
        checkLoan();
    }

    /**
     * Builder class for a loan
     */
    public static class LoanBuilder {
        private Media media;
        private LocalDateTime returnDate;
        private final long SECONDS = 30;

        /**
         * Checks that a media for a loan is valid
         *
         * @param media the media of the loan
         * @return the loan builder
         */
        public LoanBuilder media(Media media) {
            Preconditions.checkNotNull(media, "Media should not be null.");
            this.media = media;
            return this;
        }

        /**
         * Checks that a return date for a loan is valid
         *
         * @param returnDate the return date of the loan
         * @return the loan builder
         * @throws InvalidReturnDateException if the return date is past 30 seconds from now
         */
        public LoanBuilder returnDate(LocalDateTime returnDate) throws InvalidReturnDateException {
            Preconditions.checkNotNull(returnDate, "Return date should never be null.");
            LocalDateTime latest = LocalDateTime.now().plusSeconds(SECONDS);
            if (returnDate.isAfter(latest)) {
                throw new InvalidReturnDateException();
            }
            this.returnDate = returnDate;
            return this;
        }

        /**
         * Creates a loan
         *
         * @return the loan
         */
        public Loan build() {
            if (this.returnDate == null) {
                this.returnDate = LocalDateTime.now().plusSeconds(SECONDS);
            }
            return new Loan(this.media, this.returnDate);
        }
    }

    public Media getMedia() {
        return this.media;
    }

    public LocalDateTime getReturnDate() {
        return this.returnDate;
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
