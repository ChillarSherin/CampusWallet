
package com.chillarcards.campuswallet.networkmodels.ListSavedCards;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("cards_details")
    @Expose
    private List<CardsDetail> cardsDetails = null;

    public List<CardsDetail> getCardsDetails() {
        return cardsDetails;
    }

    public void setCardsDetails(List<CardsDetail> cardsDetails) {
        this.cardsDetails = cardsDetails;
    }

}
