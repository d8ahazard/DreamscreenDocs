package com.lab714.dreamscreenv2.layout;

import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amazonaws.mobileconnectors.cognitoauth.AuthUserSession;
import com.amazonaws.mobileconnectors.cognitoauth.handlers.AuthHandler;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.google.gson.JsonObject;
import com.lab714.dreamscreenv2.AwsConfig;
import com.lab714.dreamscreenv2.R;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FirmwareUpdateFragment extends Fragment {
    private static final byte[] ackArray = {97, 99, 107};
    private static final String connectEspFileName = "Connect_IDF.bin";
    private static final short[] crc_table = {0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, -32504, -28375, -24246, -20117, -15988, -11859, -7730, -3601};
    private static final String dreamScreen4KEspFileName = "DSv2_revF.ino.bin";
    private static final String dreamScreen4KPicFileName = "4KV2.X.production.hex";
    private static final String dreamScreenHdEspFileName = "DSv2_revF.ino.bin";
    private static final String dreamScreenHdPicFileName = "HDV2.X.production.hex";
    private static final String dreamScreenSoloEspFileName = "DSv2_revF.ino.bin";
    private static final String dreamScreenSoloPicFileName = "Solo.X.production.hex";
    private static final String dsv2Prefs = "dsv2Prefs";
    private static final String espPassword = "DSadmin714";
    private static final String espUsername = "admin";
    private static final int maxErrors = 2;
    private static final int maxTcpTimeout = 7;
    private static final byte[] nakArray = {110, 97, 107};
    private static final int picTcpPort = 50000;
    private static final String sideKickEspFileName = "Side2Side_revE.ino.bin";
    private static final String tag = "FirmwareUpdateFrag";
    private static final byte[] verArray = {118, 101, 114};
    private static final byte[] verifyResponseArray4K = {1, 16, 4, 38, -108, -67, -77, 4};
    private static final byte[] verifyResponseArrayHD = {1, 16, 4, 43, -82, -8, 82, 4};
    private static final byte[] verifyResponseArraySolo = {1, 16, 4, 70, -54, -84, 3, 4};
    /* access modifiers changed from: private */
    public boolean allFramesSent = false;
    /* access modifiers changed from: private */
    public boolean bootloaderFlagsResponded = false;
    /* access modifiers changed from: private */
    public LinkedList<byte[]> bootloaderFrames = new LinkedList<>();
    /* access modifiers changed from: private */
    public boolean bootloaderModeResponded = false;
    /* access modifiers changed from: private */
    public boolean buttonPressed = false;
    /* access modifiers changed from: private */
    public boolean canceled = false;
    /* access modifiers changed from: private */
    public Auth cognitoAuth;
    /* access modifiers changed from: private */
    public int diagnosticTappedCount = 0;
    /* access modifiers changed from: private */
    public TextView diagnosticText1;
    /* access modifiers changed from: private */
    public TextView diagnosticText2;
    /* access modifiers changed from: private */
    public boolean espFirmwareUpdateNeeded = false;
    private byte[] espFirmwareVersion = {0, 0};
    /* access modifiers changed from: private */
    public AlertDialog failedDialog;
    private boolean finished = false;
    /* access modifiers changed from: private */
    public FirmwareUpdateFragmentListener firmwareUpdateFragmentListener;
    private boolean framesBuilt = false;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler();
    /* access modifiers changed from: private */
    public AlertDialog lambdaWaitingDialog;
    /* access modifiers changed from: private */
    public String lightsIp;
    private InetAddress lightsUnicastIP;
    private int loop = 0;
    /* access modifiers changed from: private */
    public boolean picFirmwareUpdateNeeded = false;
    private byte[] picVersionNumber = {0, 0};
    /* access modifiers changed from: private */
    public boolean picVersionNumberRead = false;
    /* access modifiers changed from: private */
    public int productId = 0;
    /* access modifiers changed from: private */
    public boolean secondAttempt = false;
    private boolean secondFramesAttempt = false;
    private boolean shouldResumePicUpdating = false;
    private InetSocketAddress socketAddress;
    /* access modifiers changed from: private */
    public int tcpTimeoutCount = 0;
    /* access modifiers changed from: private */
    public String thingName = null;
    /* access modifiers changed from: private */
    public Runnable timeoutRunnable;
    /* access modifiers changed from: private */
    public TextView updateText;
    /* access modifiers changed from: private */
    public boolean updateWorked = false;
    /* access modifiers changed from: private */
    public boolean updating = false;
    /* access modifiers changed from: private */
    public final byte[] verifyRequestFrame4K = {1, 16, 4, 0, 0, 0, -99, 80, 54, 16, 1, 0, 38, -108, -7, -113, 4};
    /* access modifiers changed from: private */
    public final byte[] verifyRequestFrameHD = {1, 16, 4, 0, 0, 0, -99, 56, 96, 16, 1, 0, 43, -82, 81, -28, 4};
    /* access modifiers changed from: private */
    public final byte[] verifyRequestFrameSolo = {1, 16, 4, 0, 0, 0, -99, 104, 21, 16, 1, 0, 70, -54, 64, -5, 4};

    private class AuthHandlerCallback implements AuthHandler {
        private AuthHandlerCallback() {
        }

        public void onSuccess(AuthUserSession authUserSession) {
            Log.i(FirmwareUpdateFragment.tag, "cognito userpool username " + authUserSession.getUsername());
            new DeleteAwsThingViaLambda(authUserSession.getIdToken().getJWTToken()).execute(new Void[0]);
        }

        public void onSignout() {
            Log.i(FirmwareUpdateFragment.tag, "authHandlerCalback onSignout");
        }

        public void onFailure(Exception e) {
            Log.i(FirmwareUpdateFragment.tag, "authHandlerCalback onFailure " + e.toString());
            if (e instanceof ActivityNotFoundException) {
                Toast.makeText(FirmwareUpdateFragment.this.getContext(), "Google Chrome could not be launched", 1).show();
            }
            FirmwareUpdateFragment.this.buttonPressed = false;
        }
    }

    private class DeleteAwsThingViaLambda extends AsyncTask<Void, Void, Void> {
        private final String idToken;

        public DeleteAwsThingViaLambda(String idToken2) {
            this.idToken = idToken2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            if (FirmwareUpdateFragment.this.lambdaWaitingDialog == null || !FirmwareUpdateFragment.this.lambdaWaitingDialog.isShowing()) {
                if (FirmwareUpdateFragment.this.lambdaWaitingDialog == null) {
                    View lambdaWaitingView = FirmwareUpdateFragment.this.getLayoutInflater().inflate(R.layout.lambda_waiting_dialog, null);
                    ((TextView) lambdaWaitingView.findViewById(R.id.subText)).setText(FirmwareUpdateFragment.this.getContext().getResources().getString(R.string.FinalizingAccountSetup));
                    Builder alertDialogBuilder = new Builder(FirmwareUpdateFragment.this.getActivity());
                    alertDialogBuilder.setView(lambdaWaitingView).setCancelable(false);
                    FirmwareUpdateFragment.this.lambdaWaitingDialog = alertDialogBuilder.create();
                }
                FirmwareUpdateFragment.this.lambdaWaitingDialog.show();
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (FirmwareUpdateFragment.this.lambdaWaitingDialog != null && FirmwareUpdateFragment.this.lambdaWaitingDialog.isShowing()) {
                        FirmwareUpdateFragment.this.lambdaWaitingDialog.dismiss();
                    }
                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.doFactoryReset();
                    }
                }
            }, 500);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voids) {
            try {
                Log.i(FirmwareUpdateFragment.tag, "DeleteAwsThingViaLambda, doInBackground");
                String thingName = "";
                if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                    thingName = FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.getThingName();
                }
                CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(FirmwareUpdateFragment.this.getContext(), AwsConfig.IDENTITY_POOL_ID, AwsConfig.AWS_REGION);
                cognitoCachingCredentialsProvider.clear();
                Map<String, String> logins = new HashMap<>();
                logins.put("cognito-idp.us-east-1.amazonaws.com/us-east-1_FWZ89EFTn", this.idToken);
                cognitoCachingCredentialsProvider.setLogins(logins);
                cognitoCachingCredentialsProvider.refresh();
                cognitoCachingCredentialsProvider.getIdentityId();
                Log.i(FirmwareUpdateFragment.tag, "cognitoCachingCredentialsProvider.getIdentityId() " + cognitoCachingCredentialsProvider.getIdentityId());
                AWSLambdaClient awsLambdaClient = new AWSLambdaClient((AWSCredentialsProvider) cognitoCachingCredentialsProvider);
                InvokeRequest invokeRequest = new InvokeRequest();
                invokeRequest.setFunctionName("removeBackendResource");
                JsonObject jsonPayload = new JsonObject();
                jsonPayload.addProperty(ClientConstants.TOKEN_TYPE_ID, this.idToken);
                jsonPayload.addProperty("thingName", thingName.trim());
                invokeRequest.setPayload(ByteBuffer.wrap(jsonPayload.toString().getBytes("UTF-8")));
                invokeRequest.setInvocationType(InvocationType.RequestResponse);
                awsLambdaClient.invoke(invokeRequest);
            } catch (Exception e) {
                Log.i(FirmwareUpdateFragment.tag, "DeleteAwsThingViaLambda failed : " + e.toString());
            }
            return null;
        }
    }

    private class DoEsp32FirmwareUpdate extends AsyncTask<Void, Void, Void> {
        int currentProgress;

        private DoEsp32FirmwareUpdate() {
            this.currentProgress = -1;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x0293  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Void... r31) {
            /*
                r30 = this;
                java.lang.String r27 = "FirmwareUpdateFrag"
                java.lang.String r28 = "doEsp32FirmwareUpdate doInBackground"
                android.util.Log.i(r27, r28)
                r0 = r30
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r27 = r0
                r28 = 1
                r27.updating = r28
                r0 = r30
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r27 = r0
                int r27 = r27.productId
                r28 = 4
                r0 = r27
                r1 = r28
                if (r0 != r1) goto L_0x00b3
                java.lang.String r13 = "Connect_IDF.bin"
                r23 = 0
                java.lang.StringBuilder r27 = new java.lang.StringBuilder
                r27.<init>()
                java.lang.String r28 = "http://"
                java.lang.StringBuilder r27 = r27.append(r28)
                r0 = r30
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r28 = r0
                java.lang.String r28 = r28.lightsIp
                java.lang.StringBuilder r27 = r27.append(r28)
                java.lang.String r28 = "/firmware"
                java.lang.StringBuilder r27 = r27.append(r28)
                java.lang.String r25 = r27.toString()
                r8 = 0
                r19 = 0
                java.lang.String r15 = "\r\n"
                java.lang.String r24 = "--"
                java.lang.String r3 = "*****"
                r17 = 1048576(0x100000, float:1.469368E-39)
                r0 = r30
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this     // Catch:{ Exception -> 0x0099 }
                r27 = r0
                android.content.res.Resources r27 = r27.getResources()     // Catch:{ Exception -> 0x0099 }
                android.content.res.AssetManager r27 = r27.getAssets()     // Catch:{ Exception -> 0x0099 }
                r0 = r27
                java.io.InputStream r14 = r0.open(r13)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r27 = "pre"
                java.lang.String r28 = "suf"
                java.io.File r23 = java.io.File.createTempFile(r27, r28)     // Catch:{ Exception -> 0x0099 }
                java.io.FileOutputStream r18 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0099 }
                r0 = r18
                r1 = r23
                r0.<init>(r1)     // Catch:{ Exception -> 0x0099 }
                r27 = 1024(0x400, float:1.435E-42)
                r0 = r27
                byte[] r11 = new byte[r0]     // Catch:{ Exception -> 0x0099 }
            L_0x0081:
                int r21 = r14.read(r11)     // Catch:{ Exception -> 0x0099 }
                r27 = -1
                r0 = r21
                r1 = r27
                if (r0 == r1) goto L_0x00bd
                r27 = 0
                r0 = r18
                r1 = r27
                r2 = r21
                r0.write(r11, r1, r2)     // Catch:{ Exception -> 0x0099 }
                goto L_0x0081
            L_0x0099:
                r9 = move-exception
            L_0x009a:
                java.lang.String r27 = "FirmwareUpdateFrag"
                java.lang.String r28 = r9.toString()     // Catch:{ all -> 0x0290 }
                android.util.Log.i(r27, r28)     // Catch:{ all -> 0x0290 }
                r27 = -1
                r0 = r27
                r1 = r30
                r1.currentProgress = r0     // Catch:{ all -> 0x0290 }
                if (r8 == 0) goto L_0x00b0
                r8.disconnect()
            L_0x00b0:
                r27 = 0
            L_0x00b2:
                return r27
            L_0x00b3:
                java.lang.String r27 = "FirmwareUpdateFrag"
                java.lang.String r28 = "firmwareUpdateFragment doEsp32FirmwareUpdate bad type, cancelling"
                android.util.Log.i(r27, r28)
                r27 = 0
                goto L_0x00b2
            L_0x00bd:
                java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0099 }
                r0 = r23
                r12.<init>(r0)     // Catch:{ Exception -> 0x0099 }
                r18.close()     // Catch:{ Exception -> 0x0099 }
                java.net.URL r26 = new java.net.URL     // Catch:{ Exception -> 0x0099 }
                r0 = r26
                r1 = r25
                r0.<init>(r1)     // Catch:{ Exception -> 0x0099 }
                java.net.URLConnection r27 = r26.openConnection()     // Catch:{ Exception -> 0x0099 }
                r0 = r27
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x0099 }
                r8 = r0
                r27 = 15000(0x3a98, float:2.102E-41)
                r0 = r27
                r8.setConnectTimeout(r0)     // Catch:{ Exception -> 0x0099 }
                r27 = 60000(0xea60, float:8.4078E-41)
                r0 = r27
                r8.setReadTimeout(r0)     // Catch:{ Exception -> 0x0099 }
                r27 = 1
                r0 = r27
                r8.setDoInput(r0)     // Catch:{ Exception -> 0x0099 }
                r27 = 1
                r0 = r27
                r8.setDoOutput(r0)     // Catch:{ Exception -> 0x0099 }
                r27 = 0
                r0 = r27
                r8.setUseCaches(r0)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r27 = "POST"
                r0 = r27
                r8.setRequestMethod(r0)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r27 = "Connection"
                java.lang.String r28 = "Keep-Alive"
                r0 = r27
                r1 = r28
                r8.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r27 = "Content-Type"
                java.lang.StringBuilder r28 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0099 }
                r28.<init>()     // Catch:{ Exception -> 0x0099 }
                java.lang.String r29 = "multipart/form-data;boundary="
                java.lang.StringBuilder r28 = r28.append(r29)     // Catch:{ Exception -> 0x0099 }
                r0 = r28
                java.lang.StringBuilder r28 = r0.append(r3)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r28 = r28.toString()     // Catch:{ Exception -> 0x0099 }
                r0 = r27
                r1 = r28
                r8.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r16 = "admin:DSadmin714"
                java.lang.String r27 = "UTF-8"
                r0 = r16
                r1 = r27
                byte[] r27 = r0.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x0263 }
                r28 = 0
                java.lang.String r10 = android.util.Base64.encodeToString(r27, r28)     // Catch:{ UnsupportedEncodingException -> 0x0263 }
            L_0x013f:
                java.lang.String r27 = "Authorization"
                java.lang.StringBuilder r28 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0099 }
                r28.<init>()     // Catch:{ Exception -> 0x0099 }
                java.lang.String r29 = "Basic "
                java.lang.StringBuilder r28 = r28.append(r29)     // Catch:{ Exception -> 0x0099 }
                r0 = r28
                java.lang.StringBuilder r28 = r0.append(r10)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r28 = r28.toString()     // Catch:{ Exception -> 0x0099 }
                r0 = r27
                r1 = r28
                r8.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0099 }
                java.io.DataOutputStream r20 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0099 }
                java.io.OutputStream r27 = r8.getOutputStream()     // Catch:{ Exception -> 0x0099 }
                r0 = r20
                r1 = r27
                r0.<init>(r1)     // Catch:{ Exception -> 0x0099 }
                java.lang.StringBuilder r27 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27.<init>()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                r1 = r24
                java.lang.StringBuilder r27 = r0.append(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                java.lang.StringBuilder r27 = r0.append(r3)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                java.lang.StringBuilder r27 = r0.append(r15)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r27 = r27.toString()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r20
                r1 = r27
                r0.writeBytes(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.StringBuilder r27 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27.<init>()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r28 = "Content-Disposition: form-data; name=\"file[]\";filename=\""
                java.lang.StringBuilder r27 = r27.append(r28)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                java.lang.StringBuilder r27 = r0.append(r13)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r28 = "\""
                java.lang.StringBuilder r27 = r27.append(r28)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                java.lang.StringBuilder r27 = r0.append(r15)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r27 = r27.toString()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r20
                r1 = r27
                r0.writeBytes(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r20
                r0.writeBytes(r15)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                int r6 = r12.available()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r17
                int r5 = java.lang.Math.min(r6, r0)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                byte[] r4 = new byte[r5]     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27 = 0
                r0 = r27
                int r7 = r12.read(r4, r0, r5)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
            L_0x01cf:
                if (r7 <= 0) goto L_0x01de
                boolean r27 = r30.isCancelled()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                if (r27 == 0) goto L_0x0270
                java.lang.String r27 = "FirmwareUpdateFrag"
                java.lang.String r28 = "DoEsp32FirmwareUpdate Canceled"
                android.util.Log.i(r27, r28)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
            L_0x01de:
                r0 = r20
                r0.writeBytes(r15)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.StringBuilder r27 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27.<init>()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                r1 = r24
                java.lang.StringBuilder r27 = r0.append(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                java.lang.StringBuilder r27 = r0.append(r3)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                r1 = r24
                java.lang.StringBuilder r27 = r0.append(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r27
                java.lang.StringBuilder r27 = r0.append(r15)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r27 = r27.toString()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r20
                r1 = r27
                r0.writeBytes(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r12.close()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r20.flush()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r20.close()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r27 = "FirmwareUpdateFrag"
                java.lang.String r28 = "connection.getResponseCode "
                android.util.Log.i(r27, r28)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                int r22 = r8.getResponseCode()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r27 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r28 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r28.<init>()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r29 = "serverResponseCode "
                java.lang.StringBuilder r28 = r28.append(r29)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r28
                r1 = r22
                java.lang.StringBuilder r28 = r0.append(r1)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                java.lang.String r28 = r28.toString()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                android.util.Log.i(r27, r28)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27 = 200(0xc8, float:2.8E-43)
                r0 = r22
                r1 = r27
                if (r0 != r1) goto L_0x025a
                r27 = 100
                r0 = r27
                r1 = r30
                r1.currentProgress = r0     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r30
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27 = r0
                r28 = 1
                r27.updateWorked = r28     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
            L_0x025a:
                if (r8 == 0) goto L_0x02a0
                r8.disconnect()
                r19 = r20
                goto L_0x00b0
            L_0x0263:
                r9 = move-exception
                byte[] r27 = r16.getBytes()     // Catch:{ Exception -> 0x0099 }
                r28 = 0
                java.lang.String r10 = android.util.Base64.encodeToString(r27, r28)     // Catch:{ Exception -> 0x0099 }
                goto L_0x013f
            L_0x0270:
                r27 = 0
                r0 = r20
                r1 = r27
                r0.write(r4, r1, r5)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r20.flush()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                int r6 = r12.available()     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r0 = r17
                int r5 = java.lang.Math.min(r6, r0)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                r27 = 0
                r0 = r27
                int r7 = r12.read(r4, r0, r5)     // Catch:{ Exception -> 0x029b, all -> 0x0297 }
                goto L_0x01cf
            L_0x0290:
                r27 = move-exception
            L_0x0291:
                if (r8 == 0) goto L_0x0296
                r8.disconnect()
            L_0x0296:
                throw r27
            L_0x0297:
                r27 = move-exception
                r19 = r20
                goto L_0x0291
            L_0x029b:
                r9 = move-exception
                r19 = r20
                goto L_0x009a
            L_0x02a0:
                r19 = r20
                goto L_0x00b0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.DoEsp32FirmwareUpdate.doInBackground(java.lang.Void[]):java.lang.Void");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            Log.i(FirmwareUpdateFragment.tag, "doEsp32FirmwareUpdate onPostExecute");
            if (FirmwareUpdateFragment.this.updateWorked) {
                FirmwareUpdateFragment.this.espFirmwareUpdateNeeded = false;
                FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                    public void run() {
                        FirmwareUpdateFragment.this.completelyFinishedUpdating();
                    }
                }, 10000);
            } else if (!FirmwareUpdateFragment.this.secondAttempt) {
                FirmwareUpdateFragment.this.secondAttempt = true;
                Log.i(FirmwareUpdateFragment.tag, "doEspFirmwareUpdate failed, trying again");
                FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                    public void run() {
                        new DoEsp32FirmwareUpdate().execute(new Void[0]);
                    }
                }, 12000);
            } else {
                Log.i(FirmwareUpdateFragment.tag, "doEspFirmwareUpdate second attempt failed, need to recover");
                FirmwareUpdateFragment.this.picUpdateFailed();
            }
        }
    }

    private class DoEspFirmwareUpdate extends AsyncTask<Void, Void, Void> {
        private DoEspFirmwareUpdate() {
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x02f7  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Void... r33) {
            /*
                r32 = this;
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.String r30 = "doEspFirmwareUpdate doInBackground"
                android.util.Log.i(r29, r30)
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r29 = r0
                r30 = 1
                r29.updating = r30
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r29 = r0
                int r29 = r29.productId
                r30 = 1
                r0 = r29
                r1 = r30
                if (r0 != r1) goto L_0x00ab
                java.lang.String r13 = "DSv2_revF.ino.bin"
            L_0x0026:
                r25 = 0
                java.lang.StringBuilder r29 = new java.lang.StringBuilder
                r29.<init>()
                java.lang.String r30 = "http://"
                java.lang.StringBuilder r29 = r29.append(r30)
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r30 = r0
                java.lang.String r30 = r30.lightsIp
                java.lang.StringBuilder r29 = r29.append(r30)
                java.lang.String r30 = "/firmware"
                java.lang.StringBuilder r29 = r29.append(r30)
                java.lang.String r27 = r29.toString()
                r8 = 0
                r19 = 0
                java.lang.String r15 = "\r\n"
                java.lang.String r26 = "--"
                java.lang.String r3 = "*****"
                r17 = 1048576(0x100000, float:1.469368E-39)
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this     // Catch:{ Exception -> 0x0099 }
                r29 = r0
                android.content.res.Resources r29 = r29.getResources()     // Catch:{ Exception -> 0x0099 }
                android.content.res.AssetManager r29 = r29.getAssets()     // Catch:{ Exception -> 0x0099 }
                r0 = r29
                java.io.InputStream r14 = r0.open(r13)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r29 = "pre"
                java.lang.String r30 = "suf"
                java.io.File r25 = java.io.File.createTempFile(r29, r30)     // Catch:{ Exception -> 0x0099 }
                java.io.FileOutputStream r18 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0099 }
                r0 = r18
                r1 = r25
                r0.<init>(r1)     // Catch:{ Exception -> 0x0099 }
                r29 = 1024(0x400, float:1.435E-42)
                r0 = r29
                byte[] r11 = new byte[r0]     // Catch:{ Exception -> 0x0099 }
            L_0x0081:
                int r21 = r14.read(r11)     // Catch:{ Exception -> 0x0099 }
                r29 = -1
                r0 = r21
                r1 = r29
                if (r0 == r1) goto L_0x00f7
                r29 = 0
                r0 = r18
                r1 = r29
                r2 = r21
                r0.write(r11, r1, r2)     // Catch:{ Exception -> 0x0099 }
                goto L_0x0081
            L_0x0099:
                r9 = move-exception
            L_0x009a:
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.String r30 = r9.toString()     // Catch:{ all -> 0x02f4 }
                android.util.Log.i(r29, r30)     // Catch:{ all -> 0x02f4 }
                if (r8 == 0) goto L_0x00a8
                r8.disconnect()
            L_0x00a8:
                r29 = 0
            L_0x00aa:
                return r29
            L_0x00ab:
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r29 = r0
                int r29 = r29.productId
                r30 = 2
                r0 = r29
                r1 = r30
                if (r0 != r1) goto L_0x00c1
                java.lang.String r13 = "DSv2_revF.ino.bin"
                goto L_0x0026
            L_0x00c1:
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r29 = r0
                int r29 = r29.productId
                r30 = 7
                r0 = r29
                r1 = r30
                if (r0 != r1) goto L_0x00d7
                java.lang.String r13 = "DSv2_revF.ino.bin"
                goto L_0x0026
            L_0x00d7:
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                r29 = r0
                int r29 = r29.productId
                r30 = 3
                r0 = r29
                r1 = r30
                if (r0 != r1) goto L_0x00ed
                java.lang.String r13 = "Side2Side_revE.ino.bin"
                goto L_0x0026
            L_0x00ed:
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.String r30 = "firmwareUpdateFragment doEspFirmwareUpdate bad type, cancelling"
                android.util.Log.i(r29, r30)
                r29 = 0
                goto L_0x00aa
            L_0x00f7:
                java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0099 }
                r0 = r25
                r12.<init>(r0)     // Catch:{ Exception -> 0x0099 }
                r18.close()     // Catch:{ Exception -> 0x0099 }
                java.net.URL r28 = new java.net.URL     // Catch:{ Exception -> 0x0099 }
                r0 = r28
                r1 = r27
                r0.<init>(r1)     // Catch:{ Exception -> 0x0099 }
                java.net.URLConnection r29 = r28.openConnection()     // Catch:{ Exception -> 0x0099 }
                r0 = r29
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x0099 }
                r8 = r0
                r29 = 15000(0x3a98, float:2.102E-41)
                r0 = r29
                r8.setConnectTimeout(r0)     // Catch:{ Exception -> 0x0099 }
                r29 = 15000(0x3a98, float:2.102E-41)
                r0 = r29
                r8.setReadTimeout(r0)     // Catch:{ Exception -> 0x0099 }
                r29 = 1
                r0 = r29
                r8.setDoInput(r0)     // Catch:{ Exception -> 0x0099 }
                r29 = 1
                r0 = r29
                r8.setDoOutput(r0)     // Catch:{ Exception -> 0x0099 }
                r29 = 0
                r0 = r29
                r8.setUseCaches(r0)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r29 = "POST"
                r0 = r29
                r8.setRequestMethod(r0)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r29 = "Connection"
                java.lang.String r30 = "Keep-Alive"
                r0 = r29
                r1 = r30
                r8.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r29 = "Content-Type"
                java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0099 }
                r30.<init>()     // Catch:{ Exception -> 0x0099 }
                java.lang.String r31 = "multipart/form-data;boundary="
                java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ Exception -> 0x0099 }
                r0 = r30
                java.lang.StringBuilder r30 = r0.append(r3)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r30 = r30.toString()     // Catch:{ Exception -> 0x0099 }
                r0 = r29
                r1 = r30
                r8.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r16 = "admin:DSadmin714"
                java.lang.String r29 = "UTF-8"
                r0 = r16
                r1 = r29
                byte[] r29 = r0.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x02ca }
                r30 = 0
                java.lang.String r10 = android.util.Base64.encodeToString(r29, r30)     // Catch:{ UnsupportedEncodingException -> 0x02ca }
            L_0x0178:
                java.lang.String r29 = "Authorization"
                java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0099 }
                r30.<init>()     // Catch:{ Exception -> 0x0099 }
                java.lang.String r31 = "Basic "
                java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ Exception -> 0x0099 }
                r0 = r30
                java.lang.StringBuilder r30 = r0.append(r10)     // Catch:{ Exception -> 0x0099 }
                java.lang.String r30 = r30.toString()     // Catch:{ Exception -> 0x0099 }
                r0 = r29
                r1 = r30
                r8.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0099 }
                java.io.DataOutputStream r20 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0099 }
                java.io.OutputStream r29 = r8.getOutputStream()     // Catch:{ Exception -> 0x0099 }
                r0 = r20
                r1 = r29
                r0.<init>(r1)     // Catch:{ Exception -> 0x0099 }
                java.lang.StringBuilder r29 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29.<init>()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                r1 = r26
                java.lang.StringBuilder r29 = r0.append(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                java.lang.StringBuilder r29 = r0.append(r3)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                java.lang.StringBuilder r29 = r0.append(r15)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = r29.toString()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r20
                r1 = r29
                r0.writeBytes(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.StringBuilder r29 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29.<init>()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r30 = "Content-Disposition: form-data; name=\"file[]\";filename=\""
                java.lang.StringBuilder r29 = r29.append(r30)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                java.lang.StringBuilder r29 = r0.append(r13)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r30 = "\""
                java.lang.StringBuilder r29 = r29.append(r30)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                java.lang.StringBuilder r29 = r0.append(r15)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = r29.toString()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r20
                r1 = r29
                r0.writeBytes(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r20
                r0.writeBytes(r15)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                int r6 = r12.available()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r17
                int r5 = java.lang.Math.min(r6, r0)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                byte[] r4 = new byte[r5]     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29 = 0
                r0 = r29
                int r7 = r12.read(r4, r0, r5)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
            L_0x0208:
                if (r7 <= 0) goto L_0x0217
                boolean r29 = r32.isCancelled()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                if (r29 == 0) goto L_0x02d7
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.String r30 = "DoEspFirmwareUpdate Canceled"
                android.util.Log.i(r29, r30)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
            L_0x0217:
                r0 = r20
                r0.writeBytes(r15)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.StringBuilder r29 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29.<init>()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                r1 = r26
                java.lang.StringBuilder r29 = r0.append(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                java.lang.StringBuilder r29 = r0.append(r3)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                r1 = r26
                java.lang.StringBuilder r29 = r0.append(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r29
                java.lang.StringBuilder r29 = r0.append(r15)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = r29.toString()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r20
                r1 = r29
                r0.writeBytes(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r12.close()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r20.flush()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r20.close()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.String r30 = "connection.getResponseCode "
                android.util.Log.i(r29, r30)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                int r24 = r8.getResponseCode()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r30.<init>()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r31 = "serverResponseCode "
                java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r30
                r1 = r24
                java.lang.StringBuilder r30 = r0.append(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r30 = r30.toString()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                android.util.Log.i(r29, r30)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29 = 200(0xc8, float:2.8E-43)
                r0 = r24
                r1 = r29
                if (r0 != r1) goto L_0x02c1
                java.util.Scanner r23 = new java.util.Scanner     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.io.InputStream r29 = r8.getInputStream()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r23
                r1 = r29
                r0.<init>(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = "\\Z"
                r0 = r23
                r1 = r29
                r0.useDelimiter(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r22 = r23.next()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r29 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r30.<init>()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r31 = "ESP response : "
                java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r30
                r1 = r22
                java.lang.StringBuilder r30 = r0.append(r1)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                java.lang.String r30 = r30.toString()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                android.util.Log.i(r29, r30)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r32
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29 = r0
                r30 = 1
                r29.updateWorked = r30     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
            L_0x02c1:
                if (r8 == 0) goto L_0x0304
                r8.disconnect()
                r19 = r20
                goto L_0x00a8
            L_0x02ca:
                r9 = move-exception
                byte[] r29 = r16.getBytes()     // Catch:{ Exception -> 0x0099 }
                r30 = 0
                java.lang.String r10 = android.util.Base64.encodeToString(r29, r30)     // Catch:{ Exception -> 0x0099 }
                goto L_0x0178
            L_0x02d7:
                r29 = 0
                r0 = r20
                r1 = r29
                r0.write(r4, r1, r5)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                int r6 = r12.available()     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r0 = r17
                int r5 = java.lang.Math.min(r6, r0)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                r29 = 0
                r0 = r29
                int r7 = r12.read(r4, r0, r5)     // Catch:{ Exception -> 0x02ff, all -> 0x02fb }
                goto L_0x0208
            L_0x02f4:
                r29 = move-exception
            L_0x02f5:
                if (r8 == 0) goto L_0x02fa
                r8.disconnect()
            L_0x02fa:
                throw r29
            L_0x02fb:
                r29 = move-exception
                r19 = r20
                goto L_0x02f5
            L_0x02ff:
                r9 = move-exception
                r19 = r20
                goto L_0x009a
            L_0x0304:
                r19 = r20
                goto L_0x00a8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.DoEspFirmwareUpdate.doInBackground(java.lang.Void[]):java.lang.Void");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            Log.i(FirmwareUpdateFragment.tag, "doEspFirmwareUpdate onPostExecute");
            if (FirmwareUpdateFragment.this.updateWorked) {
                FirmwareUpdateFragment.this.espFirmwareUpdateNeeded = false;
                Log.i(FirmwareUpdateFragment.tag, "!!! temporary, prevent updating both at same time");
                FirmwareUpdateFragment.this.picFirmwareUpdateNeeded = false;
                if (FirmwareUpdateFragment.this.picFirmwareUpdateNeeded) {
                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            FirmwareUpdateFragment.this.startPicUpdating();
                        }
                    }, 25000);
                } else {
                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            FirmwareUpdateFragment.this.completelyFinishedUpdating();
                        }
                    }, 10000);
                }
            } else if (!FirmwareUpdateFragment.this.secondAttempt) {
                FirmwareUpdateFragment.this.secondAttempt = true;
                Log.i(FirmwareUpdateFragment.tag, "doEspFirmwareUpdate failed, trying again");
                FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                    public void run() {
                        new DoEspFirmwareUpdate().execute(new Void[0]);
                    }
                }, 12000);
            } else {
                Log.i(FirmwareUpdateFragment.tag, "doEspFirmwareUpdate second attempt failed, need to recover");
                FirmwareUpdateFragment.this.picUpdateFailed();
            }
        }
    }

    private class DoPicFirmwareUpdate extends AsyncTask<Void, Void, Void> {
        private DoPicFirmwareUpdate() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voids) {
            Log.i(FirmwareUpdateFragment.tag, "doPicFirmwareUpdate doInBackground");
            FirmwareUpdateFragment.this.bootloaderFrames = new LinkedList();
            FirmwareUpdateFragment.this.buildTXFrame(FirmwareUpdateFragment.this.hexStringToByteArray("02"));
            FirmwareUpdateFragment.this.processHexFile();
            if (FirmwareUpdateFragment.this.productId == 1) {
                FirmwareUpdateFragment.this.bootloaderFrames.add(FirmwareUpdateFragment.this.verifyRequestFrameHD);
            } else if (FirmwareUpdateFragment.this.productId == 2) {
                FirmwareUpdateFragment.this.bootloaderFrames.add(FirmwareUpdateFragment.this.verifyRequestFrame4K);
            } else if (FirmwareUpdateFragment.this.productId == 7) {
                FirmwareUpdateFragment.this.bootloaderFrames.add(FirmwareUpdateFragment.this.verifyRequestFrameSolo);
            } else {
                Log.i(FirmwareUpdateFragment.tag, "doInBackground, error invalid productId. canceling");
                return null;
            }
            double framesCount = (double) FirmwareUpdateFragment.this.bootloaderFrames.size();
            while (true) {
                if (FirmwareUpdateFragment.this.bootloaderFrames.isEmpty() || FirmwareUpdateFragment.this.canceled) {
                    break;
                } else if (isCancelled()) {
                    Log.i(FirmwareUpdateFragment.tag, "isCancelled(), need to recover");
                    break;
                } else if (FirmwareUpdateFragment.this.tcpTimeoutCount > 7) {
                    Log.i(FirmwareUpdateFragment.tag, "sendTCP, tcpTimeoutCount > 5, need to recover");
                    break;
                } else {
                    FirmwareUpdateFragment.this.sendTCP((byte[]) FirmwareUpdateFragment.this.bootloaderFrames.getFirst());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.i(FirmwareUpdateFragment.tag, "interrupted exception : " + e.toString());
                    }
                    final int percent = (int) (100.0d - ((((double) FirmwareUpdateFragment.this.bootloaderFrames.size()) / framesCount) * 100.0d));
                    FirmwareUpdateFragment.this.handler.post(new Runnable() {
                        public void run() {
                            if (percent >= 0 && percent <= 99) {
                                FirmwareUpdateFragment.this.updateText.setText(percent + "%");
                            }
                        }
                    });
                }
            }
            if (!FirmwareUpdateFragment.this.bootloaderFrames.isEmpty()) {
                FirmwareUpdateFragment.this.canceled = true;
            }
            if (!FirmwareUpdateFragment.this.canceled) {
                if (isCancelled()) {
                    Log.i(FirmwareUpdateFragment.tag, "isCancelled() 2, need to recover");
                } else {
                    FirmwareUpdateFragment.this.sendJTA();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e2) {
                        Log.i(FirmwareUpdateFragment.tag, "interrupted exception : " + e2.toString());
                    }
                    FirmwareUpdateFragment.this.sendJTA();
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            Log.i(FirmwareUpdateFragment.tag, "doPicFirmwareUpdate onPostExecute");
            if (FirmwareUpdateFragment.this.canceled || !FirmwareUpdateFragment.this.bootloaderFrames.isEmpty()) {
                Log.i(FirmwareUpdateFragment.tag, "onPostExecute, pic bootloading failed. ");
                FirmwareUpdateFragment.this.allFramesSent = false;
                FirmwareUpdateFragment.this.picUpdateFailed();
                return;
            }
            Log.i(FirmwareUpdateFragment.tag, "onPostExecute, all frames successfully sent");
            FirmwareUpdateFragment.this.allFramesSent = true;
            FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                public void run() {
                    Log.i(FirmwareUpdateFragment.tag, "clearBootloaderFlags, readPicVersionNumber");
                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.clearBootloaderFlags();
                    }
                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                        public void run() {
                            FirmwareUpdateFragment.this.picVersionNumberRead = false;
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readPicVersionNumber();
                            }
                        }
                    }, 2000);
                }
            }, 10000);
            FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (!FirmwareUpdateFragment.this.picVersionNumberRead) {
                        Log.i(FirmwareUpdateFragment.tag, "clearBootloaderFlags, readPicVersionNumber second time");
                        if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                            FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.clearBootloaderFlags();
                        }
                        FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                    FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readPicVersionNumber();
                                }
                            }
                        }, 2000);
                    }
                }
            }, 15000);
            FirmwareUpdateFragment.this.handler.postDelayed(FirmwareUpdateFragment.this.timeoutRunnable, 20000);
        }
    }

    public interface FirmwareUpdateFragmentListener {
        void clearBootloaderFlags();

        void disableBackButton();

        void doFactoryReset();

        void enterBootloaderFlags();

        boolean espFirmwareUpdateNeeded();

        byte[] getEspFirmwareVersion();

        String getIp();

        InetAddress getLightsUnicastIP();

        byte[] getPicFirmwareVersion();

        int getProductId();

        String getThingName();

        boolean picFirmwareUpdateNeeded();

        void readBootloaderFlags();

        void readBootloaderMode();

        void readDiagnostics();

        void readPicVersionNumber();

        void resetEsp();

        void resetPic();

        void restart();

        void setMode(int i);

        void stopEsp32Drivers();
    }

    public static FirmwareUpdateFragment newInstance() {
        return new FirmwareUpdateFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.productId = this.firmwareUpdateFragmentListener.getProductId();
        this.espFirmwareVersion = this.firmwareUpdateFragmentListener.getEspFirmwareVersion();
        this.espFirmwareUpdateNeeded = this.firmwareUpdateFragmentListener.espFirmwareUpdateNeeded();
        if (this.productId == 1 || this.productId == 2 || this.productId == 7) {
            this.picVersionNumber = this.firmwareUpdateFragmentListener.getPicFirmwareVersion();
            this.picFirmwareUpdateNeeded = this.firmwareUpdateFragmentListener.picFirmwareUpdateNeeded();
        }
        this.lightsIp = this.firmwareUpdateFragmentListener.getIp();
        this.lightsUnicastIP = this.firmwareUpdateFragmentListener.getLightsUnicastIP();
        if (this.productId == 4) {
            this.thingName = this.firmwareUpdateFragmentListener.getThingName().trim();
            Log.i(tag, "thingName :" + this.thingName + ":");
            if (!this.thingName.isEmpty()) {
                initCognitoAuth();
            } else {
                this.thingName = null;
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firmware_update, container, false);
        TextView versionText = (TextView) view.findViewById(R.id.versionText);
        TextView versionText2 = (TextView) view.findViewById(R.id.versionText2);
        this.updateText = (TextView) view.findViewById(R.id.updateText);
        TextView factoryResetText = (TextView) view.findViewById(R.id.factoryResetText);
        TextView factoryResetText2 = (TextView) view.findViewById(R.id.factoryResetText2);
        TextView restartText = (TextView) view.findViewById(R.id.restartText);
        TextView restartText2 = (TextView) view.findViewById(R.id.restartText2);
        TextView ipAddressText = (TextView) view.findViewById(R.id.ipAddressText);
        TextView ipAddressText2 = (TextView) view.findViewById(R.id.ipAddressText2);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        versionText.setTypeface(font);
        versionText2.setTypeface(font);
        this.updateText.setTypeface(font);
        factoryResetText.setTypeface(font);
        factoryResetText2.setTypeface(font);
        restartText.setTypeface(font);
        restartText2.setTypeface(font);
        ipAddressText.setTypeface(font);
        ipAddressText2.setTypeface(font);
        ipAddressText2.setText(this.lightsIp);
        view.findViewById(R.id.factoryResetLayout).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!FirmwareUpdateFragment.this.buttonPressed) {
                    View factoryResetDialog = FirmwareUpdateFragment.this.getActivity().getLayoutInflater().inflate(R.layout.factory_reset_dialog, null);
                    TextView resetTextButton = (TextView) factoryResetDialog.findViewById(R.id.resetTextButton);
                    TextView cancelTextButton = (TextView) factoryResetDialog.findViewById(R.id.cancelTextButton);
                    Builder builder = new Builder(FirmwareUpdateFragment.this.getActivity());
                    builder.setView(factoryResetDialog).setCancelable(false);
                    final AlertDialog resetDialog = builder.create();
                    resetDialog.show();
                    resetTextButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            resetDialog.dismiss();
                            FirmwareUpdateFragment.this.buttonPressed = true;
                            if (FirmwareUpdateFragment.this.productId == 4 && FirmwareUpdateFragment.this.thingName != null) {
                                FirmwareUpdateFragment.this.cognitoAuth.getSession();
                            } else if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.doFactoryReset();
                            }
                        }
                    });
                    cancelTextButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            resetDialog.dismiss();
                        }
                    });
                }
            }
        });
        String versionTextString = (this.espFirmwareVersion[0] & 255) + "." + (this.espFirmwareVersion[1] & 255);
        if (this.productId == 1 || this.productId == 2 || this.productId == 7) {
            versionTextString = versionTextString + "." + (this.picVersionNumber[0] & 255) + "." + (this.picVersionNumber[1] & 255);
        }
        versionText2.setText(versionTextString);
        if (this.espFirmwareUpdateNeeded || this.picFirmwareUpdateNeeded) {
            view.findViewById(R.id.firmwareUpdateLayout).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!FirmwareUpdateFragment.this.buttonPressed) {
                        View firmwareUpdateView = FirmwareUpdateFragment.this.getActivity().getLayoutInflater().inflate(R.layout.firmware_update_prompt, null);
                        TextView updateTextButton = (TextView) firmwareUpdateView.findViewById(R.id.updateTextButton);
                        TextView cancelTextButton = (TextView) firmwareUpdateView.findViewById(R.id.cancelTextButton);
                        Builder builder = new Builder(FirmwareUpdateFragment.this.getActivity());
                        builder.setView(firmwareUpdateView).setCancelable(false);
                        final AlertDialog firmwareUpdateDialog = builder.create();
                        firmwareUpdateDialog.show();
                        updateTextButton.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                firmwareUpdateDialog.dismiss();
                                FirmwareUpdateFragment.this.buttonPressed = true;
                                FirmwareUpdateFragment.this.doUpdate();
                            }
                        });
                        cancelTextButton.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                firmwareUpdateDialog.dismiss();
                            }
                        });
                    }
                }
            });
        } else {
            this.updateText.setVisibility(4);
        }
        view.findViewById(R.id.restartLayout).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!FirmwareUpdateFragment.this.buttonPressed) {
                    View restartView = FirmwareUpdateFragment.this.getActivity().getLayoutInflater().inflate(R.layout.restart_device_prompt, null);
                    final TextView restartTextButton = (TextView) restartView.findViewById(R.id.restartTextButton);
                    final TextView cancelTextButton = (TextView) restartView.findViewById(R.id.cancelTextButton);
                    Builder builder = new Builder(FirmwareUpdateFragment.this.getActivity());
                    builder.setView(restartView).setCancelable(false);
                    final AlertDialog restartDialog = builder.create();
                    restartDialog.show();
                    restartTextButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (!FirmwareUpdateFragment.this.buttonPressed) {
                                FirmwareUpdateFragment.this.buttonPressed = true;
                                Log.i(FirmwareUpdateFragment.tag, "restartTextButton tapped");
                                cancelTextButton.setVisibility(8);
                                restartTextButton.setText(FirmwareUpdateFragment.this.getContext().getResources().getString(R.string.Restarting));
                                if (FirmwareUpdateFragment.this.productId == 1 || FirmwareUpdateFragment.this.productId == 2 || FirmwareUpdateFragment.this.productId == 7) {
                                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetPic();
                                    }
                                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                        public void run() {
                                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetEsp();
                                            }
                                        }
                                    }, 300);
                                } else if (FirmwareUpdateFragment.this.productId != 3 && FirmwareUpdateFragment.this.productId != 4) {
                                    Log.i(FirmwareUpdateFragment.tag, "invalid productId");
                                } else if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                    FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetEsp();
                                }
                                FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                    public void run() {
                                        restartDialog.dismiss();
                                        if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                            FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.restart();
                                        }
                                    }
                                }, 8000);
                            }
                        }
                    });
                    cancelTextButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            restartDialog.dismiss();
                        }
                    });
                }
            }
        });
        if (this.productId == 2 || this.productId == 7) {
            this.diagnosticText1 = (TextView) view.findViewById(R.id.diagnosticText1);
            this.diagnosticText2 = (TextView) view.findViewById(R.id.diagnosticText2);
            this.diagnosticText1.setTypeface(font);
            this.diagnosticText2.setTypeface(font);
            this.diagnosticText1.setText("");
            this.diagnosticText2.setText("");
            view.findViewById(R.id.diagnosticLayout).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!FirmwareUpdateFragment.this.buttonPressed) {
                        
                        /*  JADX ERROR: Method code generation error
                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000b: INVOKE  (wrap: com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment
                              0x0009: IGET  (r0v2 com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment) = (r3v0 'this' com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment$4 A[THIS]) com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.4.this$0 com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment) com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.access$708(com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment):int type: STATIC in method: com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.4.onClick(android.view.View):void, dex: classes.dex
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
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
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:713)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:138)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
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
                            	... 50 more
                            Caused by: java.lang.ClassNotFoundException: sun.reflect.ReflectionFactory
                            	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(Unknown Source)
                            	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(Unknown Source)
                            	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
                            	at java.base/java.lang.Class.forName0(Native Method)
                            	at java.base/java.lang.Class.forName(Unknown Source)
                            	at org.objenesis.instantiator.sun.SunReflectionFactoryHelper.getReflectionFactoryClass(SunReflectionFactoryHelper.java:54)
                            	... 68 more
                            */
                        /*
                            this = this;
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            boolean r0 = r0.buttonPressed
                            if (r0 == 0) goto L_0x0009
                        L_0x0008:
                            return
                        L_0x0009:
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            
                            // error: 0x000b: INVOKE  (r0 I:com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment) com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.access$708(com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment):int type: STATIC
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            int r0 = r0.diagnosticTappedCount
                            r1 = 4
                            if (r0 <= r1) goto L_0x0008
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            r1 = 0
                            r0.diagnosticTappedCount = r1
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            android.widget.TextView r0 = r0.diagnosticText1
                            java.lang.String r1 = ""
                            r0.setText(r1)
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            android.widget.TextView r0 = r0.diagnosticText2
                            java.lang.String r1 = ""
                            r0.setText(r1)
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            android.content.Context r0 = r0.getContext()
                            java.lang.String r1 = "Requesting Diagnostics"
                            r2 = 1
                            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
                            r0.show()
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment$FirmwareUpdateFragmentListener r0 = r0.firmwareUpdateFragmentListener
                            if (r0 == 0) goto L_0x0008
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment r0 = com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.this
                            com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment$FirmwareUpdateFragmentListener r0 = r0.firmwareUpdateFragmentListener
                            r0.readDiagnostics()
                            goto L_0x0008
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.AnonymousClass4.onClick(android.view.View):void");
                    }
                });
            }
            return view;
        }

        public void diagnosticsReceived(byte[] payload) {
            Log.i(tag, "diagnosticsReceived bytes: " + payload.length);
            this.diagnosticTappedCount = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : payload) {
                stringBuilder.append(String.format("%02X ", new Object[]{Byte.valueOf(b)}));
            }
            this.diagnosticText1.setText("Diagnostics:");
            this.diagnosticText2.setText(stringBuilder.toString());
        }

        public void onResume() {
            super.onResume();
            if (this.cognitoAuth != null) {
                Log.i(tag, "onResume, username " + this.cognitoAuth.getUsername());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(dsv2Prefs, 0);
                String redirectUriString = sharedPreferences.getString(ClientConstants.DOMAIN_QUERY_PARAM_REDIRECT_URI, "");
                if (!redirectUriString.isEmpty()) {
                    Uri redirectUri = Uri.parse(redirectUriString);
                    Log.i(tag, "uri " + redirectUriString + " " + redirectUri.toString());
                    this.cognitoAuth.getTokens(redirectUri);
                    Editor editor = sharedPreferences.edit();
                    editor.putString(ClientConstants.DOMAIN_QUERY_PARAM_REDIRECT_URI, "");
                    editor.apply();
                }
            }
        }

        private void initCognitoAuth() {
            this.cognitoAuth = new Auth.Builder().setAppClientId(AwsConfig.USERPOOL_ANDROID_APP_CLIENT_ID).setAppCognitoWebDomain(AwsConfig.COGNITO_DOMAIN_NAME).setApplicationContext(getContext()).setAuthHandler(new AuthHandlerCallback()).setSignInRedirect(getString(R.string.app_signin_redirect)).setSignOutRedirect(getString(R.string.app_signout_redirect)).build();
        }

        /* access modifiers changed from: private */
        public void doUpdate() {
            if (this.firmwareUpdateFragmentListener != null) {
                this.firmwareUpdateFragmentListener.disableBackButton();
                Log.i(tag, "doUpdate");
                showProgressLayout();
                if (this.espFirmwareUpdateNeeded) {
                    this.firmwareUpdateFragmentListener.setMode(0);
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.setMode(0);
                            }
                        }
                    }, 50);
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.stopEsp32Drivers();
                            }
                        }
                    }, 100);
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.stopEsp32Drivers();
                            }
                        }
                    }, 150);
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.productId == 4) {
                                new DoEsp32FirmwareUpdate().execute(new Void[0]);
                            } else {
                                new DoEspFirmwareUpdate().execute(new Void[0]);
                            }
                        }
                    }, 4000);
                } else if (this.picFirmwareUpdateNeeded) {
                    this.firmwareUpdateFragmentListener.setMode(0);
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.setMode(0);
                            }
                        }
                    }, 50);
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            FirmwareUpdateFragment.this.startPicUpdating();
                        }
                    }, 100);
                } else {
                    Log.i(tag, "error, no update started");
                    completelyFinishedUpdating();
                }
            }
        }

        /* access modifiers changed from: private */
        public void startPicUpdating() {
            Log.i(tag, "startPicUpdating");
            this.updating = true;
            this.tcpTimeoutCount = 0;
            this.canceled = false;
            this.timeoutRunnable = new Runnable() {
                public void run() {
                    Log.i(FirmwareUpdateFragment.tag, "timeoutRunnable, never received response, picUpdateFailed");
                    FirmwareUpdateFragment.this.picUpdateFailed();
                }
            };
            if (this.shouldResumePicUpdating) {
                deviceInBootloaderModeReceived(1);
                return;
            }
            this.bootloaderModeResponded = false;
            if (this.firmwareUpdateFragmentListener != null) {
                this.firmwareUpdateFragmentListener.readBootloaderMode();
            }
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (!FirmwareUpdateFragment.this.bootloaderModeResponded && FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderMode();
                    }
                }
            }, 4000);
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (!FirmwareUpdateFragment.this.bootloaderModeResponded) {
                        if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                            FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.enterBootloaderFlags();
                        }
                        FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                    FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetPic();
                                }
                            }
                        }, 1000);
                        FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                FirmwareUpdateFragment.this.bootloaderModeResponded = false;
                                if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                    FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderMode();
                                }
                            }
                        }, 8000);
                        FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                if (!FirmwareUpdateFragment.this.bootloaderModeResponded) {
                                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.enterBootloaderFlags();
                                    }
                                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                        public void run() {
                                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetPic();
                                            }
                                        }
                                    }, 1000);
                                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                        public void run() {
                                            if (!FirmwareUpdateFragment.this.bootloaderModeResponded && FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderMode();
                                            }
                                        }
                                    }, 8000);
                                    FirmwareUpdateFragment.this.handler.postDelayed(FirmwareUpdateFragment.this.timeoutRunnable, 20000);
                                }
                            }
                        }, 20000);
                    }
                }
            }, 8000);
        }

        public void resumePicUpdating(int picIsInBootloaderMode) {
            showProgressLayout();
            this.buttonPressed = true;
            if (this.firmwareUpdateFragmentListener != null) {
                this.firmwareUpdateFragmentListener.disableBackButton();
            }
            if (this.espFirmwareUpdateNeeded) {
                Log.i(tag, "resumePicUpdating, but updating esp first");
                this.picFirmwareUpdateNeeded = true;
                this.shouldResumePicUpdating = true;
                new DoEspFirmwareUpdate().execute(new Void[0]);
                return;
            }
            Log.i(tag, "resumePicUpdating");
            this.updating = true;
            this.tcpTimeoutCount = 0;
            this.canceled = false;
            this.timeoutRunnable = new Runnable() {
                public void run() {
                    Log.i(FirmwareUpdateFragment.tag, "timeoutRunnable, never received response, picUpdateFailed");
                    FirmwareUpdateFragment.this.picUpdateFailed();
                }
            };
            deviceInBootloaderModeReceived(picIsInBootloaderMode);
        }

        public void bootloaderFlagsReceived(boolean isBootloaderFlags) {
            Log.i(tag, "bootloaderFlagsReceived, isBootloaderFlags " + isBootloaderFlags);
            this.bootloaderFlagsResponded = true;
            if (this.failedDialog != null && this.failedDialog.isShowing()) {
                this.failedDialog.dismiss();
            }
            if (this.updating) {
                this.handler.removeCallbacks(this.timeoutRunnable);
                if (isBootloaderFlags) {
                    if (!this.allFramesSent) {
                        Log.i(tag, "resetPic");
                        if (this.firmwareUpdateFragmentListener != null) {
                            this.firmwareUpdateFragmentListener.resetPic();
                        }
                        this.handler.postDelayed(new Runnable() {
                            public void run() {
                                FirmwareUpdateFragment.this.bootloaderModeResponded = false;
                                if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                    FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderMode();
                                }
                            }
                        }, 5000);
                        this.handler.postDelayed(new Runnable() {
                            public void run() {
                                if (!FirmwareUpdateFragment.this.bootloaderModeResponded) {
                                    Log.i(FirmwareUpdateFragment.tag, "resetPic, readBootloaderMode timed out. attempt second time");
                                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetPic();
                                    }
                                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                        public void run() {
                                            if (!FirmwareUpdateFragment.this.bootloaderModeResponded && FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderMode();
                                            }
                                        }
                                    }, 5000);
                                    FirmwareUpdateFragment.this.handler.postDelayed(FirmwareUpdateFragment.this.timeoutRunnable, 15000);
                                }
                            }
                        }, 15000);
                        return;
                    }
                    Log.i(tag, "bootloader flags set but all frames already sent, attempt to clear again");
                    this.loop++;
                    if (this.loop > 2) {
                        picUpdateFailed();
                        return;
                    }
                    if (this.firmwareUpdateFragmentListener != null) {
                        this.firmwareUpdateFragmentListener.clearBootloaderFlags();
                    }
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderFlags();
                            }
                        }
                    }, 1000);
                    this.handler.postDelayed(this.timeoutRunnable, 4000);
                } else if (this.allFramesSent) {
                    Log.i(tag, "pic is done bootloading");
                    this.handler.post(new Runnable() {
                        public void run() {
                            FirmwareUpdateFragment.this.picFirmwareUpdateNeeded = false;
                            Log.i(FirmwareUpdateFragment.tag, "!!! temporary, prevent updating both at same time");
                            FirmwareUpdateFragment.this.espFirmwareUpdateNeeded = false;
                            if (FirmwareUpdateFragment.this.espFirmwareUpdateNeeded) {
                                FirmwareUpdateFragment.this.showProgressLayout();
                                FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                    public void run() {
                                        new DoEspFirmwareUpdate().execute(new Void[0]);
                                    }
                                }, 6000);
                                return;
                            }
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetEsp();
                            }
                            FirmwareUpdateFragment.this.completelyFinishedUpdating();
                        }
                    });
                } else {
                    Log.i(tag, "bootloader flags not set and not all frames have been sent, attempt to re-set the flags");
                    this.loop++;
                    if (this.loop > 2) {
                        picUpdateFailed();
                        return;
                    }
                    this.firmwareUpdateFragmentListener.enterBootloaderFlags();
                    this.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderFlags();
                            }
                        }
                    }, 1000);
                    this.handler.postDelayed(this.timeoutRunnable, 4000);
                }
            }
        }

        public void deviceInBootloaderModeReceived(int picIsInBootloaderMode) {
            Log.i(tag, "deviceInBootloaderModeReceived " + picIsInBootloaderMode);
            if (this.updating) {
                if (picIsInBootloaderMode == 1 && !this.allFramesSent && !this.framesBuilt) {
                    if (this.failedDialog != null && this.failedDialog.isShowing()) {
                        this.failedDialog.dismiss();
                    }
                    this.framesBuilt = true;
                    this.bootloaderModeResponded = true;
                    this.handler.removeCallbacks(this.timeoutRunnable);
                    new DoPicFirmwareUpdate().execute(new Void[0]);
                } else if (picIsInBootloaderMode != 0 || !this.allFramesSent) {
                    Log.i(tag, "deviceInBootloaderModeReceived but not in a correct state, doing nothing");
                } else {
                    if (this.failedDialog != null && this.failedDialog.isShowing()) {
                        this.failedDialog.dismiss();
                    }
                    this.bootloaderModeResponded = true;
                    this.handler.removeCallbacks(this.timeoutRunnable);
                    this.handler.post(new Runnable() {
                        public void run() {
                            FirmwareUpdateFragment.this.picFirmwareUpdateNeeded = false;
                            Log.i(FirmwareUpdateFragment.tag, "!!! temporary, prevent updating both at same time");
                            FirmwareUpdateFragment.this.espFirmwareUpdateNeeded = false;
                            if (FirmwareUpdateFragment.this.espFirmwareUpdateNeeded) {
                                FirmwareUpdateFragment.this.showProgressLayout();
                                FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                    public void run() {
                                        new DoEspFirmwareUpdate().execute(new Void[0]);
                                    }
                                }, 6000);
                                return;
                            }
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetEsp();
                            }
                            FirmwareUpdateFragment.this.completelyFinishedUpdating();
                        }
                    });
                }
            }
        }

        public void picVersionNumberReceived(byte[] newPicVersionNumber) {
            if (!this.picVersionNumberRead) {
                this.picVersionNumberRead = true;
                if (this.failedDialog != null && this.failedDialog.isShowing()) {
                    this.failedDialog.dismiss();
                }
                if (this.updating) {
                    this.handler.removeCallbacks(this.timeoutRunnable);
                    Log.i(tag, "picVersionNumberReceived, old " + this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " to " + newPicVersionNumber[0] + "." + newPicVersionNumber[1]);
                    boolean picUpdateWorked = false;
                    if (this.firmwareUpdateFragmentListener != null) {
                        picUpdateWorked = !this.firmwareUpdateFragmentListener.picFirmwareUpdateNeeded();
                    }
                    if (picUpdateWorked) {
                        Log.i(tag, "picVersionNumberReceived, pic update worked, clear and check bootloader flags");
                        if (this.firmwareUpdateFragmentListener != null) {
                            this.firmwareUpdateFragmentListener.clearBootloaderFlags();
                        }
                        this.handler.postDelayed(new Runnable() {
                            public void run() {
                                FirmwareUpdateFragment.this.bootloaderFlagsResponded = false;
                                if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                    FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderFlags();
                                }
                            }
                        }, 1000);
                        this.handler.postDelayed(new Runnable() {
                            public void run() {
                                if (!FirmwareUpdateFragment.this.bootloaderFlagsResponded) {
                                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.clearBootloaderFlags();
                                    }
                                    FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                        public void run() {
                                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.readBootloaderFlags();
                                            }
                                        }
                                    }, 1000);
                                }
                            }
                        }, 4000);
                        this.handler.postDelayed(this.timeoutRunnable, 7000);
                        return;
                    }
                    Log.i(tag, "picVersionNumberReceived, pic update failed, need to restart?");
                    picUpdateFailed();
                    if (this.firmwareUpdateFragmentListener != null) {
                        this.firmwareUpdateFragmentListener.clearBootloaderFlags();
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void picUpdateFailed() {
            Log.i(tag, "picUpdateFailed");
            this.handler.post(new Runnable() {
                public void run() {
                    if (FirmwareUpdateFragment.this.failedDialog == null || !FirmwareUpdateFragment.this.failedDialog.isShowing()) {
                        if (FirmwareUpdateFragment.this.productId == 1 || FirmwareUpdateFragment.this.productId == 2 || FirmwareUpdateFragment.this.productId == 7) {
                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetPic();
                            }
                            FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                        FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetEsp();
                                    }
                                }
                            }, 300);
                        } else if (FirmwareUpdateFragment.this.productId != 3 && FirmwareUpdateFragment.this.productId != 4) {
                            Log.i(FirmwareUpdateFragment.tag, "invalid productId");
                        } else if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                            FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.resetEsp();
                        }
                        FirmwareUpdateFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                if (FirmwareUpdateFragment.this.failedDialog == null || !FirmwareUpdateFragment.this.failedDialog.isShowing()) {
                                    View firmwareUpdateFailedView = FirmwareUpdateFragment.this.getActivity().getLayoutInflater().inflate(R.layout.firmware_update_failed_dialog, null);
                                    Builder builder = new Builder(FirmwareUpdateFragment.this.getActivity());
                                    builder.setView(firmwareUpdateFailedView).setCancelable(false);
                                    FirmwareUpdateFragment.this.failedDialog = builder.create();
                                    FirmwareUpdateFragment.this.failedDialog.show();
                                    ((TextView) firmwareUpdateFailedView.findViewById(R.id.restartAppTextButton)).setOnClickListener(new OnClickListener() {
                                        public void onClick(View view) {
                                            if (FirmwareUpdateFragment.this.failedDialog != null && FirmwareUpdateFragment.this.failedDialog.isShowing()) {
                                                FirmwareUpdateFragment.this.failedDialog.dismiss();
                                            }
                                            if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                                                FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.restart();
                                            }
                                        }
                                    });
                                }
                            }
                        }, 5000);
                    }
                }
            });
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00f3 A[SYNTHETIC, Splitter:B:43:0x00f3] */
        /* JADX WARNING: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void sendTCP(byte[] r14) {
            /*
                r13 = this;
                r6 = 0
                java.net.InetSocketAddress r10 = r13.socketAddress     // Catch:{ Exception -> 0x0234 }
                if (r10 != 0) goto L_0x0011
                java.net.InetSocketAddress r10 = new java.net.InetSocketAddress     // Catch:{ Exception -> 0x0234 }
                java.net.InetAddress r11 = r13.lightsUnicastIP     // Catch:{ Exception -> 0x0234 }
                r12 = 50000(0xc350, float:7.0065E-41)
                r10.<init>(r11, r12)     // Catch:{ Exception -> 0x0234 }
                r13.socketAddress = r10     // Catch:{ Exception -> 0x0234 }
            L_0x0011:
                java.net.Socket r7 = new java.net.Socket     // Catch:{ Exception -> 0x0234 }
                r7.<init>()     // Catch:{ Exception -> 0x0234 }
                r10 = 1000(0x3e8, float:1.401E-42)
                r7.setSoTimeout(r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.net.InetSocketAddress r10 = r13.socketAddress     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 1000(0x3e8, float:1.401E-42)
                r7.connect(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.io.OutputStream r4 = r7.getOutputStream()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r4.write(r14)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r4.flush()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.io.InputStream r3 = r7.getInputStream()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 3
                byte[] r8 = new byte[r10]     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r8 = {0, 0, 0} // fill-array     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r2 = 0
            L_0x0037:
                int r10 = r8.length     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r2 >= r10) goto L_0x0040
                int r5 = r3.read()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r5 >= 0) goto L_0x0075
            L_0x0040:
                byte[] r10 = ackArray     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                boolean r10 = java.util.Arrays.equals(r8, r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 == 0) goto L_0x007b
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11.<init>()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r12 = "ack received "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r12 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r12 = r12.size()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.removeFirst()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 0
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
            L_0x006e:
                if (r7 == 0) goto L_0x0073
                r7.close()     // Catch:{ IOException -> 0x020b }
            L_0x0073:
                r6 = r7
            L_0x0074:
                return
            L_0x0075:
                byte r10 = (byte) r5
                r8[r2] = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r2 = r2 + 1
                goto L_0x0037
            L_0x007b:
                byte[] r10 = verArray     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                boolean r10 = java.util.Arrays.equals(r8, r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 == 0) goto L_0x01d5
                r10 = 8
                byte[] r9 = new byte[r10]     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r2 = 0
            L_0x0088:
                int r10 = r9.length     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r2 >= r10) goto L_0x0091
                int r5 = r3.read()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r5 >= 0) goto L_0x00fb
            L_0x0091:
                int r10 = r13.productId     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 1
                if (r10 != r11) goto L_0x0101
                byte[] r10 = verifyResponseArrayHD     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                boolean r10 = java.util.Arrays.equals(r9, r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 == 0) goto L_0x0101
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11.<init>()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r12 = "received HD verify properly "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r12 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r12 = r12.size()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.removeFirst()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 0
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x00c5:
                r0 = move-exception
                r6 = r7
            L_0x00c7:
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x022e }
                r11.<init>()     // Catch:{ all -> 0x022e }
                java.lang.String r12 = "sendTCP Exception "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x022e }
                java.lang.String r12 = r0.toString()     // Catch:{ all -> 0x022e }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x022e }
                java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x022e }
                android.util.Log.i(r10, r11)     // Catch:{ all -> 0x022e }
                int r10 = r13.tcpTimeoutCount     // Catch:{ all -> 0x022e }
                int r10 = r10 + 1
                r13.tcpTimeoutCount = r10     // Catch:{ all -> 0x022e }
                int r10 = r13.tcpTimeoutCount     // Catch:{ InterruptedException -> 0x020f }
                int r10 = r10 * 1000
                long r10 = (long) r10     // Catch:{ InterruptedException -> 0x020f }
                java.lang.Thread.sleep(r10)     // Catch:{ InterruptedException -> 0x020f }
            L_0x00f1:
                if (r6 == 0) goto L_0x0074
                r6.close()     // Catch:{ IOException -> 0x00f8 }
                goto L_0x0074
            L_0x00f8:
                r10 = move-exception
                goto L_0x0074
            L_0x00fb:
                byte r10 = (byte) r5
                r9[r2] = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r2 = r2 + 1
                goto L_0x0088
            L_0x0101:
                int r10 = r13.productId     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 2
                if (r10 != r11) goto L_0x013e
                byte[] r10 = verifyResponseArray4K     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                boolean r10 = java.util.Arrays.equals(r9, r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 == 0) goto L_0x013e
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11.<init>()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r12 = "received 4K verify properly "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r12 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r12 = r12.size()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.removeFirst()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 0
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x0136:
                r10 = move-exception
                r6 = r7
            L_0x0138:
                if (r6 == 0) goto L_0x013d
                r6.close()     // Catch:{ IOException -> 0x0231 }
            L_0x013d:
                throw r10
            L_0x013e:
                int r10 = r13.productId     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 7
                if (r10 != r11) goto L_0x0173
                byte[] r10 = verifyResponseArraySolo     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                boolean r10 = java.util.Arrays.equals(r9, r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 == 0) goto L_0x0173
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11.<init>()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r12 = "received Solo verify properly "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r12 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r12 = r12.size()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.removeFirst()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 0
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x0173:
                boolean r10 = r13.secondFramesAttempt     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 != 0) goto L_0x01c9
                r10 = 1
                r13.secondFramesAttempt = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.String r11 = "verify response did not match, start second attempt to build and send frames"
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                android.os.Handler r10 = r13.handler     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment$24 r11 = new com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment$24     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11.<init>()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.post(r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 0
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.clear()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r10 = "02"
                byte[] r10 = r13.hexStringToByteArray(r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r13.buildTXFrame(r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r13.processHexFile()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r10 = r13.productId     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 1
                if (r10 != r11) goto L_0x01ad
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                byte[] r11 = r13.verifyRequestFrameHD     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.add(r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x01ad:
                int r10 = r13.productId     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 2
                if (r10 != r11) goto L_0x01bb
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                byte[] r11 = r13.verifyRequestFrame4K     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.add(r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x01bb:
                int r10 = r13.productId     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11 = 7
                if (r10 != r11) goto L_0x006e
                java.util.LinkedList<byte[]> r10 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                byte[] r11 = r13.verifyRequestFrameSolo     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10.add(r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x01c9:
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.String r11 = "verify response did not match, sending frames failed crc twice, canceling"
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r10 = 1
                r13.canceled = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x01d5:
                byte[] r10 = nakArray     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                boolean r10 = java.util.Arrays.equals(r8, r10)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                if (r10 == 0) goto L_0x0203
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                r11.<init>()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r12 = "received nak, timeout++ "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.util.LinkedList<byte[]> r12 = r13.bootloaderFrames     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r12 = r12.size()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                android.util.Log.i(r10, r11)     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r10 = r13.tcpTimeoutCount     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r10 = r10 + 1
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x0203:
                int r10 = r13.tcpTimeoutCount     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                int r10 = r10 + 1
                r13.tcpTimeoutCount = r10     // Catch:{ Exception -> 0x00c5, all -> 0x0136 }
                goto L_0x006e
            L_0x020b:
                r10 = move-exception
                r6 = r7
                goto L_0x0074
            L_0x020f:
                r1 = move-exception
                java.lang.String r10 = "FirmwareUpdateFrag"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x022e }
                r11.<init>()     // Catch:{ all -> 0x022e }
                java.lang.String r12 = "interrupted exception : "
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x022e }
                java.lang.String r12 = r1.toString()     // Catch:{ all -> 0x022e }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x022e }
                java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x022e }
                android.util.Log.i(r10, r11)     // Catch:{ all -> 0x022e }
                goto L_0x00f1
            L_0x022e:
                r10 = move-exception
                goto L_0x0138
            L_0x0231:
                r11 = move-exception
                goto L_0x013d
            L_0x0234:
                r0 = move-exception
                goto L_0x00c7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.FirmwareUpdateFragment.sendTCP(byte[]):void");
        }

        /* access modifiers changed from: private */
        public void sendJTA() {
            Log.i(tag, "sendJTA");
            try {
                if (!this.bootloaderFrames.isEmpty()) {
                    Log.i(tag, "sendJTA but not all bootloader frames sent. canceling");
                    this.canceled = true;
                    return;
                }
                buildTXFrame(hexStringToByteArray("05"));
                Socket socket = new Socket(this.lightsUnicastIP, picTcpPort);
                OutputStream out = socket.getOutputStream();
                out.write((byte[]) this.bootloaderFrames.pop());
                out.flush();
                out.close();
                socket.close();
            } catch (Exception e) {
                Log.i(tag, "sendJTA exception " + e.toString());
            }
        }

        /* access modifiers changed from: private */
        public void processHexFile() {
            String fileName;
            try {
                if (this.productId == 1) {
                    fileName = dreamScreenHdPicFileName;
                } else if (this.productId == 2) {
                    fileName = dreamScreen4KPicFileName;
                } else if (this.productId == 7) {
                    fileName = dreamScreenSoloPicFileName;
                } else {
                    Log.i(tag, "processHexFile bad type, cancelling");
                    return;
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(fileName), "UTF-8"));
                ByteArrayOutputStream command = new ByteArrayOutputStream();
                int lineCounter = 0;
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    lineCounter++;
                    command.write(hexStringToByteArray(line.substring(1)));
                    if (lineCounter >= 11) {
                        programFlash(command.toByteArray());
                        lineCounter = 0;
                        command.reset();
                    }
                }
                if (lineCounter != 0) {
                    programFlash(command.toByteArray());
                }
                command.close();
                bufferedReader.close();
            } catch (Exception e) {
                Log.i(tag, e.toString());
            }
        }

        private void programFlash(byte[] data) {
            ByteArrayOutputStream command = new ByteArrayOutputStream();
            try {
                command.write(3);
                command.write(data);
                buildTXFrame(command.toByteArray());
                command.close();
            } catch (Exception e) {
            }
        }

        /* access modifiers changed from: private */
        public void buildTXFrame(byte[] data) {
            ByteArrayOutputStream dataPlusCrc = new ByteArrayOutputStream();
            try {
                dataPlusCrc.write(data);
                dataPlusCrc.write(picBootloadingCrc(data, data.length));
            } catch (Exception e) {
            }
            byte[] dataCrc = dataPlusCrc.toByteArray();
            ByteArrayOutputStream command = new ByteArrayOutputStream();
            command.write(1);
            for (byte b : dataCrc) {
                if (b == 1 || b == 4 || b == 16) {
                    command.write(16);
                }
                command.write(b);
            }
            command.write(4);
            this.bootloaderFrames.add(command.toByteArray());
            try {
                dataPlusCrc.close();
                command.close();
            } catch (IOException e2) {
            }
        }

        private byte[] picBootloadingCrc(byte[] data, int length1) {
            short j = 0;
            short crc = 0;
            for (int length = length1; length != 0; length--) {
                short crc2 = (short) (crc_table[((short) ((crc >> 12) ^ (data[j] >> 4))) & 15] ^ (crc << 4));
                crc = (short) (crc_table[((short) ((crc2 >> 12) ^ data[j])) & 15] ^ (crc2 << 4));
                j = (short) (j + 1);
            }
            return new byte[]{(byte) (crc & 255), (byte) ((crc >> 8) & 255)};
        }

        /* access modifiers changed from: private */
        public byte[] hexStringToByteArray(String s) {
            int len = s.length();
            byte[] data = new byte[(len / 2)];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
            }
            return data;
        }

        /* access modifiers changed from: private */
        public void showProgressLayout() {
            this.updateText.setText(getContext().getResources().getString(R.string.Updating));
            this.updateText.setVisibility(0);
        }

        /* access modifiers changed from: private */
        public void completelyFinishedUpdating() {
            if (!this.finished) {
                this.finished = true;
                Log.i(tag, "completelyFinishedUpdating");
                if (this.firmwareUpdateFragmentListener == null) {
                    Log.i(tag, "completelyFinishedUpdating, fragment prematurely detached");
                    return;
                }
                this.updateText.setText(getContext().getResources().getString(R.string.Finishing));
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if (FirmwareUpdateFragment.this.firmwareUpdateFragmentListener != null) {
                            FirmwareUpdateFragment.this.firmwareUpdateFragmentListener.restart();
                        }
                    }
                }, 12000);
            }
        }

        public boolean isUpdating() {
            return this.updating;
        }

        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof FirmwareUpdateFragmentListener) {
                this.firmwareUpdateFragmentListener = (FirmwareUpdateFragmentListener) context;
                return;
            }
            throw new RuntimeException(context.toString() + " must implement Listener");
        }

        public void onDetach() {
            super.onDetach();
            this.firmwareUpdateFragmentListener = null;
        }
    }
