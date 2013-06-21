package com.github.dsaran.video;

/**
 * Hold data for the vote request.
 * @author daniel
 */
public class VoteInfo {

	private Long selected;
	private Long movie1;
	private Long movie2;

	public Long getSelected() {
		return selected;
	}
	public void setSelected(Long selected) {
		this.selected = selected;
	}
	public Long getMovie1() {
		return movie1;
	}
	public void setMovie1(Long movie1) {
		this.movie1 = movie1;
	}
	public Long getMovie2() {
		return movie2;
	}
	public void setMovie2(Long movie2) {
		this.movie2 = movie2;
	}

}
