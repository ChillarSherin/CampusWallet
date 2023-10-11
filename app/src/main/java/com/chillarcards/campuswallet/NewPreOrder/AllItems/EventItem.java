package com.chillarcards.campuswallet.NewPreOrder.AllItems;

import androidx.annotation.NonNull;

public class EventItem extends ListItem {

	@NonNull
	private Event event;
	@NonNull
	private int position;

	public EventItem(@NonNull Event event, @NonNull int position) {
		this.event = event;
		this.position = position;
	}

	@NonNull
	public Event getEvent() {
		return event;
	}

	@NonNull
	public int getPosition() {
		return position;
	}

	// here getters and setters
	// for title and so on, built
	// using event

	@Override
	public int getType() {
		return TYPE_EVENT;
	}

}