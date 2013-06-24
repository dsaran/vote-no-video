package com.github.dsaran.video;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.github.dsaran.video.dao.MovieDao;
import com.github.dsaran.video.dao.VoteDao;
import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.VoteSummary;

/**
 * Handles vote requests.
 *
 * @author daniel
 */
@Resource
public class VotesController {
    /** The Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(VotesController.class);

	private final Result result;

	private MovieDao movieDao;
	private final VoteDao voteDao;

	private UserSession userSession;


	public VotesController(UserSession userSession, MovieDao movieDao, VoteDao voteDao, Result result) {
		this.userSession = userSession;
		this.voteDao = voteDao;
		this.result = result;
		this.movieDao = movieDao;
	}

	/**
	 * Will load two movies to present to user for voting.
	 */
	@Path("/")
	public void index() {
		LOGGER.debug("Showing votes to user");

		if (!userSession.isInitialized()) {
			List<Movie> movies = movieDao.loadAll();
			if (movies.isEmpty()) {
				result.use(Results.page()).redirectTo("/filmes/cadastrar");
				return;
			}

			userSession.initialize(movies);
		}
		Long[] id = userSession.getNextVote();
		if (id != null) {
			result.include("movie1", movieDao.load(id[0]));
			result.include("movie2", movieDao.load(id[1]));
			result.include("currentStep", userSession.getCurrentStep());
			result.include("totalSteps", userSession.getTotalSteps());
		} else {
			LOGGER.debug("No more votes available for this user");

			if (userSession.getUserId() == null) {
				result.use(Results.page()).redirectTo("/usuario/cadastro");
			} else {
				result.use(Results.page()).redirectTo("/resultados");
			}
		}
	}

	/**
	 * Handles a vote request.
	 *
	 * @param selected
	 *            is the id of the selected movie.
	 */
	@Path("/votar")
	public void vote(Long selected) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("User selected movie " + selected);
		}

		boolean voteOk = userSession.registerVote(selected);

		if (!voteOk) {
			result.include("message", "Voto inválido.");
		}
		result.use(Results.page()).redirectTo("/");
	}

	@Path("/resultados")
	public void results() {

		List<VoteSummary> votes = voteDao.getVotes();

		List<VoteSummary> userVotes = voteDao.getUserVotes(userSession.getUserId());

		result.include("globalResults", votes);
		result.include("userResults", userVotes);

	}
}
