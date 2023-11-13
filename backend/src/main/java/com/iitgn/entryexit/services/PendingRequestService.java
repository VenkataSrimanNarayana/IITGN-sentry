package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestVehicleDto;

import java.util.List;
import java.util.UUID;

public interface PendingRequestService {

    public void raiseRequestSelf(Long id, PendingRequestSelfDto pendingRequestSelfDto);

    PendingRequest raiseRequestVehicle(Long id, PendingRequestVehicleDto requestVehicleDto);

    public List<PendingRequest> findAllPendingRequests(int offset, int limit);

    PendingRequest raiseRequestOther(Long id, PendingRequestOtherDto requestOtherDto);

    void deleteRequest(UUID requestId);

    List<PendingRequest> findPendingRequestByUserId(Long id);

    PendingRequest findById(UUID requestId);

    void updateRequest(PendingRequest pendingRequest);


}

