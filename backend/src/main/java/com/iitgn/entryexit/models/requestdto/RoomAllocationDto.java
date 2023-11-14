package com.iitgn.entryexit.models.requestdto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoomAllocationDto {
    private UUID roomId;
    private Long userId;
}
