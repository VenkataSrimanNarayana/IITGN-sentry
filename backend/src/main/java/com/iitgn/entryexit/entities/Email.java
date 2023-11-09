package com.iitgn.entryexit.entities;


import com.iitgn.entryexit.models.id.EmailId;
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
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email")
@IdClass(EmailId.class)
public class Email {

    @Id
    private String type;

    @Id
    private String email;
}
