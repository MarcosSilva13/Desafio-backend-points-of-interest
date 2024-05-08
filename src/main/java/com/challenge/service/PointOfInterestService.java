package com.challenge.service;

import com.challenge.dto.PointOfInterestRequestDTO;
import com.challenge.dto.PointOfInterestResponseDTO;
import com.challenge.entity.PointOfInterest;
import com.challenge.exception.InvalidCoodinatePointsException;
import com.challenge.exception.InvalidDistanceValueException;
import com.challenge.repository.PointOfInterestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PointOfInterestService {

    private final PointOfInterestRepository pointOfInterestRepository;

    public PointOfInterestService(PointOfInterestRepository pointOfInterestRepository) {
        this.pointOfInterestRepository = pointOfInterestRepository;
    }

    @Transactional(readOnly = true)
    public List<PointOfInterestResponseDTO> getAll() {
        return pointOfInterestRepository.findAll()
                .stream()
                .map(PointOfInterestResponseDTO::new)
                .toList();
    }

    @Transactional
    public PointOfInterestResponseDTO createPoint(PointOfInterestRequestDTO requestDTO) {
        this.validateCoordinatePoints(requestDTO.x(), requestDTO.y());

        PointOfInterest pointOfInterest = new PointOfInterest(requestDTO.name(), requestDTO.x(), requestDTO.y());

        return new PointOfInterestResponseDTO(pointOfInterestRepository.save(pointOfInterest));
    }

    @Transactional(readOnly = true)
    public List<PointOfInterestResponseDTO> getAllByProximity(Integer x, Integer y, Integer maxDistance) {
        this.validateCoordinatePoints(x, y);
        this.validateDistanceValue(maxDistance);

        Integer xMax = x + maxDistance;
        Integer xMin = x - maxDistance;
        Integer yMax = y + maxDistance;
        Integer yMin = y - maxDistance;

        return pointOfInterestRepository.findAllByProximity(xMin, xMax, yMin, yMax)
                .stream()
                .filter(point -> this.calculateDistance(x, y, point.getX(), point.getY()) <= maxDistance)
                .map(PointOfInterestResponseDTO::new)
                .toList();
    }

    private Double calculateDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void validateCoordinatePoints(Integer x, Integer y) {
        if (x < 0 || y < 0) {
            throw new InvalidCoodinatePointsException("Pontos de coodenada devem ser valores positivos.");
        }
    }

    private void validateDistanceValue(Integer maxDistance) {
        if (maxDistance > 10) {
            throw new InvalidDistanceValueException("Valor máximo para a distância é 10 metros.");
        }
    }
}
