package com.chillarcards.campuswallet.NewPreOrder.AllItems;

import androidx.annotation.NonNull;

import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Item;

public class Event {

	private Item title;
	private String session;

	public Event(@NonNull Item title, @NonNull String session) {
		this.title = title;
		this.session = session;
	}

	public Item getTitle() {
		return title;
	}

	public String getSession() {
		return session;
	}

}
