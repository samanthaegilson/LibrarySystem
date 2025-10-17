package ca.umanitoba.cs.egilsons.domain;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<Media> media;
    private List<Resource> resources;

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

    public void removeMedia(String name) {
        boolean found = false;
        int i = 0;
        while (!found || i > this.media.size()) {
            if (this.media.get(i).getName().equalsIgnoreCase(name)) {
                found = true;
            } else {
                i++;
            }
        }
        if (found) {
            this.media.remove(i);
        } else {
            System.out.println("No such media found.");
        }
    }
}
