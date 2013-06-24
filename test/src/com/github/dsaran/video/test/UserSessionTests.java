package com.github.dsaran.video.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.dsaran.video.UserSession;
import com.github.dsaran.video.model.Movie;

/**
 * Test logic from user session logic.
 * @author daniel
 */
public class UserSessionTests {

	private List<Movie> movies = new ArrayList<Movie>();

	/**
	 * Object to be tested.
	 */
	private UserSession session;


	@BeforeClass
	public void setUp() {
		for (int i = 1; i <=5; i++) {
			Movie movie = new Movie();
			movie.setId((long) i);
			movie.setName("Movie" + i);
			movies.add(movie);
		}
	}

	@BeforeMethod
	public void setupMethods() {
		 session = new UserSession();
	}

	@Test(description="A valid vote should be registered successfully")
	public void voteShouldRegisterValidVote() {
		session.initialize(movies);
		Long selected = session.getNextVote()[0];

		boolean registered = session.registerVote(selected);

		Assert.assertTrue(registered, "Vote should've been registered.");
	}

	@Test(description="Votes should not be registered if it is uninitialized.")
	public void checkRegisterVoteUninitialized() {

		boolean registered = session.registerVote(1l);

		Assert.assertFalse(registered, "Vote should not be registered before initialization");
	}

	@Test(description="If getNextVote() is not called it should not register a vote")
	public void checkRegisterVoteNoNextVote() {

		session.initialize(movies);

		boolean registered = session.registerVote(1l);

		Assert.assertFalse(registered, "Vote should not be registered");

		registered = session.registerVote(99l);
	}

	@Test(description="An invalid vote should not be registered")
	public void checkRegisterVoteInvalidVote() {

		session.initialize(movies);
		session.getNextVote();

		boolean registered = session.registerVote(99l);

		Assert.assertFalse(registered, "Vote should not be registered");

		registered = session.registerVote(99l);
	}
}
