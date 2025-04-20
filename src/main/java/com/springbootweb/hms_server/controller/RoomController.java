package com.springbootweb.hms_server.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springbootweb.hms_server.exception.PhotoRetrieverException;
import com.springbootweb.hms_server.model.BookedRoom;
import com.springbootweb.hms_server.model.Room;
import com.springbootweb.hms_server.response.BookingResponse;
import com.springbootweb.hms_server.response.RoomResponse;
import com.springbootweb.hms_server.service.IBookingService;
import com.springbootweb.hms_server.service.IRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final IRoomService roomService;
    private final IBookingService bookingService;

    @PostMapping
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,
            @RequestParam String roomType, @RequestParam BigDecimal roomPrice)
            throws SerialException, SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = rooms.stream().map(room -> getRoomResponse(room)).toList();
        return ResponseEntity.ok(roomResponses);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomBYRoomId(@PathVariable Long id) throws SQLException {
        Room room = roomService.findRoomByRoomId(id);

        RoomResponse roomResponse = getRoomResponse(room);

        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable long id) {
        roomService.deleteRoomById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
        List<BookingResponse> bookingInfos = null;
        if (bookings != null)
            bookingInfos = bookings.stream().map(bookingInfo -> new BookingResponse(
                    bookingInfo.getId(),
                    bookingInfo.getCheckInDate(),
                    bookingInfo.getCheckOutDate(),
                    bookingInfo.getBookingConfirmationCode())).toList();

        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrieverException("Error retrieving photo");
            }
        }
        return new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.isBooked(),
                photoBytes,
                bookingInfos);
    }

    private List<BookedRoom> getAllBookingsByRoomId(long id) {
        return bookingService.getAllBookingsByRoomId(id);
    }
}
