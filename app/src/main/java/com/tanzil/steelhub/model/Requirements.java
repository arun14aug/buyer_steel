package com.tanzil.steelhub.model;

import java.util.ArrayList;

/**
 * Created by arun.sharma on 9/14/2016.
 */
public class Requirements {

    String requirement_id, user_id, grade_required, physical, chemical, test_certificate_required,
            length, type, required_by_date, budget, state, city, created_at, updated_at;

    String[] preffered_brands;
    ArrayList<Quantity> quantityArrayList;

    public String getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(String requirement_id) {
        this.requirement_id = requirement_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGrade_required() {
        return grade_required;
    }

    public void setGrade_required(String grade_required) {
        this.grade_required = grade_required;
    }

    public String getPhysical() {
        return physical;
    }

    public void setPhysical(String physical) {
        this.physical = physical;
    }

    public String getChemical() {
        return chemical;
    }

    public void setChemical(String chemical) {
        this.chemical = chemical;
    }

    public String getTest_certificate_required() {
        return test_certificate_required;
    }

    public void setTest_certificate_required(String test_certificate_required) {
        this.test_certificate_required = test_certificate_required;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired_by_date() {
        return required_by_date;
    }

    public void setRequired_by_date(String required_by_date) {
        this.required_by_date = required_by_date;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
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

    public String[] getPreffered_brands() {
        return preffered_brands;
    }

    public void setPreffered_brands(String[] preffered_brands) {
        this.preffered_brands = preffered_brands;
    }

    public ArrayList<Quantity> getQuantityArrayList() {
        return quantityArrayList;
    }

    public void setQuantityArrayList(ArrayList<Quantity> quantityArrayList) {
        this.quantityArrayList = quantityArrayList;
    }
}
