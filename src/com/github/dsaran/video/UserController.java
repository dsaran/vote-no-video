package com.github.dsaran.video;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;

import com.github.dsaran.video.dao.MovieDao;
import com.github.dsaran.video.dao.UserDao;
import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.User;
import com.github.dsaran.video.model.Vote;

/**
 * Controller responsible for the user operations.
 * @author daniel
 */
@Resource
public class UserController {

    /** The Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final Result result;

	private UserDao userDao;
	private MovieDao movieDao;

	private UserSession userSession;

	private final Validator validator;

	public UserController(
			final Validator validator,
			final UserSession userSession,
			final MovieDao movieDao,
			final UserDao userDao,
			final Result result) {
		this.validator = validator;
		this.userSession = userSession;
		this.movieDao = movieDao;
		this.result = result;
		this.userDao = userDao;
	}

	/**
	 * 
	 */
	@Path("/usuario/cadastro")
	public void signup() {
	}

	@Path("/usuario/salvar")
	public void save(final User user) {
		LOGGER.debug("Saving user");

		if (user == null) {
			LOGGER.warn("Given user is null");
			result.include("message", "Dados inválidos");
			
			return;
		}
		if (user.getName() == null || user.getName().trim().equals("")) {
			result.include("message", "Preencha o campo Nome");

		} else if (user.getEmail() == null || user.getEmail().trim().equals("")) {

			result.include("message", "Preencha o campo E-mail");

		} else {
			List<Vote> votes = new ArrayList<Vote>(); 
			for (Long id : userSession.getVotes()) {
				Movie movie = movieDao.load(id);
				Vote vote = new Vote(user, movie);
				votes.add(vote);
			}
			user.setVotes(votes);

			userDao.save(user);

			userSession.serUserId(user.getId());

			LOGGER.debug("User saved.");

			result.use(Results.page()).redirectTo("/resultados");
			return;
		}
		result.include("user", user);
		result.use(Results.page()).redirectTo("/usuario/cadastro");
	}
}
