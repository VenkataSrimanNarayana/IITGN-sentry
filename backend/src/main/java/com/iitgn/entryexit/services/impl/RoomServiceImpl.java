package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Room;
import com.iitgn.entryexit.repositories.RoomRepository;
import com.iitgn.entryexit.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(UUID roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }
}
