package com.jmcano.gestorpuestos.mapper;

import com.jmcano.gestorpuestos.dto.place.PlaceRequest;
import com.jmcano.gestorpuestos.dto.place.PlaceResponse;
import com.jmcano.gestorpuestos.model.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(target = "id", ignore = true)
    Place toEntity(PlaceRequest request);

    @Mapping(target = "preferredUserId", ignore = true)
    PlaceResponse toResponseMember(Place place);

    @Mapping(target = "preferredUserId", source = "preferredUser.id")
    PlaceResponse toResponseAdmin(Place place);

    default List<PlaceResponse> toResponseMember(List<Place> placeList){
        return placeList.stream()
                .map(this::toResponseMember)
                .toList();
    }

    default List<PlaceResponse> toResponseAdmin(List<Place> placeList) {
        return placeList.stream()
                .map(this::toResponseAdmin)
                .toList();
    }

}
