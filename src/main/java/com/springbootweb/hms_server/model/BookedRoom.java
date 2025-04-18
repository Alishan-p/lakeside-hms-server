package com.springbootweb.hms_server.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "booked_rooms")
@Data
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_fullname")
    private String guestFullName;

    private String guestEmail;

    @Column(name = "adults")
    private int numOfAdults;

    @Column(name = "childrens")
    private int numOfChildrens;

    @Column(name = "total_guests")
    private int totalNumOfGuest;

    @Column(name = "confirmation_code")
    private UUID bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void calculateTotalNumberOfGuest() {
        this.totalNumOfGuest = this.numOfAdults + this.numOfChildrens;
    }

    public void setNumOfAdults(int num) {
        this.numOfAdults = num;
        calculateTotalNumberOfGuest();
    }

    public void setNumOfChildrens(int num) {
        this.numOfChildrens = num;
        calculateTotalNumberOfGuest();
    }
}
