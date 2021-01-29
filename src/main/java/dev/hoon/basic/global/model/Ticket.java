package dev.hoon.basic.global.model;

import lombok.Getter;

@Getter
public class Ticket {

    private String ticketId;

    public Ticket(String ticketId) {

        this.ticketId = ticketId;
    }

}
