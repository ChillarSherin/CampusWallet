package com.chillarcards.campuswallet.application;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = Context.MODE_PRIVATE;

    // Shared preferences file name
    private static final String PREF_NAME = "CampusWallet   ";


    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_NUMBER_VERIFIED = "is_number_verified";
    private static final String IS_USER_REGISTERED = "is_user_registered";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_NAME = "user_name";
    private static final String USER_BALANCE = "user_balance";
    private static final String MEMBER_ID = "member_id";
    private static final String USER_PASSWORD = "password";
    private static final String IS_STUDENT_SELECTED = "is_student_selected";
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_USER_ID = "student_user_id";
    private static final String VERSION_CODE = "version_code";
    private static final String STUDENT_CODE = "student_code";
    private static final String STUDENT_STD_DIV = "student_standard_division";
    private static final String STUDENT_STD_DIV_ID = "student_standard_division_id";
    private static final String STUDENT_STD = "student_standard";
    private static final String STUDENT_DIV = "student_division";
    private static final String STUDENT_NAME = "student_name";
    private static final String STUDENT_START_LONGI = "start_longi";
    private static final String STUDENT_START_LATTI = "start_latti";
    private static final String STUDENT_ROUTE_ID = "StudentRouteID";
    private static final String STUDENT_BUS_ID = "StudentBusID";
    private static final String STUDENT_SCHOOL_ID = "StudentSchoolD";
    private static final String STUDENT_SCHOOL_NAME = "StudentSchooName";
    private static final String AIRSHIP_FIRST_RUN = "FirstRun_Airship";
    private static final String WALLET_BALANCE = "Wallet_Balance";
    private static final String AWATING_BALANCE = "Awating_Balance";
    private static final String IS_PREORDER_AVAILABLE = "PreorderAvailable";
    private static final String IS_TOKEN_CLEAR = "IsTokenClear";
    private static final String TOKEN_CART = "TokenCart";
    private static final String INNER_API_BASE_URL = "InnerApiBaseUrl";
    private static final String BASE_ORDER_URL = "BASE_ORDER_URL";
    private static final String BASE_IMAGE_URL = "BASE_IMAGE_URL";
    private static final String BASE_URL_NL = "BASE_URL_NL";
    private static final String BASE_URL_XPAY = "BASE_URL_XPAY";
    private static final String CHECK_NEW_SWITCH = "switch_new_version";
    private static final String TYPE = "type";
    private static final String VERSION_3_2 = "version3_2";



    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setVersion32(boolean isVersion3_2){
        editor.putBoolean(VERSION_3_2, isVersion3_2);
        editor.commit();
    }

    public Boolean isVersion3_2() {
        return pref.getBoolean(VERSION_3_2, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }


    public void setIsNumberVerified(Boolean setvalue) {
        editor.putBoolean(IS_NUMBER_VERIFIED, setvalue);
        editor.commit();
    }

    public void setIsStudentSelected(Boolean setvalue) {
        editor.putBoolean(IS_STUDENT_SELECTED, setvalue);
        editor.commit();
    }


    public void setIsUserRegistered(Boolean setvalue) {
        editor.putBoolean(IS_USER_REGISTERED, setvalue);
        editor.commit();
    }


    public Boolean isNumberVerified() {
        return pref.getBoolean(IS_NUMBER_VERIFIED, false);
    }

    public boolean getIsFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public Boolean getIsUserRegistered() {
        return pref.getBoolean(IS_USER_REGISTERED, false);
    }

    public Boolean getIsStudentSelected() {
        return pref.getBoolean(IS_STUDENT_SELECTED, false);
    }

    public void setUserPhone(String userPhone) {
        editor.putString(USER_PHONE, userPhone);
        editor.commit();
    }

    public void setStudentId(String studentId) {
        editor.putString(STUDENT_ID, studentId);
        editor.commit();
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public void setVersionCode(String versionCode) {
        editor.putString(VERSION_CODE, versionCode);
        editor.commit();
    }

    public void setType(String type) {
        editor.putString(TYPE, type);
        editor.commit();
    }


    public void setStudentCode(String studentCode) {
        editor.putString(STUDENT_CODE, studentCode);
        editor.commit();
    }

    public void setUserEmail(String userEmail) {
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
    }

    public void setUserBalance(String userBalance) {
        editor.putString(USER_BALANCE, userBalance);
        editor.commit();
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public void setMemberId(String memberId) {
        editor.putString(MEMBER_ID, memberId);
        editor.commit();
    }

    public void setStudentStandard(String standard) {
        editor.putString(STUDENT_STD, standard);
        editor.commit();
    }

    public String getStudentStandard() {
        return pref.getString(STUDENT_STD, "");
    }

    public void setStudentName(String name) {
        editor.putString(STUDENT_NAME, name);
        editor.commit();
    }

    public String getStudentName() {
        return pref.getString(STUDENT_NAME, "");
    }

    public void setStudentDivision(String Division) {
        editor.putString(STUDENT_DIV, Division);
        editor.commit();
    }

    public String getStudentDivision() {
        return pref.getString(STUDENT_DIV, "");
    }

    public void setStudentStandardDivision(String standardDivision) {
        editor.putString(STUDENT_STD_DIV, standardDivision);
        editor.commit();
    }

    public String getStudentStandardDivision() {
        return pref.getString(STUDENT_STD_DIV, "");
    }

    public void setStudentStandardDivisionID(String standardDivision) {
        editor.putString(STUDENT_STD_DIV_ID, standardDivision);
        editor.commit();
    }

    public String getStudentStandardDivisionID() {
        return pref.getString(STUDENT_STD_DIV_ID, "");
    }

    public void setStudentStartLongi(String startlongi) {
        editor.putString(STUDENT_START_LONGI, startlongi);
        editor.commit();
    }

    public String getStudentStartLongi() {
        return pref.getString(STUDENT_START_LONGI, "");
    }

    public void setStudentStartLatti(String startlatti) {
        editor.putString(STUDENT_START_LATTI, startlatti);
        editor.commit();
    }

    public String getStudentStartLatti() {
        return pref.getString(STUDENT_START_LATTI, "");
    }

    public void setStudentRouteID(String routeID) {
        editor.putString(STUDENT_ROUTE_ID, routeID);
        editor.commit();
    }

    public String getStudentRouteID() {
        return pref.getString(STUDENT_ROUTE_ID, "");
    }

    public void setStudentBusID(String BusID) {
        editor.putString(STUDENT_BUS_ID, BusID);
        editor.commit();
    }

    public String getStudentBusID() {
        return pref.getString(STUDENT_BUS_ID, "");
    }

    public void setWalletBalance(String walletBalance) {
        editor.putString(WALLET_BALANCE, walletBalance);
        editor.commit();
    }

    public String getWalletBalance() {
        return pref.getString(WALLET_BALANCE, "");
    }


    public String getUserPassword() {
        return pref.getString(USER_PASSWORD, "");
    }

    public String getUserPhone() {
        return pref.getString(USER_PHONE, "");
    }

    public String getStudentId() {
        return pref.getString(STUDENT_ID, "");
    }

    public String getVersionCode() {
        return pref.getString(VERSION_CODE, "");
    }

    public String getStudentCode() {
        return pref.getString(STUDENT_CODE, "");
    }

    public String getUserId() {
        return pref.getString(USER_ID, "");
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "");
    }

    public String getUserBalance() {
        return pref.getString(USER_BALANCE, "");
    }

    public String getMemberId() {
        return pref.getString(MEMBER_ID, "");
    }

    public String getType() {
        return pref.getString(TYPE, "");
    }

    public void setSChoolID(String name) {
        editor.putString(STUDENT_SCHOOL_ID, name);
        editor.commit();
    }

    public String getSChoolID() {
        return pref.getString(STUDENT_SCHOOL_ID, "");
    }

    public void setSChoolName(String name) {
        editor.putString(STUDENT_SCHOOL_NAME, name);
        editor.commit();
    }

    public String getSChoolName() {
        return pref.getString(STUDENT_SCHOOL_NAME, "");
    }

    public void setUserPassword(String password) {
        editor.putString(USER_PASSWORD, password);
        editor.commit();
    }

    public void setFirstRun(boolean Division) {
        editor.putBoolean(AIRSHIP_FIRST_RUN, Division);
        editor.commit();
    }

    public boolean getFirstRun() {
        return pref.getBoolean(AIRSHIP_FIRST_RUN, true);
    }

    public void setIsTokenClear(boolean tokenClear) {
        editor.putBoolean(IS_TOKEN_CLEAR, tokenClear);
        editor.commit();
    }

    public boolean getIsTokenClear() {
        return pref.getBoolean(IS_TOKEN_CLEAR, false);
    }

    public void setTokenCart(String tokenCart) {
        editor.putString(TOKEN_CART, tokenCart);
        editor.commit();
    }

    public String getTokenCart() {
        return pref.getString(TOKEN_CART, "");
    }

    public void setStudentUserId(String userId) {
        editor.putString(STUDENT_USER_ID, userId);
        editor.commit();
    }

    public String getStudentUserId() {
        return pref.getString(STUDENT_USER_ID, "");
    }

    public void setInnerApiBaseUrl(String innerbaseurl) {
        editor.putString(INNER_API_BASE_URL, innerbaseurl);
        editor.commit();
    }

    public String getInnerApiBaseUrl() {
        return pref.getString(INNER_API_BASE_URL, "");
    }
    public void setBaseOrderUrl(String userId) {
        editor.putString(BASE_ORDER_URL, userId);
        editor.commit();
    }

    public String getBaseOrderUrl() {
        return pref.getString(BASE_ORDER_URL, "");
    }
    public void setBaseImageUrl(String userId) {
        editor.putString(BASE_IMAGE_URL, userId);
        editor.commit();
    }

    public String getBaseImageUrl() {
        return pref.getString(BASE_IMAGE_URL, "");
    }
    public void setBaseUrlNl(String userId) {
        editor.putString(BASE_URL_NL, userId);
        editor.commit();
    }

    public String getBaseUrlNl() {
        return pref.getString(BASE_URL_NL, "");
    }
    public void setBaseUrlXpay(String userId) {
        editor.putString(BASE_URL_XPAY, userId);
        editor.commit();
    }

    public String getBaseUrlXpay() {
        return pref.getString(BASE_URL_XPAY, "");
    }

    public void setAwatingBalance(String awatingBalance) {
        editor.putString(AWATING_BALANCE, awatingBalance);
        editor.commit();
    }

    public String getAwatingBalance() {
        return pref.getString(AWATING_BALANCE, "");
    }
    public void setCheckNewSwitch(String awatingBalance) {
        editor.putString(CHECK_NEW_SWITCH, awatingBalance);
        editor.commit();
    }

    public String getCheckNewSwitch() {
        return pref.getString(CHECK_NEW_SWITCH, "");
    }
    //clear all
    public void clearAll() {
        editor.clear();
        editor.commit();
    }

    public void setPreorderAvailable(String ispreorder) {
        editor.putString(IS_PREORDER_AVAILABLE, ispreorder);
        editor.commit();
    }

    public String getPreorderAvailable() {
        return pref.getString(IS_PREORDER_AVAILABLE, "");
    }
}
