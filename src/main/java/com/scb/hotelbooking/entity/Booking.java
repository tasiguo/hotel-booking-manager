package com.scb.hotelbooking.entity;

import java.time.LocalDate;

public class Booking {
    private final String guestName;
    private final LocalDate date;
    private final Room room;

    public Booking(String guestName, LocalDate date, Room room) {
        this.guestName = guestName;
        this.date = date;
        this.room = room;
    }

    public String getGuestName() {
        return this.guestName;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Room getRoom() {
        return this.room;
    }
}
