package com.buyer.steelhub.model;

/**
 * Created by arun.sharma on 4/25/2017.
 */

public class User {
//"id": 62,
//        "name": "seller",
//        "email": "seller@gmail.com",
//        "customer_type": "",
//        "company_name": "test",
//        "contact": "9087789909",
//        "address": "8phase",
//        "state": "punjab",
//        "city": "mohali",
//        "latitude": "8789787878",
//        "longitude": "66777787878",
//        "role": "seller",
//        "preffered_brands": ["tata",”birla”,”ambuja”],
//            "created_at": "2016-09-18 19:35:57",
//            "updated_at": "2016-09-23 01:06:52",
//            "zip": 189768,
//            "tin": "5455",
//            "pan": "455654467",
//            "brand": "birla",
//            "exp_quantity": "",
//            "status": 1,
//            "reason_if_rejected": ""

    private String id, name, email, customer_type, company_name, contact,
            address, state, city, latitude, longitude, role, created_at,
            updated_at, zip, tin, pan, brand, exp_quantity, status, reason_if_required;

    private String[] preffered_brands;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExp_quantity() {
        return exp_quantity;
    }

    public void setExp_quantity(String exp_quantity) {
        this.exp_quantity = exp_quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason_if_required() {
        return reason_if_required;
    }

    public void setReason_if_required(String reason_if_required) {
        this.reason_if_required = reason_if_required;
    }

    public String[] getPreffered_brands() {
        return preffered_brands;
    }

    public void setPreffered_brands(String[] preffered_brands) {
        this.preffered_brands = preffered_brands;
    }
}
