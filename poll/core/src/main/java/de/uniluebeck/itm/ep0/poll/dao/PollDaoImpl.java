package de.uniluebeck.itm.ep0.poll.dao;

import de.uniluebeck.itm.ep0.poll.domain.BoAbstractOption;
import de.uniluebeck.itm.ep0.poll.domain.BoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.BoPoll;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PollDaoImpl extends AbstractDaoImpl<BoPoll> implements PollDao {

    public PollDaoImpl() {
        super(BoPoll.class);
    }

    @Override
    public BoPoll findByUuid(String uuid) {
        List<BoPoll> resultList = getEntityManager()
                .createQuery(
                        "SELECT p FROM BoPoll p WHERE p.uuid = ?1 OR p.adminUuid = ?1",
                        BoPoll.class)
                .setParameter(1, uuid).getResultList();
        if (resultList.size() < 1) return null;

        return resultList.get(0);
    }

    @Override
    public BoPoll findByOptionId(Integer optionId) {
        BoAbstractOption option = getEntityManager().find(
                BoAbstractOption.class, optionId);
        if (option == null || option.getOptionList() == null) return null;

        return option.getOptionList().getPoll();
    }

    @Override
    public BoOptionList findOptionList(Integer pollId, Integer optionListId) {
        List<BoOptionList> resultList = getEntityManager()
                .createQuery(
                        "SELECT ol FROM BoOptionList ol WHERE ol.id = ?1 AND ol.poll = ?2",
                        BoOptionList.class)
                .setParameter(1, optionListId)
                .setParameter(2, pollId)
                .getResultList();
        if (resultList.size() < 1) return null;

        return resultList.get(0);
    }

    @Override
    public BoOptionList findOptionList(Integer optionListId) {
        List<BoOptionList> resultList = getEntityManager()
                .createQuery(
                        "SELECT ol FROM BoOptionList ol WHERE ol.id = ?1",
                        BoOptionList.class)
                .setParameter(1, optionListId)
                .getResultList();
        if (resultList.size() < 1) return null;

        return resultList.get(0);
    }
}
