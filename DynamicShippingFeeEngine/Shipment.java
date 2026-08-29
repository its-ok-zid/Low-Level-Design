package DynamicShippingFeeEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shipment {
    private final String trackingCode;
    private final String destinationAddress;
    private final List<Package> packages = new ArrayList<>();
    private ShippingStrategy shippingStrategy;

    public Shipment(String trackingCode, String destinationAddress) {
        this(trackingCode, destinationAddress, new StandardGroundShipping());
    }

    public Shipment(String trackingCode, String destinationAddress, ShippingStrategy shippingStrategy) {
        this.trackingCode = trackingCode;
        this.destinationAddress = destinationAddress;
        this.shippingStrategy = (shippingStrategy != null) ? shippingStrategy : new StandardGroundShipping();
    }

    public void addPackage(Package pkg) {
        if (pkg != null) {
            packages.add(pkg);
        }
    }

    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = (shippingStrategy != null) ? shippingStrategy : new StandardGroundShipping();
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public List<Package> getPackages() {
        return Collections.unmodifiableList(packages);
    }

    public ShippingStrategy getShippingStrategy() {
        return shippingStrategy;
    }

    public double calculateTotalWeight() {
        return packages.stream().mapToDouble(Package::getWeightInKg).sum();
    }

    public double calculateTotalValue() {
        return packages.stream().mapToDouble(Package::getDeclaredValue).sum();
    }

    public double calculateShippingFee() {
        return shippingStrategy.calculateShippingFee(calculateTotalWeight(), calculateTotalValue());
    }
}