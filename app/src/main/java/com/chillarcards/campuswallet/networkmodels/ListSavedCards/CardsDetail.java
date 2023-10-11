
package com.chillarcards.campuswallet.networkmodels.ListSavedCards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardsDetail {

    @SerializedName("cardId")
    @Expose
    private String cardId;
    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("cardScheme")
    @Expose
    private String cardScheme;
    @SerializedName("maskedCard")
    @Expose
    private String maskedCard;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardScheme() {
        return cardScheme;
    }

    public void setCardScheme(String cardScheme) {
        this.cardScheme = cardScheme;
    }

    public String getMaskedCard() {
        return maskedCard;
    }

    public void setMaskedCard(String maskedCard) {
        this.maskedCard = maskedCard;
    }

}
