package com.github.dsaran.video.dao;

import java.util.List;

import com.github.dsaran.video.model.VoteSummary;

public interface VoteDao {

	/**
	 * Load votes grouped by movie.
	 * @return {@code List} of {@code VoteSummary}
	 */
	List<VoteSummary> getVotes();

	/**
	 * Load votes of the specified user grouped by movie.
	 *
	 * @param userId
	 *            is the user id to load the votes
	 *
	 * @return {@code List} of {@code VoteSummary}
	 */
	List<VoteSummary> getUserVotes(long userId);
}
