package com.iitgn.entryexit.models.requestdto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MaidDetailsDto {
    private UUID id;
    private boolean isEntry;
}
