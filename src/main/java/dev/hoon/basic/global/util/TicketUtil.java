package dev.hoon.basic.global.util;

import dev.hoon.basic.global.model.Ticket;

import java.util.UUID;

public class TicketUtil {

    /**
     * Ticket의 중복을 체크해주지는 않는다.
     */
    public static Ticket getTicket(TicketLabel label) {

        return new Ticket(
                new StringBuilder(label.getLabel())
                        .append("-")
                        .append(UUID.randomUUID().toString().replace("-", ""), 0, 10)
                        .toString()
        );
    }

    public enum TicketLabel {

        ORDER("O");

        private String label;

        TicketLabel(String label) {

            this.label = label;
        }

        public String getLabel() {

            return label;
        }
    }
}
