package com.booking.movies.entity;

public enum EventRating {

	LOW("low"), MID("middle"), HIGH("high");

	private String rating;

	EventRating(String rating) {
		this.rating = rating;
	}

	public String getRating() {
		return rating;
	}

}
