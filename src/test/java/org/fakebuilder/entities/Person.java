package org.fakebuilder.entities;

import java.util.Date;
import java.util.List;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private GENDER gender;
    private boolean hasCar;
    private Car car;
    private List<String> jobHistory;
    private List<Car> carHistory;
    private List<GENDER> acceptedGenders;
    private Date birdate;
    
    public enum GENDER{
        MALE, FEMALE
    }

    public Person(String firstName, String lastName, int age, GENDER gender, boolean hasCar, Car car, List<String> jobHistory, List<Car> carHistory, List<GENDER> acceptedGenders, Date birdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.hasCar = hasCar;
        this.car = car;
        this.jobHistory = jobHistory;
        this.carHistory = carHistory;
        this.acceptedGenders = acceptedGenders;
        this.birdate = birdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public boolean isHasCar() {
        return hasCar;
    }

    public void setHasCar(boolean hasCar) {
        this.hasCar = hasCar;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<String> getJobHistory() {
        return jobHistory;
    }

    public void setJobHistory(List<String> jobHistory) {
        this.jobHistory = jobHistory;
    }

    public List<Car> getCarHistory() {
        return carHistory;
    }

    public void setCarHistory(List<Car> carHistory) {
        this.carHistory = carHistory;
    }

    public Date getBirdate() {
        return birdate;
    }

    public void setBirdate(Date birdate) {
        this.birdate = birdate;
    }

    public List<GENDER> getAcceptedGenders() {
        return acceptedGenders;
    }

    public void setAcceptedGenders(List<GENDER> acceptedGenders) {
        this.acceptedGenders = acceptedGenders;
    }
}
