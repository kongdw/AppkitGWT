package com.appkit.ui.shared;

import java.math.BigDecimal;

public class Color {

    private int red;
    private int green;
    private int blue;
    private int alpha;

    public static Color whiteColor() {
        return new Color(255, 255, 255, 100);
    }

    public static Color blackColor() {
        return new Color();
    }

    public static Color redColor() {
        return new Color(255, 0, 0, 100);
    }

    public static Color greenColor() {
        return new Color(0, 255, 0, 100);
    }

    public static Color blueColor() {
        return new Color(0, 0, 255, 100);
    }

    public static Color colorFromHex(String hex) {

        Color color = null;

        try {
            color = new Color(
                    Integer.valueOf(hex.substring(1, 3), 16),
                    Integer.valueOf(hex.substring(3, 5), 16),
                    Integer.valueOf(hex.substring(5, 7), 16));

        } catch (Exception e) {
            return null;
        }

        return color;
    }

    public static Color colorWithHSBA(int hue, int saturation, int brightness, int alpha) {

        //clamp values
        hue = Math.max(Math.min(hue, 360), 0);
        saturation = Math.max(Math.min(saturation, 100), 0);
        brightness = Math.max(Math.min(brightness, 100), 0);


        double dhue = Color.precision(hue / 360.0);
        double dsaturation = Color.precision(saturation / 100.0);
        double dbrightness = Color.precision(brightness / 100.0);


        int h = (int) (dhue * 6);
        double f = Color.precision(dhue * 6 - h);
        double p = Color.precision(dbrightness * (1.0 - dsaturation));
        double q = Color.precision(dbrightness * (1.0 - f * dsaturation));
        double t = Color.precision(dbrightness * (1.0 - (1.0 - f) * dsaturation));

        switch (h) {
            case 0:
                return Color.colorWithRGBA(dbrightness, t, p, alpha);
            case 1:
                return Color.colorWithRGBA(q, dbrightness, p, alpha);
            case 2:
                return Color.colorWithRGBA(p, dbrightness, t, alpha);
            case 3:
                return Color.colorWithRGBA(p, q, dbrightness, alpha);
            case 4:
                return Color.colorWithRGBA(t, p, dbrightness, alpha);
            case 5:
                return Color.colorWithRGBA(dbrightness, p, q, alpha);
        }

        return new Color();

    }

    public static Color colorWithRGBA(double r, double g, double b, int alpha) {
        return new Color((int) Math.round(r * 255), (int) Math.round(g * 255), (int) Math.round(b * 255), alpha);
    }

    public static Color colorWithWhite(int white, int alpha) {
        return new Color(white, white, white, alpha);
    }


    private static double precision(double number) {
        return new BigDecimal(number).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public Color(int red, int green, int blue, int alpha) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 100);
    }


    public Color() {
        this(0, 0, 0, 100);
    }

    public double getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int[] getHSBComponents() {

        int max = Math.max(Math.max(red, green), blue);
        int min = Math.min(Math.min(red, green), blue);
        int delta = max - min;

        double brightness = Color.precision((double) max / 255.0);

        double saturation = 0;
        if (max > 0) {
            saturation = Color.precision((double) delta / max);
        }

        double hue;

        if (saturation == 0) {
            hue = 0.0;
        } else {

            double rr = Color.precision((double) (max - red) / delta);
            double gr = Color.precision((double) (max - green) / delta);
            double br = Color.precision((double) (max - blue) / delta);

            if (red == max) {
                hue = br - gr;
            } else if (green == max) {
                hue = 2 + rr - br;
            } else {
                hue = 4 + gr - rr;
            }

            hue = Color.precision(hue / 6.0);

            if (hue < 0) {
                hue++;
            }
        }

        return new int[]{
                (int) Math.round(hue * 360),
                (int) Math.round(saturation * 100),
                (int) Math.round(brightness * 100)
        };
    }

    public String toString() {
        return "rgba(" + red + "," + green + "," + blue + "," + Color.precision(alpha / 100.0) + ")";
    }

    private String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }

    public String toHexString() {
        StringBuilder sb = new StringBuilder();

        sb.append('#')
                .append(toBrowserHexValue(red))
                .append(toBrowserHexValue(green))
                .append(toBrowserHexValue(blue));
        return sb.toString();
    }

    public boolean equals(Color c) {
        return c.getRed() == red && c.getGreen() == green && c.getBlue() == blue &&
                c.getAlpha() == alpha;
    }


}
