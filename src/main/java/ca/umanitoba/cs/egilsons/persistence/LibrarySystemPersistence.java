package ca.umanitoba.cs.egilsons.persistence;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;

/**
 * An interface for persisting
 */
public interface LibrarySystemPersistence {
    /**
     * Persists a library system
     *
     * @param librarySystem the library system to persist
     */
    void saveLibrarySystem(LibrarySystem librarySystem);

    /**
     * Loads the library system
     *
     * @return the library system
     */
    LibrarySystem loadLibrarySystem();
}
