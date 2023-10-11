package com.chillarcards.campuswallet.payments;

import com.chillarcards.campuswallet.networkmodels.ListSavedCards.CardsDetail;

public interface SavedCardListener {

    public void onSavedCardSelected(CardsDetail mData);
}
