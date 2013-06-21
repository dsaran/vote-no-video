package com.github.dsaran.video;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.github.dsaran.video.dao.MovieDao;
import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.User;
import com.github.dsaran.video.model.Vote;

/**
 * Handles requests for index page.
 *
 * @author daniel
 */
@Resource
public class IndexController {
    /** The Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	private final Result result;

	private MovieDao movieDao;

	private UserSession userSession;

	public IndexController(UserSession userSession, MovieDao movieDao, Result result) {
		this.userSession = userSession;
		this.result = result;
		this.movieDao = movieDao;
	}

	/**
	 * Will load two movies to present to user for voting.
	 */
	@Path("/")
	public void index() {


		if (!userSession.isInitialized()) {
			List<Movie> movies = movieDao.loadAll();

			User user = new User();
			movieDao.save(user);

			userSession.initialize(user, movies);
		}
		Long[] id = userSession.getNextVote();
		if (id != null) {
			result.include("movie1", movieDao.load(id[0]));
			result.include("movie2", movieDao.load(id[1]));

		} else {
			result.use(Results.page()).redirectTo("/resultado");
		}
	}

	/**
	 * Display all available movies.
	 */
	@Path("/todos")
	public void list() {

		List<Movie> movies = movieDao.loadAll();
		result.include("list", movies);
	}

	@Path("/votar")
	public void vote(VoteInfo vote) {

		boolean marked = userSession.markVoted(vote.getMovie1(), vote.getMovie2());
		if (marked) {
			Vote userVote = new Vote();
			userVote.setUser(userSession.getUser());
			userVote.setMovie(movieDao.load(vote.getSelected()));

			movieDao.save(userVote);
		}
		result.use(Results.page()).redirectTo("/");
	}

	/**
	 * Insert data into DB.
	 */
	@Path("/cadastrar")
	public void populate() {
		Movie movie = new Movie();
		movie.setName("Harry Potter E As Rel�quias Da Morte - PARTE I");
		movie.setDescription("Nesta primeira parte do filme, Harry (Daniel Radcliffe), Rony (Rupert Grint) e Hermione (Emma Watson) abandonam a Escola de Magia e Bruxaria de Hogwarts para buscar as Horcruxes - objetos que cont�m peda�os da alma -, feitas por Voldemort, conforme indicado pelo diretor Alvo Dumbledore, antes deste morrer no longa anterior.");
		movie.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb8229d4b1.jpg");

		movieDao.save(movie);

		movie = new Movie();
		movie.setName("Ilha do Medo");
		movie.setDescription("Em 1954, o detetive Teddy Daniels (Leonardo DiCaprio) investiga o desaparecimento de uma assassina, que fugiu de um hospital psiqui�trico e est� supostamente foragida na remota Ilha Shutter. Quanto mais perto da verdade ele chega, mais enganosa ela se torna.");
		movie.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb891a18bf.jpg");

		movieDao.save(movie);

		movie = new Movie();
		movie.setName("A Rede Social");
		movie.setDescription("Em uma noite de outono, em 2003, graduado em Harvard e g�nio em programa��o de computadores, Mark Zuckerberg senta diante de seu computador e, acaloradamente, come�a a trabalhar em uma nova ideia. No furor dos blogs e programa��o, o que come�a em seu quarto logo se torna uma rede social global e uma revolu��o na comunica��o: o Facebook. Em apenas seis anos e 500 milh�es de amigos mais tarde, Mark Zuckerberg � o mais jovem bilion�rio da hist�ria... Mas, para este empres�rio, o sucesso traz complica��es pessoais e legais. ");
		movie.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb7ca9a09d.jpg");

		movieDao.save(movie);

		movie = new Movie();
		movie.setName("Cisne Negro");
		movie.setDescription("Apoiada pela m�e, uma bailarina aposentada, Nina (Natalie Portman), se dedica totalmente � companhia de dan�a de bal� da qual faz parte. A grande oportunidade da jovem surge quando o diretor art�stico Thomas Leroy (Vicent Cassel) procura por uma dan�arina para protagonizar O Lago dos Cisnes. Lily (Mila Kunis) tem toda a aptid�o para a sensualidade do Cisne Negro, enquanto Nina se mostra ideal para viver o Cisne Branco, inocente e gracioso. Nesta disputa, Nina passa a conhecer melhor o seu lado sombrio e este autoconhecimento pode ser destrutivo.");
		movie.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb7b727632.jpg");

		movieDao.save(movie);

		movie = new Movie();
		movie.setName("A Origem");
		movie.setDescription("Don Cobb (Leonardo DiCaprio) � especialista em invadir a mente das pessoas e, com isso, rouba segredos do subconsciente, especialmente durante o sono, quando a mente est� mais vulner�vel. As habilidades singulares de Cobb fazem com que ele seja cobi�ado pelo mundo da espionagem e acaba se tornando um fugitivo. Como uma chance para se redimir, Cobb ter� de, em vez de roubar os pensamentos, implant�-los. Seria um crime perfeito, mas nenhum planejamento pode preparar a equipe para enfrentar o perigoso inimigo que parece adivinhar seus movimentos. Apenas Cobb � capaz de saber o que est� por vir.");
		movie.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb87ad651d.jpg");

		movieDao.save(movie);

		result.use(Results.page()).redirectTo("/todos");
	}
}
