package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Room;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.requestdto.RoomAllocationDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.RoomService;
import com.iitgn.entryexit.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/room")
@PreAuthorize("hasAuthority('ROOM_PRIVILEGE')")
public class RoomController {

    private final UserService userService;

    private final RoomService roomService;


    @PostMapping
    public ResponseEntity<SingleLineResponse> userRegisterRoom(@RequestBody RoomAllocationDto roomAllocationDto){

        Long id = roomAllocationDto.getUserId();
        UUID roomId = roomAllocationDto.getRoomId();
        Optional<User> user = userService.getUserById(id);
        Room room = roomService.getRoomById(roomId);
        if (user.isPresent()) {
            User tempUser = user.get();
            tempUser.setRoom(room);
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

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms(){
        return ResponseEntity.ok().body(roomService.getAllRooms());
    }

}
