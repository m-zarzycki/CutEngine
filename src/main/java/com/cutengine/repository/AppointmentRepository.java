package com.cutengine.repository;

import com.cutengine.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);
    List<Appointment> findByStylistId(Long stylistId);
    List<Appointment> findByStylistIdAndStartsAtLessThanAndEndsAtGreaterThan(Long stylistId, LocalDateTime startsAt, LocalDateTime endsAt);
}
