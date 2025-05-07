package com.jmcano.gestorpuestos.repository;

import com.jmcano.gestorpuestos.model.Booking;
import com.jmcano.gestorpuestos.model.Place;
import com.jmcano.gestorpuestos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByDate(LocalDate date);

    Optional<Booking> findByDateAndUser(LocalDate date, User user);

    Optional<Booking> findByDateAndPlace(LocalDate date, Place place);

    List<Booking> findAllByUserOrderByDateDesc(User user);

}
