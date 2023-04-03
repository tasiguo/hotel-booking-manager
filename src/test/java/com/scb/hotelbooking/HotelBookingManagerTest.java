package com.scb.hotelbooking;

import com.scb.hotelbooking.entity.Booking;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

public class HotelBookingManagerTest {
    
    @Test
    public void testStoreBooking() {
        HotelBookingManager manager = new HotelBookingManager(10);
        assertTrue(manager.storeBooking("Kay", 1, LocalDate.of(2023, 4, 5)));
        assertFalse(manager.storeBooking("Hey", 1, LocalDate.of(2023, 4, 5)));
        assertTrue(manager.storeBooking("Hey", 2, LocalDate.of(2023, 4, 5)));
    }

    @Test
    public void testFindAvailableRooms() {
        HotelBookingManager manager = new HotelBookingManager(10);
        manager.storeBooking("Kay", 1, LocalDate.of(2023, 4, 5));
        manager.storeBooking("Hey", 2, LocalDate.of(2023, 4, 5));
        Set<Integer> availableRooms = manager.findAvailableRooms(LocalDate.of(2023, 4, 5));
        assertFalse(availableRooms.contains(1));
        assertFalse(availableRooms.contains(2));
        assertTrue(availableRooms.contains(3));
        assertTrue(availableRooms.contains(10));
        assertFalse(availableRooms.contains(11));
    }

    @Test
    public void testFindAllBookingsForGuest() {
        HotelBookingManager manager = new HotelBookingManager(10);
        manager.storeBooking("Kay", 1, LocalDate.of(2023, 4, 5));
        manager.storeBooking("Kay", 2, LocalDate.of(2023, 4, 6));
        List<Booking> bookings = manager.findAllBookingsForGuest("Kay");
        assertEquals(2, bookings.size());
    }
}
