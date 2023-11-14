package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<Room> getAllRooms();

    Room getRoomById(UUID roomId);
}
