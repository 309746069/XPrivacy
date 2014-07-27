package biz.bokhorst.xprivacy;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.NfcAdapter;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.telephony.TelephonyManager;

@SuppressLint("InlinedApi")
public class Meta {
	private static boolean mAnnotated = false;
	private static List<Hook> mListHook = new ArrayList<Hook>();

	public final static String cTypeAccount = "Account";
	public final static String cTypeAccountHash = "AccountHash";
	public final static String cTypeApplication = "Application";
	public final static String cTypeContact = "Contact";
	public final static String cTypeTemplate = "Template";

	public final static String cTypeAddress = "Address";
	public final static String cTypeAction = "Action";
	public final static String cTypeCommand = "Command";
	public final static String cTypeFilename = "Filename";
	public final static String cTypeIPAddress = "IPAddress";
	public final static String cTypeLibrary = "Library";
	public final static String cTypeMethod = "Method";
	public final static String cTypePermission = "Permission";
	public final static String cTypeProc = "Proc";
	public final static String cTypeTransaction = "Transaction";
	public final static String cTypeUrl = "Url";

	public static boolean isWhitelist(String type) {
		return (cTypeAddress.equals(type) || cTypeAction.equals(type) || cTypeCommand.equals(type)
				|| cTypeFilename.equals(type) || cTypeIPAddress.equals(type) || cTypeLibrary.equals(type)
				|| cTypeMethod.equals(type) || cTypePermission.equals(type) || cTypeProc.equals(type)
				|| cTypeTransaction.equals(type) || cTypeUrl.equals(type));
	}

	public static List<Hook> get() {
		// http://developer.android.com/reference/android/Manifest.permission.html
		if (mListHook.size() > 0)
			return mListHook;

		// @formatter:off
		mListHook.add(new Hook("accounts", "addOnAccountsUpdatedListener", "GET_ACCOUNTS", 5, null, null).notAOSP(19));
		mListHook.add(new Hook("accounts", "blockingGetAuthToken", "USE_CREDENTIALS", 5, null, null).unsafe());
		mListHook.add(new Hook("accounts", "getAccounts", "GET_ACCOUNTS", 5, null, null).notAOSP(19));
		mListHook.add(new Hook("accounts", "getAccountsByType", "GET_ACCOUNTS", 5, null, null).notAOSP(19));
		mListHook.add(new Hook("accounts", "getAccountsByTypeAndFeatures", "GET_ACCOUNTS", 5, null, null).notAOSP(19));
		mListHook.add(new Hook("accounts", "getAuthToken", "USE_CREDENTIALS", 5, "0.0", null).unsafe().dangerous());
		mListHook.add(new Hook("accounts", "getAuthTokenByFeatures", "MANAGE_ACCOUNTS", 5, "0.0", null).unsafe().dangerous());
		mListHook.add(new Hook("accounts", "hasFeatures", "GET_ACCOUNTS", 8, null, null).unsafe());
		mListHook.add(new Hook("accounts", "getAccountsByTypeForPackage", "GET_ACCOUNTS", 18, null, null).notAOSP(19));

		mListHook.add(new Hook("accounts", "getTokenGoogle", "GET_ACCOUNTS", 1, "0.0", null).unsafe().dangerous());
		mListHook.add(new Hook("accounts", "getTokenWithNotificationGoogle", "GET_ACCOUNTS", 1, "0.0", null).unsafe().dangerous());

		mListHook.add(new Hook("accounts", "getAuthenticatorTypes", "GET_ACCOUNTS", 5, "1.99.24", null).unsafe().dangerous());
		mListHook.add(new Hook("accounts", "getCurrentSync", "READ_SYNC_SETTINGS", 8, "1.99.24", null).notAOSP(19).dangerous());
		mListHook.add(new Hook("accounts", "getCurrentSyncs", "READ_SYNC_SETTINGS", 11, "1.99.24", null).notAOSP(19).dangerous());
		mListHook.add(new Hook("accounts", "getSyncAdapterTypes", "", 5, "1.99.24", null).unsafe().dangerous());

		mListHook.add(new Hook("accounts", "Srv_getAccounts", "GET_ACCOUNTS", 19, "2.99", "getAccounts").AOSP(19));
		mListHook.add(new Hook("accounts", "Srv_getAccountsAsUser", "GET_ACCOUNTS", 19, "2.99", null).AOSP(19));
		mListHook.add(new Hook("accounts", "Srv_getAccountsByFeatures", "GET_ACCOUNTS", 19, "2.99", "getAccountsByTypeAndFeatures").AOSP(19));
		mListHook.add(new Hook("accounts", "Srv_getSharedAccountsAsUser", "GET_ACCOUNTS", 19, "2.99", null).AOSP(19));
		mListHook.add(new Hook("accounts", "Srv_getCurrentSyncs", "READ_SYNC_SETTINGS", 19, "2.99", "getCurrentSyncs").AOSP(19));

		mListHook.add(new Hook("browser", "BrowserProvider2", "com.android.browser.permission.READ_HISTORY_BOOKMARKS,GLOBAL_SEARCH", 1, null, null));
		mListHook.add(new Hook("browser", "Downloads", "ACCESS_DOWNLOAD_MANAGER,ACCESS_DOWNLOAD_MANAGER_ADVANCED,ACCESS_ALL_DOWNLOADS", 1, "1.99.43", null).dangerous());

		mListHook.add(new Hook("calendar", "CalendarProvider2", "READ_CALENDAR,WRITE_CALENDAR", 1, null, null));

		mListHook.add(new Hook("calling", "sendDataMessage", "SEND_SMS", 4, null, null).notAOSP(19).whitelist(cTypeAddress).doNotify());
		mListHook.add(new Hook("calling", "sendMultipartTextMessage", "SEND_SMS", 4, null, null).notAOSP(19).whitelist(cTypeAddress).doNotify());
		mListHook.add(new Hook("calling", "sendTextMessage", "SEND_SMS", 4, null, null).notAOSP(19).whitelist(cTypeAddress).doNotify());

		mListHook.add(new Hook("calling", "Srv_sendData", "SEND_SMS", 4, null, "sendDataMessage").AOSP(19).whitelist(cTypeAddress).doNotify());
		mListHook.add(new Hook("calling", "Srv_sendMultipartText", "SEND_SMS", 4, null, "sendMultipartTextMessage").AOSP(19).whitelist(cTypeAddress).doNotify());
		mListHook.add(new Hook("calling", "Srv_sendText", "SEND_SMS", 4, null, "sendTextMessage").AOSP(19).whitelist(cTypeAddress).doNotify());

		mListHook.add(new Hook("calling", TelephonyManager.ACTION_RESPOND_VIA_MESSAGE, "SEND_RESPOND_VIA_MESSAGE", 18, null, null).doNotify());
		mListHook.add(new Hook("calling", Intent.ACTION_CALL, "CALL_PHONE", 10, null, null).doNotify());
		mListHook.add(new Hook("calling", Intent.ACTION_DIAL, "", 10, "2.2.2", null).doNotify());
		mListHook.add(new Hook("calling", Intent.ACTION_NEW_OUTGOING_CALL, "PROCESS_OUTGOING_CALLS", 10, "2.1.23", "phone/android.intent.action.NEW_OUTGOING_CALL"));
		mListHook.add(new Hook("calling", "CallLogProvider", "READ_CALL_LOG,WRITE_CALL_LOG", 1, "2.1.23", "phone/CallLogProvider"));

		mListHook.add(new Hook("calling", "SIP.isApiSupported", "USE_SIP", 9, null, null).doNotify());
		mListHook.add(new Hook("calling", "SIP.isSipWifiOnly", "USE_SIP", 9, null, null).doNotify());
		mListHook.add(new Hook("calling", "SIP.isVoipSupported", "USE_SIP", 9, null, null).doNotify());
		mListHook.add(new Hook("calling", "SIP.newInstance", "USE_SIP", 9, null, null).doNotify());

		mListHook.add(new Hook("clipboard", "addPrimaryClipChangedListener", "", 11, null, null));
		mListHook.add(new Hook("clipboard", "getPrimaryClip", "", 11, null, null).doNotify());
		mListHook.add(new Hook("clipboard", "getPrimaryClipDescription", "", 11, null, null).doNotify());
		mListHook.add(new Hook("clipboard", "getText", "", 10, null, null).doNotify());
		mListHook.add(new Hook("clipboard", "hasPrimaryClip", "", 11, null, null).doNotify());
		mListHook.add(new Hook("clipboard", "hasText", "", 10, null, null).doNotify());

		mListHook.add(new Hook("contacts", "contacts/contacts", "READ_CONTACTS,WRITE_CONTACTS", 1, null, null));
		mListHook.add(new Hook("contacts", "contacts/data", "READ_CONTACTS,WRITE_CONTACTS", 1, null, null));
		mListHook.add(new Hook("contacts", "contacts/people", "READ_CONTACTS,WRITE_CONTACTS", 1, "1.99.46", null));
		mListHook.add(new Hook("contacts", "contacts/phone_lookup", "READ_CONTACTS,WRITE_CONTACTS", 1, null, null));
		mListHook.add(new Hook("contacts", "contacts/profile", "READ_PROFILE,WRITE_PROFILE", 1, "1.99.38", null).dangerous());
		mListHook.add(new Hook("contacts", "contacts/raw_contacts", "READ_CONTACTS,WRITE_CONTACTS", 1, null, null));
		mListHook.add(new Hook("contacts", "ContactsProvider2", "READ_CONTACTS,WRITE_CONTACTS,READ_PROFILE,WRITE_PROFILE", 1, "1.99.38", null).dangerous());
		mListHook.add(new Hook("contacts", "IccProvider", "READ_CONTACTS,WRITE_CONTACTS", 1, "1.99.38", null));

		mListHook.add(new Hook("dictionary", "UserDictionary", "READ_USER_DICTIONARY", 1, null, null));

		mListHook.add(new Hook("email", "EMailProvider", "com.android.email.permission.ACCESS_PROVIDER", 1, null, null));
		mListHook.add(new Hook("email", "GMailProvider", "com.google.android.gm.permission.READ_CONTENT_PROVIDER", 8, "1.99.20", null));

		mListHook.add(new Hook("identification", "%hostname", "", 1, null, null).unsafe());
		mListHook.add(new Hook("identification", "%imei", "", 1, null, null).unsafe());
		mListHook.add(new Hook("identification", "%macaddr", "", 1, null, null).unsafe());
		mListHook.add(new Hook("identification", "%serialno", "", 1, null, null).unsafe());
		mListHook.add(new Hook("identification", "%cid", "", 1, null, null).unsafe());
		mListHook.add(new Hook("identification", "/proc", "", 1, "1.7", null).unsafe().dangerous().whitelist(cTypeProc));
		mListHook.add(new Hook("identification", "/system/build.prop", "", 1, "1.9.9", null).unsafe().dangerous());
		mListHook.add(new Hook("identification", "/sys/block/.../cid", "", 1, "0.0", null).unsafe().dangerous());
		mListHook.add(new Hook("identification", "/sys/class/.../cid", "", 1, "0.0", null).unsafe().dangerous());
		mListHook.add(new Hook("identification", "AdvertisingId", "", 1, null, null).unsafe());
		mListHook.add(new Hook("identification", "getString", "", 1, null, null));
		mListHook.add(new Hook("identification", "getDescriptor", "", 16, null, null));
		mListHook.add(new Hook("identification", "InputDevice.getName", "", 9, null, null));
		mListHook.add(new Hook("identification", "GservicesProvider", "com.google.android.providers.gsf.permission.READ_GSERVICES,com.google.android.providers.gsf.permission.WRITE_GSERVICES", 1, null, null).dangerous());
		mListHook.add(new Hook("identification", "SERIAL", "", 1, null, null).restart().noUsageData());

		mListHook.add(new Hook("identification", "USB.getDeviceId", "", 12, "2.1.7", null));
		mListHook.add(new Hook("identification", "USB.getDeviceName", "", 12, "2.1.7", null));
		mListHook.add(new Hook("identification", "USB.getSerialNumber", "", 20, "2.1.17", null));

		// java.net.InetAddress
		mListHook.add(new Hook("internet", "getAllByName", "INTERNET", 1, "0.0", null).unsafe().dangerous().whitelist(cTypeIPAddress));
		mListHook.add(new Hook("internet", "getByAddress", "INTERNET", 1, "0.0", null).unsafe().dangerous().whitelist(cTypeIPAddress));
		mListHook.add(new Hook("internet", "getByName", "INTERNET", 1, "0.0", null).unsafe().dangerous().whitelist(cTypeIPAddress));

		// java.net.NetworkInterface
		mListHook.add(new Hook("internet", "getByInetAddress", "INTERNET", 1, null, null).unsafe());
		mListHook.add(new Hook("internet", "getNetworkInterfaces", "INTERNET", 1, null, null).unsafe());

		mListHook.add(new Hook("internet", "inet", "INTERNET", 1, null, null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("internet", "inet_admin", "NET_ADMIN", 1, "2.1.1", null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("internet", "inet_bw", "READ_NETWORK_USAGE_HISTORY,MODIFY_NETWORK_ACCOUNTING", 1, "2.1.1", null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("internet", "inet_vpn", "NET_TUNNELING", 1, "2.1.1", null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("internet", "inet_mesh", "LOOP_RADIO", 1, "2.1.1", null).dangerous().restart().noUsageData());

		// android.net.ConnectivityManager
		mListHook.add(new Hook("internet", "getActiveNetworkInfo", null, 1, null, null).dangerous());
		mListHook.add(new Hook("internet", "getAllNetworkInfo", null, 1, null, null));
		mListHook.add(new Hook("internet", "getNetworkInfo", null, 1, null, null).dangerous());

		// android.net.NetworkInfo
		mListHook.add(new Hook("internet", "getDetailedState", null, 1, null, null));
		mListHook.add(new Hook("internet", "getExtraInfo", null, 1, null, null));
		mListHook.add(new Hook("internet", "getState", null, 1, null, null));
		mListHook.add(new Hook("internet", "isConnected", null, 1, null, null));
		mListHook.add(new Hook("internet", "isConnectedOrConnecting", null, 1, null, null));

		// android.net.wifi.WifiManager
		mListHook.add(new Hook("internet", "getConnectionInfo", null, 10, null, null));

		mListHook.add(new Hook("internet", "connect", null, 1, "1.99.45", null).unsafe().dangerous().whitelist(cTypeIPAddress));

		mListHook.add(new Hook("internet", "LinkAddress.toString", null, 20, "2.1.17", null));

		mListHook.add(new Hook("ipc", "Binder", "", 1, "2.1.21", null).notAOSP(19).dangerous().whitelist(cTypeTransaction));

		mListHook.add(new Hook("location", "addGeofence", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 17, null, null));
		mListHook.add(new Hook("location", "addGpsStatusListener", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 3, "2.1.17", null));
		mListHook.add(new Hook("location", "addNmeaListener", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 5, null, null));
		mListHook.add(new Hook("location", "addProximityAlert", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null));
		mListHook.add(new Hook("location", "getAllProviders", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.20", null).dangerous());
		mListHook.add(new Hook("location", "getBestProvider", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.20", null).dangerous());
		mListHook.add(new Hook("location", "getGpsStatus", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 3, "1.99.29", null));
		mListHook.add(new Hook("location", "getLastKnownLocation", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null));
		mListHook.add(new Hook("location", "getProviders", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "1.99.1", null).dangerous());
		mListHook.add(new Hook("location", "isProviderEnabled", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "1.99.1", null).dangerous());
		mListHook.add(new Hook("location", "requestLocationUpdates", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null));
		mListHook.add(new Hook("location", "requestSingleUpdate", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 9, null, null));
		mListHook.add(new Hook("location", "sendExtraCommand", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 3, null, null));

		mListHook.add(new Hook("location", "enableLocationUpdates", "CONTROL_LOCATION_UPDATES", 10, null, null));
		mListHook.add(new Hook("location", "getCellLocation", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null));
		mListHook.add(new Hook("location", "getNeighboringCellInfo", "ACCESS_COARSE_UPDATES", 3, null, null));
		mListHook.add(new Hook("location", "getAllCellInfo", "ACCESS_COARSE_UPDATES", 17, null, null));
		mListHook.add(new Hook("location", "getScanResults", "ACCESS_WIFI_STATE", 1, null, null).dangerous());
		mListHook.add(new Hook("location", "listen", "ACCESS_COARSE_LOCATION", 1, null, null));

		mListHook.add(new Hook("location", "GMS.addGeofences", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null).unsafe());
		mListHook.add(new Hook("location", "GMS.getLastLocation", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null).unsafe());
		mListHook.add(new Hook("location", "GMS.requestLocationUpdates", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, null, null).unsafe());
		mListHook.add(new Hook("location", "GMS.requestActivityUpdates", "com.google.android.gms.permission.ACTIVITY_RECOGNITION", 1, null, null).unsafe());

		mListHook.add(new Hook("location", "MapV1.getLatitudeE6", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV1.getLongitudeE6", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV1.enableMyLocation", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());

		mListHook.add(new Hook("location", "MapV2.getMyLocation", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV2.getPosition", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV2.setLocationSource", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV2.setOnMapClickListener", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV2.setOnMapLongClickListener", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());
		mListHook.add(new Hook("location", "MapV2.setOnMyLocationChangeListener", "ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION", 1, "2.1.25", null).unsafe());

		mListHook.add(new Hook("media", "startRecording", "RECORD_AUDIO", 3, null, null).doNotify());
		mListHook.add(new Hook("media", "setPreviewCallback", "CAMERA", 1, null, null).doNotify());
		mListHook.add(new Hook("media", "setPreviewCallbackWithBuffer", "CAMERA", 8, null, null).doNotify());
		mListHook.add(new Hook("media", "setOneShotPreviewCallback", "CAMERA", 3, null, null).doNotify());
		mListHook.add(new Hook("media", "takePicture", "CAMERA", 1, null, null).doNotify());
		mListHook.add(new Hook("media", "setOutputFile", "RECORD_AUDIO,RECORD_VIDEO", 1, null, null).doNotify());
		mListHook.add(new Hook("media", MediaStore.ACTION_IMAGE_CAPTURE, "", 3, null, null).doNotify());
		mListHook.add(new Hook("media", MediaStore.ACTION_IMAGE_CAPTURE_SECURE, "", 17, null, null).doNotify());
		mListHook.add(new Hook("media", MediaStore.ACTION_VIDEO_CAPTURE, "", 3, null, null).doNotify());
		mListHook.add(new Hook("media", "Camera2.capture", "CAMERA", 20, null, null).doNotify());
		mListHook.add(new Hook("media", "Camera2.captureBurst", "CAMERA", 20, null, null).doNotify());
		mListHook.add(new Hook("media", "Camera2.setRepeatingRequest", "CAMERA", 20, null, null).doNotify());
		mListHook.add(new Hook("media", "Camera2.setRepeatingBurst", "CAMERA", 20, null, null).doNotify());

		mListHook.add(new Hook("messages", "getAllMessagesFromIcc", "RECEIVE_SMS", 10, null, null).notAOSP(19));
		mListHook.add(new Hook("messages", "Srv_getAllMessagesFromIccEf", "RECEIVE_SMS", 10, null, "getAllMessagesFromIcc").AOSP(19));
		mListHook.add(new Hook("messages", "SmsProvider", "READ_SMS,WRITE_SMS", 1, null, null));
		mListHook.add(new Hook("messages", "MmsProvider", "READ_SMS,WRITE_SMS", 1, null, null));
		mListHook.add(new Hook("messages", "MmsSmsProvider", "READ_SMS,WRITE_SMS", 1, null, null));
		mListHook.add(new Hook("messages", "VoicemailContentProvider", "com.android.voicemail.permission.READ_WRITE_ALL_VOICEMAIL", 1, null, null));
		mListHook.add(new Hook("messages", Telephony.Sms.Intents.DATA_SMS_RECEIVED_ACTION, "RECEIVE_SMS", 1, null, null));
		mListHook.add(new Hook("messages", Telephony.Sms.Intents.SMS_RECEIVED_ACTION, "RECEIVE_SMS", 1, null, null));
		mListHook.add(new Hook("messages", Telephony.Sms.Intents.WAP_PUSH_RECEIVED_ACTION, "RECEIVE_WAP_PUSH", 1, null, null));

		// android.bluetooth.BluetoothAdapter/BluetoothDevice
		mListHook.add(new Hook("network", "getAddress", "android.permission.BLUETOOTH", 5, null, null));
		mListHook.add(new Hook("network", "getBondedDevices", "android.permission.BLUETOOTH", 5, null, null));

		// java.net.NetworkInterface
		mListHook.add(new Hook("network", "getHardwareAddress", "ACCESS_NETWORK_STATE", 9, null, null).unsafe());
		mListHook.add(new Hook("network", "getInetAddresses", "ACCESS_NETWORK_STATE", 9, null, null).unsafe());
		mListHook.add(new Hook("network", "getInterfaceAddresses", "ACCESS_NETWORK_STATE", 9, null, null).unsafe());

		// android.net.wifi.WifiManager
		mListHook.add(new Hook("network", "getConfiguredNetworks", "ACCESS_WIFI_STATE", 10, null, null));
		mListHook.add(new Hook("network", "getConnectionInfo", "ACCESS_WIFI_STATE", 10, null, null));
		mListHook.add(new Hook("network", "getDhcpInfo", "ACCESS_WIFI_STATE", 10, null, null));
		mListHook.add(new Hook("network", "getScanResults", "ACCESS_WIFI_STATE", 10, "0.0", null).dangerous());
		mListHook.add(new Hook("network", "getWifiApConfiguration", "ACCESS_WIFI_STATE", 10, null, null));

		mListHook.add(new Hook("nfc", "getNfcAdapter", "android.permission.NFC", 14, null, null));
		mListHook.add(new Hook("nfc", "getDefaultAdapter", "android.permission.NFC", 10, null, null));
		mListHook.add(new Hook("nfc", NfcAdapter.ACTION_ADAPTER_STATE_CHANGED, "android.permission.NFC", 18, null, null));
		mListHook.add(new Hook("nfc", NfcAdapter.ACTION_NDEF_DISCOVERED, "android.permission.NFC", 10, null, null));
		mListHook.add(new Hook("nfc", NfcAdapter.ACTION_TAG_DISCOVERED, "android.permission.NFC", 10, null, null));
		mListHook.add(new Hook("nfc", NfcAdapter.ACTION_TECH_DISCOVERED, "android.permission.NFC", 10, null, null));

		mListHook.add(new Hook("notifications", "android.service.notification.NotificationListenerService", "BIND_NOTIFICATION_LISTENER_SERVICE", 18, null, null));
		mListHook.add(new Hook("notifications", "com.google.android.c2dm.intent.REGISTRATION", "com.google.android.c2dm.permission.RECEIVE", 10, null, null).dangerous());
		mListHook.add(new Hook("notifications", "com.google.android.c2dm.intent.RECEIVE", "com.google.android.c2dm.permission.RECEIVE", 10, null, null).dangerous());

		mListHook.add(new Hook("overlay", "addView", "SYSTEM_ALERT_WINDOW", 1, null, null));

		mListHook.add(new Hook("phone", "getDeviceId", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getIsimDomain", "READ_PRIVILEGED_PHONE_STATE", 14, null, null));
		mListHook.add(new Hook("phone", "getIsimImpi", "READ_PRIVILEGED_PHONE_STATE", 14, null, null));
		mListHook.add(new Hook("phone", "getIsimImpu", "READ_PRIVILEGED_PHONE_STATE", 14, null, null));
		mListHook.add(new Hook("phone", "getLine1AlphaTag", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getLine1Number", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getMsisdn", "READ_PHONE_STATE", 14, null, null));
		mListHook.add(new Hook("phone", "getSimSerialNumber", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getSubscriberId", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getVoiceMailAlphaTag", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getVoiceMailNumber", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "listen", "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "getNetworkCountryIso", "", 10, null, null));
		mListHook.add(new Hook("phone", "getNetworkOperator", "", 10, null, null));
		mListHook.add(new Hook("phone", "getNetworkOperatorName", "", 10, null, null));
		mListHook.add(new Hook("phone", "getNetworkType", "", 10, null, null));
		mListHook.add(new Hook("phone", "getPhoneType", "", 10, null, null));
		mListHook.add(new Hook("phone", "getSimCountryIso", "", 10, null, null));
		mListHook.add(new Hook("phone", "getSimOperator", "", 10, null, null));
		mListHook.add(new Hook("phone", "getSimOperatorName", "", 10, null, null));
		mListHook.add(new Hook("phone", "getGroupIdLevel1", "READ_PHONE_STATE", 18, null, null));
		mListHook.add(new Hook("phone", TelephonyManager.ACTION_PHONE_STATE_CHANGED, "READ_PHONE_STATE", 10, null, null));
		mListHook.add(new Hook("phone", "TelephonyProvider", "WRITE_APN_SETTINGS", 1, null, null));
		mListHook.add(new Hook("phone", "Configuration.MCC", "", 1, "2.0", null).noUsageData().noOnDemand());
		mListHook.add(new Hook("phone", "Configuration.MNC", "", 1, "2.0", null).noUsageData().noOnDemand());

		mListHook.add(new Hook("sensors", "getDefaultSensor", "", 3, null, null).dangerous());
		mListHook.add(new Hook("sensors", "getSensorList", "", 3, null, null).dangerous());
		mListHook.add(new Hook("sensors", "acceleration", "", 3, null, null));
		mListHook.add(new Hook("sensors", "gravity", "", 3, null, null));
		mListHook.add(new Hook("sensors", "humidity", "", 3, null, null));
		mListHook.add(new Hook("sensors", "light", "", 3, null, null));
		mListHook.add(new Hook("sensors", "magnetic", "", 3, null, null));
		mListHook.add(new Hook("sensors", "motion", "", 3, null, null));
		mListHook.add(new Hook("sensors", "orientation", "", 3, null, null));
		mListHook.add(new Hook("sensors", "pressure", "", 3, null, null));
		mListHook.add(new Hook("sensors", "proximity", "", 3, null, null));
		mListHook.add(new Hook("sensors", "rotation", "", 3, null, null));
		mListHook.add(new Hook("sensors", "temperature", "", 3, null, null));
		mListHook.add(new Hook("sensors", "step", "", 3, null, null));
		mListHook.add(new Hook("sensors", "heartrate", "", 20, null, null));

		mListHook.add(new Hook("shell", "sh", "", 10, "0.0", null).unsafe().dangerous().whitelist(cTypeCommand));
		mListHook.add(new Hook("shell", "su", "", 10, "0.0", null).unsafe().dangerous().whitelist(cTypeCommand));
		mListHook.add(new Hook("shell", "exec", "", 10, "0.0", null).unsafe().dangerous().whitelist(cTypeCommand));
		mListHook.add(new Hook("shell", "load", "", 10, "0.0", null).unsafe().dangerous().restart().whitelist(cTypeLibrary));
		mListHook.add(new Hook("shell", "loadLibrary", "", 10, "0.0", null).unsafe().dangerous().restart().whitelist(cTypeLibrary));
		mListHook.add(new Hook("shell", "start", "", 10, "0.0", null).unsafe().dangerous().whitelist(cTypeCommand));

		mListHook.add(new Hook("storage", "media", "WRITE_MEDIA_STORAGE", 10, null, null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("storage", "sdcard", "READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,ACCESS_ALL_EXTERNAL_STORAGE", 10, null, null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("storage", "mtp", "ACCESS_MTP", 10, "2.1.1", null).dangerous().restart().noUsageData());
		mListHook.add(new Hook("storage", "getExternalStorageState", null, 10, null, null));
		mListHook.add(new Hook("storage", "open", null, 1, "1.99.46", null).unsafe().dangerous().whitelist(cTypeFilename));

		mListHook.add(new Hook("storage", "openAssetFileDescriptor", null, 3, "2.1.17", null).dangerous().whitelist(cTypeFilename));
		mListHook.add(new Hook("storage", "openFileDescriptor", null, 1, "2.1.17", null).dangerous().whitelist(cTypeFilename));
		mListHook.add(new Hook("storage", "openInputStream", null, 1, "2.1.17", null).dangerous().whitelist(cTypeFilename));
		mListHook.add(new Hook("storage", "openOutputStream", null, 1, "2.1.17", null).dangerous().whitelist(cTypeFilename));
		mListHook.add(new Hook("storage", "openTypedAssetFileDescriptor", null, 11, "2.1.17", null).dangerous().whitelist(cTypeFilename));
		mListHook.add(new Hook("storage", "openAssetFile", null, 5, "2.1.17", null).dangerous().whitelist(cTypeFilename));
		mListHook.add(new Hook("storage", "openFile", null, 5, "2.1.17", null).dangerous().whitelist(cTypeFilename));

		mListHook.add(new Hook("system", "getInstalledApplications", "", 1, null, null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "getInstalledPackages", "", 1, null, null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "getPackagesForUid", "", 1, "2.1.17", null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "getPackagesHoldingPermissions", "", 18, "1.99.1", null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "getPreferredActivities", "", 1, "1.99.44", null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "getPreferredPackages", "", 1, null, null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "queryBroadcastReceivers", "", 1, null, null).dangerous());
		mListHook.add(new Hook("system", "queryContentProviders", "", 1, null, null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "queryIntentActivities", "", 1, null, null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "queryIntentActivityOptions", "", 1, null, null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "queryIntentContentProviders", "", 19, "1.99.1", null).notAOSP(19).dangerous());
		mListHook.add(new Hook("system", "queryIntentServices", "", 1, null, null).notAOSP(19).dangerous());

		mListHook.add(new Hook("system", "getInstalledProviders", "", 3, null, null).dangerous());

		mListHook.add(new Hook("system", "getRecentTasks", "GET_TASKS", 1, null, null).dangerous());
		mListHook.add(new Hook("system", "getRunningAppProcesses", "", 3, null, null).dangerous());
		mListHook.add(new Hook("system", "getRunningServices", "", 1, null, null).dangerous());
		mListHook.add(new Hook("system", "getRunningTasks", "GET_TASKS", 1, null, null).dangerous());

		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_ADDED, "", 1, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_REPLACED, "", 3, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_RESTARTED, "", 1, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_REMOVED, "", 1, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_CHANGED, "", 1, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_DATA_CLEARED, "", 3, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_FIRST_LAUNCH, "", 12, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_FULLY_REMOVED, "", 14, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_NEEDS_VERIFICATION, "", 14, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_PACKAGE_VERIFIED, "", 17, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE, "", 8, null, null).dangerous());
		mListHook.add(new Hook("system", Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE, "", 8, null, null).dangerous());
		mListHook.add(new Hook("system", "ApplicationsProvider", "", 1, null, null).to(18));

		mListHook.add(new Hook("system", "checkPermission", "", 1, "2.1.24", null).dangerous().whitelist(cTypePermission));
		mListHook.add(new Hook("system", "checkUidPermission", "", 1, "2.1.24", null).dangerous().whitelist(cTypePermission));

		mListHook.add(new Hook("system", "Srv_getInstalledApplications", "", 19, "2.99", "getInstalledApplications").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_getInstalledPackages", "", 19, "2.99", "getInstalledPackages").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_getPackagesForUid", "", 19, "2.99", "getPackagesForUid").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_getPackagesHoldingPermissions", "", 19, "2.99", "getPackagesHoldingPermissions").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_getPersistentApplications", "", 19, "2.99", null).AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_getPreferredPackages", "", 19, "2.99", "getPreferredPackages").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_queryContentProviders", "", 19, "2.99", "queryContentProviders").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_queryIntentActivities", "", 19, "2.99", "queryIntentActivities").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_queryIntentActivityOptions", "", 19, "2.99", "queryIntentActivityOptions").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_queryIntentContentProviders", "", 19, "2.99", "queryIntentContentProviders").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_queryIntentReceivers", "", 19, "2.99", "queryBroadcastReceivers").AOSP(19).dangerous());
		mListHook.add(new Hook("system", "Srv_queryIntentServices", "", 19, "2.99", "queryIntentServices").AOSP(19).dangerous());

		mListHook.add(new Hook("system", "IntentFirewall", "", 19, "2.99", null).AOSP(19).dangerous().whitelist(cTypeAction));

		mListHook.add(new Hook("view", "loadUrl", "", 1, null, null).unsafe().whitelist(cTypeUrl));
		mListHook.add(new Hook("view", "WebView", "", 1, null, null).unsafe());
		mListHook.add(new Hook("view", "getDefaultUserAgent", "", 17, null, null).unsafe());
		mListHook.add(new Hook("view", "getUserAgent", "", 3, null, null).unsafe());
		mListHook.add(new Hook("view", "getUserAgentString", "", 3, null, null).unsafe());
		mListHook.add(new Hook("view", "setUserAgent", "", 3, null, null).unsafe());
		mListHook.add(new Hook("view", "setUserAgentString", "", 3, null, null).unsafe());
		mListHook.add(new Hook("view", Intent.ACTION_VIEW, "", 1, null, null).notAOSP(19).doNotify().whitelist(cTypeUrl));

		mListHook.add(new Hook("view", "Srv_" + Intent.ACTION_VIEW, "", 19, "2.99", Intent.ACTION_VIEW).AOSP(19).doNotify().whitelist(cTypeUrl));

		// @formatter:on
		return mListHook;
	}

	public static void annotate(Resources resources) {
		if (mAnnotated)
			return;

		String self = Meta.class.getPackage().getName();
		for (Hook hook : get()) {
			String name = hook.getRestrictionName() + "_" + hook.getName();
			name = name.replace(".", "_").replace("/", "_").replace("%", "_").replace("-", "_");
			int resId = resources.getIdentifier(name, "string", self);
			if (resId > 0)
				hook.annotate(resources.getString(resId));
		}

		mAnnotated = true;
	}
}