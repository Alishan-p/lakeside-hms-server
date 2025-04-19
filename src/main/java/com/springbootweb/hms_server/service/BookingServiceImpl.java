package com.springbootweb.hms_server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springbootweb.hms_server.model.BookedRoom;

@Service
public class BookingServiceImpl implements IBookingService {

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(long id) {
        return null;
    }

}
