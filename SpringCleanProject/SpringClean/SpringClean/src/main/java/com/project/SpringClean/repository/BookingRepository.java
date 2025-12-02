package com.project.SpringClean.repository;



import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.CompanyCleaner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer_CustomerId(Long customerId);
    List<Booking> findByCompanyCleaner_CompanyCleanerId(Long companyCleanerId);
    List<Booking> findByCompanyCleanerAndBookingDate(CompanyCleaner companyCleaner, LocalDate bookingDate);

}
