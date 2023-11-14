package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Maid;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.requestdto.MaidDto;
import com.iitgn.entryexit.models.responses.MaidResponse;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.MaidService;
import com.iitgn.entryexit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//Maid Table:
//        maid(worker_id, first_name, last_name, house_no, area, Landmark, Pin_code, Town_city, State, Country, work_doing, mobile_no)


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maids")
public class MaidController {

    private final MaidService maidService;
    private final UserService userService;

    private Maid maidBuilderSave(@RequestBody MaidDto maidDto) {
        return Maid.builder().firstName(maidDto.getFirstName()).lastName(maidDto.getLastName()).houseNo(maidDto.getHouseNo()).area(maidDto.getArea()).landmark(maidDto.getLandmark()).pinCode(maidDto.getPinCode()).townCity(maidDto.getTownCity()).state(maidDto.getState()).country(maidDto.getCountry()).workDoing(maidDto.getWorkDoing()).mobileNo(maidDto.getMobileNo()).build();
    }

    @PreAuthorize("hasAuthority('REGISTER_MAID_PRIVILEGE')")
    @PostMapping("/register")
    public ResponseEntity<SingleLineResponse> registerMaid(@RequestBody MaidDto maidDto) {
        Maid maid = maidBuilderSave(maidDto);
        Optional<User> user = userService.getUserById(getCurrentUser());
        user.ifPresent(maid::setUser);
        maidService.saveMaid(maid);
        return ResponseEntity.ok(new SingleLineResponse("Maid Registered Successfully"));
    }

    @PreAuthorize("hasAuthority('UPDATE_MAID_PRIVILEGE')")
    @PostMapping("/update/{id}")
    public ResponseEntity<SingleLineResponse> updateMaid(@RequestBody MaidDto maidDto, @PathVariable UUID id) {
        Maid maid = maidService.getMaid(id);
        maidBuilderSave(maidDto);
        maidService.saveMaid(maid);
        return ResponseEntity.ok(new SingleLineResponse("Maid Updated Successfully"));
    }

    @PreAuthorize("hasAuthority('DELETE_MAID_PRIVILEGE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SingleLineResponse> deleteMaid(@PathVariable UUID id) {
        maidService.deleteMaid(id);
        return ResponseEntity.ok(new SingleLineResponse("Maid Deleted Successfully"));
    }

    private MaidResponse maidResponseBuilder(Maid maid){
        return MaidResponse.builder().workerId(maid.getWorkerId())
                .firstName(maid.getFirstName()).lastName(maid.getLastName()).houseNo(maid.getHouseNo())
                .area(maid.getArea()).landmark(maid.getLandmark()).pinCode(maid.getPinCode())
                .townCity(maid.getTownCity()).state(maid.getState()).country(maid.getCountry())
                .UserId(maid.getUser().getId()).workDoing(maid.getWorkDoing()).mobileNo(maid.getMobileNo()).build();
    }

    @PreAuthorize("hasAuthority('READ_MAID_DETAILS_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<MaidResponse>> getAllMaid() {
        List<MaidResponse> maidResponses = new ArrayList<>();
        List<Maid> maids = maidService.getAllMaid();
        for (Maid maid : maids) {
            MaidResponse maidResponse = maidResponseBuilder(maid);
            maidResponses.add(maidResponse);
        }
        return ResponseEntity.ok(maidResponses);
    }

    @PreAuthorize("hasAuthority('READ_MAID_DETAILS_PRIVILEGE')")
    @GetMapping("/{id}")
    public ResponseEntity<MaidResponse> getMaid(@PathVariable UUID id) {
        Maid maid = maidService.getMaid(id);
        if (maid == null) {
            return ResponseEntity.notFound().build();
        } else {
            MaidResponse maidResponse = maidResponseBuilder(maid);
            return ResponseEntity.ok(maidResponse);
        }
    }

    @PreAuthorize("hasAuthority('READ_MAID_DETAILS_USER_PRIVILEGE')")
    @GetMapping("/user")
    public ResponseEntity<List<Maid>> getAllMaidUser(){
        Long id = getCurrentUser();
        List<Maid> maid = maidService.getMaidByUserId(id);
        if(maid == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(maid);
        }
    }

    private Long getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }



}
