package com.springbootweb.hms_server.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.springbootweb.hms_server.model.Room;

public interface IRoomService {

    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice)
            throws SerialException, SQLException, IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    Optional<Room> findRoomByRoomId(Long id);

    void deleteRoomById(long id);

    byte[] getPhotoByRoomId(Long id) throws SQLException;

    Room updateRoom(Long id, String roomType, BigDecimal roomPrice, Blob photoByte);

}
