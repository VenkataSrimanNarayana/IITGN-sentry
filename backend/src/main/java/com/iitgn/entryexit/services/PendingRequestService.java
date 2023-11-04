package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;

import java.util.List;

public interface PendingRequestService {

    public void raiseRequestSelf(Long id, PendingRequestSelfDto pendingRequestSelfDto);

    public List<PendingRequest> findAllPendingRequests();

    void raiseRequestOther(Long id, PendingRequestOtherDto requestOtherDto);
}

