package com.jmcano.gestorpuestos.repository;

import com.jmcano.gestorpuestos.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
