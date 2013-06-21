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
import org.testng.annotations.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

import com.github.dsaran.video.IndexController;
import com.github.dsaran.video.UserSession;
import com.github.dsaran.video.dao.MovieDao;
import com.github.dsaran.video.model.Movie;

//@Test
public class IndexControllerTests {

	@Mock
	private MovieDao dao;
	@Spy
	private Result result = new MockResult();

	private List<Movie> movies = new ArrayList<Movie>();

	private UserSession votes = new UserSession();

	/**
	 * Object to be tested.
	 */
	private IndexController indexController;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.indexController = new IndexController(votes, dao, result);

		movies.add(new Movie("movie1"));
		movies.add(new Movie("movie2"));
		movies.add(new Movie("movie3"));
		movies.add(new Movie("movie4"));
		movies.add(new Movie("movie5"));
	}

	@AfterMethod
	public void cleanUp() {
		reset(dao, result);
	}

	@Test(description="populate() should insert movies into DB.")
	public void populateShouldInsertMovies() {
		indexController.populate();

		verify(dao, times(5)).save(any(Movie.class));
	}

	@Test(description="list() should load all movies from DB.")
	public void listShouldLoadMovies() {
		given(dao.loadAll()).willReturn(movies);

		indexController.list();

		verify(dao).loadAll();
		verify(result).include("list", movies);
	}

	@Test(description="index() should select and return two movies.")
	public void indexShouldReturnTwoMovies() {
		given(dao.loadAll()).willReturn(movies);

		indexController.index();

		Movie movie1 = (Movie) result.included().get("movie1");
		Movie movie2 = (Movie) result.included().get("movie2");

		Assert.assertNotNull(movie1, "Movie1 should not be null.");
		Assert.assertNotNull(movie2, "Movie2 should not be null.");
		Assert.assertNotEquals(movie1.getName(), movie1.getName(), "Movies for voting should be different.");
	}
}
