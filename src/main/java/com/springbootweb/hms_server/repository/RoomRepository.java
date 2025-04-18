package com.springbootweb.hms_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootweb.hms_server.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
