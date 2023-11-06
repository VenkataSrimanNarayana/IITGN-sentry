package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Room;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/room")
public class RoomController {

    private final UserService userService;

    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> userRegisterRoom(@PathVariable Long id, @RequestBody Room room){
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            User tempUser = user.get();
            Room room1 = Room.builder().blockName(room.getBlockName()).roomNo(room.getRoomNo()).build();
            tempUser.setRoom(room1);
            userService.updateUserById(id, tempUser);
            return ResponseEntity.ok().body(new SingleLineResponse("Room Allocated"));
        }else {
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid User Id"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            User tempUser = user.get();
            return ResponseEntity.ok().body(tempUser.getRoom());
        }else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleLineResponse> deleteRoomAllocation(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            User tempUser = user.get();
            tempUser.setRoom(null);
            userService.updateUserById(id, tempUser);
            return ResponseEntity.ok().body(new SingleLineResponse("Room Allocation Deleted"));
        }else{
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid User Id"));
        }
    }




}
