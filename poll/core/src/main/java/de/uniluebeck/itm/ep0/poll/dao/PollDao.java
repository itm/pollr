package de.uniluebeck.itm.ep0.poll.dao;

import de.uniluebeck.itm.ep0.poll.domain.BoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.BoPoll;

public interface PollDao extends Dao<BoPoll> {

    BoPoll findByUuid(String uuid);

    BoPoll findByOptionId(Integer optionId);

    BoOptionList findOptionList(Integer pollId, Integer optionListId);

    BoOptionList findOptionList(Integer optionListId);
}
