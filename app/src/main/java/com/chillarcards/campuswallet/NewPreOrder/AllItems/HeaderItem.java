package com.chillarcards.campuswallet.NewPreOrder.AllItems;

import androidx.annotation.NonNull;

public class HeaderItem extends ListItem {

	@NonNull
	private String sessions;
	@NonNull
	private int position;

	public HeaderItem(@NonNull String sessions, @NonNull int position) {
		this.position = position;
		this.sessions = sessions;
	}

	@NonNull
	public String getSession() {
		return sessions;
	}

	@NonNull
	public int getPosition() {
		return position;
	}

	// here getters and setters
	// for title and so on, built
	// using date

	@Override
	public int getType() {
		return TYPE_HEADER;
	}

}