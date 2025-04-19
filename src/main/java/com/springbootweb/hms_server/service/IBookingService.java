package com.springbootweb.hms_server.service;

import java.util.List;

import com.springbootweb.hms_server.model.BookedRoom;

public interface IBookingService {

    List<BookedRoom> getAllBookingsByRoomId(long id);

}
