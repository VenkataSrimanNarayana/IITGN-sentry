package com.iitgn.entryexit.models.id;

import lombok.Setter;

import java.io.Serializable;

@Setter
public class RoomId implements Serializable {
    private String blockName;
    private int roomNo;
}
