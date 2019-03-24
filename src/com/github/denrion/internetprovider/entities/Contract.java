package com.github.denrion.internetprovider.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Contract {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty address;
    private SimpleStringProperty speed;
    private SimpleStringProperty bandwidth;
    private SimpleStringProperty contractLength;
    private SimpleStringProperty creationDate;

    public Contract() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.speed = new SimpleStringProperty();
        this.bandwidth = new SimpleStringProperty();
        this.contractLength = new SimpleStringProperty();
        this.creationDate = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getSpeed() {
        return speed.get();
    }

    public void setSpeed(String speed) {
        this.speed.set(speed);
    }

    public String getBandwidth() {
        return bandwidth.get();
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth.set(bandwidth);
    }

    public String getContractLength() {
        return contractLength.get();
    }

    public void setContractLength(String contractLength) {
        this.contractLength.set(contractLength);
    }

    public String getCreationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String unformattedDate = creationDate.get().replace(" ", "T");
        LocalDateTime ldt = LocalDateTime.parse(unformattedDate);
        return ldt.format(dtf);
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.set(creationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return this.getFirstName().equals(contract.getFirstName()) &&
                this.getLastName().equals(contract.getLastName()) &&
                this.getAddress().equals(contract.getAddress()) &&
                this.getSpeed().equals(contract.getSpeed()) &&
                this.getBandwidth().equals(contract.getBandwidth()) &&
                this.getContractLength().equals(contract.getContractLength());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, speed, bandwidth, contractLength);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", address=" + address +
                ", speed=" + speed +
                ", bandwidth=" + bandwidth +
                ", contractLength=" + contractLength +
                ", creationDate=" + creationDate +
                '}';
    }
}
