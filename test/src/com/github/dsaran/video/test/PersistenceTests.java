package com.github.dsaran.video.test;

import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.dsaran.video.SessionComponentFactory;
import com.github.dsaran.video.model.Movie;

@Test
public class PersistenceTests {

	/**
	 * Testing the persistence of movie data correctly (inclusive encoding).
	 */
	public void moviePersistence() {
		SessionComponentFactory factory = new SessionComponentFactory();
		factory.create();
		Session session = factory.getInstance();
		Movie movie = new Movie();
		movie.setName("Harry Potter E As RelÌ≠quias Da Morte - PARTE I");
		movie.setDescription("„o Nesta primeira parte do filme, Harry (Daniel Radcliffe), Rony (Rupert Grint) e Hermione (Emma Watson) abandonam a Escola de Magia e Bruxaria de Hogwarts para buscar as Horcruxes - objetos que cont√™m peda√ßos da alma -, feitas por Voldemort, conforme indicado pelo diretor Alvo Dumbledore, antes deste morrer no longa anterior.");
		movie.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb8229d4b1.jpg");

		session.save(movie);

		Movie movie2 = new Movie();
		movie2.setName("Ilha do Medo");
		movie2.setDescription("Em 1954, o detetive Teddy Daniels (Leonardo DiCaprio) investiga o desaparecimento de uma assassina, que fugiu de um hospital psiqui√°trico e est√° supostamente foragida na remota Ilha Shutter. Quanto mais perto da verdade ele chega, mais enganosa ela se torna.");
		movie2.setImage("http://static.cineclick.com.br/sites/adm/uploads/banco_imagens/53/260x365_519eb891a18bf.jpg");

		session.save(movie2);

		factory.destroy();

		factory.create();
		session = factory.getInstance();		
		Movie loadedMovie = (Movie) session.load(Movie.class, movie.getId());

		Assert.assertNotNull(loadedMovie);
		Assert.assertEquals(loadedMovie.getDescription(), movie.getDescription());

		Movie loadedMovie2 = (Movie) session.load(Movie.class, movie2.getId());
		
		Assert.assertNotNull(loadedMovie2);
		Assert.assertEquals(loadedMovie2.getDescription(), movie2.getDescription());
	}
}
