package com.springbootweb.hms_server.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springbootweb.hms_server.exception.ResourceNotFoundException;
import com.springbootweb.hms_server.model.Room;
import com.springbootweb.hms_server.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepo;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice)
            throws SerialException, SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photBlob = new SerialBlob(photoBytes);
            room.setPhoto(photBlob);
        }

        return roomRepo.save(room);

    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepo.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Optional<Room> findRoomByRoomId(Long id) {
        return roomRepo.findById(id);
    }

    @Override
    public void deleteRoomById(long id) {
        roomRepo.deleteById(id);
    }

    @Override
    public byte[] getPhotoByRoomId(Long id) throws SQLException {
        return roomRepo.findPhotoByRoomId(id);
    }

    @Override
    public Room updateRoom(Long id, String roomType, BigDecimal roomPrice, Blob photoByte)
            throws ResourceNotFoundException {
        Room room = roomRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if (room == null)
            return null;

        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        room.setPhoto(photoByte);
        return roomRepo.save(room);
    }

}
