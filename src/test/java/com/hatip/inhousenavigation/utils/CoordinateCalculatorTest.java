package com.hatip.inhousenavigation.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoordinateCalculatorTest {

    @Test
    void testDistance() {
        double x1 = 0.0;
        double y1 = 0.0;
        double x2 = 3.0;
        double y2 = 4.0;

        double expectedDistance = 5.0;
        double actualDistance = CoordinateCalculator.distance(x1, y1, x2, y2);

        Assertions.assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void testFindCoordinatesOfZ() {
        CoordinateCalculator.CoordinatesAndDistances data = CoordinateCalculator.CoordinatesAndDistances.builder()
                .xA(1.0)
                .yA(5.0)
                .xB(15.0)
                .yB(9.0)
                .xC(20.0)
                .yC(10.0)
                .distanceToA(10.0)
                .distanceToB(14.0)
                .distanceToC(16.0)
                .build();


        double[] expectedCoordinates = {-8.5861613483026, 7.847017844044757};
        double[] actualCoordinates = CoordinateCalculator.findCoordinatesOfZ(data);

        Assertions.assertArrayEquals(expectedCoordinates, actualCoordinates, 0.001);
    }

}