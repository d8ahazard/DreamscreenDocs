using System;
using System.IO;
using System.Text;

namespace com.lab714.dreamscreenv2.devices
{
	using AsyncTask = android.os.AsyncTask;
	using Handler = android.os.Handler;
	using Log = android.util.Log;

	public abstract class Light
	{
		private const int dreamScreenPort = 8888;
		private const string resetEspKey = "er";
		private const string tag = "Light";
		private static readonly sbyte[] uartComm_crc8_table = new sbyte[] {0, 7, 14, 9, 28, 27, 18, 21, 56, 63, 54, 49, 36, 35, 42, 45, 112, 119, 126, 121, 108, 107, 98, 101, 72, 79, 70, 65, 84, 83, 90, 93, -32, -25, -18, -23, -4, -5, -14, -11, -40, -33, -42, -47, -60, -61, -54, -51, -112, -105, -98, -103, -116, -117, -126, -123, -88, -81, -90, -95, -76, -77, -70, -67, -57, -64, -55, -50, -37, -36, -43, -46, -1, -8, -15, -10, -29, -28, -19, -22, -73, -80, -71, -66, -85, -84, -91, -94, -113, -120, -127, -122, -109, -108, -99, -102, 39, 32, 41, 46, 59, 60, 53, 50, 31, 24, 17, 22, 3, 4, 13, 10, 87, 80, 89, 94, 75, 76, 69, 66, 111, 104, 97, 102, 115, 116, 125, 122, -119, -114, -121, sbyte.MinValue, -107, -110, -101, -100, -79, -74, -65, -72, -83, -86, -93, -92, -7, -2, -9, -16, -27, -30, -21, -20, -63, -58, -49, -56, -35, -38, -45, -44, 105, 110, 103, 96, 117, 114, 123, 124, 81, 86, 95, 88, 77, 74, 67, 68, 25, 30, 23, 16, 5, 2, 11, 12, 33, 38, 47, 40, 61, 58, 51, 52, 78, 73, 64, 71, 82, 85, 92, 91, 118, 113, 120, sbyte.MaxValue, 106, 109, 100, 99, 62, 57, 48, 55, 34, 37, 44, 43, 6, 1, 8, 15, 26, 29, 20, 19, -82, -87, -96, -89, -78, -75, -68, -69, -106, -111, -104, -97, -118, -115, -124, -125, -34, -39, -48, -41, -62, -59, -52, -53, -26, -31, -24, -17, -6, -3, -12, -13};
		internal sbyte[] ambientColor = new sbyte[] {0, 0, 0};
		internal int ambientModeType = 0;
		internal int ambientShowType = 0;
		internal int brightness = 0;
		/* access modifiers changed from: private */
		public InetAddress broadcastIP;
		private sbyte[] espSerialNumber = new sbyte[] {0, 0};
		internal int fadeRate = 4;
		internal string groupName = "unassigned";
		internal int groupNumber = 0;
		/* access modifiers changed from: private */
		public InetAddress lightsUnicastIP;
		internal int mode = 0;
		internal string name = tag;
		internal int productId;
		internal sbyte[] saturation = new sbyte[] {-1, -1, -1};

		private class UDPBroadcast : AsyncTask<sbyte[], Void, Void>
		{
			private readonly Light outerInstance;

			internal UDPBroadcast(Light outerInstance)
			{
				this.outerInstance = outerInstance;
			}

			/* access modifiers changed from: protected */
			public virtual Void doInBackground(params sbyte[][] bytes)
			{
				try
				{
					DatagramSocket s = new DatagramSocket();
					s.Broadcast = true;
					sbyte[] command = bytes[0];
					s.send(new DatagramPacket(command, command.Length, outerInstance.broadcastIP, Light.dreamScreenPort));
					s.close();
				}
				catch (SocketException socketException)
				{
					Log.i(Light.tag, "sending broadcast socketException-" + socketException.ToString());
				}
				catch (Exception e)
				{
					Log.i(Light.tag, "sending broadcast error-" + e.ToString());
				}
				return null;
			}
		}

		private class UDPUnicast : AsyncTask<sbyte[], Void, Void>
		{
			private readonly Light outerInstance;

			internal UDPUnicast(Light outerInstance)
			{
				this.outerInstance = outerInstance;
			}

			/* access modifiers changed from: protected */
			public virtual Void doInBackground(params sbyte[][] bytes)
			{
				try
				{
					DatagramSocket s = new DatagramSocket();
					sbyte[] command = bytes[0];
					s.send(new DatagramPacket(command, command.Length, outerInstance.lightsUnicastIP, Light.dreamScreenPort));
					s.close();
				}
				catch (SocketException socketException)
				{
					Log.i(Light.tag, "sending unicast socketException-" + socketException.ToString());
				}
				catch (Exception e)
				{
					Log.i(Light.tag, "sending unicast error-" + e.ToString());
				}
				return null;
			}
		}

		internal Light(string ipAddress, string broadcastIpString)
		{
			try
			{
				this.lightsUnicastIP = InetAddress.getByName(ipAddress);
				this.broadcastIP = InetAddress.getByName(broadcastIpString);
			}
			catch (UnknownHostException e)
			{
				Log.i(tag, "UnknownHostException " + e.ToString());
			}
		}

		public virtual void resetEsp(bool broadcastingToGroup)
		{
			try
			{
				Log.i(tag, "resetEsp");
				sendUDPWrite(1, 5, resetEspKey.GetBytes(Encoding.UTF8), broadcastingToGroup);
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual sbyte[] EspSerialNumber
		{
			get
			{
				return this.espSerialNumber;
			}
		}

		public virtual void initEspSerialNumber(sbyte[] espSerialNumber2)
		{
			this.espSerialNumber = espSerialNumber2;
		}

		public virtual string Name
		{
			get
			{
				return this.name;
			}
			set
			{
				try
				{
					this.name = value;
					Log.i(tag, "setName " + value);
					sendUDPWrite(1, 7, value.GetBytes(Encoding.UTF8), false);
				}
				catch (UnsupportedEncodingException)
				{
				}
			}
		}


		public virtual bool initName(string name2)
		{
			if (this.name.Equals(name2))
			{
				return false;
			}
			this.name = name2;
			return true;
		}

		public virtual string GroupName
		{
			get
			{
				return this.groupName;
			}
		}

		public virtual void setGroupName(string groupName2, bool broadcastingToGroup)
		{
			try
			{
				this.groupName = groupName2;
				Log.i(tag, "setGroupName " + groupName2);
				sendUDPWrite(1, 8, groupName2.GetBytes(Encoding.UTF8), broadcastingToGroup);
			}
			catch (UnsupportedEncodingException)
			{
			}
		}

		public virtual bool initGroupName(string groupName2)
		{
			if (this.groupName.Equals(groupName2))
			{
				return false;
			}
			this.groupName = groupName2;
			return true;
		}

		public virtual int GroupNumber
		{
			get
			{
				return this.groupNumber;
			}
		}

		public virtual void setGroupNumber(int groupNumber2, bool broadcastingToGroup)
		{
			Log.i(tag, "setGroupNumber " + groupNumber2);
			sendUDPWrite(1, 9, new sbyte[]{(sbyte) groupNumber2}, broadcastingToGroup);
			this.groupNumber = groupNumber2;
		}

		public virtual bool initGroupNumber(int groupNumber2)
		{
			if (this.groupNumber == groupNumber2)
			{
				return false;
			}
			this.groupNumber = groupNumber2;
			return true;
		}

		public virtual int ProductId
		{
			get
			{
				return this.productId;
			}
		}

		public virtual int Brightness
		{
			get
			{
				return this.brightness;
			}
		}

		public virtual void setBrightness(int brightness2, bool broadcastingToGroup)
		{
			this.brightness = brightness2;
			Log.i(tag, "setBrightness " + brightness2);
			sendUDPWrite(3, 2, new sbyte[]{(sbyte) brightness2}, broadcastingToGroup);
		}

		public virtual int BrightnessConstantUnicast
		{
			set
			{
				this.brightness = value;
				Log.i(tag, "setBrightness constant " + value);
				constantUDPUnicastWrite(3, 2, new sbyte[]{(sbyte) value});
			}
		}

		public virtual bool initBrightness(int brightness2)
		{
			if (this.brightness == brightness2)
			{
				return false;
			}
			this.brightness = brightness2;
			return true;
		}

		public virtual int Mode
		{
			get
			{
				return this.mode;
			}
		}

		public virtual void setMode(int mode2, bool broadcastingToGroup)
		{
			this.mode = mode2;
			Log.i(tag, "setMode " + mode2);
			sendUDPWrite(3, 1, new sbyte[]{(sbyte) mode2}, broadcastingToGroup);
		}

		public virtual bool initMode(int mode2)
		{
			Log.i(tag, "Set mode " + this.mode + " to " + mode2);
			if (this.mode == mode2)
			{
				return false;
			}
			this.mode = mode2;
			return true;
		}

		public virtual sbyte[] AmbientColor
		{
			get
			{
				return this.ambientColor;
			}
		}

		public virtual void setAmbientColor(sbyte[] ambientColor2, bool broadcastingToGroup)
		{
			this.ambientColor = ambientColor2;
			Log.i(tag, "setColor " + ambientColor2[0] + " " + ambientColor2[1] + " " + ambientColor2[2]);
			sendUDPWrite(3, 5, ambientColor2, broadcastingToGroup);
		}

		public virtual sbyte[] AmbientColorConstantUnicast
		{
			set
			{
				this.ambientColor = value;
				Log.i(tag, "setColor constant " + value[0] + " " + value[1] + " " + value[2]);
				constantUDPUnicastWrite(3, 5, value);
			}
		}

		public virtual bool initAmbientColor(sbyte[] ambientColor2)
		{
			if (this.ambientColor == ambientColor2)
			{
				return false;
			}
			this.ambientColor = ambientColor2;
			return true;
		}

		public virtual int AmbientModeType
		{
			get
			{
				return this.ambientModeType;
			}
		}

		public virtual void setAmbientModeType(int ambientModeType2, bool broadcastingToGroup)
		{
			this.ambientModeType = ambientModeType2;
			Log.i(tag, "setAmbientModeType " + ambientModeType2);
			sendUDPWrite(3, 8, new sbyte[]{(sbyte) ambientModeType2}, broadcastingToGroup);
		}

		public virtual bool initAmbientModeType(int ambientModeType2)
		{
			if (this.ambientModeType == ambientModeType2)
			{
				return false;
			}
			this.ambientModeType = ambientModeType2;
			return true;
		}

		public virtual int AmbientShowType
		{
			get
			{
				return this.ambientShowType;
			}
		}

		public virtual void setAmbientShowType(int ambientShowType2, bool broadcastingToGroup)
		{
			this.ambientShowType = ambientShowType2;
			Log.i(tag, "setAmbientShowType " + ambientShowType2);
			sendUDPWrite(3, 13, new sbyte[]{(sbyte) ambientShowType2}, broadcastingToGroup);
		}

		public virtual bool initAmbientShowType(int ambientShowType2)
		{
			if (this.ambientShowType == ambientShowType2)
			{
				return false;
			}
			this.ambientShowType = ambientShowType2;
			return true;
		}

		public virtual int FadeRate
		{
			get
			{
				return this.fadeRate;
			}
		}

		public virtual void setFadeRate(int fadeRate2, bool broadcastingToGroup)
		{
			this.fadeRate = fadeRate2;
			Log.i(tag, "setFadeRate " + fadeRate2);
			sendUDPWrite(3, 14, new sbyte[]{(sbyte) fadeRate2}, broadcastingToGroup);
		}

		public virtual void initFadeRate(int fadeRate2)
		{
			this.fadeRate = fadeRate2;
		}

		public virtual sbyte[] Saturation
		{
			get
			{
				return this.saturation;
			}
		}

		public virtual void setSaturation(sbyte[] saturation2, bool broadcastingToGroup)
		{
			this.saturation = saturation2;
			Log.i(tag, "setSaturation" + saturation2);
			sendUDPWrite(3, 6, saturation2, broadcastingToGroup);
		}

		public virtual void initSaturation(sbyte[] saturation2)
		{
			this.saturation = saturation2;
		}

		public virtual string Ip
		{
			get
			{
				return this.lightsUnicastIP.HostAddress;
			}
		}

		public virtual InetAddress LightsUnicastIP
		{
			get
			{
				return this.lightsUnicastIP;
			}
		}

		public virtual InetAddress BroadcastIP
		{
			get
			{
				return this.broadcastIP;
			}
		}

//JAVA TO C# CONVERTER WARNING: 'final' parameters are ignored unless the option to convert to C# 7.2 'in' parameters is selected:
//ORIGINAL LINE: public void doFactoryReset(final boolean broadcastingToGroup)
		public virtual void doFactoryReset(bool broadcastingToGroup)
		{
			Log.i(tag, "doFactoryReset");
			try
			{
				sbyte[] resetPicPayload = "Bg".GetBytes(Encoding.UTF8);
//JAVA TO C# CONVERTER WARNING: The original Java variable was marked 'final':
//ORIGINAL LINE: final byte[] resetEspPayload = "aH".getBytes("UTF-8");
				sbyte[] resetEspPayload = "aH".GetBytes(Encoding.UTF8);
				if (this.productId == 1 || this.productId == 2 || this.productId == 7)
				{
					sendUDPWrite(4, 3, resetPicPayload, broadcastingToGroup);
					(new Handler()).postDelayed(() =>
					{
					Light.this.sendUDPWrite(1, 6, resetEspPayload, broadcastingToGroup);
					}, 1500);
				}
				else if (this.productId == 3)
				{
					sendUDPWrite(1, 6, resetEspPayload, broadcastingToGroup);
				}
				else if (this.productId == 4)
				{
					sendUDPWrite(1, 6, resetEspPayload, broadcastingToGroup);
				}
				else
				{
					Log.i(tag, "invalid productId");
				}
			}
			catch (UnsupportedEncodingException e)
			{
				Log.i(tag, "doFactoryReset error " + e.ToString());
			}
		}

		public virtual void sendPing()
		{
			sendUDPUnicastRead(1, 11);
		}

		/* access modifiers changed from: protected */
		public virtual void sendUDPWrite(sbyte command1, sbyte command2, sbyte[] payload, bool broadcastingToGroup)
		{
			MemoryStream response = new MemoryStream();
			response.WriteByte(252);
			response.WriteByte((sbyte)(payload.Length + 5));
			response.WriteByte((sbyte) this.groupNumber);
			if (broadcastingToGroup)
			{
				response.WriteByte(33);
			}
			else
			{
				response.WriteByte(17);
			}
			response.WriteByte(command1);
			response.WriteByte(command2);
			foreach (sbyte b in payload)
			{
				response.WriteByte(b);
			}
			response.WriteByte(uartComm_calculate_crc8(response.toByteArray()));
			if (broadcastingToGroup)
			{
				sendUDPBroadcast(response.toByteArray());
			}
			else
			{
				sendUDPUnicast(response.toByteArray());
			}
		}

		private void constantUDPUnicastWrite(sbyte command1, sbyte command2, sbyte[] payload)
		{
			MemoryStream response = new MemoryStream();
			response.WriteByte(252);
			response.WriteByte((sbyte)(payload.Length + 5));
			response.WriteByte((sbyte) this.groupNumber);
			response.WriteByte(1);
			response.WriteByte(command1);
			response.WriteByte(command2);
			foreach (sbyte b in payload)
			{
				response.WriteByte(b);
			}
			response.WriteByte(uartComm_calculate_crc8(response.toByteArray()));
			sendUDPUnicast(response.toByteArray());
		}

		/* access modifiers changed from: protected */
		public virtual void sendUDPUnicastRead(sbyte command1, sbyte command2)
		{
			MemoryStream response = new MemoryStream();
			response.WriteByte(252);
			response.WriteByte(5);
			response.WriteByte((sbyte) this.groupNumber);
			response.WriteByte(16);
			response.WriteByte(command1);
			response.WriteByte(command2);
			response.WriteByte(uartComm_calculate_crc8(response.toByteArray()));
			sendUDPUnicast(response.toByteArray());
		}

		private void sendUDPBroadcast(sbyte[] commandBytes)
		{
			(new UDPBroadcast(this)).execute(new sbyte[][]{commandBytes});
		}

		private void sendUDPUnicast(sbyte[] commandBytes)
		{
			(new UDPUnicast(this)).execute(new sbyte[][]{commandBytes});
		}

		public static sbyte uartComm_calculate_crc8(sbyte[] data)
		{
			int size = (data[1] & 255) + 1;
			sbyte crc = 0;
			for (int cntr = 0; cntr < size; cntr++)
			{
				crc = uartComm_crc8_table[((sbyte)(data[cntr] ^ crc)) & 255];
			}
			return crc;
		}
	}

}