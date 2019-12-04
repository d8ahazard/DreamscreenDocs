using System.Text;

namespace com.lab714.dreamscreenv2.devices
{
	using Log = android.util.Log;

	public class DreamScreen : Light
	{
		private const string bootloaderKey = "Ka";
		private static readonly sbyte[] requiredEspFirmwareVersion = new sbyte[] {1, 6};
		private static readonly sbyte[] requiredPicVersionNumber = new sbyte[] {1, 7};
		private const string resetKey = "sA";
		private const string tag = "DreamScreen";
		private sbyte[] appMusicData;
		private int bootState;
		private int cecPassthroughEnable;
		private int cecPowerEnable;
		private int cecSwitchingEnable;
		private int colorBoost;
		internal sbyte[] espFirmwareVersion;
		private sbyte[] flexSetup;
		internal sbyte hdmiActiveChannels;
		private int hdmiInput;
		internal sbyte[] hdmiInputName1;
		internal sbyte[] hdmiInputName2;
		internal sbyte[] hdmiInputName3;
		private int hdrToneRemapping;
		private int hpdEnable;
		private int indicatorLightAutoOff;
		internal bool isDemo;
		private int letterboxingEnable;
		private sbyte[] minimumLuminosity;
		private sbyte[] musicModeColors;
		private int musicModeSource;
		private int musicModeType;
		private sbyte[] musicModeWeights;
		internal sbyte[] picVersionNumber;
		private int pillarboxingEnable;
		private int sectorBroadcastControl;
		private int sectorBroadcastTiming;
		private int skuSetup;
		private int usbPowerEnable;
		private int videoFrameDelay;
		private sbyte zones;
		private sbyte[] zonesBrightness;

		public DreamScreen(string ipAddress, string broadcastIpString) : base(ipAddress, broadcastIpString)
		{
			this.espFirmwareVersion = new sbyte[]{0, 0};
			this.picVersionNumber = new sbyte[]{0, 0};
			this.zones = 15;
			this.zonesBrightness = new sbyte[]{(sbyte)-1, (sbyte)-1, (sbyte)-1, (sbyte)-1};
			this.musicModeType = 0;
			this.musicModeColors = new sbyte[]{2, 1, 0};
			this.musicModeWeights = new sbyte[]{100, 100, 100};
			this.minimumLuminosity = new sbyte[]{0, 0, 0};
			this.indicatorLightAutoOff = 1;
			this.usbPowerEnable = 0;
			this.sectorBroadcastControl = 0;
			this.sectorBroadcastTiming = 1;
			this.hdmiInput = 0;
			this.musicModeSource = 0;
			this.appMusicData = new sbyte[]{0, 0, 0};
			this.cecPassthroughEnable = 1;
			this.cecSwitchingEnable = 1;
			this.hpdEnable = 1;
			this.videoFrameDelay = 0;
			this.letterboxingEnable = 0;
			this.pillarboxingEnable = 0;
			this.hdmiActiveChannels = 0;
			this.colorBoost = 0;
			this.cecPowerEnable = 0;
			this.flexSetup = new sbyte[]{8, 16, 48, 0, 7, 0};
			this.skuSetup = 0;
			this.hdrToneRemapping = 0;
			this.bootState = 0;
			this.isDemo = false;
			this.productId = 1;
			this.name = "DreamScreen HD";
			try
			{
				this.hdmiInputName1 = "HDMI 1".GetBytes(Encoding.UTF8);
				this.hdmiInputName2 = "HDMI 2".GetBytes(Encoding.UTF8);
				this.hdmiInputName3 = "HDMI 3".GetBytes(Encoding.UTF8);
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual void setAsDemo()
		{
			this.isDemo = true;
			this.espFirmwareVersion = (sbyte[]) (sbyte[])requiredEspFirmwareVersion.Clone();
			this.picVersionNumber = (sbyte[]) (sbyte[])requiredPicVersionNumber.Clone();
			this.hdmiActiveChannels = -1;
			try
			{
				this.hdmiInputName1 = "DirecTV".GetBytes(Encoding.UTF8);
				this.hdmiInputName2 = "Xbox One".GetBytes(Encoding.UTF8);
				this.hdmiInputName3 = "Chrome cast".GetBytes(Encoding.UTF8);
			}
			catch (UnsupportedEncodingException)
			{
			}
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

		public virtual bool picFirmwareUpdateNeeded()
		{
			Log.i(tag, this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " compared to required, " + requiredPicVersionNumber[0] + "." + requiredPicVersionNumber[1]);
			if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0)
			{
				return false;
			}
			if (requiredPicVersionNumber[0] > this.picVersionNumber[0])
			{
				return true;
			}
			if (requiredPicVersionNumber[0] != this.picVersionNumber[0] || requiredPicVersionNumber[1] <= this.picVersionNumber[1])
			{
				return false;
			}
			return true;
		}

		public virtual bool picFirmwareValid()
		{
			if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0)
			{
				return false;
			}
			return true;
		}

		public virtual sbyte[] PicVersionNumber
		{
			get
			{
				return this.picVersionNumber;
			}
		}

		public virtual void initPicVersionNumber(sbyte[] picVersionNumber2)
		{
			Log.i(tag, "initPicVersionNumber " + this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " to " + picVersionNumber2[0] + "." + picVersionNumber2[1]);
			this.picVersionNumber = picVersionNumber2;
		}

		public virtual void enterBootloaderFlags()
		{
			try
			{
				Log.i(tag, "enterBootloaderFlags");
				sendUDPWrite(4, 1, bootloaderKey.GetBytes(Encoding.UTF8), false);
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual void clearBootloaderFlags()
		{
			Log.i(tag, "clearBootloaderFlags");
			sendUDPWrite(4, 1, new sbyte[]{0, 0}, false);
		}

		public virtual void readBootloaderFlags()
		{
			Log.i(tag, "readBootloaderFlags");
			sendUDPUnicastRead(4, 1);
		}

		public virtual bool areFlagsInBootloaderMode(sbyte[] bootloaderKey2)
		{
			try
			{
				sbyte[] bootloaderKeyArray = bootloaderKey.GetBytes(Encoding.UTF8);
				if (bootloaderKeyArray[0] == bootloaderKey2[0] && bootloaderKeyArray[1] == bootloaderKey2[1])
				{
					return true;
				}
				return false;
			}
			catch (UnsupportedEncodingException)
			{
				Log.i(tag, "areFlagsInBootloaderMode exception, returning false");
				return false;
			}
		}

		public virtual void resetPic()
		{
			try
			{
				Log.i(tag, "resetPic");
				sendUDPWrite(4, 2, resetKey.GetBytes(Encoding.UTF8), false);
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual void readBootloaderMode()
		{
			Log.i(tag, "readBootloaderMode");
			sendUDPUnicastRead(1, 21);
		}

		public virtual void readPicVersionNumber()
		{
			Log.i(tag, "readPicVersionNumber");
			sendUDPUnicastRead(2, 2);
		}

		public virtual sbyte Zones
		{
			get
			{
				return this.zones;
			}
		}

		public virtual void setZones(sbyte zones2, bool broadcastingToGroup)
		{
			this.zones = zones2;
			Log.i(tag, "setZones " + zones2);
			sendUDPWrite(3, 3, new sbyte[]{zones2}, broadcastingToGroup);
		}

		public virtual void initZones(sbyte zones2)
		{
			this.zones = zones2;
		}

		public virtual sbyte[] ZonesBrightness
		{
			get
			{
				return this.zonesBrightness;
			}
		}

		public virtual void setZonesBrightness(sbyte[] zonesBrightness2, bool broadcastingToGroup)
		{
			this.zonesBrightness = zonesBrightness2;
			Log.i(tag, "setZonesBrightness " + zonesBrightness2[0] + zonesBrightness2[1] + zonesBrightness2[2] + zonesBrightness2[3]);
			sendUDPWrite(3, 4, zonesBrightness2, broadcastingToGroup);
		}

		public virtual void initZonesBrightness(sbyte[] zonesBrightness2)
		{
			this.zonesBrightness = zonesBrightness2;
		}

		public virtual int MusicModeType
		{
			get
			{
				return this.musicModeType;
			}
		}

		public virtual void setMusicModeType(int musicModeType2, bool broadcastingToGroup)
		{
			this.musicModeType = musicModeType2;
			Log.i(tag, "setMusicModeType " + musicModeType2);
			sendUDPWrite(3, 9, new sbyte[]{(sbyte) musicModeType2}, broadcastingToGroup);
		}

		public virtual void initMusicModeType(int musicModeType2)
		{
			this.musicModeType = musicModeType2;
		}

		public virtual int MusicModeSource
		{
			get
			{
				return this.musicModeSource;
			}
		}

		public virtual void setMusicModeSource(int musicModeSource2, bool broadcastingToGroup)
		{
			this.musicModeSource = musicModeSource2;
			Log.i(tag, "setMusicModeSource " + musicModeSource2);
			sendUDPWrite(3, 33, new sbyte[]{(sbyte) musicModeSource2}, broadcastingToGroup);
		}

		public virtual bool initMusicModeSource(int musicModeSource2)
		{
			if (this.musicModeSource == musicModeSource2)
			{
				return false;
			}
			this.musicModeSource = musicModeSource2;
			return true;
		}

		public virtual sbyte[] MusicModeColors
		{
			get
			{
				return this.musicModeColors;
			}
		}

		public virtual void setMusicModeColors(sbyte[] musicModeColors2, bool broadcastingToGroup)
		{
			this.musicModeColors = musicModeColors2;
			Log.i(tag, "setMusicModeColors ");
			sendUDPWrite(3, 10, musicModeColors2, broadcastingToGroup);
		}

		public virtual void initMusicModeColors(sbyte[] musicModeColors2)
		{
			this.musicModeColors = musicModeColors2;
		}

		public virtual sbyte[] MusicModeWeights
		{
			get
			{
				return this.musicModeWeights;
			}
		}

		public virtual void setMusicModeWeights(sbyte[] musicModeWeights2, bool broadcastingToGroup)
		{
			this.musicModeWeights = musicModeWeights2;
			Log.i(tag, "setMusicModeWeights ");
			sendUDPWrite(3, 11, musicModeWeights2, broadcastingToGroup);
		}

		public virtual void initMusicModeWeights(sbyte[] musicModeWeights2)
		{
			this.musicModeWeights = musicModeWeights2;
		}

		public virtual sbyte[] MinimumLuminosity
		{
			get
			{
				return this.minimumLuminosity;
			}
		}

		public virtual void setMinimumLuminosity(sbyte[] minimumLuminosity2, bool broadcastingToGroup)
		{
			this.minimumLuminosity = minimumLuminosity2;
			Log.i("Light", "setMinimumLuminosity " + minimumLuminosity2[0]);
			sendUDPWrite(3, 12, minimumLuminosity2, broadcastingToGroup);
		}

		public virtual void initMinimumLuminosity(sbyte[] minimumLuminosity2)
		{
			this.minimumLuminosity = minimumLuminosity2;
		}

		public virtual int IndicatorLightAutoOff
		{
			get
			{
				return this.indicatorLightAutoOff;
			}
		}

		public virtual void setIndicatorLightAutoOff(int indicatorLightAutoOff2, bool broadcastingToGroup)
		{
			this.indicatorLightAutoOff = indicatorLightAutoOff2;
			Log.i(tag, "setIndicatorLightAutoOff " + indicatorLightAutoOff2);
			sendUDPWrite(3, 19, new sbyte[]{(sbyte) indicatorLightAutoOff2}, broadcastingToGroup);
		}

		public virtual void initIndicatorLightAutoOff(int indicatorLightAutoOff2)
		{
			this.indicatorLightAutoOff = indicatorLightAutoOff2;
		}

		public virtual int UsbPowerEnable
		{
			get
			{
				return this.usbPowerEnable;
			}
		}

		public virtual void setUsbPowerEnable(int usbPowerEnable2, bool broadcastingToGroup)
		{
			this.usbPowerEnable = usbPowerEnable2;
			Log.i(tag, "setUsbPowerEnable " + usbPowerEnable2);
			sendUDPWrite(3, 20, new sbyte[]{(sbyte) usbPowerEnable2}, broadcastingToGroup);
		}

		public virtual void initUsbPowerEnable(int usbPowerEnable2)
		{
			this.usbPowerEnable = usbPowerEnable2;
		}

		public virtual int SectorBroadcastControl
		{
			get
			{
				return this.sectorBroadcastControl;
			}
		}

		public virtual void setSectorBroadcastControl(int sectorBroadcastControl2, bool broadcastingToGroup)
		{
			this.sectorBroadcastControl = sectorBroadcastControl2;
			Log.i(tag, "setSectorBroadcastControl " + sectorBroadcastControl2);
			sendUDPWrite(3, 24, new sbyte[]{(sbyte) sectorBroadcastControl2}, broadcastingToGroup);
		}

		public virtual void initSectorBroadcastControl(int sectorBroadcastControl2)
		{
			this.sectorBroadcastControl = sectorBroadcastControl2;
		}

		public virtual int SectorBroadcastTiming
		{
			get
			{
				return this.sectorBroadcastTiming;
			}
		}

		public virtual void setsectorBroadcastTiming(int sectorBroadcastTiming2, bool broadcastingToGroup)
		{
			this.sectorBroadcastTiming = sectorBroadcastTiming2;
			Log.i(tag, "setSectorBroadcastControl " + sectorBroadcastTiming2);
			sendUDPWrite(3, 25, new sbyte[]{(sbyte) sectorBroadcastTiming2}, broadcastingToGroup);
		}

		public virtual void initsectorBroadcastTiming(int sectorBroadcastTiming2)
		{
			this.sectorBroadcastTiming = sectorBroadcastTiming2;
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
			Log.i(tag, "setHdmiInput " + hdmiInput2);
			sendUDPWrite(3, 32, new sbyte[]{(sbyte) hdmiInput2}, broadcastingToGroup);
		}

		public virtual bool initHdmiInput(int hdmiInput2)
		{
			Log.i(tag, "initHdmiInput " + hdmiInput2);
			if (this.hdmiInput == hdmiInput2)
			{
				return false;
			}
			this.hdmiInput = hdmiInput2;
			return true;
		}

		public virtual void readHdmiInput()
		{
			Log.i(tag, "readHdmiInput");
			sendUDPUnicastRead(3, 32);
		}

		public virtual sbyte[] AppMusicData
		{
			get
			{
				return this.appMusicData;
			}
		}

		public virtual void setAppMusicData(sbyte[] appMusicData2, bool broadcastingToGroup)
		{
			this.appMusicData = appMusicData2;
			Log.i(tag, "setAppMusicData");
			sendUDPWrite(3, 33, appMusicData2, broadcastingToGroup);
		}

		public virtual void initAppMusicData(sbyte[] appMusicData2)
		{
			this.appMusicData = appMusicData2;
		}

		public virtual sbyte[] HdmiInputName1
		{
			get
			{
				return this.hdmiInputName1;
			}
		}

		public virtual void setHdmiInputName1(sbyte[] hdmiInputName12, bool broadcastingToGroup)
		{
			this.hdmiInputName1 = hdmiInputName12;
			Log.i(tag, "setHdmiInputName1 ");
			sendUDPWrite(3, 35, hdmiInputName12, broadcastingToGroup);
		}

		public virtual bool initHdmiInputName1(sbyte[] hdmiInputName12)
		{
			if (this.hdmiInputName1 == hdmiInputName12)
			{
				return false;
			}
			this.hdmiInputName1 = hdmiInputName12;
			return true;
		}

		public virtual sbyte[] HdmiInputName2
		{
			get
			{
				return this.hdmiInputName2;
			}
		}

		public virtual void setHdmiInputName2(sbyte[] hdmiInputName22, bool broadcastingToGroup)
		{
			this.hdmiInputName2 = hdmiInputName22;
			Log.i(tag, "setHdmiInputName2 ");
			sendUDPWrite(3, 36, hdmiInputName22, broadcastingToGroup);
		}

		public virtual bool initHdmiInputName2(sbyte[] hdmiInputName22)
		{
			if (this.hdmiInputName2 == hdmiInputName22)
			{
				return false;
			}
			this.hdmiInputName2 = hdmiInputName22;
			return true;
		}

		public virtual sbyte[] HdmiInputName3
		{
			get
			{
				return this.hdmiInputName3;
			}
		}

		public virtual void setHdmiInputName3(sbyte[] hdmiInputName32, bool broadcastingToGroup)
		{
			this.hdmiInputName3 = hdmiInputName32;
			Log.i(tag, "setHdmiInputName3 ");
			sendUDPWrite(3, 37, hdmiInputName32, broadcastingToGroup);
		}

		public virtual bool initHdmiInputName3(sbyte[] hdmiInputName32)
		{
			if (this.hdmiInputName3 == hdmiInputName32)
			{
				return false;
			}
			this.hdmiInputName3 = hdmiInputName32;
			return true;
		}

		public virtual int CecPassthroughEnable
		{
			get
			{
				return this.cecPassthroughEnable;
			}
		}

		public virtual void setCecPassthroughEnable(int cecPassthroughEnable2, bool broadcastingToGroup)
		{
			this.cecPassthroughEnable = cecPassthroughEnable2;
			Log.i(tag, "setCecPassthroughEnable " + cecPassthroughEnable2);
			sendUDPWrite(3, 38, new sbyte[]{(sbyte) cecPassthroughEnable2}, broadcastingToGroup);
		}

		public virtual void initCecPassthroughEnable(int cecPassthroughEnable2)
		{
			this.cecPassthroughEnable = cecPassthroughEnable2;
		}

		public virtual int CecSwitchingEnable
		{
			get
			{
				return this.cecSwitchingEnable;
			}
		}

		public virtual void setCecSwitchingEnable(int cecSwitchingEnable2, bool broadcastingToGroup)
		{
			this.cecSwitchingEnable = cecSwitchingEnable2;
			Log.i(tag, "setCecSwitchingEnable " + cecSwitchingEnable2);
			sendUDPWrite(3, 39, new sbyte[]{(sbyte) cecSwitchingEnable2}, broadcastingToGroup);
		}

		public virtual void initCecSwitchingEnable(int cecSwitchingEnable2)
		{
			this.cecSwitchingEnable = cecSwitchingEnable2;
		}

		public virtual int HpdEnable
		{
			get
			{
				return this.hpdEnable;
			}
		}

		public virtual void setHpdEnable(int hpdEnable2, bool broadcastingToGroup)
		{
			this.hpdEnable = hpdEnable2;
			Log.i(tag, "setHpdEnable " + hpdEnable2);
			sendUDPWrite(3, 40, new sbyte[]{(sbyte) hpdEnable2}, broadcastingToGroup);
		}

		public virtual void initHpdEnable(int hpdEnable2)
		{
			this.hpdEnable = hpdEnable2;
		}

		public virtual int VideoFrameDelay
		{
			get
			{
				return this.videoFrameDelay;
			}
		}

		public virtual void setVideoFrameDelay(int videoFrameDelay2, bool broadcastingToGroup)
		{
			this.videoFrameDelay = videoFrameDelay2;
			Log.i(tag, "setVideoFrameDelay " + videoFrameDelay2);
			sendUDPWrite(3, 42, new sbyte[]{(sbyte) videoFrameDelay2}, broadcastingToGroup);
		}

		public virtual void initVideoFrameDelay(int videoFrameDelay2)
		{
			this.videoFrameDelay = videoFrameDelay2;
		}

		public virtual int LetterboxingEnable
		{
			get
			{
				return this.letterboxingEnable;
			}
		}

		public virtual void setLetterboxingEnable(int letterboxingEnable2, bool broadcastingToGroup)
		{
			this.letterboxingEnable = letterboxingEnable2;
			Log.i(tag, "setLetterboxingEnable " + letterboxingEnable2);
			sendUDPWrite(3, 43, new sbyte[]{(sbyte) letterboxingEnable2}, broadcastingToGroup);
		}

		public virtual void initLetterboxingEnable(int letterboxingEnable2)
		{
			this.letterboxingEnable = letterboxingEnable2;
		}

		public virtual sbyte HdmiActiveChannels
		{
			get
			{
				return this.hdmiActiveChannels;
			}
		}

		public virtual bool initHdmiActiveChannels(sbyte hdmiActiveChannels2)
		{
			Log.i(tag, "initHdmiActiveChannels " + (hdmiActiveChannels2 & 255));
			if (this.hdmiActiveChannels == hdmiActiveChannels2)
			{
				return false;
			}
			this.hdmiActiveChannels = hdmiActiveChannels2;
			return true;
		}

		public virtual void readHdmiActiveChannels()
		{
			Log.i(tag, "readHdmiActiveChannels");
			sendUDPUnicastRead(3, 44);
		}

		public virtual int ColorBoost
		{
			get
			{
				return this.colorBoost;
			}
		}

		public virtual void setColorBoost(int colorBoost2, bool broadcastingToGroup)
		{
			this.colorBoost = colorBoost2;
			Log.i(tag, "setColorBoost " + colorBoost2);
			sendUDPWrite(3, 45, new sbyte[]{(sbyte) colorBoost2}, broadcastingToGroup);
		}

		public virtual void initColorBoost(int colorBoost2)
		{
			this.colorBoost = colorBoost2;
		}

		public virtual int CecPowerEnable
		{
			get
			{
				return this.cecPowerEnable;
			}
		}

		public virtual void setCecPowerEnable(int cecPowerEnable2, bool broadcastingToGroup)
		{
			this.cecPowerEnable = cecPowerEnable2;
			Log.i(tag, "setCecPowerEnable " + cecPowerEnable2);
			sendUDPWrite(3, 46, new sbyte[]{(sbyte) cecPowerEnable2}, broadcastingToGroup);
		}

		public virtual void initCecPowerEnable(int cecPowerEnable2)
		{
			this.cecPowerEnable = cecPowerEnable2;
		}

		public virtual int PillarboxingEnable
		{
			get
			{
				return this.pillarboxingEnable;
			}
		}

		public virtual void setPillarboxingEnable(int pillarboxingEnable2, bool broadcastingToGroup)
		{
			this.pillarboxingEnable = pillarboxingEnable2;
			Log.i(tag, "setPillarboxingEnable " + pillarboxingEnable2);
			sendUDPWrite(3, 47, new sbyte[]{(sbyte) pillarboxingEnable2}, broadcastingToGroup);
		}

		public virtual void initPillarboxingEnable(int pillarboxingEnable2)
		{
			this.pillarboxingEnable = pillarboxingEnable2;
		}

		public virtual int SkuSetup
		{
			get
			{
				return this.skuSetup;
			}
		}

		public virtual void setSkuSetup(int skuSetup2, bool broadcastingToGroup)
		{
			this.skuSetup = skuSetup2;
			Log.i(tag, "setSkuSetup " + skuSetup2);
			sendUDPWrite(3, 64, new sbyte[]{(sbyte) skuSetup2}, broadcastingToGroup);
		}

		public virtual void initSkuSetup(int skuSetup2)
		{
			this.skuSetup = skuSetup2;
		}

		public virtual sbyte[] FlexSetup
		{
			get
			{
				return this.flexSetup;
			}
		}

		public virtual void setFlexSetup(sbyte[] flexSetup2, bool broadcastingToGroup)
		{
			this.flexSetup = flexSetup2;
			Log.i(tag, "setFlexSetup ");
			sendUDPWrite(3, 65, flexSetup2, broadcastingToGroup);
		}

		public virtual void initFlexSetup(sbyte[] flexSetup2)
		{
			this.flexSetup = flexSetup2;
		}

		public virtual int HdrToneRemapping
		{
			get
			{
				return this.hdrToneRemapping;
			}
		}

		public virtual void setHdrToneRemapping(int hdrToneRemapping2, bool broadcastingToGroup)
		{
			this.hdrToneRemapping = hdrToneRemapping2;
			Log.i(tag, "setHdrToneRemapping " + hdrToneRemapping2);
			sendUDPWrite(3, 96, new sbyte[]{(sbyte) hdrToneRemapping2}, broadcastingToGroup);
		}

		public virtual void initHdrToneRemapping(int hdrToneRemapping2)
		{
			this.hdrToneRemapping = hdrToneRemapping2;
		}

		public virtual int parsePayload(sbyte[] payload)
		{
			Log.i(tag, "parsePayload");
			try
			{
				string name1 = new string(Arrays.copyOfRange(payload, 0, 16), "UTF-8");
				if (name1.Length == 0)
				{
					name1 = tag;
				}
				this.name = name1;
				string groupName1 = new string(Arrays.copyOfRange(payload, 16, 32), "UTF-8");
				if (groupName1.Length == 0)
				{
					groupName1 = "Group";
				}
				this.groupName = groupName1;
			}
			catch (UnsupportedEncodingException)
			{
			}
			this.groupNumber = payload[32] & 255;
			this.mode = payload[33] & 255;
			this.brightness = payload[34] & 255;
			this.zones = payload[35];
			this.zonesBrightness = Arrays.copyOfRange(payload, 36, 40);
			this.ambientColor = Arrays.copyOfRange(payload, 40, 43);
			this.saturation = Arrays.copyOfRange(payload, 43, 46);
			this.flexSetup = Arrays.copyOfRange(payload, 46, 52);
			this.musicModeType = payload[52] & 255;
			this.musicModeColors = Arrays.copyOfRange(payload, 53, 56);
			this.musicModeWeights = Arrays.copyOfRange(payload, 56, 59);
			this.minimumLuminosity = Arrays.copyOfRange(payload, 59, 62);
			this.ambientShowType = payload[62] & 255;
			this.fadeRate = payload[63] & 255;
			this.indicatorLightAutoOff = payload[69] & 255;
			this.usbPowerEnable = payload[70] & 255;
			this.sectorBroadcastControl = payload[71] & 255;
			this.sectorBroadcastTiming = payload[72] & 255;
			this.hdmiInput = payload[73] & 255;
			this.musicModeSource = payload[74] & 255;
			this.hdmiInputName1 = Arrays.copyOfRange(payload, 75, 91);
			this.hdmiInputName2 = Arrays.copyOfRange(payload, 91, 107);
			this.hdmiInputName3 = Arrays.copyOfRange(payload, 107, 123);
			this.cecPassthroughEnable = payload[123] & 255;
			this.cecSwitchingEnable = payload[124] & 255;
			this.hpdEnable = payload[125] & 255;
			this.videoFrameDelay = payload[127] & 255;
			this.letterboxingEnable = payload[128] & 255;
			this.hdmiActiveChannels = payload[129];
			this.espFirmwareVersion = Arrays.copyOfRange(payload, 130, 132);
			this.picVersionNumber = Arrays.copyOfRange(payload, 132, 134);
			this.colorBoost = payload[134] & 255;
			if (payload.Length >= 137)
			{
				this.cecPowerEnable = payload[135] & 255;
			}
			if (payload.Length >= 138)
			{
				this.skuSetup = payload[136] & 255;
			}
			if (payload.Length >= 139)
			{
				this.bootState = payload[137] & 255;
			}
			if (payload.Length >= 140)
			{
				this.pillarboxingEnable = payload[138] & 255;
			}
			if (payload.Length >= 141)
			{
				this.hdrToneRemapping = payload[139] & 255;
			}
			return this.bootState;
		}
	}

}