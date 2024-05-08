package com.challenge.dto;

import com.challenge.entity.PointOfInterest;

public record PointOfInterestResponseDTO(Long id, String name, Integer x, Integer y) {

    public PointOfInterestResponseDTO(PointOfInterest pointOfInterest) {
        this(pointOfInterest.getId(), pointOfInterest.getName(), pointOfInterest.getX(), pointOfInterest.getY());
    }
}
