package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidNameException;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A library. Libraries contain {@link Media} and {@link Resource}.
 */
public class Library {
    private final String name;
    private List<Media> media;
    private List<Resource> resources;
    private Map map;

    /**
     * Invariant properties for Library
     */
    private void checkLibrary() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1, "Library name should have at least 1 symbol.");
        Preconditions.checkNotNull(media, "Media should never be null.");
        Preconditions.checkNotNull(resources, "Resources should never be null.");
        Preconditions.checkNotNull(map, "Map should never be null.");

        for (Media med : media) {
            Preconditions.checkNotNull(med, "Media in media should never be null.");
        }

        for (Resource resource : resources) {
            Preconditions.checkNotNull(resource, "Resources in resources should never be null.");
        }
    }

    /**
     * Creates a new map to reflect any changes in the library
     */
    private void updateMap() {
        this.map = new Map(this);
    }

    /**
     * Constructor for a library. Receives input for the name
     *
     * @param name the name of the library
     */
    private Library(String name) {
        this.name = name;
        this.media = new ArrayList<>();
        this.resources = new ArrayList<>();
        updateMap();
        checkLibrary();
    }

    public static class LibraryBuilder {
        private String name;

        public LibraryBuilder name(String name)  {
            Preconditions.checkNotNull(name, "Name should not be null.");

            if (name.isEmpty()) {
                // throw new InvalidNameException();
            }

            this.name = name;
            return this;
        }

        public Library build() {
            return new Library(this.name);
        }
    }

    public String getName() {
        return this.name;
    }

    public List<Media> getMedia() {
        return this.media;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public Map getMap() {
        return this.map;
    }

    /**
     * Adds a resource to the library's resources and updates the map
     *
     * @param resource the resource to be added
     */
    public void addResource(Resource resource) {
        this.resources.add(resource);
        updateMap();
    }

    /**
     * Adds a media to the library's media
     *
     * @param newMedia the media to be added
     */
    public void addMedia(Media newMedia) {
        boolean found = false;
        int index = 0;
        // Checks if the new media is already in the media list
        while (!found && index < this.media.size()) {
            if (newMedia.equals(this.media.get(index))) {
                found = true;
            } else {
                index++;
            }
        }

        if (found) {
            this.media.get(index).addCopy();
        } else {
            this.media.add(newMedia);
        }
    }

    /**
     * Removes media from the library's media
     *
     * @param media the media to be removed
     */
    public void removeMedia(Media media) {
        this.media.remove(media);
    }
}
