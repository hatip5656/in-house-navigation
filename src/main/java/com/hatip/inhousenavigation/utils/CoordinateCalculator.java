package com.hatip.inhousenavigation.utils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinateCalculator {

    static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    @Builder
    public static class CoordinatesAndDistances {
        private double xA, yA, xB, yB, xC, yC;
        private double distanceToA, distanceToB, distanceToC;
    }

    // ... (The rest of the methods remain unchanged)

    public static double[] findCoordinatesOfZ(CoordinatesAndDistances data) {
        double x, y;

        double slopeAB = (data.xB - data.xA) / (data.yB - data.yA);
        double slopeAC = (data.xC - data.xA) / (data.yC - data.yA);

        x = (slopeAB * slopeAC * (data.yA + data.yC) + slopeAB * (data.xA + data.xC) - slopeAC * (data.xA + data.xB)) / (2 * (slopeAB - slopeAC));
        y = -1 * (x - (data.xA + data.xB) / 2) / slopeAB + (data.yA + data.yB) / 2;

        validate(x, y);
        double distanceToCenter = distance(x, y, data.xA, data.yA);
        double scaleFactor = data.distanceToA / distanceToCenter;

        // Calculate the coordinates of point Z
        x = data.xA + (x - data.xA) * scaleFactor;
        y = data.yA + (y - data.yA) * scaleFactor;
        validate(x, y);
        return new double[] {x, y};
    }

    private static void validate(double x, double y) {
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("Invalid input data. Unable to calculate coordinates of point Z.");
        }
    }
}
