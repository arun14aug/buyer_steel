package com.buyer.steelhub.model;

import java.util.ArrayList;

/**
 * Created by arun.sharma on 10/18/2016.
 */
public class Response {

    private String seller_id, seller_name, is_seller_read, initial_amt, is_buyer_read, req_for_bargain, is_seller_read_bargain, is_best_price,
            bargain_amt, is_buyer_read_bargain, is_accepted, is_seller_deleted, is_buyer_deleted;

    ArrayList<BargainUnit> bargainUnitArrayList;

    private String[] brands;

    public String[] getBrands() {
        return brands;
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    public ArrayList<BargainUnit> getBargainUnitArrayList() {
        return bargainUnitArrayList;
    }

    public void setBargainUnitArrayList(ArrayList<BargainUnit> bargainUnitArrayList) {
        this.bargainUnitArrayList = bargainUnitArrayList;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getIs_seller_read() {
        return is_seller_read;
    }

    public void setIs_seller_read(String is_seller_read) {
        this.is_seller_read = is_seller_read;
    }

    public String getInitial_amt() {
        return initial_amt;
    }

    public void setInitial_amt(String initial_amt) {
        this.initial_amt = initial_amt;
    }

    public String getIs_buyer_read() {
        return is_buyer_read;
    }

    public void setIs_buyer_read(String is_buyer_read) {
        this.is_buyer_read = is_buyer_read;
    }

    public String getReq_for_bargain() {
        return req_for_bargain;
    }

    public void setReq_for_bargain(String req_for_bargain) {
        this.req_for_bargain = req_for_bargain;
    }

    public String getIs_seller_read_bargain() {
        return is_seller_read_bargain;
    }

    public void setIs_seller_read_bargain(String is_seller_read_bargain) {
        this.is_seller_read_bargain = is_seller_read_bargain;
    }

    public String getIs_best_price() {
        return is_best_price;
    }

    public void setIs_best_price(String is_best_price) {
        this.is_best_price = is_best_price;
    }

    public String getBargain_amt() {
        return bargain_amt;
    }

    public void setBargain_amt(String bargain_amt) {
        this.bargain_amt = bargain_amt;
    }

    public String getIs_buyer_read_bargain() {
        return is_buyer_read_bargain;
    }

    public void setIs_buyer_read_bargain(String is_buyer_read_bargain) {
        this.is_buyer_read_bargain = is_buyer_read_bargain;
    }

    public String getIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(String is_accepted) {
        this.is_accepted = is_accepted;
    }

    public String getIs_seller_deleted() {
        return is_seller_deleted;
    }

    public void setIs_seller_deleted(String is_seller_deleted) {
        this.is_seller_deleted = is_seller_deleted;
    }

    public String getIs_buyer_deleted() {
        return is_buyer_deleted;
    }

    public void setIs_buyer_deleted(String is_buyer_deleted) {
        this.is_buyer_deleted = is_buyer_deleted;
    }
}
