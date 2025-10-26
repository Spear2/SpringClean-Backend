package com.project.SpringClean.service;

import com.project.SpringClean.model.Booking;
import com.project.SpringClean.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setBookingDate(bookingDetails.getBookingDate());
            booking.setBookingTime(bookingDetails.getBookingTime());
            booking.setDuration(bookingDetails.getDuration());
            booking.setCustomer(bookingDetails.getCustomer());
            booking.setCompanyCleaner(bookingDetails.getCompanyCleaner());
            return bookingRepository.save(booking);
        }
        return null;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
