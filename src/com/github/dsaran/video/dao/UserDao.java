package com.github.dsaran.video.dao;

import com.github.dsaran.video.model.User;

public interface UserDao {
	/**
	 * Persists a user into db.
	 *
	 * @param user
	 *            is the user to persist.
	 */
	void save(User user);
}
