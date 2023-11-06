package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pending-requests")
@AllArgsConstructor
public class PendingRequestController {

    private final PendingRequestService pendingRequestService;

    public Long getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

    @PostMapping("/raise-self")
    public ResponseEntity<SingleLineResponse> raiseRequestSelf(@RequestBody PendingRequestSelfDto pendingRequestSelfDto){
        Long id = getCurrentUser();
        pendingRequestService.raiseRequestSelf(id, pendingRequestSelfDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }

    @PostMapping("/raise-other")
    public ResponseEntity<SingleLineResponse> raiseRequestOther(@RequestBody PendingRequestOtherDto requestOtherDto){
        Long id = getCurrentUser();
        pendingRequestService.raiseRequestOther(id, requestOtherDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/user/{requestId}")
    public ResponseEntity<SingleLineResponse> raiseRequest(@PathVariable Long requestId){
        Long id = getCurrentUser();
        PendingRequest pendingRequest = pendingRequestService.findById(requestId);
        if(pendingRequest.getUser().getId() != id){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        pendingRequestService.deleteRequest(requestId);
        return new ResponseEntity<>(new SingleLineResponse("Request deleted successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<SingleLineResponse> deleteRequest(@PathVariable Long requestId){
        pendingRequestService.deleteRequest(requestId);
        return new ResponseEntity<>(new SingleLineResponse("Request deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PendingRequest>> getPendingRequests(@RequestParam int offset, @RequestParam int limit){
        List<PendingRequest> pendingRequestList = pendingRequestService.findAllPendingRequests(offset, limit);
        if(pendingRequestList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pendingRequestList, HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<PendingRequest>> getPendingRequestSelf(){
        Long id = getCurrentUser();
        List<PendingRequest> pendingRequestList = pendingRequestService.findPendingRequestByUserId(id);
        if(pendingRequestList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pendingRequestList, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<PendingRequest> getPendingRequestById(@PathVariable Long requestId){
        PendingRequest pendingRequest = pendingRequestService.findById(requestId);
        if(pendingRequest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pendingRequest, HttpStatus.OK);
    }

    @GetMapping("/user/{requestId}")
    public ResponseEntity<PendingRequest> getPendingUserPendingRequest(@PathVariable Long requestId){
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
