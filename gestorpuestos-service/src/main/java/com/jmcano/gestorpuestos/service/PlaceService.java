package com.jmcano.gestorpuestos.service;

import com.jmcano.gestorpuestos.dto.place.PlaceRequest;
import com.jmcano.gestorpuestos.dto.place.PlaceResponse;
import com.jmcano.gestorpuestos.dto.user.UserResponse;
import com.jmcano.gestorpuestos.mapper.PlaceMapper;
import com.jmcano.gestorpuestos.model.Place;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.repository.PlaceRepository;
import com.jmcano.gestorpuestos.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceMapper placeMapper;

    public PlaceResponse createPlace(PlaceRequest placeRequest) {
        Place place = placeMapper.toEntity(placeRequest);
        return placeMapper.toResponseAdmin(placeRepository.save(place));
    }

    @Transactional
    public PlaceResponse updatePreferredUser(Long placeId, Long userId){
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User user = userId != null
                ? userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
                : null;

        place.setPreferredUser(user);
        Place savedPlace = placeRepository.save(place);

        return placeMapper.toResponseAdmin(savedPlace);
    }
}
