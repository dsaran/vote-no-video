package com.github.dsaran.video.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.ioc.Component;

import com.github.dsaran.video.exception.VideoRuntimeException;
import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.User;
import com.github.dsaran.video.model.Vote;

/**
 * DAO for Movie persistence
 * @author daniel
 */
@Component
public class MovieDaoImpl implements MovieDao {
    /** The Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(MovieDaoImpl.class);

    /**
     * The hibernate Session.
     */
	private Session session;

    public MovieDaoImpl(Session session) {
    	this.session = session;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Movie> loadAll() {
		try {
			@SuppressWarnings("unchecked")
			List<Movie> list = session.createCriteria(Movie.class).list();
			return list;
		} catch (HibernateException e) {
			LOG.error(e.getMessage(), e);
			throw new VideoRuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(Movie movie) {
		session.save(movie);
	}

	/**
	 * {@inheritDoc}
	 */
	public Movie load(Long id) {
		return (Movie) session.load(Movie.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(User user) {
		session.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(Vote vote) {
		session.save(vote);
	}
}
