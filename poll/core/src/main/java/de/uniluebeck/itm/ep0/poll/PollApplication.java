package de.uniluebeck.itm.ep0.poll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PollApplication {

    private final static Logger LOG = LoggerFactory.getLogger(PollApplication.class);

    private final static String SPRING_CONFIG = "core-context.xml";

    public static void main(final String[] args) {
        // Create the spring container using the XML configuration from core-context.xml
        final ApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_CONFIG);
        LOG.info("Server started...");
    }
}
