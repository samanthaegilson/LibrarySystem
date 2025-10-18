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
        %% A collection of libraries
        -List<Library> libraries
        -TreeSet<Member> members
        
        %% For now replaces the old instance with a new instance
        +addLibrary(String) void
        %% don't want duplicates, use "set"?
        +addMember(String, List<String>) void
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
        %% interface?
        -Text name
        -Collection media
        -Collection resources
        -Map map
        
        +addResource() void
        +addMedia() void
        +removeMedia(Media) void
    }
    Library --* Media
    Library --* Resource
    Library --* Map

    note for Library "Invariant properties:
    <ul>
        <li>media != null
        <li>resources != null
        <li>loop: no media are null in media.
        <li>loop: no resources are null in resources.
    </ul>"
    
    class Member {
        %% class
        %% becoming member for entire system
        -Text name
        -Collection constraints
        -Collection contactInfo
        -Collection currentMedia
        -Collection reviews
        %% Library card number?
        
        %% asks for member, so should be in library?
        +addReview(Review) void
    }
    Member --* Review

    note for Member "Invariant properties:
    <ul>
        <li>name != null
        <li>name.length() >= 1
        <li>constraints != null
        <li>contactInfo != null
        <li>currentMedia != null
        <li>loop: no media are null in currentMedia.
    </ul>"

    
    class Media {
        %%record
        %% class for copies and lists
        -Text name
        -Text creator
        -Format format
        -Category category
        -PositiveNumber copies
        -Collection reviews
        %% should waitlist be a stack?
 
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
        <li>waitlist != null
        <li>copies >= 0
        <li>reviews != null
        <li>loop: no members are null in waitlist.
        <li>loop: no reviews are null in reviews.
    </ul>"
    
    class Resource {
        <<interface>>
        %% time
        +bookTime(Time) boolean
        +cancelBooking(Time) boolean
    }
    
    class Computer {
        -int number
        -Collection bookings
        +bookTime(Time) boolean
        +cancelBooking(Time) boolean
    }
    Computer ..|> Resource
    
    class Room {
        -int number
        -Collection bookings
        +bookTime(Time) boolean
        +cancelBooking(Time) boolean
    }
    Room ..|> Resource
    
    class Map {
        -Display map
        %% Written list of symbols
        -String legend
    }
    Map --o MediaCategory
    
    class Review {
        %% can also review resources!!!
        -Member member
        -Media media
        -Text review
        -PositiveNumber stars
        %% Stars?
        %% record?
    }
    
    note for Review "Invariant Properties:
    <ul>
        <li>member != null
        <li>media != null
        <li>review != null
        <li>review.length() >= 1
        <li>stars > 0
        <li>stars <= 5
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