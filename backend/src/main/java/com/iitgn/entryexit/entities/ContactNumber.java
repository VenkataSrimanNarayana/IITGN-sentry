package com.iitgn.entryexit.entities;

import com.iitgn.entryexit.models.id.ContactNumberId;
import jakarta.persistence.*;
import lombok.*;


//Contact Number Table: contact_no(user_id, phone)

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_number")
@IdClass(ContactNumberId.class)
public class ContactNumber {

    @Id
    private String type;

    @Id
    private String phone;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ContactNumber other = (ContactNumber) object;
        return type.equals(other.type) && phone.equals(other.phone);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(type, phone);
    }

}