package com.ecommerce.campestre.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_address", schema = "ecommerce")
public class UserAddress {

    @EmbeddedId
    private UserAddressId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("addressId")
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
