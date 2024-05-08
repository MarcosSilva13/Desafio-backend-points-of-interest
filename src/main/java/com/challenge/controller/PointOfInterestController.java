package com.challenge.controller;

import com.challenge.dto.PointOfInterestRequestDTO;
import com.challenge.dto.PointOfInterestResponseDTO;
import com.challenge.service.PointOfInterestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointOfInterestController {

    private final PointOfInterestService pointOfInterestService;

    public PointOfInterestController(PointOfInterestService pointOfInterestService) {
        this.pointOfInterestService = pointOfInterestService;
    }

    @GetMapping
    public ResponseEntity<List<PointOfInterestResponseDTO>> listAll() {
        return ResponseEntity.ok(pointOfInterestService.getAll());
    }

    @PostMapping
    public ResponseEntity<PointOfInterestResponseDTO> create(@RequestBody PointOfInterestRequestDTO requestDTO) {
        PointOfInterestResponseDTO responseDTO = pointOfInterestService.createPoint(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/proximity")
    public ResponseEntity<List<PointOfInterestResponseDTO>> listAllByProximity(@RequestParam Integer x,
                                                                               @RequestParam Integer y,
                                                                               @RequestParam Integer maxDistance) {
        return ResponseEntity.ok(pointOfInterestService.getAllByProximity(x, y, maxDistance));

    }
}
