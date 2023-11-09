package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.RequestDetails;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.entities.VehicleRequestDetails;
import com.iitgn.entryexit.models.requestdto.PendingRequestOtherDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.requestdto.PendingRequestVehicleDto;
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
    public void raiseRequestVehicle(Long id, PendingRequestVehicleDto requestVehicleDto) {
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
        }
    }

    @Override
    public void raiseRequestOther(Long id, PendingRequestOtherDto requestOtherDto) {
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
            pendingRequestRepository.save(pendingRequest);
            requestDetails.setPendingRequest(pendingRequest);
            requestDetailsRepository.save(requestDetails);
        }
    }

    @Override
    public void deleteRequest(Long requestId) {
        pendingRequestRepository.deleteById(requestId);
    }

    @Override
    public List<PendingRequest> findAllPendingRequests(int offset, int limit) {
        return pendingRequestRepository.findAll(PageRequest.of(offset/limit, limit)).getContent();
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
    public PendingRequest findById(Long requestId) {
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


