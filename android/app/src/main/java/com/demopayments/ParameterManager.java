package com.demopayments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pax.poscore.commsetting.AidlSetting;
import com.pax.poscore.commsetting.BluetoothSetting;
import com.pax.poscore.commsetting.CommunicationSetting;
import com.pax.poscore.commsetting.HttpSetting;
import com.pax.poscore.commsetting.HttpsSetting;
import com.pax.poscore.commsetting.SslSetting;
import com.pax.poscore.commsetting.TcpSetting;
import com.pax.poscore.commsetting.UartSetting;
import com.pax.poscore.commsetting.UsbSetting;

public class ParameterManager {
    public static final String KEY_COMM_UART = "setting_comm_uart";
    public static final String KEY_COMM_USB = "setting_comm_usb";
    public static final String KEY_COMM_BLE = "setting_comm_ble";
    public static final String KEY_COMM_ETHERNET = "setting_comm_net";
    public static final String KEY_COMM_PORT = "setting_comm_net_port";
    public static final String KEY_COMM_ETH_TYPE = "setting_comm_net_protocol";
    public static final String KEY_COMM_HOST = "setting_comm_net_host";
    public static final String KEY_COMM_RATE = "setting_comm_uart_rate";
    public static final String KEY_COMM_COM_PORT = "setting_comm_uart_port";
    public static final String KEY_COMM_TIMEOUT = "setting_comm_timeout";
    public static final String KEY_COMM_AIDL = "setting_comm_aidl";
    public static final String KEY_COMM_BLE_MAC = "setting_comm_ble_mac";

    public static final String TCP = "TCP";
    public static final String HTTP = "HTTP";
    public static final String SSL = "SSL";
    public static final String HTTPS = "HTTPS";
    public static final String BLE = "BLE";
    public static final String USB = "USB";
    public static final String UART = "UART";
    public static final String AIDL = "AIDL";


    private static ParameterManager instance;

    private SharedPreferences preferences;

    private ParameterManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static ParameterManager getInstance(Context context) {
        if (instance == null) {
            instance = new ParameterManager(context);
        }
        return instance;
    }

    public String getCommType() {
        if (isAIDL()) {
            return AIDL;
        }
        if (isEth()) {
            return preferences.getString(KEY_COMM_ETH_TYPE, TCP);
        }
        if (preferences.getBoolean(KEY_COMM_BLE, false)) {
            return BLE;
        }
        if (preferences.getBoolean(KEY_COMM_USB, false)) {
            return USB;
        }
        if (isUART()) {
            return UART;
        }
        return TCP;
    }


    public boolean isEth() {
        return preferences.getBoolean(KEY_COMM_ETHERNET, true);
    }

    public boolean isUART() {
        return preferences.getBoolean(KEY_COMM_UART, false);
    }

    public boolean isUSB() {
        return preferences.getBoolean(KEY_COMM_USB, false);
    }

    public boolean isAIDL() {
        return preferences.getBoolean(KEY_COMM_AIDL, false);
    }

    public String getBaudRate() {
        return preferences.getString(KEY_COMM_RATE, "9600");
    }

    public boolean isBlueTooth() {
        return preferences.getBoolean(KEY_COMM_BLE, false);
    }

    public String getBleMac() {
        return preferences.getString(KEY_COMM_BLE_MAC, "00:00:00:00:00:00");
    }

    public String getEthernetPort() {
        return preferences.getString(KEY_COMM_PORT, "10009");
    }

    public String getHost() {
        return preferences.getString(KEY_COMM_HOST, "127.0.0.1");
    }

    public int getTimeout() {
        String timeout = preferences.getString(KEY_COMM_TIMEOUT, "60000");
        return Integer.parseInt(timeout);
    }

    public String getPort() {
        return preferences.getString(KEY_COMM_COM_PORT, "COM1");
    }

    public void setParameter(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void setParameter(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public CommunicationSetting getCommSetting() {
        if(true){
            return new AidlSetting();
        }
        String type = getCommType();
        switch (type) {
            case TCP:
                return new TcpSetting(getHost(), getEthernetPort(), getTimeout());
            case SSL:
                return new SslSetting(getHost(), getEthernetPort(), getTimeout());
            case HTTP:
                return new HttpSetting(getHost(), getEthernetPort(), getTimeout());
            case HTTPS:
                return new HttpsSetting(getHost(), getEthernetPort(), getTimeout());
            case UART:
                return new UartSetting(getPort(), getBaudRate(), getTimeout());
            case USB:
                UsbSetting usbSetting = new UsbSetting();
                usbSetting.setTimeout(getTimeout());
                return usbSetting;
            case AIDL:
                return new AidlSetting();
            case BLE:
                BluetoothSetting btSetting = new BluetoothSetting();
                btSetting.setMacAddr(getBleMac());
                btSetting.setTimeout(getTimeout());
                return btSetting;
            default:
                return null;
        }
    }
}
