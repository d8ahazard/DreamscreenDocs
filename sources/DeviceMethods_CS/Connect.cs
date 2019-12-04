using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace com.lab714.dreamscreenv2.devices
{
	using Nullable = androidx.annotation.Nullable;
	using Log = android.util.Log;
	using JSONException = org.json.JSONException;
	using JSONObject = org.json.JSONObject;

	public class Connect : Light
	{
		public const string DEFAULT_NAME = "Connect";
		public static readonly sbyte[] DEFAULT_SECTOR_ASSIGNMENT = new sbyte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 0, 0};
		public static readonly List<int> DS_REMOTE_COMMAND_VALUES = new List<int>(Arrays.asList(new int?[]{Convert.ToInt32(1836103725), Convert.ToInt32(1342658845), Convert.ToInt32(1339163517), Convert.ToInt32(459778273), Convert.ToInt32(720925977), Convert.ToInt32(1114347969), Convert.ToInt32(30463369), Convert.ToInt32(-1573044211)}));
		public static readonly List<string> IR_COMMANDS = new List<string>(Arrays.asList(new string[]{"Undefined", "Mode Toggle", "Mode Sleep", "Mode Video", "Mode Audio", "Mode Ambient", "Brightness Up 10%", "Brightness Down 10%", "HDMI Toggle", "HDMI 1", "HDMI 2", "HDMI 3", "Ambient Scene Toggle"}));
		private const string JSON_HUE_BRIDGE_CLIENT_KEY = "hueBridgeClientKey";
		private const string JSON_HUE_BRIDGE_GROUP_NAME = "hueBridgeGroupName";
		private const string JSON_HUE_BRIDGE_GROUP_NUMBER = "hueBridgeGroupNumber";
		private const string JSON_HUE_BRIDGE_USERNAME = "hueBridgeUsername";
		private const string JSON_HUE_BULB_IDS = "hueBulbIds";
		private const string JSON_LIGHT_IPADDRESSES = "lightIpAddresses";
		private const string JSON_LIGHT_NAMES = "lightNames";
		private const string JSON_LIGHT_SECTOR_ASSIGNMENTS = "lightSectorAssignments";
		private const string JSON_LIGHT_TYPE = "lightType";
		public const int LIGHT_COUNT = 10;
		public static readonly List<int> VALID_LIFX_PRODUCTIDS = new List<int>(Arrays.asList(new int?[]{Convert.ToInt32(1), Convert.ToInt32(3), Convert.ToInt32(20), Convert.ToInt32(22), Convert.ToInt32(27), Convert.ToInt32(28), Convert.ToInt32(29), Convert.ToInt32(30), Convert.ToInt32(31), Convert.ToInt32(32), Convert.ToInt32(36), Convert.ToInt32(37), Convert.ToInt32(43), Convert.ToInt32(44), Convert.ToInt32(45), Convert.ToInt32(46), Convert.ToInt32(49), Convert.ToInt32(52)}));
		private static readonly sbyte[] requiredEspFirmwareVersion = new sbyte[] {0, 4};
		private const string tag = "Connect";
		private int ambientLightAutoAdjustEnabled;
		private int displayAnimationEnabled;
		private string emailAddress;
		private bool emailReceived;
		private sbyte[] espFirmwareVersion;
		private int hdmiInput;
		private sbyte[] hueBridgeClientKey;
		private string hueBridgeGroupName;
		private int hueBridgeGroupNumber;
		private string hueBridgeUsername;
		private sbyte[] hueBulbIds;
		/* access modifiers changed from: private */
		public bool hueLifxSettingsReceived;
		private int irEnabled;
		private int irLearningMode;
		private sbyte[] irManifest;
		private bool isDemo;
		private readonly InetAddress[] lightIpAddresses;
		private string[] lightNames;
		private sbyte[] lightSectorAssignments;
		private int lightType;
		private int microphoneAudioBroadcastEnabled;
		private sbyte[] sectorData;
		private string thingName;

		public Connect(string ipAddress, string broadcastIpString) : base(ipAddress, broadcastIpString)
		{
			this.espFirmwareVersion = new sbyte[]{0, 0};
			this.hdmiInput = 0;
			this.displayAnimationEnabled = 0;
			this.ambientLightAutoAdjustEnabled = 0;
			this.microphoneAudioBroadcastEnabled = 0;
			this.irEnabled = 1;
			this.irLearningMode = 0;
			this.irManifest = new sbyte[40];
			this.emailAddress = "";
			this.thingName = "";
			this.lightType = 0;
			this.lightIpAddresses = new InetAddress[10];
			this.lightSectorAssignments = new sbyte[150];
			this.hueBridgeUsername = "";
			this.hueBulbIds = new sbyte[10];
			this.hueBridgeClientKey = new sbyte[16];
			this.hueBridgeGroupNumber = 0;
			this.hueBridgeGroupName = "";
			this.lightNames = new string[10];
			this.sectorData = new sbyte[]{0};
			this.isDemo = false;
			this.hueLifxSettingsReceived = false;
			this.emailReceived = false;
			this.productId = 4;
			this.name = "Connect";
			sbyte b = 0;
			while (b < 10)
			{
				try
				{
					this.lightIpAddresses[b] = InetAddress.getByAddress(new sbyte[]{0, 0, 0, 0});
					this.hueBulbIds[b] = 0;
					this.lightNames[b] = "";
					b = (sbyte)(b + 1);
				}
				catch (UnknownHostException)
				{
				}
			}
			for (sbyte b2 = 0; b2 < 16; b2 = (sbyte)(b2 + 1))
			{
				this.hueBridgeClientKey[b2] = 0;
			}
		}

		public virtual void setAsDemo()
		{
			this.isDemo = true;
			this.espFirmwareVersion = (sbyte[]) (sbyte[])requiredEspFirmwareVersion.Clone();
		}

		public virtual bool Demo
		{
			get
			{
				return this.isDemo;
			}
		}

		public virtual bool espFirmwareUpdateNeeded()
		{
			if (this.espFirmwareVersion[0] == 0 && this.espFirmwareVersion[1] == 0)
			{
				return false;
			}
			if (requiredEspFirmwareVersion[0] > this.espFirmwareVersion[0])
			{
				return true;
			}
			if (requiredEspFirmwareVersion[0] != this.espFirmwareVersion[0] || requiredEspFirmwareVersion[1] <= this.espFirmwareVersion[1])
			{
				return false;
			}
			return true;
		}

		public virtual sbyte[] EspFirmwareVersion
		{
			get
			{
				return this.espFirmwareVersion;
			}
		}

		public virtual void initEspFirmwareVersion(sbyte[] espFirmwareVersion2)
		{
			this.espFirmwareVersion = espFirmwareVersion2;
		}

		public virtual sbyte[] SectorData
		{
			get
			{
				return this.sectorData;
			}
		}

		public virtual void setSectorData(sbyte[] sectorData2, bool broadcastingToGroup)
		{
			this.sectorData = sectorData2;
			Log.i("Connect", "setSectorData ");
			send UDPWrite(3, 22, sectorData2, broadcastingToGroup);
		}

		public virtual void initSectorData(sbyte[] sectorData2)
		{
			this.sectorData = sectorData2;
		}

		public virtual int HdmiInput
		{
			get
			{
				return this.hdmiInput;
			}
		}

		public virtual void setHdmiInput(int hdmiInput2, bool broadcastingToGroup)
		{
			this.hdmiInput = hdmiInput2;
			Log.i("Connect", "setHdmiInput " + hdmiInput2);
			sendUDPWrite(3, 32, new sbyte[]{(sbyte) hdmiInput2}, broadcastingToGroup);
		}

		public virtual bool initHdmiInput(int hdmiInput2)
		{
			Log.i("Connect", "initHdmiInput " + hdmiInput2);
			if (this.hdmiInput == hdmiInput2)
			{
				return false;
			}
			this.hdmiInput = hdmiInput2;
			return true;
		}

		public virtual int DisplayAnimationEnabled
		{
			get
			{
				return this.displayAnimationEnabled;
			}
		}

		public virtual void setDisplayAnimationEnabled(int displayAnimationEnabled2, bool broadcastingToGroup)
		{
			this.displayAnimationEnabled = displayAnimationEnabled2;
			Log.i("Connect", "setDisplayAnimationEnabled " + displayAnimationEnabled2);
			sendUDPWrite(5, 1, new sbyte[]{(sbyte) displayAnimationEnabled2}, broadcastingToGroup);
		}

		public virtual void initDisplayAnimationEnabled(int displayAnimationEnabled2)
		{
			this.displayAnimationEnabled = displayAnimationEnabled2;
		}

		public virtual int AmbientLightAutoAdjustEnabled
		{
			get
			{
				return this.ambientLightAutoAdjustEnabled;
			}
		}

		public virtual void setAmbientLightAutoAdjustEnabled(int ambientLightAutoAdjustEnabled2, bool broadcastingToGroup)
		{
			this.ambientLightAutoAdjustEnabled = ambientLightAutoAdjustEnabled2;
			Log.i("Connect", "setAmbientLightAutoAdjustEnabled " + ambientLightAutoAdjustEnabled2);
			sendUDPWrite(5, 2, new sbyte[]{(sbyte) ambientLightAutoAdjustEnabled2}, broadcastingToGroup);
		}

		public virtual void initAmbientLightAutoAdjustEnabled(int ambientLightAutoAdjustEnabled2)
		{
			this.ambientLightAutoAdjustEnabled = ambientLightAutoAdjustEnabled2;
		}

		public virtual int MicrophoneAudioBroadcastEnabled
		{
			get
			{
				return this.microphoneAudioBroadcastEnabled;
			}
		}

		public virtual void setMicrophoneAudioBroadcastEnabled(int microphoneAudioBroadcastEnabled2, bool broadcastingToGroup)
		{
			this.microphoneAudioBroadcastEnabled = microphoneAudioBroadcastEnabled2;
			Log.i("Connect", "setMicrophoneAudioBroadcastEnabled " + microphoneAudioBroadcastEnabled2);
			sendUDPWrite(5, 3, new sbyte[]{(sbyte) microphoneAudioBroadcastEnabled2}, broadcastingToGroup);
		}

		public virtual void initMicrophoneAudioBroadcastEnabled(int microphoneAudioBroadcastEnabled2)
		{
			this.microphoneAudioBroadcastEnabled = microphoneAudioBroadcastEnabled2;
		}

		public virtual int IrEnabled
		{
			get
			{
				return this.irEnabled;
			}
		}

		public virtual void setIrEnabled(int irEnabled2, bool broadcastingToGroup)
		{
			this.irEnabled = irEnabled2;
			Log.i("Connect", "setIrEnabled " + irEnabled2);
			sendUDPWrite(5, 16, new sbyte[]{(sbyte) irEnabled2}, broadcastingToGroup);
		}

		public virtual void initIrEnabled(int irEnabled2)
		{
			this.irEnabled = irEnabled2;
		}

		public virtual int IrLearningMode
		{
			get
			{
				return this.irLearningMode;
			}
		}

		public virtual void setIrLearningMode(int irLearningMode2, bool broadcastingToGroup)
		{
			this.irLearningMode = irLearningMode2;
			Log.i("Connect", "setIrLearningMode " + irLearningMode2);
			sendUDPWrite(5, 17, new sbyte[]{(sbyte) irLearningMode2}, broadcastingToGroup);
		}

		public virtual void initIrLearningMode(int irLearningMode2)
		{
			this.irLearningMode = irLearningMode2;
		}

		public virtual sbyte[] IrManifest
		{
			get
			{
				return this.irManifest;
			}
		}

		public virtual void initIrManifest(sbyte[] irManifest2)
		{
			this.irManifest = irManifest2;
		}

		public virtual void setIrManifestEntry(sbyte[] irManifestEntry, bool broadcastingToGroup)
		{
			Log.i("Connect", "setIrManifestEntry " + irManifestEntry);
			sendUDPWrite(5, 19, irManifestEntry, broadcastingToGroup);
		}

		public virtual string EmailAddress
		{
			get
			{
				return this.emailAddress;
			}
		}

		public virtual void setEmailAddress(string emailAddress2, bool broadcastingToGroup)
		{
			try
			{
				this.emailAddress = emailAddress2;
				sbyte[] payload = emailAddress2.GetBytes(Encoding.UTF8);
				if (payload.Length > 249)
				{
					Log.i("Connect", "setEmailAddress but too many bytes. canceling");
				}
				else
				{
					sendUDPWrite(5, 32, payload, broadcastingToGroup);
				}
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual void initEmailAddress(string emailAddress2)
		{
			if (emailAddress2.Length > 249)
			{
				Log.i("Connect", "initEmail address error, length " + emailAddress2.Length);
				return;
			}
			this.emailReceived = true;
			Log.i("Connect", emailAddress2.Length + " initEmailAddress " + emailAddress2);
			this.emailAddress = emailAddress2;
		}

		public virtual void readEmailAddress()
		{
			Log.i("Connect", "readEmailAddress");
			sendUDPUnicastRead(5, 32);
		}

		public virtual string ThingName
		{
			get
			{
				return this.thingName;
			}
		}

		public virtual void setThingName(string thingName2, bool broadcastingToGroup)
		{
			try
			{
				this.thingName = thingName2;
				Log.i("Connect", "setThingName " + thingName2);
				sendUDPWrite(5, 33, thingName2.GetBytes(Encoding.UTF8), broadcastingToGroup);
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual void initThingName(string thingName2)
		{
			Log.i("Connect", thingName2.Length + " initThingName " + thingName2);
			this.thingName = thingName2;
		}

//JAVA TO C# CONVERTER WARNING: 'final' parameters are ignored unless the option to convert to C# 7.2 'in' parameters is selected:
//ORIGINAL LINE: public void sendCertificates(final String certificate, final String privateKey)
		public virtual void sendCertificates(string certificate, string privateKey)
		{
			(new Thread(() =>
			{
			/* JADX WARNING: Removed duplicated region for block: B:11:0x00a6 A[DONT_GENERATE] */
			/* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
			/* Code decompiled incorrectly, please refer to instructions dump. */
			/*
			    r10 = this;
			    r2 = 0
			    java.net.URL r6 = new java.net.URL     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r7.<init>()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r8 = "http://"
			    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    com.lab714.dreamscreenv2.devices.Connect r8 = com.lab714.dreamscreenv2.devices.Connect.this     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r8 = r8.getIp()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r8 = "/api/connect/state"
			    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r6.<init>(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.net.URLConnection r7 = r6.openConnection()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r0 = r7
			    java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r2 = r0
			    r7 = 1
			    r2.setDoOutput(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = "PUT"
			    r2.setRequestMethod(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = "Content-Type"
			    java.lang.String r8 = "application/json"
			    r2.setRequestProperty(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r3.<init>()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = "certificate"
			    java.lang.String r8 = r3     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r3.put(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = "private_key"
			    java.lang.String r8 = r4     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r3.put(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = r3.toString()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r8 = "UTF-8"
			    byte[] r7 = r7.getBytes(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r4.write(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r4.close()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    int r5 = r2.getResponseCode()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r7 = "Connect"
			    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    r8.<init>()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r9 = "responseCode "
			    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.StringBuilder r8 = r8.append(r5)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    android.util.Log.i(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
			    if (r2 == 0) goto L_0x0085
			    r2.disconnect()
			L_0x0085:
			    return
			L_0x0086:
			    r7 = move-exception
			    r1 = r7
			L_0x0088:
			    java.lang.String r7 = "Connect"
			    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00aa }
			    r8.<init>()     // Catch:{ all -> 0x00aa }
			    java.lang.String r9 = "sendCertificate error: "
			    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00aa }
			    java.lang.String r9 = r1.toString()     // Catch:{ all -> 0x00aa }
			    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00aa }
			    java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00aa }
			    android.util.Log.i(r7, r8)     // Catch:{ all -> 0x00aa }
			    if (r2 == 0) goto L_0x0085
			    r2.disconnect()
			    goto L_0x0085
			L_0x00aa:
			    r7 = move-exception
			    if (r2 == 0) goto L_0x00b0
			    r2.disconnect()
			L_0x00b0:
			    throw r7
			L_0x00b1:
			    r7 = move-exception
			    r1 = r7
			    goto L_0x0088
			*/
			throw new System.NotSupportedException("Method not decompiled: com.lab714.dreamscreenv2.devices.Connect.AnonymousClass1.run():void");
			})).Start();
		}

		public virtual int LightType
		{
			get
			{
				return this.lightType;
			}
		}

		public virtual bool initLightType(int lightType2)
		{
			Log.i("Connect", "initLightType " + lightType2);
			if (lightType2 < 0 || lightType2 == this.lightType)
			{
				return false;
			}
			this.lightType = lightType2;
			return true;
		}

		public virtual InetAddress[] LightIpAddresses
		{
			get
			{
				return this.lightIpAddresses;
			}
		}

		public virtual void initLightIpAddresses(InetAddress[] lightIpAddresses2)
		{
			for (sbyte b = 0; b < 10; b = (sbyte)(b + 1))
			{
				this.lightIpAddresses[b] = lightIpAddresses2[b];
			}
		}

		public virtual sbyte[] LightSectorAssignments
		{
			get
			{
				return this.lightSectorAssignments;
			}
		}

		public virtual void initLightSectorAssignments(sbyte[] lightSectorAssignments2)
		{
			this.lightSectorAssignments = lightSectorAssignments2;
		}

		public virtual string HueBridgeUsername
		{
			get
			{
				return this.hueBridgeUsername;
			}
		}

		public virtual void initHueBridgeUsername(string hueBridgeUsername2)
		{
			this.hueBridgeUsername = hueBridgeUsername2;
		}

		public virtual sbyte[] HueBulbIds
		{
			get
			{
				return this.hueBulbIds;
			}
		}

		public virtual void initHueBulbIds(sbyte[] hueBulbIds2)
		{
			this.hueBulbIds = hueBulbIds2;
		}

		public virtual sbyte[] HueBridgeClientKey
		{
			get
			{
				return this.hueBridgeClientKey;
			}
		}

		public virtual void initHueBridgeClientKey(sbyte[] hueBridgeClientKey2)
		{
			this.hueBridgeClientKey = hueBridgeClientKey2;
		}

		public virtual int HueBridgeGroupNumber
		{
			get
			{
				return this.hueBridgeGroupNumber;
			}
		}

		public virtual void initHueBridgeGroupNumber(int hueBridgeGroupNumber2)
		{
			this.hueBridgeGroupNumber = hueBridgeGroupNumber2;
		}

		public virtual string HueBridgeGroupName
		{
			get
			{
				return this.hueBridgeGroupName;
			}
		}

		public virtual void initHueBridgeGroupName(string hueBridgeGroupName2)
		{
			this.hueBridgeGroupName = hueBridgeGroupName2;
		}

		public virtual string[] LightNames
		{
			get
			{
				return this.lightNames;
			}
		}

		public virtual void initLightNames(string[] lightNames2)
		{
			Log.i("Connect", "initLightNames " + lightNames2[0] + " - " + lightNames2[1] + " - " + lightNames2[2] + " - " + lightNames2[3]);
			this.lightNames = lightNames2;
		}

//JAVA TO C# CONVERTER TODO TASK: Most Java annotations will not have direct .NET equivalent attributes:
//ORIGINAL LINE: public void setHueLifxSettings(int lightType2, @Nullable InetAddress[] lightIpAddresses2, @Nullable byte[] lightSectorAssignments2, @Nullable String hueBridgeUsername2, @Nullable byte[] hueBulbIds2, @Nullable byte[] hueBridgeClientKey2, int hueBridgeGroupNumber2, @Nullable String[] lightNames2, @Nullable String hueBridgeGroupName2)
		public virtual void setHueLifxSettings(int lightType2, InetAddress[] lightIpAddresses2, sbyte[] lightSectorAssignments2, string hueBridgeUsername2, sbyte[] hueBulbIds2, sbyte[] hueBridgeClientKey2, int hueBridgeGroupNumber2, string[] lightNames2, string hueBridgeGroupName2)
		{
			Log.i("Connect", "setHueLifxSettings");
//JAVA TO C# CONVERTER WARNING: The original Java variable was marked 'final':
//ORIGINAL LINE: final org.json.JSONObject json = new org.json.JSONObject();
			JSONObject json = new JSONObject();
			if (lightType2 >= 0 && lightType2 <= 2)
			{
				try
				{
					initLightType(lightType2);
					json.put(JSON_LIGHT_TYPE, lightType2);
				}
				catch (JSONException)
				{
					Log.i("Connect", "setHueLifxSettings json failed");
					return;
				}
			}
			if (lightIpAddresses2 != null)
			{
				initLightIpAddresses(lightIpAddresses2);
				string asciiHex = "";
				foreach (InetAddress inetAddress in lightIpAddresses2)
				{
					asciiHex = asciiHex + byteArrayToAsciiHex(inetAddress.Address, inetAddress.Address.length);
				}
				json.put(JSON_LIGHT_IPADDRESSES, asciiHex);
			}
			if (lightSectorAssignments2 != null)
			{
				initLightSectorAssignments(lightSectorAssignments2);
				json.put(JSON_LIGHT_SECTOR_ASSIGNMENTS, byteArrayToAsciiHex(lightSectorAssignments2, lightSectorAssignments2.Length));
			}
			if (!string.ReferenceEquals(hueBridgeUsername2, null))
			{
				initHueBridgeUsername(hueBridgeUsername2);
				json.put(JSON_HUE_BRIDGE_USERNAME, hueBridgeUsername2);
			}
			if (hueBulbIds2 != null)
			{
				initHueBulbIds(hueBulbIds2);
				json.put(JSON_HUE_BULB_IDS, byteArrayToAsciiHex(hueBulbIds2, hueBulbIds2.Length));
			}
			if (hueBridgeClientKey2 != null)
			{
				initHueBridgeClientKey(hueBridgeClientKey2);
				json.put(JSON_HUE_BRIDGE_CLIENT_KEY, byteArrayToAsciiHex(hueBridgeClientKey2, hueBridgeClientKey2.Length));
			}
			if (hueBridgeGroupNumber2 >= 0)
			{
				initHueBridgeGroupNumber(hueBridgeGroupNumber2);
				json.put(JSON_HUE_BRIDGE_GROUP_NUMBER, hueBridgeGroupNumber2);
			}
			if (!string.ReferenceEquals(hueBridgeGroupName2, null))
			{
				initHueBridgeGroupName(hueBridgeGroupName2);
				json.put(JSON_HUE_BRIDGE_GROUP_NAME, hueBridgeGroupName2);
			}
			if (lightNames2 != null)
			{
				initLightNames(lightNames2);
				MemoryStream byteArrayOutputStream = new MemoryStream();
				try
				{
					foreach (string lightName in lightNames2)
					{
						byteArrayOutputStream.WriteByte(Arrays.copyOf(lightName.GetBytes(Encoding.UTF8), 32));
					}
				}
				catch (IOException)
				{
				}
				json.put(JSON_LIGHT_NAMES, byteArrayToAsciiHex(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.toByteArray().length));
			}
			Log.i("Connect", "json: " + json.ToString());
			(new Thread(() =>
			{
			HttpURLConnection httpURLConnection = null;
			try
			{
				httpURLConnection = (HttpURLConnection) (new URL("http://" + Connect.this.Ip + "/api/connect/state")).openConnection();
				httpURLConnection.DoOutput = true;
				httpURLConnection.RequestMethod = "PUT";
				httpURLConnection.setRequestProperty("Content-Type", "application/json");
				Stream outputStream = httpURLConnection.OutputStream;
				outputStream.write(json.ToString().GetBytes(Encoding.UTF8));
				outputStream.close();
				Log.i("Connect", "responseCode " + httpURLConnection.ResponseCode);
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
			}
			catch (IOException e)
			{
				Log.i("Connect", "setHueLifxSettings error: " + e.ToString());
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
			}
			catch (Exception th)
			{
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
				throw th;
			}
			})).Start();
		}

		public virtual void readHueLifxSettings()
		{
			Log.i("Connect", "readHueLifxSettings");
			(new Thread(() =>
			{
			HttpURLConnection httpURLConnection = null;
			try
			{
				httpURLConnection = (HttpURLConnection) (new URL("http://" + Connect.this.Ip + "/api/connect/state")).openConnection();
				httpURLConnection.RequestMethod = "GET";
				int responseCode = httpURLConnection.ResponseCode;
				Log.i("Connect", "responseCode " + responseCode);
				if (responseCode == 200)
				{
					StreamReader @in = new StreamReader(httpURLConnection.InputStream);
					StringBuilder response = new StringBuilder();
					while (true)
					{
						string inputLine = @in.readLine();
						if (string.ReferenceEquals(inputLine, null))
						{
							break;
						}
						response.Append(inputLine);
					}
					@in.close();
					Log.i("Connect", "readHueLifxSettings: " + response.ToString());
					try
					{
						JSONObject rootObject = new JSONObject(response.ToString());
						int tempInt = rootObject.optInt(Connect.JSON_LIGHT_TYPE, -1);
						if (tempInt != -1)
						{
							Connect.this.initLightType(tempInt);
						}
						string asciiHex = rootObject.optString(Connect.JSON_LIGHT_IPADDRESSES, null);
						if (!string.ReferenceEquals(asciiHex, null))
						{
							InetAddress[] ipAddresses = new InetAddress[10];
							sbyte[] tempBytes = Connect.asciiHexToByteArray(asciiHex, asciiHex.Length);
							for (int lightCount = 0; lightCount < 10; lightCount++)
							{
								try
								{
									ipAddresses[lightCount] = InetAddress.getByAddress(Arrays.CopyOfRange(tempBytes, lightCount * 4, (lightCount * 4) + 4));
								}
								catch (UnknownHostException)
								{
								}
							}
							Connect.this.initLightIpAddresses(ipAddresses);
						}
						string asciiHex2 = rootObject.optString(Connect.JSON_LIGHT_SECTOR_ASSIGNMENTS, null);
						if (!string.ReferenceEquals(asciiHex2, null))
						{
							Connect.this.initLightSectorAssignments(Connect.asciiHexToByteArray(asciiHex2, asciiHex2.Length));
						}
						string tempString = rootObject.optString(Connect.JSON_HUE_BRIDGE_USERNAME, null);
						if (!string.ReferenceEquals(asciiHex2, null))
						{
							Connect.this.initHueBridgeUsername(tempString.Trim());
						}
						string asciiHex3 = rootObject.optString(Connect.JSON_HUE_BULB_IDS, null);
						if (!string.ReferenceEquals(asciiHex3, null))
						{
							Connect.this.initHueBulbIds(Connect.asciiHexToByteArray(asciiHex3, asciiHex3.Length));
						}
						string asciiHex4 = rootObject.optString(Connect.JSON_HUE_BRIDGE_CLIENT_KEY, null);
						if (!string.ReferenceEquals(asciiHex4, null))
						{
							Connect.this.initHueBridgeClientKey(Connect.asciiHexToByteArray(asciiHex4, asciiHex4.Length));
						}
						int tempInt2 = rootObject.optInt(Connect.JSON_HUE_BRIDGE_GROUP_NUMBER, -1);
						if (tempInt2 != -1)
						{
							Connect.this.initHueBridgeGroupNumber(tempInt2);
						}
						string tempString2 = rootObject.optString(Connect.JSON_HUE_BRIDGE_GROUP_NAME, null);
						if (!string.ReferenceEquals(asciiHex4, null))
						{
							Connect.this.initHueBridgeGroupName(tempString2.Trim());
						}
						string asciiHex5 = rootObject.optString(Connect.JSON_LIGHT_NAMES, null);
						if (!string.ReferenceEquals(asciiHex5, null))
						{
							string[] lightNames = new string[10];
							sbyte[] tempBytes2 = Connect.asciiHexToByteArray(asciiHex5, asciiHex5.Length);
							for (int lightCount2 = 0; lightCount2 < 10; lightCount2++)
							{
								try
								{
									lightNames[lightCount2] = (new string(Arrays.CopyOfRange(tempBytes2, lightCount2 * 32, (lightCount2 * 32) + 32), "UTF-8")).Trim();
								}
								catch (IOException)
								{
								}
							}
							Connect.this.initLightNames(lightNames);
						}
						Connect.this.hueLifxSettingsReceived = true;
					}
					catch (JSONException)
					{
						if (httpURLConnection != null)
						{
							httpURLConnection.disconnect();
							return;
						}
						return;
					}
				}
				else
				{
					Log.i("Connect", "readHueLifxSettings, did not receive 200");
				}
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
			}
			catch (IOException e4)
			{
				Log.i("Connect", "readHueLifxSettings error: " + e4.ToString());
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
			}
			catch (Exception th)
			{
				if (httpURLConnection != null)
				{
					httpURLConnection.disconnect();
				}
				throw th;
			}
			})).Start();
		}

		public virtual void stopEsp32Drivers()
		{
			Log.i("Connect", "stopEsp32Drivers");
			sendUDPWrite(1, 23, new sbyte[]{0}, false);
		}

		public virtual bool HueLifxSettingsReceived
		{
			get
			{
				return this.hueLifxSettingsReceived;
			}
		}

		public virtual bool EmailReceived
		{
			get
			{
				return this.emailReceived;
			}
		}

		public virtual void parsePayload(sbyte[] payload)
		{
			Log.i("Connect", "parsePayload, " + payload.Length + " bytes");
			try
			{
				string name = new string(Arrays.copyOfRange(payload, 0, 16), "UTF-8");
				if (name.Length == 0)
				{
					name = "Connect";
				}
				this.name = name;
				string groupName = new string(Arrays.copyOfRange(payload, 16, 32), "UTF-8");
				if (groupName.Length == 0)
				{
					groupName = "Group";
				}
				this.groupName = groupName;
			}
			catch (UnsupportedEncodingException)
			{
			}
			this.groupNumber = payload[32] & 255;
			this.mode = payload[33] & 255;
			this.brightness = payload[34] & 255;
			this.ambientColor = Arrays.copyOfRange(payload, 35, 38);
			this.fadeRate = payload[41] & 255;
			this.espFirmwareVersion = Arrays.copyOfRange(payload, 57, 59);
			this.ambientModeType = payload[59] & 255;
			this.ambientShowType = payload[60] & 255;
			this.hdmiInput = payload[61] & 255;
			this.displayAnimationEnabled = payload[62] & 255;
			this.ambientLightAutoAdjustEnabled = payload[63] & 255;
			this.microphoneAudioBroadcastEnabled = payload[64] & 255;
			this.irEnabled = payload[65] & 255;
			this.irLearningMode = payload[66] & 255;
			this.irManifest = Arrays.copyOfRange(payload, 67, 107);
			try
			{
				this.thingName = new string(Arrays.copyOfRange(payload, 115, 178), "UTF-8");
			}
			catch (UnsupportedEncodingException)
			{
				this.thingName = "";
			}
		}

		private static sbyte decodeAsciiCharToNibble(char c)
		{
			if (c >= '0' && c <= '9')
			{
				return (sbyte)(c - '0');
			}
			if (c >= 'A' && c <= 'F')
			{
				return (sbyte)((c - 'A') + 10);
			}
			if (c < 'a' || c > 'f')
			{
				return 0;
			}
			return (sbyte)((c - 'a') + 10);
		}

		/* access modifiers changed from: private */
		public static sbyte[] asciiHexToByteArray(string inString, int inLength)
		{
			MemoryStream @out = new MemoryStream();
			int outIndex = 0;
			int inIndex = 0;
			while (inIndex < inLength)
			{
				@out.WriteByte((decodeAsciiCharToNibble(inString[inIndex]) << 4) | decodeAsciiCharToNibble(inString[inIndex + 1]));
				inIndex += 2;
				outIndex++;
			}
			return @out.toByteArray();
		}

		private static char encodeNibbleToAsciiChar(sbyte b)
		{
			if (b < 10)
			{
				return (char)(b + 48);
			}
			if (b <= 15)
			{
				return (char)((b - 10) + 65);
			}
			return '0';
		}

		private static string byteArrayToAsciiHex(sbyte[] @in, int inLength)
		{
			string outString = "";
			int outIndex = 0;
			for (int inIndex = 0; inIndex < inLength; inIndex++)
			{
				outString = (outString + encodeNibbleToAsciiChar((sbyte)(((sbyte)(@in[inIndex] >> 4)) & 15))) + encodeNibbleToAsciiChar((sbyte)(@in[inIndex] & 15));
				outIndex += 2;
			}
			return outString;
		}
	}

}