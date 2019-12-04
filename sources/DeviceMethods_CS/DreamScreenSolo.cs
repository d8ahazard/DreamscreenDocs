using System.Text;

namespace com.lab714.dreamscreenv2.devices
{
	using Log = android.util.Log;

	public class DreamScreenSolo : DreamScreen
	{
		private static readonly sbyte[] requiredSoloEspFirmwareVersion = new sbyte[] {1, 6};
		private static readonly sbyte[] requiredSoloPicVersionNumber = new sbyte[] {6, 2};
		private const string tag = "DreamScreenSolo";

		public DreamScreenSolo(string ipAddress, string broadcastIpString) : base(ipAddress, broadcastIpString)
		{
			this.productId = 7;
			this.name = "DreamScreen Solo";
		}

		public override void setAsDemo()
		{
			this.isDemo = true;
			this.espFirmwareVersion = (sbyte[]) (sbyte[])requiredSoloEspFirmwareVersion.Clone();
			this.picVersionNumber = (sbyte[]) (sbyte[])requiredSoloPicVersionNumber.Clone();
			this.hdmiActiveChannels = 0;
			try
			{
				this.hdmiInputName1 = "".GetBytes(Encoding.UTF8);
				this.hdmiInputName2 = "".GetBytes(Encoding.UTF8);
				this.hdmiInputName3 = "".GetBytes(Encoding.UTF8);
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
			if (requiredSoloEspFirmwareVersion[0] > this.espFirmwareVersion[0])
			{
				return true;
			}
			if (requiredSoloEspFirmwareVersion[0] != this.espFirmwareVersion[0] || requiredSoloEspFirmwareVersion[1] <= this.espFirmwareVersion[1])
			{
				return false;
			}
			return true;
		}

		public override bool picFirmwareUpdateNeeded()
		{
			Log.i(tag, this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " compared to required, " + requiredSoloPicVersionNumber[0] + "." + requiredSoloPicVersionNumber[1]);
			if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0)
			{
				return false;
			}
			if (requiredSoloPicVersionNumber[0] > this.picVersionNumber[0])
			{
				return true;
			}
			if (requiredSoloPicVersionNumber[0] != this.picVersionNumber[0] || requiredSoloPicVersionNumber[1] <= this.picVersionNumber[1])
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