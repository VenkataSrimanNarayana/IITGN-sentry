package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.RequestDetails;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.repositories.PendingRequestRepository;
import com.iitgn.entryexit.repositories.RequestDetailsRepository;
import com.iitgn.entryexit.repositories.UserRepository;
import com.iitgn.entryexit.services.PendingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PendingRequestServiceImpl implements PendingRequestService {

    private final PendingRequestRepository pendingRequestRepository;
    private final RequestDetailsRepository requestDetailsRepository;
    private final UserRepository userRepository;

    @Override
    public void raiseRequestSelf(Long id, PendingRequestSelfDto pendingRequestSelfDto) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            PendingRequest pendingRequest = PendingRequest.builder()
                    .validFromDate(pendingRequestSelfDto.getValidFromDate())
                    .validFromTime(pendingRequestSelfDto.getValidFromTime())
                    .validUptoDate(pendingRequestSelfDto.getValidUptoDate())
                    .validUptoTime(pendingRequestSelfDto.getValidUptoTime())
                    .reason(pendingRequestSelfDto.getReason())
                    .build();
            pendingRequest.setUser(user.get());
            pendingRequestRepository.save(pendingRequest);
        }
    }

    @Override
    public List<PendingRequest> findAllPendingRequests() {
        return pendingRequestRepository.findAll();
    }

    @Override
    public void raiseRequestOther(Long id, PendingRequestOtherDto requestOtherDto) {
        final Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            PendingRequest pendingRequest = PendingRequest.builder()
                    .validFromDate(requestOtherDto.getValidFromDate())
                    .validFromTime(requestOtherDto.getValidFromTime())
                    .validUptoDate(requestOtherDto.getValidUptoDate())
                    .validUptoTime(requestOtherDto.getValidUptoTime())
                    .reason(requestOtherDto.getReason())
                    .build();


            RequestDetails requestDetails = RequestDetails.builder()
                    .firstName(requestOtherDto.getFirstName())
                    .lastName(requestOtherDto.getLastName())
                    .houseNo(requestOtherDto.getHouseNo())
                    .area(requestOtherDto.getArea())
                    .landmark(requestOtherDto.getLandmark())
                    .pinCode(requestOtherDto.getPinCode())
                    .townCity(requestOtherDto.getTownCity())
                    .state(requestOtherDto.getState())
                    .country(requestOtherDto.getCountry())
                    .mobileNo(requestOtherDto.getMobileNo())
                    .vehicleNo(requestOtherDto.getVehicleNo())
                    .build();
            pendingRequest.setUser(user.get());

//            requestDetails.setPendingRequest(pendingRequest);
//            requestDetailsRepository.save(requestDetails);
            pendingRequest.setRequestDetails(requestDetails);
            pendingRequestRepository.save(pendingRequest);

        }
    }
}


