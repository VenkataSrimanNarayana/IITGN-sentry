package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// user(user_id, first_name, last_name, house_no, area, Landmark, pin_code, Town_city, State, Country, Type)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    private long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(length = 10)
    private String houseNo;

    @Column(length = 30)
    private String area;

    @Column(length = 30)
    private String landmark;

    @Column(nullable = false)
    private int pinCode;

    @Column(length = 30)
    private String townCity;

    @Column(length = 30, nullable = false)
    private String state;

    @Column(length = 30, nullable = false)
    private String country;

    @Column(length = 10, nullable = false)
    private String userType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<ContactNumber> contactNumbers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Email> emails;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserLog> userLogs;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<PendingRequest> pendingRequest;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserVisitorLog> userVisitorLogs;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "user_room",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "room_id", referencedColumnName = "id"))
    private Room room;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getPrivileges().stream().map(privilege ->
                new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toList());
    }

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserVehicleLog> userVehicleLogs;

    @JsonIgnore
    @OneToOne
    private Maid maid;

    @JsonIgnore
    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}