package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.iitgn.entryexit.models.responses.UserLogResponse;

import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    @Query("SELECT ul FROM UserLog ul WHERE ul.user.id = ?1")
    List<UserLog> findAllByUserId(Long userId);

    @Query("SELECT new com.iitgn.entryexit.models.responses.UserLogResponse(ul.userLogId, ul.purpose, ul.isEntry, ul.eventDate, ul.eventTime, " +
            "ul.vehicleNo, ul.blockName, ul.roomNo, ul.user.id) FROM UserLog ul")
    List<UserLogResponse> findCustomDataFromUserLog();
}
