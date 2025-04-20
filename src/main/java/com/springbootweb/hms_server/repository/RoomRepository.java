package com.springbootweb.hms_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springbootweb.hms_server.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT  DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r.photo FROM Room r WHERE r.id = :roomId")
    byte[] findPhotoByRoomId(@Param("roomId") Long roomId);

}
