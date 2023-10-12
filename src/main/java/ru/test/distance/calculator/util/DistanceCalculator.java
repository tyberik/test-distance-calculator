package ru.test.distance.calculator.util;

import ru.test.distance.calculator.dto.Point;

import static java.lang.Math.*;

public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371.;


    public static double getDistance(Point pointFrom, Point pointTo) {


        final double dlng = deg2rad(pointFrom.getY() - pointTo.getY());
        final double dlat = deg2rad(pointFrom.getX() - pointTo.getX());
        final double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(pointTo.getX()))
                * cos(deg2rad(pointFrom.getX())) * sin(dlng / 2) * sin(dlng / 2);
        final double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return c * EARTH_RADIUS;
    }

    private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }
}
