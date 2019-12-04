using System.Text;

namespace com.lab714.dreamscreenv2.devices
{
	using Log = android.util.Log;

	public class DreamScreen4K : DreamScreen
	{
		private static readonly sbyte[] required4KEspFirmwareVersion = new sbyte[] {1, 6};
		private static readonly sbyte[] required4KPicVersionNumber = new sbyte[] {5, 6};
		private const string tag = "DreamScreen4K";

		public DreamScreen4K(string ipAddress, string broadcastIpString) : base(ipAddress, broadcastIpString)
		{
			this.productId = 2;
			this.name = "DreamScreen 4K";
		}

		public override void setAsDemo()
		{
			this.isDemo = true;
			this.espFirmwareVersion = (sbyte[]) (sbyte[])required4KEspFirmwareVersion.Clone();
			this.picVersionNumber = (sbyte[]) (sbyte[])required4KPicVersionNumber.Clone();
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

		public override bool espFirmwareUpdateNeeded()
		{
			if (this.espFirmwareVersion[0] == 0 && this.espFirmwareVersion[1] == 0)
			{
				return false;
			}
			if (required4KEspFirmwareVersion[0] > this.espFirmwareVersion[0])
			{
				return true;
			}
			if (required4KEspFirmwareVersion[0] != this.espFirmwareVersion[0] || required4KEspFirmwareVersion[1] <= this.espFirmwareVersion[1])
			{
				return false;
			}
			return true;
		}

		public override bool picFirmwareUpdateNeeded()
		{
			Log.i(tag, this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " compared to required, " + required4KPicVersionNumber[0] + "." + required4KPicVersionNumber[1]);
			if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0)
			{
				return false;
			}
			if (required4KPicVersionNumber[0] > this.picVersionNumber[0])
			{
				return true;
			}
			if (required4KPicVersionNumber[0] != this.picVersionNumber[0] || required4KPicVersionNumber[1] <= this.picVersionNumber[1])
			{
				return false;
			}
			return true;
		}

		public virtual void readDiagnostics()
		{
			Log.i(tag, "readDiagnostics");
			sendUDPUnicastRead(2, 3);
		}
	}

}