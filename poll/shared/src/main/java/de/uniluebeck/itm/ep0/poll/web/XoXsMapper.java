package de.uniluebeck.itm.ep0.poll.web;

import de.uniluebeck.itm.ep0.poll.domain.VoteType;
import de.uniluebeck.itm.ep0.poll.domain.XoDateOption;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;
import de.uniluebeck.itm.ep0.poll.domain.XoPollWithVotes;
import de.uniluebeck.itm.ep0.poll.domain.XoTextOption;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;

import java.util.ArrayList;
import java.util.List;

public class XoXsMapper {

    public static List<XoVote> xsVote2XoVotes(XsVote vote) {
        List<XoVote> result = new ArrayList<XoVote>();
        XoVote xoVote;
        for (String optionId : vote.getOptionIds()) {
            xoVote = new XoVote(vote.getVoter(), VoteType.YES, optionId);
            result.add(xoVote);
        }
        return result;
    }

    public static XsOption xoOption2xsOption(XoOption xoOption,
                                             String languageCode) {
        XsOption result = new XsOption();
        result.setId(xoOption.getId().toString());
        if (xoOption instanceof XoDateOption) {
            result.setDateTime(((XoDateOption) xoOption).getDateOption());
        }
        if (xoOption instanceof XoTextOption) {
            result.setValue(((XoTextOption) xoOption).getText()
                    .getLocalizedValue(languageCode));
        }

        return result;
    }

    public static List<XsPollInfo> xoPollInfo2xsPollInfo(
            List<XoPollInfo> pollInfos, String languageCode) {
        List<XsPollInfo> result = new ArrayList<XsPollInfo>();
        for (XoPollInfo xo : pollInfos) {
            result.add(xoPollInfo2xsPollInfo(xo, languageCode));
        }
        return result;
    }

    public static XsPollInfo xoPollInfo2xsPollInfo(XoPollInfo xo,
                                                   String languageCode) {
        XsPollInfo result = new XsPollInfo(xo.getUuid(), xo.getName()
                .getLocalizedValue(languageCode));
        return result;
    }

    public static XsPoll xoPoll2xsPoll(XoPoll poll, String languageCode) {
        XsPoll result = new XsPoll();
        for (XoOptionList xoOptionList : poll.getOptionLists()) {
            result.addOptionList(xoOptionList2xsOptionList(xoOptionList,
                    languageCode));
        }
        result.setId(poll.getUuid());
        result.setTitle(poll.getName().getLocalizedValue(languageCode));
        return result;
    }

    private static XsOptionList xoOptionList2xsOptionList(
            XoOptionList xoOptionList, String languageCode) {
        XsOptionList result = new XsOptionList();
        result.setId(xoOptionList.getId());
        result.setTitle(xoOptionList.getName().getLocalizedValue(languageCode));
        for (XoOption xoOption : xoOptionList.getOptions()) {
            result.addOption(xoOption2xsOption(xoOption, languageCode));
        }
        return result;
    }

    public static XsVotes xoVotes2xsVotes(List<XoVote> votes) {
        XsVotes result = new XsVotes();
        List<String> voters = new ArrayList<String>(votes.size());
        for (XoVote xoVote : votes) {
            voters.add(xoVote.getVoter());
        }
        result.setVoters(voters);
        return result;
    }

    public static List<XoPollInfo> xsPollInfos2xoPollInfos(
            List<XsPollInfo> pollInfos, String languageCode) {
        List<XoPollInfo> result = new ArrayList<XoPollInfo>(pollInfos.size());
        for (XsPollInfo xsPollInfo : pollInfos) {
            result.add(xsPollInfo2xoPollInfo(xsPollInfo, languageCode));
        }
        return result;
    }

    private static XoPollInfo xsPollInfo2xoPollInfo(XsPollInfo xsPollInfo,
                                                    String languageCode) {
        XoPollInfo result = new XoPollInfo();
        result.setUuid(xsPollInfo.getId());
        result.setName(new XoLocalizedString("", languageCode, xsPollInfo
                .getTitle()));
        return result;
    }

    public static XoPollWithVotes xsPoll2xoPoll(XsPoll poll, String languageCode) {
        XoPollWithVotes result = new XoPollWithVotes();
        XoPoll xoPoll = new XoPoll();
        xoPoll.setUuid(poll.getId());
        xoPoll.setName(new XoLocalizedString("", languageCode, poll.getTitle()));
        xoPoll.setOptionLists(xsOptionLists2xoOptionLists(poll.getOptionList(),
                languageCode));
        result.setVotes(xsVotes2xoVotes(poll));
        result.setPoll(xoPoll);
        return result;
    }

    private static List<XoVote> xsVotes2xoVotes(XsPoll poll) {
        List<XoVote> result = new ArrayList<XoVote>();
        for (XsOptionList xsOptionList : poll.getOptionList()) {
            for (XsOption xsOption : xsOptionList.getOptions()) {
                if (null != xsOption.getVotes()
                        && null != xsOption.getVotes().getVoters()) {
                    for (String voter : xsOption.getVotes().getVoters()) {

                        XoVote vote = new XoVote(null, voter, VoteType.YES,
                                xsOption.getId());
                        result.add(vote);
                    }
                }
            }
        }
        return result;
    }

    private static List<XoOptionList> xsOptionLists2xoOptionLists(
            ArrayList<XsOptionList> optionList, String languageCode) {
        List<XoOptionList> result = new ArrayList<XoOptionList>(
                optionList.size());
        for (XsOptionList xsOptionList : optionList) {
            result.add(xsOptionList2xoOptionList(xsOptionList, languageCode));
        }
        return result;
    }

    private static XoOptionList xsOptionList2xoOptionList(
            XsOptionList xsOptionList, String languageCode) {
        XoOptionList result = new XoOptionList(
                xsOptionList.getId(),
                new XoLocalizedString("", languageCode, xsOptionList.getTitle()));

        result.setOptions(xsOptions2xoOptions(xsOptionList.getOptions(),
                languageCode));
        return result;
    }

    private static List<XoOption> xsOptions2xoOptions(
            ArrayList<XsOption> options, String languageCode) {
        List<XoOption> result = new ArrayList<XoOption>(options.size());
        for (XsOption xsOption : options) {
            XoOption xoOption;
            if (xsOption.getDateTime() != null) {

                xoOption = new XoDateOption(xsOption.getId(),
                        xsOption.getDateTime(), new XoLocalizedString("",
                                languageCode, xsOption.getValue()));
            } else {

                XoTextOption xo;
                xo = new XoTextOption(xsOption.getId(),
                        new XoLocalizedString(""), new XoLocalizedString("",
                                languageCode, xsOption.getValue()));
                xo.setText(new XoLocalizedString("", languageCode,
                        xsOption.getValue()));
                xoOption = xo;
            }
            result.add(xoOption);

        }
        return result;
    }

    public static XsVote XoVote2XsVote(XoVote vote) {
        XsVote result = new XsVote();
        List<String> optionIds = new ArrayList<String>(1);
        optionIds.add(vote.getOptionId().toString());
        result.setOptionsIds(optionIds);
        result.setVoter(vote.getVoter());
        return result;
    }

}
