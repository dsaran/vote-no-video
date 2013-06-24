package com.github.dsaran.video.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;

import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.Vote;
import com.github.dsaran.video.model.VoteSummary;

@Component
public class VoteDaoImpl implements VoteDao {

    /**
     * The hibernate Session.
     */
	private Session session;

    public VoteDaoImpl(Session session) {
    	this.session = session;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<VoteSummary> getVotes() {
		Criteria criteria = session.createCriteria(Vote.class).setProjection(
				Projections.projectionList()
						.add(Projections.groupProperty("movie"))
						.add(Projections.rowCount())
						);
		@SuppressWarnings("unchecked")
		List<Object[]> list = criteria.list();

		List<VoteSummary> votes = new ArrayList<VoteSummary>(list.size());
		for (Object[] obj : list) {
			Movie movie = (Movie) obj[0];
			Long count = (Long) obj[1];
			votes.add(new VoteSummary(movie, count));
		}
		return votes;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<VoteSummary> getUserVotes(long userId) {
		Criteria criteria = session.createCriteria(Vote.class)
				.add(Restrictions.eqOrIsNull("user.id", userId))
				.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("movie"))
						.add(Projections.rowCount())
						);
		@SuppressWarnings("unchecked")
		List<Object[]> list = criteria.list();

		List<VoteSummary> votes = new ArrayList<VoteSummary>(list.size());
		for (Object[] obj : list) {
			Movie movie = (Movie) obj[0];
			Long count = (Long) obj[1];
			votes.add(new VoteSummary(movie, count));
		}
		return votes;
	}


}
