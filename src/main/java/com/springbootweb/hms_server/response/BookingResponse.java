package com.springbootweb.hms_server.response;

import java.time.LocalDate;
import java.util.UUID;

import com.springbootweb.hms_server.model.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingResponse {
    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String guestFullName;

    private String guestEmail;

    private int numOfAdults;

    private int numOfChildrens;

    private int totalNumOfGuest;

    private UUID bookingConfirmationCode;

    private Room room;

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, UUID bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

}
