package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestVehicleDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pending-requests")
@AllArgsConstructor
public class PendingRequestController {

    private final PendingRequestService pendingRequestService;

    public Long getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

    @PreAuthorize("hasAuthority('RAISE_PREQUEST_PRIVILEGE')")
    @PostMapping("/raise-self")
    public ResponseEntity<SingleLineResponse> raiseRequestSelf(@RequestBody PendingRequestSelfDto pendingRequestSelfDto){
        Long id = getCurrentUser();
        pendingRequestService.raiseRequestSelf(id, pendingRequestSelfDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RAISE_PREQUEST_PRIVILEGE')")
    @PostMapping("/raise-other")
    public ResponseEntity<SingleLineResponse> raiseRequestOther(@RequestBody PendingRequestOtherDto requestOtherDto){
        Long id = getCurrentUser();
        pendingRequestService.raiseRequestOther(id, requestOtherDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RAISE_PREQUEST_PRIVILEGE')")
    @PostMapping("/raise-vehicle")
    public ResponseEntity<SingleLineResponse> raiseVehicleRequest(@RequestBody PendingRequestVehicleDto requestVehicleDto){
        Long id = getCurrentUser();
        pendingRequestService.raiseRequestVehicle(id, requestVehicleDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_USER_PREQUEST_PRIVILEGE')")
    @DeleteMapping("/user/{requestId}")
    public ResponseEntity<SingleLineResponse> raiseRequest(@PathVariable UUID requestId){
        Long id = getCurrentUser();
        PendingRequest pendingRequest = pendingRequestService.findById(requestId);
        if(pendingRequest.getUser().getId() != id){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        pendingRequestService.deleteRequest(requestId);
        return new ResponseEntity<>(new SingleLineResponse("Request deleted successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_PREQUEST_PRIVILEGE')")
    @DeleteMapping("/{requestId}")
    public ResponseEntity<SingleLineResponse> deleteRequest(@PathVariable UUID requestId){
        pendingRequestService.deleteRequest(requestId);
        return new ResponseEntity<>(new SingleLineResponse("Request deleted successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_PREQUEST_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<PendingRequest>> getPendingRequests(@RequestParam int offset, @RequestParam int limit){
        List<PendingRequest> pendingRequestList = pendingRequestService.findAllPendingRequests(offset, limit);
        if(pendingRequestList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pendingRequestList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_USER_PREQUEST_PRIVILEGE')")
    @GetMapping("/user/all")
    public ResponseEntity<List<PendingRequest>> getPendingRequestSelf(){
        Long id = getCurrentUser();
        List<PendingRequest> pendingRequestList = pendingRequestService.findPendingRequestByUserId(id);
        if(pendingRequestList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pendingRequestList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_PREQUEST_PRIVILEGE')")
    @GetMapping("/{requestId}")
    public ResponseEntity<PendingRequest> getPendingRequestById(@PathVariable UUID requestId){
        PendingRequest pendingRequest = pendingRequestService.findById(requestId);
        if(pendingRequest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pendingRequest, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_USER_PREQUEST_PRIVILEGE')")
    @GetMapping("/user/{requestId}")
    public ResponseEntity<PendingRequest> getPendingUserPendingRequest(@PathVariable UUID requestId){
        PendingRequest pendingRequest = pendingRequestService.findById(requestId);
        if(pendingRequest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Long id = getCurrentUser();
        if(pendingRequest.getUser().getId() == id){
            return new ResponseEntity<>(pendingRequest, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }




}
