package com.challenge.repository;

import com.challenge.entity.PointOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {

    @Query(value = """
            SELECT point FROM PointOfInterest point
            WHERE (point.x >= :xMin AND point.x <= :xMax AND point.y >= :yMin AND point.y <= :yMax)
            """)
    List<PointOfInterest> findAllByProximity(@Param("xMin") Integer xMin, @Param("xMax") Integer xMax,
                                             @Param("yMin") Integer yMin, @Param("yMax") Integer yMax);
}
