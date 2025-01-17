package org.example.creational.builder;

// TODO implement logic
public class Car {
    private String engine;
    private String transmission;
    private String interior;
    private String color;
    private boolean hasSunroof;
    private boolean hasGPS;
    private boolean hasSafetyPackage;

    private Car(Builder builder) {
    }

    @Override
    public String toString() {
        return "";
    }

    public static class Builder {

        public Car build() {
            return new Car(this);
        }
    }
}
