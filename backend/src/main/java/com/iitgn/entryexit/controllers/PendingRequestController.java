package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pending-requests")
@AllArgsConstructor
public class PendingRequestController {

    private final PendingRequestService pendingRequestService;

    @PostMapping("{id}/raise-self")
    public ResponseEntity<SingleLineResponse> raiseRequestSelf(@RequestBody PendingRequestSelfDto pendingRequestSelfDto, @PathVariable Long id){
        pendingRequestService.raiseRequestSelf(id, pendingRequestSelfDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }

//    @GetMapping("/")
//    public ResponseEntity<List<PendingRequest>> getPendingRequests(){
//        return pendingRequestService.findAllPendingRequests();
//
//    }

    @PostMapping("{id}/raise-other")
    public ResponseEntity<SingleLineResponse> raiseRequestOther(@RequestBody PendingRequestOtherDto requestOtherDto, @PathVariable Long id){
        pendingRequestService.raiseRequestOther(id, requestOtherDto);
        return new ResponseEntity<>(new SingleLineResponse("Request raised successfully"), HttpStatus.OK);
    }
}
