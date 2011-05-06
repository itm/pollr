package de.uniluebeck.itm.ep0.poll;

import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoTextOption;
import de.uniluebeck.itm.ep0.poll.exception.PollException;
import de.uniluebeck.itm.ep0.poll.service.PollService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for all {@link de.uniluebeck.itm.ep0.poll.service.PollService} methods.
 */
public class ClassicPollServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollServiceTest.class);

    /**
     * Instance of the {@link de.uniluebeck.itm.ep0.poll.service.PollService} to be tested.
     */
    private PollService pollService;

    @Before
    public void setUp() {
        final ApplicationContext ctx = new ClassPathXmlApplicationContext("testContext.xml");
        pollService = ctx.getBean(PollService.class);
    }

    @After
    public void tearDown() {
        pollService = null;
    }

    /**
     * Test for storing {@link de.uniluebeck.itm.ep0.poll.domain.BoPoll}s into a database.
     *
     * Set timeout to 10 seconds.
     *
     * @throws de.uniluebeck.itm.ep0.poll.exception.PollException when an error occurs during XO <-> BO conversion
     */
    @Test(timeout = 10000)
    public void testAddPoll() throws PollException {
        XoPoll poll = createPoll();

        XoPoll persistedPoll = pollService.addPoll(poll);

        // The returned object must not be null!
        assertNotNull("persistedPoll is null", persistedPoll);

        // Hibernate should have generated an ID, so let's test it.
        assertNotNull("persistedPoll's ID is null", persistedPoll.getId());

        // Write the object to the log.
        LOGGER.info(persistedPoll.toString());
    }

    /**
     * Test for loading {@link de.uniluebeck.itm.ep0.poll.domain.BoPoll}s from a database.
     *
     * Set timeout to 10 seconds.
     *
     * @throws de.uniluebeck.itm.ep0.poll.exception.PollException when an error occurs during XO <-> BO conversion
     */
    @Test(timeout = 10000)
    public void testGetPoll() throws PollException {
        XoPoll poll = createPoll();

        XoPoll persistedPoll = pollService.addPoll(poll);

        // The returned object must not be null!
        assertNotNull("persistedPoll is null", persistedPoll);

        // Hibernate should have generated an ID, so let's test it.
        assertNotNull("persistedPoll's ID is null", persistedPoll.getId());

        XoPoll loadedPoll = pollService.getPoll(Integer.parseInt(persistedPoll.getId()));

        assertNotNull(loadedPoll);
        assertEquals(persistedPoll.getId(), loadedPoll.getId());

        loadedPoll = pollService.getPoll(persistedPoll.getUuid());

        assertNotNull(loadedPoll);
        assertEquals(persistedPoll.getUuid(), loadedPoll.getUuid());

        // Write the object to the log.
        LOGGER.info(loadedPoll.toString());
    }

    private XoPoll createPoll() {
        XoPoll poll = new XoPoll();
        poll.setName(new XoLocalizedString("A test poll"));

        XoOptionList optionList = new XoOptionList(new XoLocalizedString("Test Option List (Which drink?)"));

        XoTextOption option1 = new XoTextOption();
        option1.setText(new XoLocalizedString("Test Option A (Beer)"));
        option1.setDescription(new XoLocalizedString("Description A"));

        XoTextOption option2 = new XoTextOption();
        option2.setText(new XoLocalizedString("Test Option B (Whine)"));
        option2.setDescription(new XoLocalizedString("Description B"));

        XoTextOption option3 = new XoTextOption();
        option3.setText(new XoLocalizedString("Test Option C (Whiskey)"));
        option3.setDescription(new XoLocalizedString("Description C"));

        List<XoOption> options = new ArrayList<XoOption>();
        options.add(option1);
        options.add(option2);
        options.add(option3);

        optionList.setOptions(options);

        List<XoOptionList> optionLists = new ArrayList<XoOptionList>();
        optionLists.add(optionList);

        poll.setOptionLists(optionLists);

        return poll;
    }
}

