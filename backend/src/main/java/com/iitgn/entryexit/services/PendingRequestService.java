package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestVehicleDto;

import java.util.List;

public interface PendingRequestService {

    public void raiseRequestSelf(Long id, PendingRequestSelfDto pendingRequestSelfDto);

    void raiseRequestVehicle(Long id, PendingRequestVehicleDto requestVehicleDto);

    public List<PendingRequest> findAllPendingRequests(int offset, int limit);

    void raiseRequestOther(Long id, PendingRequestOtherDto requestOtherDto);

    void deleteRequest(Long requestId);

    List<PendingRequest> findPendingRequestByUserId(Long id);

    PendingRequest findById(Long requestId);

    void updateRequest(PendingRequest pendingRequest);


}
