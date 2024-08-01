package com.hitit.project.microservices.reservation_app.dto;

import java.time.LocalDate;



public class PassengerRequest {

    private String pnr_code;

    private String passportNo;

    private String name;
    private String surname;
    private String nationalityNo;
    private LocalDate birthDate;
    private String telNo;
    private String gender;
    private String email;
    private String pType;
    public String getPnr_code() {
        return pnr_code;
    }
    public void setPnr_code(String pnr_code) {
        this.pnr_code = pnr_code;
    }
    public String getPassportNo() {
        return passportNo;
    }
    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getNationalityNo() {
        return nationalityNo;
    }
    public void setNationalityNo(String nationalityNo) {
        this.nationalityNo = nationalityNo;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getTelNo() {
        return telNo;
    }
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getpType() {
        return pType;
    }
    public void setpType(String pType) {
        this.pType = pType;
    }
    @Override
    public String toString() {
        return "PassengerRequest [pnr_code=" + pnr_code + ", passportNo=" + passportNo + ", name=" + name + ", surname="
                + surname + ", nationalityNo=" + nationalityNo + ", birthDate=" + birthDate + ", telNo=" + telNo
                + ", gender=" + gender + ", email=" + email + ", pType=" + pType + "]";
    }
    

    
}
