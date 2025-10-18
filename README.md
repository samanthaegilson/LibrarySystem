---
title: LibrarySystem
author: Samantha Egilson (egilsons@myumanitoba.ca)
date: Fall 2025
---

# Overview

LibrarySystem is an implementation of an online system for a library for COMP 2450 in
Fall 2025. LibrarySystem can create libraries with a map and different kinds of media and 
resources. LibrarySystem can also create members who can borrow media, return them, add 
themselves to waitlists for media that are already borrowed, request media from other 
libraries within the system and write reviews for media. Members can also book resources 
at an available time. Members can have various ways of being contacted and can have
constraints placed on their account.

# Running

This project was developed using IntelliJ IDEA and uses Maven, so there are two
ways to run it:

1. Open the class called `Main.java` and click the green play button on the
   `main`method, or
2. Run Maven on the command line:

   ```
   mvn compile exec:java -Dexec.mainClass="ca.umanitoba.cs.egilsons.Main"
   ```

# Domain model

Here's my domain model:

```mermaid
classDiagram
    class LibrarySystem {
        -List<Library> libraries
        %% No duplicate member
        -TreeSet<Member> members
        
        +addLibrary(String name) void
        +addMember(String name, List<String> contactInfo) void
        +removeMember() boolean
    }
    LibrarySystem --* Library
    LibrarySystem --* Member

    note for LibrarySystem "Invariant properties:
    <ul>
        <li>libraries != null
        <li>members != null
        <li>loop: no libraries are null in libraries.
        <li>loop: no members are null in members.
    </ul>"
    
    class Library {
        -String name
        -List<Media> media
        -List<Resource> resources
        -Map map
        
        +addResource(Resource resource) void
        +addMedia(Media media) void
        +removeMedia(Media media) void
        -updateMap() void
    }
    Library --* Media
    Library --* Resource
    Library --* Map

    note for Library "Invariant properties:
    <ul>
        <li>name != null
        <li>name.length() >= 1
        <li>media != null
        <li>resources != null
        <li>map != null
        <li>loop: no media are null in media.
        <li>loop: no resources are null in resources.
    </ul>"
    
    class Member {
        -String name

        +compareTo(Member other) int
    }
    Member --* Review

    note for Member "Invariant properties:
    <ul>
        <li>name != null
        <li>name.length() >= 1
    </ul>"

    
    class Media {
        -String name
        -String creator
        -Format format
        -Category category
        %% Has to be a positive number
        -int copies
        -List<Reviews> reviews
 
        +equals(Media other) boolean
        +addCopy() void
        +addReview() void
    }
    Media --* Review
    Media --* MediaCategory
    Media --* MediaFormat

    note for Media "Invariant properties:
    <ul>
        <li>name != null
        <li>name.length() >= 1
        <li>creator != null
        <li>creator.length() >= 1
        <li>format != null
        <li>category != null
        <li>copies >= 0
        <li>reviews != null
        <li>loop: no reviews are null in reviews.
    </ul>"
    
    class Resource {
        <<interface>>
    }
    Resource --* ResourceType
    
    class Computer {
        -ResourceType type
        -int number
        -static int count
    }
    Computer ..|> Resource

    note for Computer "Invariant properties:
    <ul>
        <li>type != null
        <li>number > 0
        <li>count >= 0
    </ul>"
    
    class Room {
        -ResourceType type
        -int number
        -static int count
    }
    Room ..|> Resource

    note for Room "Invariant properties:
    <ul>
        <li>type != null
        <li>number > 0
        <li>count >= 0
    </ul>"
    
    class Map {
        %% An overhead visual representation of the map
        -char[][] display
        -String[] legend
    }

    note for Map "Invariant properties:
    <ul>
        <li>display != null
        <li>legend != null
        <li>loop: no entries are null in legend.
        <li>loop: all entries.length() >= 1 in legend.
    </ul>"
    
    class Review {
        <<record>>
        -Member member
        -Media media
        -String text
        %% Number between 1 and 5
        -int stars
    }
    
    note for Review "Invariant Properties:
    <ul>
        <li>member != null
        <li>media != null
        <li>text != null
        <li>text.length() >= 1
        <li>stars > 0 && stars <= 5
    </ul>"
    
    class MediaCategory {
        <<enumeration>>
        FANTASY,
        YOUNG ADULT,
        ROMANCE,
        SCIENCE FICTION,
        CHILDREN'S,
        HISTORICAL FICTION,
        NON-FICTION,
        MYSTERY,
        HORROR
    }
    
    class MediaFormat {
        <<enumeration>>
        BOOK,
        MOVIE,
        VIDEO GAME,
        BOARD GAME
    }

    class ResourceType{
        <<enumeration>>
        ROOM,
        COMPUTER
    }
```