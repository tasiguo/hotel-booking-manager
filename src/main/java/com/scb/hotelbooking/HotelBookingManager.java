package com.scb.hotelbooking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.scb.hotelbooking.entity.Booking;
import com.scb.hotelbooking.entity.Room;

public class HotelBookingManager {
    // number of rooms, initial by constructure fuction
    private final int numberOfRooms;
    private final ConcurrentMap<LocalDate, Set<Integer>> bookingsByDate;
    private final ConcurrentMap<String, List<Booking>> bookingsByGuest;

    public HotelBookingManager(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        this.bookingsByDate = new ConcurrentHashMap<>();
        this.bookingsByGuest = new ConcurrentHashMap<>();
    }

    public boolean storeBooking(String guestName, int roomNumber, LocalDate date) {
        if (roomNumber < 1 || roomNumber > numberOfRooms) {
            return false;
        }

        Set<Integer> roomsForDate = bookingsByDate.computeIfAbsent(date, k -> new HashSet<>());
        synchronized (roomsForDate) {
            if (roomsForDate.contains(roomNumber)) {
                return false;
            }
            roomsForDate.add(roomNumber);
        }

        Room roon = new Room(roomNumber);
        Booking booking = new Booking(guestName, date, roon);
        bookingsByGuest.computeIfAbsent(guestName, k -> new ArrayList<>()).add(booking);

        return true;
    }

    public Set<Integer> findAvailableRooms(LocalDate date) {
        Set<Integer> bookedRooms = bookingsByDate.getOrDefault(date, Collections.emptySet());
        Set<Integer> availableRooms = new HashSet<>();
        for (int i = 1; i <= numberOfRooms; i++) {
            if (!bookedRooms.contains(i)) {
                availableRooms.add(i);
            }
        }
        return availableRooms;
    }

    public List<Booking> findAllBookingsForGuest(String guestName) {
        return new ArrayList<>(bookingsByGuest.getOrDefault(guestName, Collections.emptyList()));
    }

}
