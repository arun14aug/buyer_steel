package com.buyer.steelhub.utility;

/**
 * Created by arun on 5/3/15.
 */
public class ServiceApi {

    private static final String baseurl = "http://mysteelhub.com/"; //staging..


    // Users Family
    public static final String LOGIN = baseurl + "auth/securelogin";
//    public static final String LOGIN = baseurl + "authenticate";
    public static final String REGISTER = baseurl + "auth/register";
    public static final String VERIFY_USER = baseurl + "verifyuser.php";
    public static final String FORGOT_PASSWORD = baseurl + "recoverpassword";
    public static final String CHANGE_PASSWORD = baseurl + "auth/changepassword";
    public static final String SELLER_POST = "http://mysteelhub.com/seller/post";
    public static final String BUYER_POST = baseurl + "buyer/post";
    public static final String LOGOUT = baseurl + "auth/logout";

    public static final String FETCH_STEEL_SIZES = baseurl + "steelsizes";
    public static final String POSTED_REQUIREMENTS = baseurl + "posted/requirements";
    public static final String REQUIREMENT_DETAILS = baseurl + "requirement/details";
    public static final String FETCH_BRANDS = baseurl + "brands";
    public static final String FETCH_GRADES = baseurl + "grades";
    public static final String UPDATE_CONVERSATION_STATUS = baseurl + "updateConversationStatus";
    public static final String DELETE_POST = baseurl + "deletePost";

    public static final String FETCH_STATES = baseurl + "states";
    public static final String FETCH_TAX_TYPES = baseurl + "tax/types";
    public static final String FETCH_CUSTOMER_TYPE = baseurl + "customer/types";

    public static final String UPDATE_CONVERSATION = baseurl + "updateConversationStatus";

    public static final String FETCH_ADDRESS = baseurl + "fetchAddress";
    public static final String ADD_ADDRESS = baseurl + "addNewAddress";
    public static final String UPDATE_ADDRESS = baseurl + "editAddress";
    public static final String GET_ORDERS = baseurl + "getOrders";
    public static final String DELETE_ADDRESS = baseurl + "deleteAddress";
    public static final String GET_PROFILE = baseurl + "getProfile";
    public static final String SAVE_RTGS = baseurl + "saveRTGS";
    public static final String ACCOUNT_DETAILS = baseurl + "accountDetails";
    public static final String SAVE_ORDER_ADDRESS = baseurl + "saveOrderAddress";


    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    static final int NOTIFICATION_ID = 100;
    static final int NOTIFICATION_ID_BIG_IMAGE = 101;
}
