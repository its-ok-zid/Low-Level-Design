package DynamicShippingFeeEngine;

public class Package {
    private final String description;
    private final double weightInKg;
    private final double declaredValue;

    public Package(String description, double weightInKg, double declaredValue) {
        this.description = description;
        this.weightInKg = weightInKg;
        this.declaredValue = declaredValue;
    }

    public String getDescription() {
        return description;
    }

    public double getWeightInKg() {
        return weightInKg;
    }

    public double getDeclaredValue() {
        return declaredValue;
    }
}