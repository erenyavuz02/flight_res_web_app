package com.hitit.project.microservices.reservation_app.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
    

    @Id
    private String PNR;

    private String status;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;


    public String getPNR() {
        return PNR;
    }

    public void setPNR(String pNR) {
        PNR = pNR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




    
}
