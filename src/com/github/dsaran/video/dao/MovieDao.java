package com.github.dsaran.video.dao;

import java.util.List;

import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.User;
import com.github.dsaran.video.model.Vote;

/**
 * Provides database access for the {@code Movie} object.
 *
 * @author daniel
 */
public interface MovieDao {

	/**
	 * Load all movies from the database.
	 *
	 * @return a {@code List} of {@code Movie}
	 */
	List<Movie> loadAll();

	/**
	 * Persists a movie into db.
	 *
	 * @param movie
	 *            is the movie to persist.
	 */
	void save(Movie movie);

	/**
	 * Load the movie from its ID.
	 *
	 * @param id
	 *            is the movie ID to load from db.
	 * @return the {@code Movie} for the given ID.
	 */
	Movie load(Long id);
}
