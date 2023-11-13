package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Maid;
import com.iitgn.entryexit.models.requestdto.MaidDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.MaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


//Maid Table:
//        maid(worker_id, first_name, last_name, house_no, area, Landmark, Pin_code, Town_city, State, Country, work_doing, mobile_no)


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maids")
public class MaidController {

    private final MaidService maidService;

    private void maidBuilderSave(@RequestBody MaidDto maidDto) {
        Maid maid = Maid.builder().firstName(maidDto.getFirstName()).lastName(maidDto.getLastName()).houseNo(maidDto.getHouseNo()).area(maidDto.getArea()).landmark(maidDto.getLandmark()).pinCode(maidDto.getPinCode()).townCity(maidDto.getTownCity()).state(maidDto.getState()).country(maidDto.getCountry()).workDoing(maidDto.getWorkDoing()).mobileNo(maidDto.getMobileNo()).build();
        maidService.saveMaid(maid);
    }

    @PreAuthorize("hasAuthority('REGISTER_MAID_PRIVILEGE')")
    @PostMapping("/register")
    public ResponseEntity<SingleLineResponse> registerMaid(@RequestBody MaidDto maidDto) {
        maidBuilderSave(maidDto);
        return ResponseEntity.ok(new SingleLineResponse("Maid Registered Successfully"));
    }

    @PreAuthorize("hasAuthority('UPDATE_MAID_PRIVILEGE')")
    @PostMapping("/update/{id}")
    public ResponseEntity<SingleLineResponse> updateMaid(@RequestBody MaidDto maidDto, @PathVariable UUID id) {
        Maid maid = maidService.getMaid(id);
        maidBuilderSave(maidDto);
        return ResponseEntity.ok(new SingleLineResponse("Maid Updated Successfully"));
    }

    @PreAuthorize("hasAuthority('DELETE_MAID_PRIVILEGE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SingleLineResponse> deleteMaid(@PathVariable UUID id) {
        maidService.deleteMaid(id);
        return ResponseEntity.ok(new SingleLineResponse("Maid Deleted Successfully"));
    }

    @PreAuthorize("hasAuthority('READ_MAID_DETAILS_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<Maid>> getAllMaid() {
        return ResponseEntity.ok(maidService.getAllMaid());
    }

    @PreAuthorize("hasAuthority('READ_MAID_DETAILS_PRIVILEGE')")
    @GetMapping("/{id}")
    public ResponseEntity<Maid> getMaid(@PathVariable UUID id) {
        Maid maid = maidService.getMaid(id);
        if (maid == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(maid);
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
