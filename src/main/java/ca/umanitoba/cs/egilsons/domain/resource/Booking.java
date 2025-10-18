package ca.umanitoba.cs.egilsons.domain.resource;

/**
 * A booking. Bookings are a reserved time for a {@link Resource}.
 *
 * @param startHour the hour the booking begins.
 * @param startMinute the minute the booking begins.
 * @param endHour the hour the booking ends.
 * @param endMinute the minute the booking ends.
 */
public record Booking(int startHour, int startMinute, int endHour, int endMinute) {
}
