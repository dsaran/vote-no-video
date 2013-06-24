package com.github.dsaran.video.test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

import com.github.dsaran.video.UserSession;
import com.github.dsaran.video.VotesController;
import com.github.dsaran.video.dao.MovieDao;
import com.github.dsaran.video.dao.VoteDao;
import com.github.dsaran.video.model.Movie;

public class VotesControllerTests {

	@Mock
	private MovieDao movieDao;
	@Mock
	private VoteDao voteDao;
	@Spy
	private Result result = new MockResult();

	private List<Movie> movies = new ArrayList<Movie>();

	@Spy
	private UserSession userSession;

	/**
	 * Object to be tested.
	 */
	private VotesController controller;

	@BeforeClass
	public void setUpClass() {

		for (int i = 1; i <=5; i++) {
			Movie movie = new Movie();
			movie.setId((long) i);
			movie.setName("Movie" + i);
			movies.add(movie);
		}
	}

	@BeforeMethod
	public void setUp() {
		userSession = new UserSession();

		MockitoAnnotations.initMocks(this);

		this.controller = new VotesController(userSession, movieDao, voteDao, result);
	}

	@AfterMethod
	public void cleanUp() {
		reset(movieDao, result);
	}

	@Test(description="index() should select and return two movies.")
	public void indexShouldReturnTwoMovies() {
		given(movieDao.loadAll()).willReturn(movies);
		given(movieDao.load(any(Long.class))).willReturn(new Movie());

		controller.index();

		Movie movie1 = (Movie) result.included().get("movie1");
		Movie movie2 = (Movie) result.included().get("movie2");
		Integer totalSteps = (Integer) result.included().get("totalSteps");
		Integer currentStep = (Integer) result.included().get("currentStep");

		Assert.assertNotNull(movie1, "Movie1 should not be null.");
		Assert.assertNotNull(movie2, "Movie2 should not be null.");
		Assert.assertNotNull(totalSteps, "total steps not added to the result.");
		Assert.assertNotNull(currentStep, "Current step not added to the result.");
		Assert.assertEquals(currentStep, Integer.valueOf(1), "Unexpected value for currentStep");

		verify(movieDao, times(2)).load(any(Long.class));
	}

	@Test(description="A valid vote should be registered successfully")
	public void voteShouldRegisterValidVote() {
		userSession.initialize(movies);
		Long selected = userSession.getNextVote()[0];

		controller.vote(selected);

		verify(userSession).registerVote(selected);
	}

	@Test(description="Session should be initialized by the controller on first call")
	public void checkInitialized() {
		given(movieDao.loadAll()).willReturn(movies);

		controller.index();

		verify(userSession).initialize(movies);

	}

	@Test(description="Session should not be initialized twice")
	public void checkNotInitializedTwice() {
		userSession.initialize(movies);
		verify(userSession, times(1)).initialize(movies);

		controller.index();

		verify(userSession, times(1)).initialize(movies);

	}
}
