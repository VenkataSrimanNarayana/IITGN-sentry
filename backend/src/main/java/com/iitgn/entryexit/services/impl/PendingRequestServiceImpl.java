package com.iitgn.entryexit.services.impl;
import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.VisitorRequestDetails;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.entities.VehicleRequestDetails;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestVehicleDto;
import com.iitgn.entryexit.models.responses.PendingRequestResponse;
import com.iitgn.entryexit.repositories.PendingRequestRepository;
import com.iitgn.entryexit.repositories.RequestDetailsRepository;
import com.iitgn.entryexit.repositories.UserRepository;
import com.iitgn.entryexit.repositories.VehicleRequestDetailsRepository;
import com.iitgn.entryexit.services.PendingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PendingRequestServiceImpl implements PendingRequestService {

    private final PendingRequestRepository pendingRequestRepository;
    private final RequestDetailsRepository requestDetailsRepository;
    private final UserRepository userRepository;
    private final VehicleRequestDetailsRepository VehicleRequestDetailsRepository;

    @Override
    public void raiseRequestSelf(Long id, PendingRequestSelfDto pendingRequestSelfDto) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            PendingRequest pendingRequest = PendingRequest.builder()
                    .validFromDate(pendingRequestSelfDto.getValidFromDate())
                    .validFromTime(pendingRequestSelfDto.getValidFromTime())
                    .validUptoDate(pendingRequestSelfDto.getValidUptoDate())
                    .validUptoTime(pendingRequestSelfDto.getValidUptoTime())
                    .isEntry(pendingRequestSelfDto.isEntry())
                    .vehicleNo(pendingRequestSelfDto.getVehicleNo())
                    .reason(pendingRequestSelfDto.getReason())
                    .requestType("self")
                    .build();

            pendingRequest.setUser(user.get());
            pendingRequestRepository.save(pendingRequest);
        }
    }

    @Override
    public PendingRequest raiseRequestVehicle(Long id, PendingRequestVehicleDto requestVehicleDto) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            PendingRequest pendingRequest = PendingRequest.builder()
                    .validFromDate(requestVehicleDto.getValidFromDate())
                    .validFromTime(requestVehicleDto.getValidFromTime())
                    .validUptoDate(requestVehicleDto.getValidUptoDate())
                    .validUptoTime(requestVehicleDto.getValidUptoTime())
                    .isEntry(true)
                    .vehicleNo("taxi")
                    .reason("taxi")
                    .requestType("vehicle")
                    .build();

            VehicleRequestDetails vehicleRequestDetails = VehicleRequestDetails.builder()
                    .vehicleNo(requestVehicleDto.getVehicleNo())
                    .firstName(requestVehicleDto.getFirstName())
                    .lastName(requestVehicleDto.getLastName())
                    .mobileNo(requestVehicleDto.getMobileNo())
                    .isPickup(requestVehicleDto.isPickUp())
                    .build();

            pendingRequest.setUser(user.get());
            pendingRequestRepository.save(pendingRequest);
            vehicleRequestDetails.setPendingRequest(pendingRequest);
            VehicleRequestDetailsRepository.save(vehicleRequestDetails);
            return pendingRequest;
        }else{
            return null;
        }
    }

    @Override
    public PendingRequest raiseRequestOther(Long id, PendingRequestOtherDto requestOtherDto) {
        final Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            PendingRequest pendingRequest = PendingRequest.builder()
                    .validFromDate(requestOtherDto.getValidFromDate())
                    .validFromTime(requestOtherDto.getValidFromTime())
                    .validUptoDate(requestOtherDto.getValidUptoDate())
                    .vehicleNo(requestOtherDto.getVehicleNo())
                    .isEntry(true)
                    .validUptoTime(requestOtherDto.getValidUptoTime())
                    .reason(requestOtherDto.getReason())
                    .requestType("other")
                    .build();

            VisitorRequestDetails visitorRequestDetails = VisitorRequestDetails.builder()
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
            pendingRequestRepository.save(pendingRequest);
            visitorRequestDetails.setPendingRequest(pendingRequest);
            requestDetailsRepository.save(visitorRequestDetails);
            return pendingRequest;
        }
        return null;
    }

    @Override
    public void deleteRequest(UUID requestId) {
        pendingRequestRepository.deleteById(requestId);
    }

    @Override
    public List<PendingRequestResponse> findAllPendingRequests(int offset, int limit) {
        List<PendingRequest> pendingRequests = pendingRequestRepository.findAll(PageRequest.of(offset/limit, limit)).getContent();
        List<PendingRequestResponse> pendingRequestResponses = new java.util.ArrayList<>();
        for (PendingRequest pendingRequest :
                pendingRequests) {
            PendingRequestResponse pendingRequestResponse = PendingRequestResponse.builder()
                    .userId(pendingRequest.getUser().getId())
                    .requestId(pendingRequest.getRequestId())
                    .validFromDate(pendingRequest.getValidFromDate())
                    .validFromTime(pendingRequest.getValidFromTime())
                    .validUptoDate(pendingRequest.getValidUptoDate())
                    .requestType(pendingRequest.getRequestType())
                    .validUptoTime(pendingRequest.getValidUptoTime())
                    .reason(pendingRequest.getReason())
                    .isEntry(pendingRequest.isEntry())
                    .vehicleNo(pendingRequest.getVehicleNo())
                    .vehicleRequestDetails(pendingRequest.getVehicleRequestDetails())
                    .visitorRequestDetails(pendingRequest.getVisitorRequestDetails()).build();
            pendingRequestResponses.add(pendingRequestResponse);
        }
        return pendingRequestResponses;
    }

    @Override
    public List<PendingRequest> findPendingRequestByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            System.out.println(user.get().getPendingRequest());
            return pendingRequestRepository.findByUser(user.get());
        }
        return List.of();
    }

    @Override
    public PendingRequest findById(UUID requestId) {
        if(pendingRequestRepository.findById(requestId).isPresent()){
            return pendingRequestRepository.findById(requestId).get();
        }
        return null;
    }

    @Override
    public void updateRequest(PendingRequest pendingRequest) {
        pendingRequestRepository.save(pendingRequest);
    }


}


