
package com.chillarcards.campuswallet.networkmodels.CartDetailsDummy;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDetailsJSON {

    @SerializedName("OrderDetails")
    @Expose
    private List<OrderDetail> orderDetails = null;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
