namespace com.lab714.dreamscreenv2.devices
{
	using Log = android.util.Log;

	public class SideKick : Light
	{
		private static readonly sbyte[] requiredEspFirmwareVersion = new sbyte[] {3, 1};
		private const string tag = "SideKick";
		private sbyte[] espFirmwareVersion;
		private bool isDemo;
		private sbyte[] sectorAssignment;
		private sbyte[] sectorData;

		public SideKick(string ipAddress, string broadcastIpString) : base(ipAddress, broadcastIpString)
		{
			this.espFirmwareVersion = new sbyte[]{0, 0};
			this.sectorData = new sbyte[]{0};
			this.sectorAssignment = new sbyte[0];
			this.isDemo = false;
			this.productId = 3;
			this.name = tag;
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
			Log.i(tag, "setSectorData ");
			sendUDPWrite(3, 22, sectorData2, broadcastingToGroup);
		}

		public virtual void initSectorData(sbyte[] sectorData2)
		{
			this.sectorData = sectorData2;
		}

		public virtual sbyte[] SectorAssignment
		{
			get
			{
				return this.sectorAssignment;
			}
		}

		public virtual void setSectorAssignment(sbyte[] sectorAssignment2, bool broadcastingToGroup)
		{
			this.sectorAssignment = sectorAssignment2;
			Log.i(tag, "setSectorAssignment ");
			sendUDPWrite(3, 23, sectorAssignment2, broadcastingToGroup);
		}

		public virtual void initSectorAssignment(sbyte[] sectorAssignment2)
		{
			this.sectorAssignment = sectorAssignment2;
		}

		public virtual void parsePayload(sbyte[] payload)
		{
			Log.i(tag, "parsePayload");
			try
			{
				string name = new string(Arrays.copyOfRange(payload, 0, 16), "UTF-8");
				if (name.Length == 0)
				{
					name = tag;
				}
				this.name = name;
				string groupName = new string(Arrays.copyOfRange(payload, 16, 32), "UTF-8");
				if (groupName.Length == 0)
				{
					groupName = "unassigned";
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
			this.saturation = Arrays.copyOfRange(payload, 38, 41);
			this.fadeRate = payload[41] & 255;
			this.sectorAssignment = Arrays.copyOfRange(payload, 42, 57);
			this.espFirmwareVersion = Arrays.copyOfRange(payload, 57, 59);
			if (payload.Length == 62)
			{
				this.ambientModeType = payload[59] & 255;
				this.ambientShowType = payload[60] & 255;
			}
		}
	}

}