package unal.edu.co.controlcar.models;

/**
 * Created by erick on 26/10/2017.
 */

public class Car {

    private String plate;
    private String ownerEmail;
    private String model;
    private String brand;

    public Car() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString(){
        return this.plate + " - " + this.brand;
    }
}
