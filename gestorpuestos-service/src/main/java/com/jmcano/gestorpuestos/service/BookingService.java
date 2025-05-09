package com.jmcano.gestorpuestos.service;

import com.jmcano.gestorpuestos.dto.booking.BookingResponse;
import com.jmcano.gestorpuestos.dto.placeAvailability.PlaceAvailabilityResponse;
import com.jmcano.gestorpuestos.mapper.BookingMapper;
import com.jmcano.gestorpuestos.model.Booking;
import com.jmcano.gestorpuestos.model.Place;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.repository.BookingRepository;
import com.jmcano.gestorpuestos.repository.PlaceRepository;
import com.jmcano.gestorpuestos.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse assist(String userName, LocalDate date) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        bookingRepository.findByDateAndUser(date, user).ifPresent(r -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a booking for that date");
        });

        List<Place> allPlaces = placeRepository.findAll();
        List<Booking> existingBookings = bookingRepository.findAllByDate(date);

        if (existingBookings.size() >= allPlaces.size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No available places for this date");
        }

        Map<Long, Booking> occupied = new HashMap<>();
        existingBookings.forEach(b -> occupied.put(b.getPlace().getId(), b));

        // 🟡 Buscar los sitios donde este usuario es prioritario
        List<Place> preferredPlaces = allPlaces.stream()
                .filter(p -> p.getPreferredUser() != null && p.getPreferredUser().getId().equals(user.getId()))
                .toList();

        Place assignedPlace = null;

        for (Place preferredPlace : preferredPlaces) {
            Booking currentBooking = occupied.get(preferredPlace.getId());

            if (currentBooking == null) {
                assignedPlace = preferredPlace;
                break;
            } else {
                User occupyingUser = currentBooking.getUser();

                // Buscar sitio libre para recolocar al usuario desplazado
                Place alternative = allPlaces.stream()
                        .filter(p -> !occupied.containsKey(p.getId()) && !p.getId().equals(preferredPlace.getId()))
                        .findFirst()
                        .orElse(null);

                if (alternative != null) {
                    bookingRepository.delete(currentBooking);
                    bookingRepository.flush();

                    Booking displaced = new Booking();
                    displaced.setUser(occupyingUser);
                    displaced.setPlace(alternative);
                    displaced.setDate(date);
                    bookingRepository.save(displaced);

                    occupied.put(alternative.getId(), displaced);
                    assignedPlace = preferredPlace;
                    break;
                }
            }
        }

        if (assignedPlace == null) {
            assignedPlace = allPlaces.stream()
                    .filter(p -> !occupied.containsKey(p.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "No available places"));
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setPlace(assignedPlace);
        booking.setDate(date);

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toResponse(saved);
    }



    public void cancelAssist(String userName, LocalDate date) {
        User user = userRepository.findByName(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Booking booking = bookingRepository.findByDateAndUser(date, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No booking found for user on this date"));

        bookingRepository.delete(booking);
    }

    public List<BookingResponse> findAll() {
        return bookingMapper.toResponse(bookingRepository.findAll());
    }

    public List<BookingResponse> getBookingHistory(String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Booking> bookings = bookingRepository.findAllByUserOrderByDateDesc(user);

        return bookingMapper.toResponse(bookings);
    }

    public List<BookingResponse> getBookingHistoryByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Booking> bookings = bookingRepository.findAllByUserOrderByDateDesc(user);

        return bookingMapper.toResponse(bookings);
    }

    public List<PlaceAvailabilityResponse> getAvailability(LocalDate date) {
        List<Place> places = placeRepository.findAll();
        List<Booking> bookings = bookingRepository.findAllByDate(date);

        Map<Long, Booking> bookingMap = bookings.stream()
                .collect(Collectors.toMap(b -> b.getPlace().getId(), b -> b));

        return places.stream()
                .map(place -> {
                    Booking booking = bookingMap.get(place.getId());
                    boolean occupied = booking != null;
                    Long userId = occupied ? booking.getUser().getId() : null;
                    String userName = occupied ? booking.getUser().getName() : null;
                    Long preferredUserId = !Objects.isNull(place.getPreferredUser())?place.getPreferredUser().getId():null;
                    return new PlaceAvailabilityResponse(
                            place.getId(),
                            place.getName(),
                            place.getX(),
                            place.getY(),
                            occupied,
                            userId,
                            userName,
                            preferredUserId,
                            date
                    );
                })
                .collect(Collectors.toList());
    }

    public List<PlaceAvailabilityResponse> getMonthlyAvailability(LocalDate fromDate, LocalDate toDate) {
        List<Place> places = placeRepository.findAll();
        List<Booking> bookings = bookingRepository.findAllByDateBetween(fromDate, toDate);

        return bookings.stream()
                .map(booking -> {
                    Place place = booking.getPlace();
                    User user = booking.getUser();
                    return new PlaceAvailabilityResponse(
                            place.getId(),
                            place.getName(),
                            place.getX(),
                            place.getY(),
                            true,
                            user.getId(),
                            user.getName(),
                            place.getPreferredUser() != null ? place.getPreferredUser().getId() : null,
                            booking.getDate()
                    );
                })
                .collect(Collectors.toList());
    }

}
