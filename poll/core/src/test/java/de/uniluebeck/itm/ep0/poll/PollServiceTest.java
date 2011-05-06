package de.uniluebeck.itm.ep0.poll;

import de.uniluebeck.itm.ep0.poll.domain.VoteType;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;
import de.uniluebeck.itm.ep0.poll.exception.PollException;
import de.uniluebeck.itm.ep0.poll.service.PollService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.PersistenceException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PollServiceTest {

    private final static Logger LOG = LoggerFactory.getLogger(PollServiceTest.class);

    private final static String LOCALE_EN = "en";

    /**
     * Performs a simple Voting using the <code>PollService</code>.
     */
    @Test
    public void simpleServiceUsage() throws PollException {

        // Create the spring container using the XML configuration in
        // testContext.xml
        final ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "testContext.xml");

        // Retrieve the beans we'll use during testing
        final PollService pollService = ctx.getBean(PollService.class);

        // Persist some test data
        final XoPoll poll = TestDataHelper.createAndAddPoll(pollService);
        final XoVote wineYes = pollService.addVote(new XoVote("Soenke", VoteType.YES,
                TestDataHelper.getIdForTextOption(poll, LOCALE_EN, "Wine")));

        final XoVote whiskeyMaybe = pollService.addVote(new XoVote("Soenke", VoteType.MAYBE,
                TestDataHelper.getIdForTextOption(poll, LOCALE_EN, "Whiskey")));

        final XoVote barYes = pollService.addVote(new XoVote("Soenke", VoteType.YES,
                TestDataHelper.getIdForTextOption(poll, LOCALE_EN, "Bar")));

        final XoVote dateVote = pollService.addVote(new XoVote("Soenke", VoteType.YES,
                TestDataHelper.getIdForDateOption(poll, LOCALE_EN, "tomorrow")));

        assertThat(pollService.getPolls().size(), equalTo(1));

        assertThat(pollService.getVotes(TestDataHelper.getIdForTextOptionInt(poll, LOCALE_EN, "Wine")).size(), equalTo(1));
        assertThat(pollService.getVotes(TestDataHelper.getIdForTextOptionInt(poll, LOCALE_EN, "Whiskey")).size(), equalTo(1));
        assertThat(pollService.getVotes(TestDataHelper.getIdForTextOptionInt(poll, LOCALE_EN, "Bar")).size(), equalTo(1));

        // One shall not be able to vote for the same option a second or third time
        XoVote barYesSecondTry = null;
        try {
            barYesSecondTry = pollService.addVote(new XoVote("Soenke",
                    VoteType.YES,
                    TestDataHelper.getIdForTextOption(poll, LOCALE_EN, "Bar")));
        } catch (PersistenceException e) {
            LOG.info(e.getMessage());
        } finally {
            assertThat(barYesSecondTry, equalTo(null));
        }
    }
}
