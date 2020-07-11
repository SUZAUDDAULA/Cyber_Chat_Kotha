package com.opus_bd.myapplication.Utils;

public class Constants {
    public static final String BASE_URL = "http://220.247.164.188:8080/";
    public static final float PROGRESSBAR_ALPHA = 0.6f;
    public static final float PROGRESSBAR_AFTER_FINISH_ALPHA = 1f;

    // Login error codes

    public static final String TOKEN_INVALID = "400";
    public static final String TOKEN_EXPIRED = "401";
    public static final String TOKEN_NOT_FOUND = "320";
    public static final String USER_NOT_FOUND = "404";

    public static final int DUMMY_USER = 0;

    public static final int MODE_PURCHASE = 1;
    public static final int MODE_SALE = 2;
    public static final String DATABASE_NAME = "winkel_db";
    public static final int PURCHASE_DRAFT_ORDER = 1;
    public static final int SALE_DRAFT_ORDER = 2;
    public static final String DIALOG = "Dialog";
    public static final String RECEIVER_ID = "receiverID";
    public static final String SENDER_ID = "senderID";
    public static final String MESSAGE = "message";
    // Attendance Constants
    public static final int CHECK_IN = 1;
    public static final int CHECK_OUT = 2;
    public static final String STATUS_ID = "statusid";
    public static final int LATE_UPDATE = 3;
    public static final String ADMIN_EMP_CODE = "0001";
    public static final int MOVE_IN = 1;
    public static final int MOVE_OUT = 2;
    public static final int MEETING = 3;
    // Titles
    public static String BANGLA_TITLE = "বাংলা";
    public static String ENGLISH_TITLE = "ENG";
    // Languages
    public static String ENGLISH = "en";
    public static String BANGLA = "bd";


    //Movement Constants
    // Office Location
    public static double OFFICE_LONGITUDE = 90.4077325;
    public static double OFFICE_LATITUDE = 23.7371625;
    public static double CHECK_IN_APPROVE_DISTANCE = 100;


    //

    public static String callerId;
    public static String recipientId;
    public static String CallState;

    public static String Voice = "Voice";
    public static String Video = "Video";
    public static String ReciverNmae;
}
