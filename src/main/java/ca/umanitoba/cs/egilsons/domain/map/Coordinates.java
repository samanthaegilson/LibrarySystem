package ca.umanitoba.cs.egilsons.domain.map;

/**
 * A coordinate of a {@link ca.umanitoba.cs.egilsons.domain.resource.Resource} or
 * {@link ca.umanitoba.cs.egilsons.domain.media.Media} on the {@link Map}.
 *
 * @param row the row of the coordinate
 * @param column the column of the coordinate
 */
public record Coordinates(int row, int column) {
}
