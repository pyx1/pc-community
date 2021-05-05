package com.pccommunity;

import java.io.Serializable;

public class OrderProductPK implements Serializable{

    Long productId;
    Long orderId;

    public OrderProductPK(){
        super();
    }
    public OrderProductPK(Product productId, Order orderId){
        super();
        this.productId = productId.getIdProduct();
        this.orderId = orderId.getIdOrder();
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        OrderProductPK that = (OrderProductPK) object;
        return java.util.Objects.equals(productId, that.productId) &&
                java.util.Objects.equals(orderId, that.orderId);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), productId, orderId);
    }
}