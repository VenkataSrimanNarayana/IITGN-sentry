package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {

}
