package com.lab714.dreamscreenv2.devices;

import android.os.AsyncTask;
import android.os.Handler;
import androidx.core.view.InputDeviceCompat;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class UDPListener {
    private static final int dreamScreenPort = 8888;
    private static final String tag = "UDPListener";
    private String broadcastIpString;
    /* access modifiers changed from: private */
    public DatagramSocket datagramSocketListener = null;
    private byte[] discovery = null;
    /* access modifiers changed from: private */
    public final Handler handler;
    /* access modifiers changed from: private */
    public InterfaceListener interfaceListener;
    /* access modifiers changed from: private */
    public String ipAddressString;
    /* access modifiers changed from: private */
    public int threadCount = 0;
    /* access modifiers changed from: private */
    public boolean udpListening = false;
    /* access modifiers changed from: private */
    public InetAddress unknownLightIp;

    public interface InterfaceListener {
        void bootloaderFlagsReceived(Light light, byte[] bArr);

        void deviceInBadBootStateReceived(Light light);

        void deviceInBootloaderModeReceived(Light light, int i);

        void diagnosticDataReceived(Light light, byte[] bArr);

        void espConnectedToWiFi(boolean z);

        Light getLightFromIp(InetAddress inetAddress);

        void insertNewLight(Light light);

        void lightUpdatedRefreshUi(Light light, boolean z, boolean z2);

        void newDeviceReceived(String str);

        void onIrCommandReceived(Light light, int i);

        void picVersionNumberReceived(Light light, byte[] bArr);

        void pingReceived(Light light);

        void setLightToGroup(Light light, int i);

        void updateLightsInGroup(int i, byte[] bArr);
    }

    private class UDPUnicast extends AsyncTask<byte[], Void, Void> {
        private UDPUnicast() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, UDPListener.this.unknownLightIp, UDPListener.dreamScreenPort));
                s.close();
            } catch (Exception e) {
                Log.i("Light", "sending unicast error-" + e.toString());
            }
            return null;
        }
    }

    public UDPListener(String ipAddressString2, String broadcastIpString2) {
        this.ipAddressString = ipAddressString2;
        this.broadcastIpString = broadcastIpString2;
        this.handler = new Handler();
    }

    public void setIpAddressString(String ipAddressString2) {
        this.ipAddressString = ipAddressString2;
    }

    public void setBroadcastIpString(String broadcastIpString2) {
        this.broadcastIpString = broadcastIpString2;
    }

    public void startListening() {
        if (!this.udpListening) {
            startBackgroundUdpListener();
        }
    }

    public void stopListening() {
        this.udpListening = false;
        if (this.datagramSocketListener != null) {
            this.datagramSocketListener.close();
        }
        this.datagramSocketListener = null;
    }

    private void startBackgroundUdpListener() {
        new Thread(new Runnable() {
            public void run() {
                
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0004: INVOKE  (wrap: com.lab714.dreamscreenv2.devices.UDPListener
                      0x0002: IGET  (r6v0 com.lab714.dreamscreenv2.devices.UDPListener) = (r11v0 'this' com.lab714.dreamscreenv2.devices.UDPListener$1 A[THIS]) com.lab714.dreamscreenv2.devices.UDPListener.1.this$0 com.lab714.dreamscreenv2.devices.UDPListener) com.lab714.dreamscreenv2.devices.UDPListener.access$008(com.lab714.dreamscreenv2.devices.UDPListener):int type: STATIC in method: com.lab714.dreamscreenv2.devices.UDPListener.1.run():void, dex: classes.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                    	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:661)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:595)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:353)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:773)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:625)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:353)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                    	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:88)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:682)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
                    	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:76)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
                    	at jadx.core.ProcessClass.process(ProcessClass.java:36)
                    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                    Caused by: org.objenesis.ObjenesisException: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                    	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:57)
                    	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.newConstructorForSerialization(SunReflectionFactoryHelper.java:37)
                    	at org.objenesis.instantiator.sun.SunReflectionFactoryInstantiator.<init>(SunReflectionFactoryInstantiator.java:41)
                    	at org.objenesis.strategy.StdInstantiatorStrategy.newInstantiatorOf(StdInstantiatorStrategy.java:68)
                    	at org.objenesis.ObjenesisBase.getInstantiatorOf(ObjenesisBase.java:94)
                    	at org.objenesis.ObjenesisBase.newInstance(ObjenesisBase.java:73)
                    	at com.rits.cloning.ObjenesisInstantiationStrategy.newInstance(ObjenesisInstantiationStrategy.java:17)
                    	at com.rits.cloning.Cloner.newInstance(Cloner.java:300)
                    	at com.rits.cloning.Cloner.cloneObject(Cloner.java:461)
                    	at com.rits.cloning.Cloner.cloneInternal(Cloner.java:456)
                    	at com.rits.cloning.Cloner.deepClone(Cloner.java:326)
                    	at jadx.core.dex.nodes.InsnNode.copy(InsnNode.java:352)
                    	at jadx.core.dex.nodes.InsnNode.copyCommonParams(InsnNode.java:333)
                    	at jadx.core.dex.instructions.IndexInsnNode.copy(IndexInsnNode.java:24)
                    	at jadx.core.dex.instructions.IndexInsnNode.copy(IndexInsnNode.java:9)
                    	at jadx.core.codegen.InsnGen.inlineMethod(InsnGen.java:880)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:669)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                    	... 43 more
                    Caused by: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                    	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(Unknown Source)
                    	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(Unknown Source)
                    	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
                    	at java.base/java.lang.Class.forName0(Native Method)
                    	at java.base/java.lang.Class.forName(Unknown Source)
                    	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:54)
                    	... 61 more
                    */
                /*
                    this = this;
                    r10 = 1
                    r9 = 0
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    
                    // error: 0x0004: INVOKE  (r6 I:com.lab714.dreamscreenv2.devices.UDPListener) com.lab714.dreamscreenv2.devices.UDPListener.access$008(com.lab714.dreamscreenv2.devices.UDPListener):int type: STATIC
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    int r5 = r6.threadCount
                    java.lang.String r6 = "UDPListener"
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    java.lang.String r8 = "new listening thread started : "
                    java.lang.StringBuilder r7 = r7.append(r8)
                    java.lang.StringBuilder r7 = r7.append(r5)
                    java.lang.String r7 = r7.toString()
                    android.util.Log.i(r6, r7)
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    r6.udpListening = r10
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.net.DatagramSocket r7 = new java.net.DatagramSocket     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r8 = 0     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r7.<init>(r8)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r6.datagramSocketListener = r7     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.net.DatagramSocket r6 = r6.datagramSocketListener     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r7 = 1     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r6.setReuseAddress(r7)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.net.DatagramSocket r6 = r6.datagramSocketListener     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.net.InetSocketAddress r7 = new java.net.InetSocketAddress     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r8 = 8888(0x22b8, float:1.2455E-41)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r7.<init>(r8)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r6.bind(r7)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                L_0x004f:
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    boolean r6 = r6.udpListening     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    if (r6 == 0) goto L_0x0072     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r6 = 256(0x100, float:3.59E-43)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    byte[] r2 = new byte[r6]     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.net.DatagramPacket r0 = new java.net.DatagramPacket     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    int r6 = r2.length     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r0.<init>(r2, r6)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.net.DatagramSocket r6 = r6.datagramSocketListener     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r6.receive(r0)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    boolean r6 = r6.udpListening     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    if (r6 != 0) goto L_0x0097     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                L_0x0072:
                    java.lang.String r6 = "UDPListener"     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.lang.String r7 = "out of udpListening while loop"     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    android.util.Log.i(r6, r7)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    r6.udpListening = r9
                    java.lang.String r6 = "UDPListener"
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    java.lang.String r8 = "UDPListener stopped : "
                    java.lang.StringBuilder r7 = r7.append(r8)
                    java.lang.StringBuilder r7 = r7.append(r5)
                    java.lang.String r7 = r7.toString()
                    android.util.Log.i(r6, r7)
                L_0x0096:
                    return
                L_0x0097:
                    java.net.InetAddress r4 = r0.getAddress()     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.lang.String r6 = r4.getHostAddress()     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r7 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    java.lang.String r7 = r7.ipAddressString     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    boolean r6 = r6.equals(r7)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    if (r6 != 0) goto L_0x004f     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    int r6 = r0.getLength()     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    byte[] r3 = java.util.Arrays.copyOf(r2, r6)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    android.os.Handler r6 = r6.handler     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    com.lab714.dreamscreenv2.devices.UDPListener$1$1 r7 = new com.lab714.dreamscreenv2.devices.UDPListener$1$1     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r7.<init>(r3, r4)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    r6.post(r7)     // Catch:{ SocketException -> 0x00c2, Exception -> 0x00fd }
                    goto L_0x004f
                L_0x00c2:
                    r1 = move-exception
                    java.lang.String r6 = "UDPListener"
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0139 }
                    r7.<init>()     // Catch:{ all -> 0x0139 }
                    java.lang.String r8 = "SocketException-"     // Catch:{ all -> 0x0139 }
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0139 }
                    java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x0139 }
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0139 }
                    java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0139 }
                    android.util.Log.i(r6, r7)     // Catch:{ all -> 0x0139 }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    r6.udpListening = r9
                    java.lang.String r6 = "UDPListener"
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    java.lang.String r8 = "UDPListener stopped : "
                    java.lang.StringBuilder r7 = r7.append(r8)
                    java.lang.StringBuilder r7 = r7.append(r5)
                    java.lang.String r7 = r7.toString()
                    android.util.Log.i(r6, r7)
                    goto L_0x0096
                L_0x00fd:
                    r1 = move-exception
                    java.lang.String r6 = "UDPListener"
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0139 }
                    r7.<init>()     // Catch:{ all -> 0x0139 }
                    java.lang.String r8 = "UDPListener receiver error-"     // Catch:{ all -> 0x0139 }
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0139 }
                    java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x0139 }
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0139 }
                    java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0139 }
                    android.util.Log.i(r6, r7)     // Catch:{ all -> 0x0139 }
                    com.lab714.dreamscreenv2.devices.UDPListener r6 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    r6.udpListening = r9
                    java.lang.String r6 = "UDPListener"
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    java.lang.String r8 = "UDPListener stopped : "
                    java.lang.StringBuilder r7 = r7.append(r8)
                    java.lang.StringBuilder r7 = r7.append(r5)
                    java.lang.String r7 = r7.toString()
                    android.util.Log.i(r6, r7)
                    goto L_0x0096
                L_0x0139:
                    r6 = move-exception
                    com.lab714.dreamscreenv2.devices.UDPListener r7 = com.lab714.dreamscreenv2.devices.UDPListener.this
                    r7.udpListening = r9
                    java.lang.String r7 = "UDPListener"
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder
                    r8.<init>()
                    java.lang.String r9 = "UDPListener stopped : "
                    java.lang.StringBuilder r8 = r8.append(r9)
                    java.lang.StringBuilder r8 = r8.append(r5)
                    java.lang.String r8 = r8.toString()
                    android.util.Log.i(r7, r8)
                    throw r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.devices.UDPListener.AnonymousClass1.run():void");
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void handleReceivedPacket(byte[] received, InetAddress senderIp) {
        if (received.length >= 7) {
            Log.i(tag, "handleReceivedPacket " + senderIp.getHostAddress() + String.format("- %02X.%02X", new Object[]{Byte.valueOf(received[4]), Byte.valueOf(received[5])}));
            int length = received[1] & 255;
            if (length != received.length - 2) {
                Log.i(tag, "bad length, ignoring");
            } else if (received[length + 1] != Light.uartComm_calculate_crc8(Arrays.copyOf(received, length + 1))) {
                Log.i(tag, "bad crc in received packet");
            } else if (received[0] != -4 || length < 6) {
                Log.i(tag, "bad packet, ignoring");
            } else if (this.interfaceListener != null) {
                Light lightSender = this.interfaceListener.getLightFromIp(senderIp);
                if (lightSender == null) {
                    Log.i(tag, "received packet from unknown light");
                    boolean isResponse = ((received[3] >> 6) & 1) == 1;
                    boolean isGroupBroadcast = ((received[3] >> 5) & 1) == 1;
                    boolean isResponseRequested = ((received[3] >> 4) & 1) == 1;
                    boolean isWrite = (received[3] & 1) == 1;
                    if (266 == (received[4] * 256) + received[5] && !isResponseRequested && isResponse) {
                        byte[] payload = Arrays.copyOfRange(received, 6, received.length - 1);
                        byte productId = payload[payload.length - 1];
                        if (productId == 1) {
                            Log.i(tag, "new DreamScreen");
                            final DreamScreen newDreamScreen = new DreamScreen(senderIp.getHostAddress(), this.broadcastIpString);
                            int bootState = newDreamScreen.parsePayload(payload);
                            Log.i(tag, "bootState : " + bootState);
                            switch (bootState) {
                                case 0:
                                case 1:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(newDreamScreen);
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(newDreamScreen);
                                    }
                                    Handler handler2 = this.handler;
                                    AnonymousClass2 r0 = new Runnable() {
                                        public void run() {
                                            if (UDPListener.this.interfaceListener != null) {
                                                UDPListener.this.interfaceListener.deviceInBootloaderModeReceived(newDreamScreen, 1);
                                            }
                                        }
                                    };
                                    handler2.postDelayed(r0, 200);
                                    return;
                                case 3:
                                case 4:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.deviceInBadBootStateReceived(newDreamScreen);
                                        return;
                                    }
                                    return;
                                default:
                                    Log.i(tag, "unknown boot state, not doing anything");
                                    return;
                            }
                        } else if (productId == 2) {
                            Log.i(tag, "new DreamScreen 4K " + senderIp.getHostAddress());
                            DreamScreen4K dreamScreen4K = new DreamScreen4K(senderIp.getHostAddress(), this.broadcastIpString);
                            int bootState2 = dreamScreen4K.parsePayload(payload);
                            Log.i(tag, "bootState : " + bootState2);
                            switch (bootState2) {
                                case 0:
                                case 1:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(dreamScreen4K);
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(dreamScreen4K);
                                    }
                                    Handler handler3 = this.handler;
                                    final DreamScreen4K dreamScreen4K2 = dreamScreen4K;
                                    AnonymousClass3 r02 = new Runnable() {
                                        public void run() {
                                            if (UDPListener.this.interfaceListener != null) {
                                                UDPListener.this.interfaceListener.deviceInBootloaderModeReceived(dreamScreen4K2, 1);
                                            }
                                        }
                                    };
                                    handler3.postDelayed(r02, 200);
                                    return;
                                case 3:
                                case 4:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.deviceInBadBootStateReceived(dreamScreen4K);
                                    }
                                    Log.i(tag, "!!! New 4k is in bad state; let it show in app so user can perform firmware update");
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(dreamScreen4K);
                                        return;
                                    }
                                    return;
                                default:
                                    Log.i(tag, "unknown boot state, not doing anything");
                                    return;
                            }
                        } else if (productId == 7) {
                            Log.i(tag, "new DreamScreen Solo " + senderIp.getHostAddress());
                            DreamScreenSolo dreamScreenSolo = new DreamScreenSolo(senderIp.getHostAddress(), this.broadcastIpString);
                            int bootState3 = dreamScreenSolo.parsePayload(payload);
                            Log.i(tag, "bootState : " + bootState3);
                            switch (bootState3) {
                                case 0:
                                case 1:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(dreamScreenSolo);
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(dreamScreenSolo);
                                    }
                                    Handler handler4 = this.handler;
                                    final DreamScreenSolo dreamScreenSolo2 = dreamScreenSolo;
                                    AnonymousClass4 r03 = new Runnable() {
                                        public void run() {
                                            if (UDPListener.this.interfaceListener != null) {
                                                UDPListener.this.interfaceListener.deviceInBootloaderModeReceived(dreamScreenSolo2, 1);
                                            }
                                        }
                                    };
                                    handler4.postDelayed(r03, 200);
                                    return;
                                case 3:
                                case 4:
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.deviceInBadBootStateReceived(dreamScreenSolo);
                                    }
                                    Log.i(tag, "!!! New solo is in bad state; let it show in app so user can perform firmware update");
                                    if (this.interfaceListener != null) {
                                        this.interfaceListener.insertNewLight(dreamScreenSolo);
                                        return;
                                    }
                                    return;
                                default:
                                    Log.i(tag, "unknown boot state, not doing anything");
                                    return;
                            }
                        } else if (productId == 3) {
                            Log.i(tag, "new SideKick " + senderIp.getHostAddress());
                            SideKick sideKick = new SideKick(senderIp.getHostAddress(), this.broadcastIpString);
                            sideKick.parsePayload(payload);
                            if (this.interfaceListener != null) {
                                this.interfaceListener.insertNewLight(sideKick);
                            }
                        } else if (productId == 4) {
                            Log.i(tag, "new Connect " + senderIp.getHostAddress());
                            final Connect newConnect = new Connect(senderIp.getHostAddress(), this.broadcastIpString);
                            newConnect.parsePayload(payload);
                            if (this.interfaceListener != null) {
                                this.interfaceListener.insertNewLight(newConnect);
                            }
                            Handler handler5 = this.handler;
                            AnonymousClass5 r04 = new Runnable() {
                                public void run() {
                                    newConnect.readHueLifxSettings();
                                }
                            };
                            handler5.postDelayed(r04, 100);
                            Handler handler6 = this.handler;
                            AnonymousClass6 r05 = new Runnable() {
                                public void run() {
                                    newConnect.readEmailAddress();
                                }
                            };
                            handler6.postDelayed(r05, 200);
                            Handler handler7 = this.handler;
                            AnonymousClass7 r06 = new Runnable() {
                                public void run() {
                                    newConnect.readEmailAddress();
                                }
                            };
                            handler7.postDelayed(r06, 300);
                        } else {
                            Log.i(tag, "invalid productId");
                        }
                    } else if (267 == (received[4] * 256) + received[5] && !isResponseRequested && isResponse) {
                        byte[] payload2 = Arrays.copyOfRange(received, 6, received.length - 1);
                        if (payload2.length == 1 && payload2[0] == 1) {
                            Log.i(tag, "received keep-alive from unknown light, sendUnicastDiscovery");
                            sendUnicastDiscovery(senderIp);
                        }
                    } else if (received[4] == 1 && received[5] == 13) {
                        Log.i(tag, "0x010D esp connected to wifi " + received[6]);
                        boolean didConnect = false;
                        if (received[6] == 1) {
                            didConnect = true;
                        }
                        if (this.interfaceListener != null) {
                            this.interfaceListener.espConnectedToWiFi(didConnect);
                        }
                    } else if (received[4] == 1 && received[5] == 20) {
                        Log.i(tag, "0x0114 connected as client to esp's ap, not doing anything ");
                    } else if (!isGroupBroadcast || !isWrite || isResponseRequested || isResponse) {
                        Log.i(tag, "unknown ip, packet");
                    } else {
                        Log.i(tag, "received group broadcast write packet from another app");
                        if (this.interfaceListener != null) {
                            this.interfaceListener.updateLightsInGroup(received[2] & 255, received);
                        }
                    }
                } else {
                    updateLightState(lightSender, received);
                }
            }
        }
    }

    public void updateLightState(Light lightSender, byte[] received) {
        Log.i(tag, "updateLightState " + lightSender.getName());
        byte[] payload = Arrays.copyOfRange(received, 6, received.length - 1);
        byte b = received[6] & 255;
        boolean drawerChanged = false;
        boolean uiUpdateNeeded = false;
        switch ((received[4] * 256) + received[5]) {
            case 259:
                lightSender.initEspSerialNumber(payload);
                return;
            case 260:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initEspFirmwareVersion(payload);
                    return;
                } else if (lightSender instanceof SideKick) {
                    ((SideKick) lightSender).initEspFirmwareVersion(payload);
                    return;
                } else if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initEspFirmwareVersion(payload);
                    return;
                } else {
                    return;
                }
            case 263:
                try {
                    String name = new String(payload, "UTF-8");
                    if (name.isEmpty()) {
                        if (lightSender.getProductId() == 1) {
                            name = "DreamScreen HD";
                        } else if (lightSender.getProductId() == 2) {
                            name = "DreamScreen 4K";
                        } else if (lightSender.getProductId() == 7) {
                            name = "DreamScreen Solo";
                        } else if (lightSender.getProductId() == 3) {
                            name = "SideKick";
                        } else if (lightSender.getProductId() == 4) {
                            name = Connect.DEFAULT_NAME;
                        } else {
                            Log.i(tag, "error, bad productId");
                            name = "Light";
                        }
                    }
                    uiUpdateNeeded = lightSender.initName(name);
                    drawerChanged = uiUpdateNeeded;
                    break;
                } catch (UnsupportedEncodingException e) {
                    break;
                }
            case 264:
                try {
                    String groupName = new String(payload, "UTF-8");
                    if (groupName.isEmpty()) {
                        groupName = "Group";
                    }
                    uiUpdateNeeded = lightSender.initGroupName(groupName);
                    drawerChanged = uiUpdateNeeded;
                    break;
                } catch (UnsupportedEncodingException e2) {
                    break;
                }
            case 265:
                if (lightSender.getGroupNumber() != b) {
                    if (this.interfaceListener != null) {
                        this.interfaceListener.setLightToGroup(lightSender, b);
                    }
                    uiUpdateNeeded = true;
                    drawerChanged = true;
                    break;
                } else {
                    uiUpdateNeeded = false;
                    break;
                }
            case 267:
                if (this.interfaceListener != null) {
                    this.interfaceListener.pingReceived(lightSender);
                    return;
                }
                return;
            case 275:
                if (this.interfaceListener != null) {
                    try {
                        String str = new String(payload, "UTF-8");
                        String apName = str.trim();
                        if (apName != null && !apName.isEmpty()) {
                            this.interfaceListener.newDeviceReceived(apName);
                            return;
                        }
                        return;
                    } catch (UnsupportedEncodingException e3) {
                        return;
                    }
                } else {
                    return;
                }
            case 276:
                Log.i(tag, "shouldnt be getting this command");
                return;
            case 277:
                if (this.interfaceListener != null) {
                    this.interfaceListener.deviceInBootloaderModeReceived(lightSender, b);
                    return;
                }
                return;
            case 514:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initPicVersionNumber(payload);
                    if (this.interfaceListener != null) {
                        this.interfaceListener.picVersionNumberReceived(lightSender, payload);
                        return;
                    }
                    return;
                }
                return;
            case 515:
                if (((lightSender instanceof DreamScreen4K) || (lightSender instanceof DreamScreenSolo)) && this.interfaceListener != null) {
                    this.interfaceListener.diagnosticDataReceived(lightSender, payload);
                    return;
                }
                return;
            case 769:
                uiUpdateNeeded = lightSender.initMode(b);
                break;
            case 770:
                uiUpdateNeeded = lightSender.initBrightness(b);
                break;
            case 771:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initZones(received[6]);
                    return;
                }
                return;
            case 772:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initZonesBrightness(payload);
                    return;
                }
                return;
            case 773:
                uiUpdateNeeded = lightSender.initAmbientColor(payload);
                break;
            case 774:
                lightSender.initSaturation(payload);
                return;
            case 776:
                uiUpdateNeeded = lightSender.initAmbientModeType(b);
                break;
            case 777:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initMusicModeType(b);
                    return;
                }
                return;
            case 778:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initMusicModeColors(payload);
                    return;
                }
                return;
            case 779:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initMusicModeWeights(payload);
                    return;
                }
                return;
            case 780:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initMinimumLuminosity(payload);
                    return;
                }
                return;
            case 781:
                uiUpdateNeeded = lightSender.initAmbientShowType(b);
                break;
            case 782:
                lightSender.initFadeRate(b);
                return;
            case 787:
                ((DreamScreen) lightSender).initIndicatorLightAutoOff(b);
                return;
            case 788:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initUsbPowerEnable(b);
                    return;
                }
                return;
            case 791:
                if (lightSender instanceof SideKick) {
                    ((SideKick) lightSender).initSectorAssignment(payload);
                    return;
                }
                return;
            case 792:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initSectorBroadcastControl(b);
                    return;
                }
                return;
            case 793:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initsectorBroadcastTiming(b);
                    return;
                }
                return;
            case 800:
                if (lightSender instanceof DreamScreen) {
                    uiUpdateNeeded = ((DreamScreen) lightSender).initHdmiInput(b);
                    break;
                } else {
                    return;
                }
            case 801:
                if (lightSender instanceof DreamScreen) {
                    uiUpdateNeeded = ((DreamScreen) lightSender).initMusicModeSource(b);
                    break;
                } else {
                    return;
                }
            case 802:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initAppMusicData(payload);
                    return;
                }
                return;
            case 803:
                if (lightSender instanceof DreamScreen) {
                    try {
                        String str2 = new String(payload, "UTF-8");
                        uiUpdateNeeded = ((DreamScreen) lightSender).initHdmiInputName1(str2.trim().getBytes("UTF-8"));
                        break;
                    } catch (UnsupportedEncodingException e4) {
                        break;
                    }
                } else {
                    return;
                }
            case 804:
                if (lightSender instanceof DreamScreen) {
                    try {
                        String str3 = new String(payload, "UTF-8");
                        uiUpdateNeeded = ((DreamScreen) lightSender).initHdmiInputName2(str3.trim().getBytes("UTF-8"));
                        break;
                    } catch (UnsupportedEncodingException e5) {
                        break;
                    }
                } else {
                    return;
                }
            case 805:
                if (lightSender instanceof DreamScreen) {
                    try {
                        String str4 = new String(payload, "UTF-8");
                        uiUpdateNeeded = ((DreamScreen) lightSender).initHdmiInputName3(str4.trim().getBytes("UTF-8"));
                        break;
                    } catch (UnsupportedEncodingException e6) {
                        break;
                    }
                } else {
                    return;
                }
            case 806:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initCecPassthroughEnable(b);
                    return;
                }
                return;
            case 807:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initCecSwitchingEnable(b);
                    return;
                }
                return;
            case 808:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initHpdEnable(b);
                    return;
                }
                return;
            case 810:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initVideoFrameDelay(b);
                    return;
                }
                return;
            case 811:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initLetterboxingEnable(b);
                    return;
                }
                return;
            case 812:
                if (lightSender instanceof DreamScreen) {
                    uiUpdateNeeded = ((DreamScreen) lightSender).initHdmiActiveChannels(received[6]);
                    break;
                } else {
                    return;
                }
            case 813:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initColorBoost(b);
                    return;
                }
                return;
            case 814:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initCecPowerEnable(b);
                    return;
                }
                return;
            case 815:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initPillarboxingEnable(b);
                    return;
                }
                return;
            case 832:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initSkuSetup(b);
                    return;
                }
                return;
            case 833:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initFlexSetup(payload);
                    return;
                }
                return;
            case 864:
                if (lightSender instanceof DreamScreen) {
                    ((DreamScreen) lightSender).initHdrToneRemapping(b);
                    return;
                }
                return;
            case InputDeviceCompat.SOURCE_GAMEPAD /*1025*/:
                if (lightSender instanceof DreamScreen) {
                    this.interfaceListener.bootloaderFlagsReceived(lightSender, payload);
                    return;
                }
                return;
            case 1281:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initDisplayAnimationEnabled(b);
                    return;
                }
                return;
            case 1282:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initAmbientLightAutoAdjustEnabled(b);
                    return;
                }
                return;
            case 1283:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initMicrophoneAudioBroadcastEnabled(b);
                    return;
                }
                return;
            case 1296:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initIrEnabled(b);
                    return;
                }
                return;
            case 1297:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initIrLearningMode(b);
                    return;
                }
                return;
            case 1298:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initIrManifest(payload);
                    return;
                }
                return;
            case 1300:
                if (lightSender instanceof Connect) {
                    int lastIrCommandReceived = ((payload[0] & 255) << 24) + ((payload[1] & 255) << 16) + ((payload[2] & 255) << 8) + (payload[3] & 255);
                    if (this.interfaceListener != null) {
                        this.interfaceListener.onIrCommandReceived(lightSender, lastIrCommandReceived);
                        return;
                    }
                    return;
                }
                return;
            case 1312:
                if (lightSender instanceof Connect) {
                    try {
                        ((Connect) lightSender).initEmailAddress(new String(payload, "UTF-8"));
                        return;
                    } catch (UnsupportedEncodingException e7) {
                        return;
                    }
                } else {
                    return;
                }
            case 1313:
                if (lightSender instanceof Connect) {
                    try {
                        String str5 = new String(payload, "UTF-8");
                        ((Connect) lightSender).initThingName(str5);
                        return;
                    } catch (UnsupportedEncodingException e8) {
                        return;
                    }
                } else {
                    return;
                }
            case 1328:
                if (lightSender instanceof Connect) {
                    boolean uiUpdateNeeded2 = ((Connect) lightSender).initLightType(b);
                    return;
                }
                return;
            case 1329:
                if (lightSender instanceof Connect) {
                    try {
                        InetAddress[] newLightIpAddresses = new InetAddress[10];
                        for (byte b2 = 0; b2 < 10; b2 = (byte) (b2 + 1)) {
                            newLightIpAddresses[b2] = InetAddress.getByAddress(Arrays.copyOfRange(payload, b2 * 4, (b2 * 4) + 4));
                        }
                        ((Connect) lightSender).initLightIpAddresses(newLightIpAddresses);
                        return;
                    } catch (UnknownHostException e9) {
                        return;
                    }
                } else {
                    return;
                }
            case 1331:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initLightSectorAssignments(payload);
                    return;
                }
                return;
            case 1332:
                if (lightSender instanceof Connect) {
                    try {
                        Connect connect = (Connect) lightSender;
                        String str6 = new String(payload, "UTF-8");
                        connect.initHueBridgeUsername(str6);
                        return;
                    } catch (UnsupportedEncodingException e10) {
                        return;
                    }
                } else {
                    return;
                }
            case 1333:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initHueBulbIds(payload);
                    return;
                }
                return;
            case 1335:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initHueBridgeClientKey(payload);
                    return;
                }
                return;
            case 1336:
                if (lightSender instanceof Connect) {
                    ((Connect) lightSender).initHueBridgeGroupNumber(b);
                    return;
                }
                return;
            case 1337:
                if (lightSender instanceof Connect) {
                    try {
                        String[] newLightNames = new String[10];
                        for (byte lightIndex = 0; lightIndex < 10; lightIndex = (byte) (lightIndex + 1)) {
                            String str7 = new String(Arrays.copyOfRange(payload, lightIndex * 32, (lightIndex * 32) + 32), "UTF-8");
                            newLightNames[lightIndex] = str7.trim();
                        }
                        ((Connect) lightSender).initLightNames(newLightNames);
                        return;
                    } catch (UnsupportedEncodingException e11) {
                        Log.i(tag, "0x0539 encoding exception " + e11.toString());
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
        if (uiUpdateNeeded) {
            boolean isGroupBroadcast = ((received[3] >> 5) & 1) == 1;
            if (this.interfaceListener != null) {
                this.interfaceListener.lightUpdatedRefreshUi(lightSender, drawerChanged, isGroupBroadcast);
                return;
            }
            return;
        }
        Log.i(tag, "uiUpdate not Needed");
    }

    private void sendUnicastDiscovery(InetAddress unknownIP) {
        Log.i(tag, "sendUnicastDiscovery");
        this.unknownLightIp = unknownIP;
        if (this.discovery == null) {
            ByteArrayOutputStream response = new ByteArrayOutputStream();
            response.write(252);
            response.write(5);
            response.write(255);
            response.write(48);
            response.write(1);
            response.write(10);
            response.write(Light.uartComm_calculate_crc8(response.toByteArray()));
            this.discovery = response.toByteArray();
        }
        new UDPUnicast().execute(new byte[][]{this.discovery});
    }

    public void setListener(InterfaceListener interfaceListener2) {
        this.interfaceListener = interfaceListener2;
    }
}
