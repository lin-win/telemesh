package com.w3engineers.unicef.telemesh.data.helper.constants;

import android.net.Uri;
import android.os.Build;

import com.w3engineers.unicef.telemesh.BuildConfig;
import com.w3engineers.unicef.telemesh.data.helper.AppCredentials;

import java.util.Locale;

public class Constants {

    public static boolean IS_LOADING_ENABLE = false;
    public static boolean IsMeshInit;
    public static boolean IS_LOG_UPLOADING_START = false;
    public static boolean IS_DATA_ON = false;

    public static int INTERNET_ONLY = 3;
    public static int SELLER_MODE = 1;

    public static Uri WALLET_URI;

    public static String DEFAULT_PASSWORD = "mesh_123";

    public static String FILE_TYPE = "application/*";

    public static int DEFAULT_AVATAR = 21;

    public static String DEFAULT_ADDRESS;
    public static String CURRENT_ADDRESS;

    public static long MINIMUM_SPACE = 50; // Initially checking 50 MB for downloading Telemesh and service App

    public interface DefaultValue {
        int NEG_INTEGER_ONE = -1;
        int DELAY_INTERVAL = 3000;
        int DOUBLE_PRESS_INTERVAL = 2000;
        int MINIMUM_TEXT_LIMIT = 2;
        int MINIMUM_PASSWORD_LIMIT = 8;
        int MINIMUM_INFO_LIMIT = 3;
        int MAXIMUM_TEXT_LIMIT = 20;
        int INTEGER_VALUE_ZERO = 0;
        int MAXIMUM_BADGE_VALUE = 99;
    }

    public interface preferenceKey {
        String USER_NAME = "first_name";
        String IMAGE_INDEX = "image_index";
        String MY_USER_ID = "my_user_id";//Constant.KEY_USER_ID; // We will change later hot fix by Azim vai.
        String IS_USER_REGISTERED = "user_registered";
        String IS_NOTIFICATION_ENABLED = "notification_enable";
        String APP_LANGUAGE = "app_language";
        String APP_LANGUAGE_DISPLAY = "app_language_display";
        String COMPANY_NAME = "company_name";
        String COMPANY_ID = "company_id";
        String MY_REGISTRATION_TIME = "registration_time";
        String MY_SYNC_IS_DONE = "my_sync_is_done";
        String ASK_ME_LATER = "ask_me_later";
        String ASK_ME_LATER_TIME = "ask_me_later_time";
        String UPDATE_APP_VERSION = "update_app_version";
        String UPDATE_APP_URL = "update_app_url";
        String MY_MODE = "my_mode";
        String MY_PASSWORD = "my_password";
        String MY_WALLET_ADDRESS = "my_wallet_address";
        String MY_PUBLIC_KEY = "my_public_key";
        String MY_WALLET_PATH = "my_wallet_path";
        String MY_WALLET_IMAGE = "my_wallet_image";
        String IS_RESTART = "is_restart";
        String NETWORK_PREFIX = "NETWORK_PREFIX";
        String CONFIG_STATUS = "CONFIG_STATUS";
        String CONFIG_VERSION_CODE = "CONFIG_VERSION_CODE";
        String TOKEN_GUIDE_VERSION_CODE = "TOKEN_GUIDE_VERSION_CODE";
    }

    public interface MenuItemPosition {
        int POSITION_FOR_DISCOVER = 0;
        int POSITION_FOR_FAVORITE = 1;
        int POSITION_FOR_MESSAGE_FEED = 2;
        int POSITION_FOR_MESSAGE_SETTINGS = 2;
    }

    public interface drawables {
        String AVATAR_IMAGE = "avatar";
        String AVATAR_DRAWABLE_DIRECTORY = "mipmap";
    }

    public interface IntentKeys {
        String USER_NAME = "user_name";
        String AVATAR_INDEX = "avatar_index";
        String PASSWORD = "password";
        String WALLET_PATH = "wallet_path";
    }

    public interface MessageStatus {
        int STATUS_UNREAD = 1;
        int STATUS_READ = 2;
        int STATUS_SENDING = 3;
        int STATUS_DELIVERED = 4;
        int STATUS_FAILED = 5;
        int STATUS_SEND = 6;
        int STATUS_RECEIVED = 7;
    }

    public interface DataType {
        //RM data type
        // Restricted for 1 and 3 for type
        byte MESSAGE = 0x2;
        byte MESSAGE_COUNT = 0x5;
        byte MESSAGE_FEED = 0x4;
        byte APP_SHARE_COUNT = 0x6;
        byte VERSION_HANDSHAKING = 0x7;
        byte SERVER_LINK = 0x8;
        byte FEEDBACK_TEXT = 0x9;
        byte FEEDBACK_ACK = 0x10;
        byte USER_UPDATE_INFO = 0x11;
        byte CONFIG_UPDATE_INFO = 0x12;
        byte TOKEN_GUIDE_REQUEST = 0x13;
        byte TOKEN_GUIDE_INFO = 0x14;
    }

    public interface MessageType {
        int TEXT_MESSAGE = 100;
        int DATE_MESSAGE = 101;
        int MESSAGE_INCOMING = 1;
        int MESSAGE_OUTGOING = 0;
    }

    public interface FavouriteStatus {
        int UNFAVOURITE = 0;
        int FAVOURITE = 1;
    }

    public interface SpinnerItem {
        int FAVOURITE = 0;
        int ALL = 1;
    }

    public interface UserStatus {
        int OFFLINE = 0;
        int WIFI_ONLINE = 5;
        int WIFI_MESH_ONLINE = 4;
        int BLE_MESH_ONLINE = 3;
        int BLE_ONLINE = 2;
        int INTERNET_ONLINE = 1;
    }

    public interface AppConstant {
        long LOADING_TIME = 30 * 1000;
        int MESSAGE_SYNC_PLOT = 10;
        int DEFAULT = 0;
        long LOADING_TIME_SHORT = 1000;
        String LOG_FOLDER = ".log";
        String DEFAULT_ADDRESS = ".defaultAddress";
        String INFO_LOG_FILE = "InfoLog.txt";
        String CRASH_REPORT_FILE_NAME = "Crashes.txt";
        String DEFAULT_ADDRESS_FILE = "defaultAddressFile.txt";
        String DASHES = "-------------------------------";
    }

    public interface Bulletin {
        int DEFAULT = 0;
        int BULLETIN_SEND = 1;
        int BULLETIN_RECEIVED = 2;
        int BULLETIN_SEND_TO_SERVER = 3;

        int MINE = 1;
        int OTHERS = 0;
    }

    public interface AnalyticsResponseType {
        byte MESSAGE_COUNT = 1;
        byte NEW_USER_COUNT = 2;
        byte APP_SHARE_COUNT = 3;
    }

    public interface UpdatedData {
        int YES = 0;
        int NO = 1;
    }

    public interface MeshLogType {
        int SPECIAL = 1;
        int WARNING = 2;
        int INFO = 3;
        int ERROR = 4;
        int DATE = 5;
    }

    public interface InAppUpdate {
        String LATEST_VERSION_KEY = "latestVersion";
        String LATEST_VERSION_CODE_KEY = "latestVersionCode";
        String URL_KEY = "url";
        String RELEASE_NOTE_KEY = "releaseNotes";
    }

    /**
     * Purpose: Get device name.
     *
     * @return string type device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase(Locale.getDefault()).startsWith(manufacturer.toLowerCase(Locale.getDefault()))) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public interface GradleBuildValues {
        String AUTH_PASSWORD = AppCredentials.getInstance().getAuthPassword();
        String AUTH_USER_NAME = AppCredentials.getInstance().getAuthUserName();
        String BROADCAST_TOKEN = AppCredentials.getInstance().getBroadCastToken();
        String BROADCAST_URL = AppCredentials.getInstance().getBroadCastUrl();
        String FILE_REPO_LINK = AppCredentials.getInstance().getFileRepoLink();
        String PARSE_APP_ID = AppCredentials.getInstance().getParseAppId();
        String PARSE_URL = AppCredentials.getInstance().getParseUrl();
    }
}
