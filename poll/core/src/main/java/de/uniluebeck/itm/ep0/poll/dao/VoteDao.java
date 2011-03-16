package de.uniluebeck.itm.ep0.poll.dao;

import de.uniluebeck.itm.ep0.poll.domain.BoVote;

import java.util.List;

public interface VoteDao extends Dao<BoVote> {

    List<BoVote> findByOptionId(Integer optionId);

    List<BoVote> findByVoterAndOption(String voter, Integer optionId);
}
