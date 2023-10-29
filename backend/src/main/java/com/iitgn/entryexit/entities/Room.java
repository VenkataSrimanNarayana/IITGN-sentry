package com.iitgn.entryexit.entities;

//Room Table: room(block_no, room_no)
import com.iitgn.entryexit.models.id.RoomId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RoomId.class)
@Table(name = "room")
public class Room {
    @Id
    private String blockName;
    @Id
    private int roomNo;
}
