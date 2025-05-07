package com.jmcano.gestorpuestos.mapper;

import com.jmcano.gestorpuestos.dto.booking.BookingResponse;
import com.jmcano.gestorpuestos.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "place.id", target = "placeId")
    BookingResponse toResponse(Booking booking);

    List<BookingResponse> toResponse(List<Booking> bookingList);
}
