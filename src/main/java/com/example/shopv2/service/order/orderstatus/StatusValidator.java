package com.example.shopv2.service.order.orderstatus;

import com.example.shopv2.model.Orders;


public abstract class StatusValidator {

    protected StatusValidator next;

    public abstract Orders sendStatus(Orders orders);
    public abstract Orders confirmStatus(Orders orders);

    public void setNext(StatusValidator next) {
        this.next = next;
    }
}
