package Helpers;

import java.awt.*;

public class ShadeMaker {

    public Color shade(Color color, double percent) {
        int shadedRed = (int) normal(0, color.getRed(), percent);
        int shadedGreen = (int) normal(0, color.getGreen(), percent);
        int shadedBlue = (int) normal(0, color.getBlue(), percent);

        return new Color(shadedRed, shadedGreen, shadedBlue);
    }

    private double normal(double minValue, double originalValue, double percent) {
        return minValue + (originalValue - minValue) * (percent);
    }
}
