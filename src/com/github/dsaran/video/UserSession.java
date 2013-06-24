package com.github.dsaran.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import br.com.caelum.vraptor.ioc.SessionScoped;

import com.github.dsaran.video.model.Movie;

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

	private Long[] currentVote = null;
	private List<Long> votes = new ArrayList<Long>(10);
	private int totalSteps = 0;
	private int currentStep = 0;
	private Long userId;

	/**
	 * Load the available movies for voting.
	 * @param movies
	 */
	public void initialize(List<Movie> movies) {
		if (movies == null || movies.isEmpty()) {
			return;
		}
		for (int i = 0; i < movies.size() - 1; i++) {
			for (int j = i + 1; j < movies.size(); j++) {
				Movie movie1 = movies.get(i);
				Movie movie2 = movies.get(j);
				if (!combinations.containsKey(movie1.getId())) {
					combinations.put(movie1.getId(), new HashSet<Long>());
				}
				combinations.get(movie1.getId()).add(movie2.getId());
				totalSteps++;
			}
		}
		currentStep = 0;
		if (totalSteps > 0) {
			this.initialized = true;
		}
	}

	/**
	 * Check if the movies are already initialized.
	 * @return {@code true} if the movies are initialized up.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	public Long[] getNextVote() {
		if (currentVote != null) {
			return currentVote;
		}
		if (!combinations.isEmpty()) {
			try {

				Iterator<Long> keyIterator = combinations.keySet().iterator();

				Long id1 = keyIterator.next();
				Set<Long> set = combinations.get(id1);

				Long id2 = set.iterator().next();

				currentVote = new Long[] {id1, id2};
				currentStep++;
				return currentVote;

			} catch (NoSuchElementException e) {}
		}
		return null;
	}

	/**
	 * Register user vote.
	 *
	 * @param selected
	 *            is the selected movie
	 * @return {@code true} if it is a valid vote and it was registered successfully.
	 */
	public boolean registerVote(Long selected) {
		if (currentVote == null || (!selected.equals(currentVote[0]) && !selected.equals(currentVote[1]))) {
			return false;
		}
		boolean validVote = false;

		Long id1 = currentVote[0];
		Long id2 = currentVote[1];

        if (combinations.containsKey(id1)) {
        	Set<Long> items = combinations.get(id1);
        	if (items.contains(id2)) {
        		validVote = items.remove(id2);
        	}
    		if (items.isEmpty()) {
    			combinations.remove(id1);
    		}
        } else if (combinations.containsKey(id2)) {
        	Set<Long> items = combinations.get(id2);
        	if (items.contains(id1)) {
        		validVote = items.remove(id1);

        		if (items.isEmpty()) {
        			combinations.remove(id2);
        		}
        	}
        }
        if (validVote) {
        	votes.add(selected);
        	currentVote = null;
        }
        return validVote;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	public List<Long> getVotes() {
		return votes;
	}

	public void serUserId(Long id) {
		this.userId = id;
	}

	public Long getUserId() {
		return userId;
	}

}
