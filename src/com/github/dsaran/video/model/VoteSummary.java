package com.github.dsaran.video.model;

public class VoteSummary {

	private Movie movie;

	private long count;


	public VoteSummary(Movie movie, long votes) {
		this.movie = movie;
		this.count = votes;
		
	}
	public Movie getMovie() {
		return movie;
	}

	public long getCount() {
		return count;
	}
}
