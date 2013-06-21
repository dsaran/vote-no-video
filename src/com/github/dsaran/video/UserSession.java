package com.github.dsaran.video;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import br.com.caelum.vraptor.ioc.SessionScoped;

import com.github.dsaran.video.model.Movie;
import com.github.dsaran.video.model.User;

/**
 * This class is responsible for controlling the combinations of movies the user should be presented for voting.
 * For a small number of movies it is acceptable, but if the number of
 * movies grows larger we need to change this to store only the IDs or even
 * change the entire logic to prevent storing large amounts of data in Session.
 *
 * @author daniel
 */
@SessionScoped
public class UserSession {

	/**
	 * Stores the possible combinations of movies.
	 */
	private Map<Long, Set<Long>> combinations = new HashMap<Long, Set<Long>>();

	private boolean initialized;

	private User user;

	/**
	 * Load the available movies for voting.
	 * @param movies
	 */
	public void initialize(User user, List<Movie> movies) {
		for (int i = 0; i < movies.size() - 1; i++) {
			for (int j = i + 1; j < movies.size(); j++) {
				Movie movie1 = movies.get(i);
				Movie movie2 = movies.get(j);
				if (!combinations.containsKey(movie1.getId())) {
					combinations.put(movie1.getId(), new HashSet<Long>());
				}
				combinations.get(movie1.getId()).add(movie2.getId());
			}
		}
		this.initialized = true;
	}

	/**
	 * Check if the movies are already initialized.
	 * @return {@code true} if the movies are initialized up.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	public Long[] getNextVote() {
		if (!combinations.isEmpty()) {
			try {

				Iterator<Long> keyIterator = combinations.keySet().iterator();

				Long id1 = keyIterator.next();
				Set<Long> set = combinations.get(id1);

				Long id2 = set.iterator().next();

				return new Long[] {id1, id2};

			} catch (NoSuchElementException e) {}
		}
		return null;
	}

	public boolean markVoted(Long id1, Long id2) {
		boolean marked = false;
        if (combinations.containsKey(id1)) {
        	Set<Long> items = combinations.get(id1);
        	if (items.contains(id2)) {
        		marked = items.remove(id2);
        	}
    		if (items.isEmpty()) {
    			combinations.remove(id1);
    		}
        } else if (combinations.containsKey(id2)) {
        	Set<Long> items = combinations.get(id2);
        	if (items.contains(id1)) {
        		marked = items.remove(id1);

        		if (items.isEmpty()) {
        			combinations.remove(id2);
        		}
        	}
        }
        return marked;
	}

	public User getUser() {
		return user;
	}
}
