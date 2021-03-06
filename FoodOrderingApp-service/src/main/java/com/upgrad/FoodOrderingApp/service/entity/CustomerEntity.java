package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("ALL")
@Entity
@Table(name = "customer", schema = "public", catalog = "restaurantdb")
@NamedQueries(
        {
                @NamedQuery(name = "customerByContactNumber", query = "select c from CustomerEntity c where c.contactNumber=:contactNumber"),
                @NamedQuery(name = "customerByCustomerId", query = "select c from CustomerEntity c where c.uuid=:customerId")
        }
)
public class CustomerEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "firstname")
    @Size(max = 30)
    @NotNull
    private String firstname;

    @Column(name = "lastname")
    @Size(max = 30)
    private String lastname;

    @Column(name = "email")
    @Size(max = 50)
    private String email;

    @Column(name = "contact_number")
    @Size(max = 30)
    @NotNull
    private String contactNumber;

    @Column(name = "password")
    @Size(max = 255)
    @NotNull
    private String password;

    @Column(name = "salt")
    @Size(max = 255)
    @NotNull
    private String salt;


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


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
