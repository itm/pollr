package de.uniluebeck.itm.ep0.poll.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.uniluebeck.itm.ep0.poll.TestDataHelper;
import de.uniluebeck.itm.ep0.poll.service.PollService;

public class PollWebServiceTest {

    final static Logger log = LoggerFactory.getLogger(PollWebServiceTest.class);

    private static final ApplicationContext CTX = new ClassPathXmlApplicationContext(
            "webservice-context.xml");
    private static final PollService SERVICE = (PollService) CTX.getBean("pollService");

    @Test
    public void spikeTest() {
        // Persist some test data (via RMI)
        TestDataHelper.createAndAddPoll(SERVICE);


        PollWebserviceClient pc = new PollWebserviceClient();
        PollWebService ws = pc.getPollWebServicePort();

        log.info(ws.getPolls("en_US").toString());
    }
}
