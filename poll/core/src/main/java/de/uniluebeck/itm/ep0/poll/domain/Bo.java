package de.uniluebeck.itm.ep0.poll.domain;

import de.uniluebeck.itm.ep0.poll.exception.PollException;

import java.io.Serializable;

/**
 * Common interface that encompasses all business objects.
 */
public interface Bo extends Serializable {

    /**
     * Converts a business object to a transport object.
     *
     * @return The corresponding transport object
     * @throws PollException Thrown, if the
     *                       value for the given language cannot be found.
     */
    public Xo toXo() throws PollException;
}
