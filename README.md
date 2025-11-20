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

## Flow of interaction

### Sign in
Here is the flowchart for the "Enter account" task...

```mermaid
flowchart
    subgraph ENTER ACCOUNT
        %% Should logout be apart of this??
        signInScreen[[Sign In Screen]]

        signInScreen ==No Account==> chooseCredentials
        signInScreen ==Have An Account==> loginToAccount

        chooseCredentials[Choose Credentials]
        makeAccount{Make Account}
        
        %% Enter name and other information too?
        chooseCredentials ==Name, Password==> makeAccount
        makeAccount -.Account Created.-> homeScreen
        
        loginToAccount[Login To Account]
        checkUser{Check User}
        homeScreen[[Home Screen]]
        
        loginToAccount ==Make new account==> chooseCredentials
        loginToAccount ==Name, Password==> checkUser
        checkUser -.No Such User Exists.->loginToAccount
        checkUser -.Credentials Match Account.->homeScreen
        homeScreen -.Log out.->signInScreen
            
    end
```

### Borrow Media
Here is the flowchart for the "Borrow Media" task

```mermaid
flowchart
    subgraph BORROW MEDIA
        %% Account for constraints?
        browseMedia[[Browse Media]]
        
        filterSelection[Filter Selection]
        filterMedia{Filter Media}
        
        browseMedia ==Filter==> filterSelection
        filterSelection==Selected Filter==> filterMedia
        filterMedia-.Filter Applied.->browseMedia
        browseMedia ==Chosen Media==> mediaInfo
        
        mediaInfo[Media Info]
        borrowMedia{Borrow Media}
        mediaBorrowed[[Media Borrowed]]
        
        mediaInfo-.Cancel.->browseMedia
        mediaInfo-.Selected Media.->borrowMedia
        borrowMedia-.No Copies Available.->waitlistInformation
        borrowMedia-.Media borrowed.-> mediaBorrowed

        waitlistInformation[Waitlist Information]
        addToWaitlist{Add to Waitlist}
        
        waitlistInformation-.Join Waitlist.-> addToWaitlist
        
    end
```

### Book Resource
Here is the flowchart for the "Book Resource" task

```mermaid
flowchart
    subgraph BOOK RESOURCE
        %% Divide by hours
        availableTimes[[Available Timeslots Screen]]
        
        filterChoice[Filter Choice]
        filterTimes{Filter Times}
        
        availableTimes==Filter==>filterChoice
        filterChoice==Selected filter==>filterTimes
        filterTimes-.Filter applied.->availableTimes
        
        bookTime{bookTime}
        resourceBooked[[Resource Booked]]
        
        availableTimes==Book time==>bookTime
        bookTime-.Time booked.->resourceBooked
        
    end
```

### Find Item
Here is the flowchart for the "Find Item" task

```mermaid
flowchart
    subgraph FIND ITEM
        enterItem[[Enter Item]]
        
        findItemLocation{Find Item Location}
        findItemPath{Find Item Path}
        showItemPath[[Show Item Path]]
        
        enterItem==Chosen item==>findItemLocation
        findItemLocation-.Item location.->findItemPath
        findItemPath-.Path to item.->showItemPath
        
    end
```

### Return Media
Here is the flowchart for the "Return Media" task

```mermaid
flowchart
    subgraph RETURN MEDIA 
        returnScreen[[Return Screen]]
        
        makeReview[Make Review]
        addReview{Add Review}
        
        returnScreen==Review==>makeReview
        %% Need to make more specific input??
        makeReview==Review information==>addReview
        addReview-.Review added.->returnScreen
        
        selectReview[Review Selection]
        reviewInformation[Review Information]
        
        returnScreen==Read review==>selectReview
        selectReview==Chosen review==>reviewInformation
        reviewInformation-.Review read.->returnScreen
        
        returnMedia{Return Media}
        mediaReturned[[Media Returned]]
        
        returnScreen==Return media==>returnMedia
        returnMedia-.Returned.->mediaReturned
        
    end
```

# Domain model

## Changes

* I realized when implementing my flow of interaction that
  `CoolObject` really should have a `decreaseCoolness` method
* 

## Diagram
Here's my domain model:

```mermaid
classDiagram
    class LibrarySystem {
        %% Collection of libraries
        -List~Library~ libraries
        %% Collection of members with no duplicate members
        -TreeSet~Member~ members
        
        +addLibrary(Library library) void
        +addMember(Member member) void
        +removeMember(Member member) boolean
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
        %% Text for the name of the library
        -String name
        %% Collection of media in the library
        -List~Media~ media
        %% Collection of resources in the library
        -List~Resource~ resources
        %% An overhead visual representation of 
        %% the library
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
        %% Text for the name of the member
        -String name
        %% Text for the password of the member
        -String password
        %% Collection of loans taken out of the library
        -List~Loan~ takenOut
        %% Collection of reserved slots at a resource
        %% in the library
        -List~TimeSlot~ bookings
        %% Collection of titles that have become available
        -List~String~ announcements

        +hasOverdueMedia() boolean
        +compareTo(Member other) int
        +borrowMedia(Media media) boolean
        +returnMedia(Media media) void
        +bookResource(TimeSlot slot, Resource resource) void
        +addAnnouncement(String title) void
        +removeAnnouncement(String title) void
    }
    Member --o Loan
    Member --o TimeSlot

    note for Member "Invariant properties:
    <ul>
        <li>name != null
        <li>name.length() >= 1
        <li>password != null
        <li>password.length() >= 1
        <li>takenOut != null
        <li>bookings != null
        <li>announcements != null
        <li>loop: no loans are null in takenOut.
        <li>loop: no time slots are null in bookings.
        <li>loop: no strings are null in announcements.
    </ul>"
    
    class Media {
        <<interface>>
        +frontOfWaitlist(Member member) boolean
        +equals(Media other) boolean
        +addCopy() void
        +takeOutCopy() Media
        +addToWaitlist(Member member) void
        +addReview(Review review) void
    }
    Media --* Review
    Media --* MediaCategory
    Media --o Coordinates
    Media --o Member

    class Book {
        %% Text for the title of the book
        -String title
        %% Text for the name of the author
        -String author
        %% A positive number of pages
        -int pages
        %% The type of category or genre
        -MediaCategory category
        %% Has to be a positive number or zero
        -int copies
        %% Collection of reviews about the book
        -List~Reviews~ reviews
        %% Collection of members waiting to take out the book
        -Queue~Member~ waitlist
        %% Coordinates on the library map
        -Coordinates coordinates

        +frontOfWaitlist(Member member) boolean
        +equals(Media other) boolean
        +addCopy() void
        +takeOutCopy() Media
        +addToWaitlist(Member member) void
        +addReview(Review review) void
    }
    Book ..|> Media

    note for Book "Invariant properties:
    <ul>
        <li>title != null
        <li>title.length() >= 1
        <li>author != null
        <li>author.length() >= 1
        <li>pages > 0
        <li>category != null
        <li>copies >= 0
        <li>reviews != null
        <li>waitlist != null
        <li>coordinates != null
        <li>loop: no reviews are null in reviews.
        <li>loop: no members are null in waitlist.
    </ul>"

    class DVD {
        %% Text for the title of the DVD
        -String title
        %% Text for the name of the director
        -String director
        %% A positive number for the length of the 
        %% movie
        -int runTime
        %% The type of category or genre
        -MediaCategory category
        %% Has to be a positive number or zero
        -int copies
        %% Collection of reviews about the DVD
        -List~Reviews~ reviews
        %% Collection of members waiting to take out the DVD
        -Queue~Member~ waitlist
        %% Coordinates on the library map
        -Coordinates coordinates

        +frontOfWaitlist(Member member) boolean
        +equals(Media other) boolean
        +addCopy() void
        +takeOutCopy() Media
        +addToWaitlist(Member member) void
        +addReview(Review review) void
    }
    DVD ..|> Media

    note for DVD "Invariant properties:
    <ul>
        <li>title != null
        <li>title.length() >= 1
        <li>director != null
        <li>director.length() >= 1
        <li>runTime > 0
        <li>category != null
        <li>copies >= 0
        <li>reviews != null
        <li>waitlist != null
        <li>coordinates != null
        <li>loop: no reviews are null in reviews.
        <li>loop: no members are null in waitlist.
    </ul>"
    
    class Loan {
        %% The media the loan is for
        -Media media
        %% The time starting when the media was borrowed
        -LocalDateTime borrowDate
        %% The time the media needs to be returned by
        -LocalDateTime returnDate
        
        +isOverdue() boolean
    }
    Loan --o Media

    note for Loan "Invariant properties:
    <ul>
        <li>media != null
        <li>borrowDate != null
        <li>returnDate != null
    </ul>"
    
    class Resource {
        <<interface>>
    }
    Resource --* Booking
    Resource --o Coordinates
    
    class Computer {
        %% A positive number representing the
        %% amount in a library
        -int number
        %% Time slots for the month that can
        %% be booked
        -Booking monthBookings
        %% Has to be a positive number or zero
        -static int count
        %% Coordinates on the library map
        -Coordinates coordinates
    }
    Computer ..|> Resource

    note for Computer "Invariant properties:
    <ul>
        <li>number > 0
        <li>monthBookings != null
        <li>count >= 0
        <li>coordinates != null
    </ul>"
    
    class Room {
        %% A positive number representing the
        %% amount in a library
        -int number
        %% Time slots for the month that can
        %% be booked
        -Booking monthBookings
        %% Has to be a positive number or zero
        -static int count
        %% Coordinates on the library map
        -Coordinates coordinates
    }
    Room ..|> Resource

    note for Room "Invariant properties:
    <ul>
        <li>number > 0
        <li>monthBookings != null
        <li>count >= 0
        <li>coordinates != null
    </ul>"
    
    class Booking {
        -TimeSlot[][][] monthBookings
        
        +book(int week, int day, int startTime) void
    }
    Booking --* TimeSlot

    note for Booking "Invariant properties:
    <ul>
        <li>monthBookings != null
        <li>loop: no time slots are null in monthBookings.
    </ul>"
    
    class TimeSlot {
        %% Has to be between 8am and 7pm
        -int startHour;
        %% Has to be between 9am and 8pm
        -int endHour;
        %% Has to be between 1 and 7
        -int day;
        %% Has to be between 1 and 4
        -int week;
        %% Shows if the time slot has been reserved
        -boolean booked;
        
        +book() boolean
    }

    note for TimeSlot "Invariant properties:
    <ul>
        <li>startHour >= 8 && startHour < 20
        <li>endHour > 8 && endHour <= 20
        <li>day >= 1 && day <= 7
        <li>week >= 1 && week <= 4
    </ul>"

    class Review {
        %% The member who made the review
        -Member member
        %% The media the review is about
        -Media media
        %% Optional text that expresses the member's 
        %% opinion on the media
        -String text
        %% Number between 1 and 5
        -int stars
    }
    Review --o Member

    note for Review "Invariant Properties:
    <ul>
        <li>member != null
        <li>media != null
        <li>text != null
        <li>text.length() >= 1
        <li>stars > 0 && stars <= 5
    </ul>"
    
    class Map {
        %% An overhead visual representation of the map
        -MapType[][] display
        %% Coordinates for the kiosk on the map
        -Coordinates kiosk
    }
    Map --* MapType
    Map --* Coordinates
    Map --o Media
    Map --o Resource

    note for Map "Invariant properties:
    <ul>
        <li>display != null
        <li>kiosk != null
    </ul>"
    
    class Coordinates {
        <<record>>
        -int row
        -int column
    }
    
    class Stack {
        <<interface>>
        +push(T item) void
        +pop() T
        +size() int
        +isEmpty() boolean
        +peek() T
    }
    
    class LinkedListStack {
        %% The top of the stack
        -Node~T~ top
        %% The amount of entries in the stack
        -int nodeCount

        +push(T item) void
        +pop() T
        +size() int
        +isEmpty() boolean
        +peek() T
    }
    LinkedListStack ..|> Stack
    
    class MapType {
        <<enumeration>>
        WALL,
        EXIT,
        DESK,
        KIOSK,
        ROOM_DOOR,
        COMPUTER,
        FANTASY_SECTION,
        YOUNG_ADULT_SECTION,
        ROMANCE_SECTION,
        SCIENCE_FICTION_SECTION,
        CHILDREN_SECTION,
        HISTORICAL_FICTION_SECTION,
        NON_FICTION_SECTION,
        MYSTERY_SECTION,
        HORROR_SECTION,
        PATH,
        DESTINATION
    }
    
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

```