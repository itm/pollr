package de.uniluebeck.itm.ep0.poll.dao;

import de.uniluebeck.itm.ep0.poll.domain.BoVote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteDaoImpl extends AbstractDaoImpl<BoVote> implements VoteDao {

    public VoteDaoImpl() {
        super(BoVote.class);
    }

    @Override
    public List<BoVote> findByOptionId(final Integer optionId) {
        List<BoVote> allVotes = getEntityManager().createQuery("SELECT v FROM BoVote v WHERE v.optionId = ?1",
                BoVote.class).setParameter(1, optionId).getResultList();
        return allVotes;
    }

    @Override
    public List<BoVote> findByVoterAndOption(final String voter, final Integer optionId) {
        List<BoVote> allVotes = getEntityManager().createQuery(
                "SELECT v FROM BoVote v WHERE v.voter = ?1 AND v.optionId = ?2",
                BoVote.class).setParameter(1, voter).setParameter(2, optionId).getResultList();
        return allVotes;
    }
}
