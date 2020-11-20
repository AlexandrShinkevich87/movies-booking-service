package com.booking.movies.entity;

public enum SeatType {
	ORDINARY("ordinary"), VIP("vip");

	private String type;

	SeatType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
