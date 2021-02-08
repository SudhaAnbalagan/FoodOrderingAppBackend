package com.upgrad.FoodOrderingApp.service.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("ALL")
@Entity
@Table(name = "address", schema = "public", catalog = "restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getAddressByUuid", query = "SELECT a FROM AddressEntity a WHERE a.uuid = :uuid"),
})
public class AddressEntity implements Serializable {


    public AddressEntity() {
    }

    public AddressEntity(String uuid, String flatBuilNumber, String locality, String city, String pincode, StateEntity stateEntityId) {
        this.uuid = uuid;
        this.flatBuilNumber = flatBuilNumber;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.stateEntityId = stateEntityId;
        return;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "flatBuilNumber")
    @NotNull
    @Size(max = 255)
    private String flatBuilNumber;

    @Column(name = "locality")
    @NotNull
    @Size(max = 255)
    private String locality;

    @Column(name = "city")
    @NotNull
    @Size(max = 30)
    private String city;

    @Column(name = "pincode")
    @NotNull
    @Size(max = 30)
    private String pincode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stateId")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StateEntity stateEntityId;


    @Column(name = "active")
    @NotNull
    private Integer active = 1;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getFlatBuilNumber() {
        return flatBuilNumber;
    }

    public void setFlatBuilNumber(String flatBuilNumber) {
        this.flatBuilNumber = flatBuilNumber;
    }


    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


    public StateEntity getStateEntityId() {
        return stateEntityId;
    }

    public void setStateEntityId(StateEntity stateEntityId) {
        this.stateEntityId = stateEntityId;
    }


    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }


}
