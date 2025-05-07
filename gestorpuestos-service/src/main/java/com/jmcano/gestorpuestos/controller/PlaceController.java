package com.jmcano.gestorpuestos.controller;

import com.jmcano.gestorpuestos.dto.place.PlaceRequest;
import com.jmcano.gestorpuestos.dto.place.PlaceResponse;
import com.jmcano.gestorpuestos.dto.preferredPlace.PreferredUserRequest;
import com.jmcano.gestorpuestos.dto.user.UserResponse;
import com.jmcano.gestorpuestos.mapper.PlaceMapper;
import com.jmcano.gestorpuestos.model.Place;
import com.jmcano.gestorpuestos.repository.PlaceRepository;
import com.jmcano.gestorpuestos.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PlaceResponse> createPlace(@RequestBody @Valid PlaceRequest placeRequest) {
        Place place = placeMapper.toEntity(placeRequest);
        Place savedPlace = placeRepository.save(place);
        PlaceResponse placeResponse = placeMapper.toResponseAdmin(savedPlace);

        URI location = URI.create("/api/places/" + savedPlace.getId());

        return ResponseEntity.created(location).body(placeResponse);
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> getAllPlaces() {
        List<PlaceResponse> places = placeMapper.toResponseMember(placeRepository.findAll());
        return ResponseEntity.ok(places);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{placeId}/preferreduser")
    public ResponseEntity<PlaceResponse> updatePreferredUser(@PathVariable Long placeId, @RequestBody @Valid PreferredUserRequest request){
        PlaceResponse placeResponse = placeService.updatePreferredUser(placeId, request.getUserId());
        return ResponseEntity.ok(placeResponse);
    }
}
