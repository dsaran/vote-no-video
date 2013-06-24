package com.github.dsaran.video.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;

import com.github.dsaran.video.model.User;

@Component
public class UserDaoImpl implements UserDao {

    /**
     * The hibernate Session.
     */
	private Session session;

    public UserDaoImpl(Session session) {
    	this.session = session;
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(User user) {
		session.save(user);
	}

}
