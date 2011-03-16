package de.uniluebeck.itm.ep0.poll;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.uniluebeck.itm.ep0.poll.web.PollWebServiceImpl;

public class WebServiceApplication {

    final static Logger log = LoggerFactory.getLogger(WebServiceApplication.class);
    private static final String WEBSERVICE_URL = "http://0.0.0.0:8080/poll";

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "webservice-context.xml");
        log.info("application context created: " + ctx.getDisplayName());
        String url = WEBSERVICE_URL;
        if (args.length == 1) {
            url = args[0];
        }
        Endpoint.publish(url, new PollWebServiceImpl());
        log.info("web services published at: " + url);
    }
}
