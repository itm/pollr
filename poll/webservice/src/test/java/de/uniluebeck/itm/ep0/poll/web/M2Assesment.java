package de.uniluebeck.itm.ep0.poll.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import de.uniluebeck.itm.ep0.poll.TestDataHelper;
import de.uniluebeck.itm.ep0.poll.service.PollService;
import de.uniluebeck.itm.ep0.poll.web.XsOption;
import de.uniluebeck.itm.ep0.poll.web.XsOptionList;
import de.uniluebeck.itm.ep0.poll.web.XsPoll;
import de.uniluebeck.itm.ep0.poll.web.XsPollInfo;
import de.uniluebeck.itm.ep0.poll.web.XsVote;

public class M2Assesment {

    final static Logger log = LoggerFactory.getLogger(M2Assesment.class);

    @Test
    public void milestone2Test() throws MalformedURLException {
        log.info("\n");
        log.info("starting M2 assesment");
        String endpointUrl = System.getProperty("pollServiceURL");
        String pollLanguage = System.getProperty("pollLanguage");
        Assert.notNull(endpointUrl,
                "missing required system property pollServiceURL");

        Assert.notNull(pollLanguage,
                "missing required system property pollLanguage");

        QName qName = new QName("www.itm.uniluebeck.de/pollservice",
                "Pollservice");

        PollWebserviceClient pc;
        pc = new PollWebserviceClient(new URL(endpointUrl), qName);

        PollWebService ws = pc.getPollWebServicePort();

        List<XsPollInfo> pollInfos = ws.getPolls(pollLanguage);
        Assert.notEmpty(pollInfos, "no pollInfos returned");
        log.info(" got following pollInfos: \n" + pollInfos.toString());
        for (XsPollInfo xsPollInfo : pollInfos) {
            XsPoll poll = ws.getPoll(xsPollInfo.getId(), pollLanguage);
            Assert.notNull(poll,
                    " poll returned by getPoll(" + xsPollInfo.getId() + ","
                            + pollLanguage + ") is null");
            String voter = voteForPoll(poll, ws);
            checkVotes(poll.getId(), ws, voter);
        }
        log.info("finished M2 assesment");
    }

    private void checkVotes(String pollId, PollWebService ws, String voter) {
        XsPoll poll = ws.getPoll(pollId, System.getProperty("pollLanguage"));
        log.info("checking votes of poll " + poll.getTitle());
        for (XsOptionList optionList : poll.getOptionList()) {
            for (XsOption option : optionList.getOptions()) {
                String optionValue = (option.getValue() != null) ? option
                        .getValue() : option.getDateTime().toString();
                Assert.isTrue(option.getVotes().getVoters().contains(voter),
                        "Voter " + voter
                                + " not contained in voters of option "
                                + optionValue);
            }
        }

    }

    private String voteForPoll(XsPoll poll, PollWebService ws) {
        log.info("voting for poll " + poll.getTitle());
        List<String> optionIds = new ArrayList<String>();
        for (XsOptionList xsOptionList : poll.getOptionList()) {
            for (XsOption xsOption : xsOptionList.getOptions()) {
                Assert.isTrue(
                        xsOption.getValue() != null
                                || xsOption.getDateTime() != null, "option "
                                + xsOption.getId()
                                + " is neither text nor date option");
                optionIds.add(xsOption.getId());
            }
        }
        XsVote xsVote = new XsVote();
        xsVote.setOptionsIds(optionIds);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.LONG);
        String voter = "M2Assesment " + df.format(new Date());
        xsVote.setVoter(voter);
        ws.voteForOptions(xsVote);
        return voter;
    }

    public static void main(String args[]) {
        if (args.length != 2) {
            log.error("usage: M2Assesment PollServiceURL Language \nexample: M2Assesment http://localhost:8080/poll en_US");
            System.exit(1);
        }
        System.setProperty("pollServiceURL", args[0]);
        System.setProperty("pollLanguage", args[1]);
        org.junit.runner.JUnitCore.main(M2Assesment.class.getName());
    }
}
