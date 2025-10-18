package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * A library. Libraries contain {@link Media} and {@link Resource}.
 */
public class Library {
    private String name;
    private List<Media> media;
    private List<Resource> resources;

    /**
     * Constructor for a library. Receives input for the name
     *
     * @param name the name of the library
     */
    public Library(String name) {
        this.name = name;
        this.media = new ArrayList<>();
        this.resources = new ArrayList<>();
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

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    /**
     * Adds a media to the library's media
     *
     * @param newMedia the media being added
     */
    public void addMedia(Media newMedia) {
        boolean found = false;
        int index = 0;
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
     * @param mediaIndex the index of the media to be removed
     */
    public void removeMedia(int mediaIndex) {
        this.media.remove(mediaIndex);
    }
}
