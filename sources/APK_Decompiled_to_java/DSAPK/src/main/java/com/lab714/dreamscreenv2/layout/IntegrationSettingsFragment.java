package com.lab714.dreamscreenv2.layout;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lab714.dreamscreenv2.AwsConfig;
import com.lab714.dreamscreenv2.R;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class IntegrationSettingsFragment extends Fragment {
    private static final String dsv2Prefs = "dsv2Prefs";
    private static final String tag = "IntegrationSettingsFrag";
    private ImageView accountImage;
    private TextView accountImageText;
    private LinearLayout accountLoginLayout;
    private OnClickListener alexaClickedListener;
    private View alexaDivider;
    private LinearLayout alexaLayout;
    /* access modifiers changed from: private */
    public String cachedIdToken;
    /* access modifiers changed from: private */
    public Auth cognitoAuth;
    /* access modifiers changed from: private */
    public String emailAddress;
    private TextView emailText;
    private OnClickListener googleClickedListener;
    private View googleDivider;
    private LinearLayout googleLayout;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public IntegrationSettingsFragmentListener integrationSettingsFragmentListener;
    /* access modifiers changed from: private */
    public AlertDialog lambdaWaitingDialog;
    /* access modifiers changed from: private */
    public boolean linkButtonTapped = false;
    /* access modifiers changed from: private */
    public AlertDialog promptDeleteDialog;

    private class AuthHandlerCallback implements AuthHandler {
        private AuthHandlerCallback() {
        }

        public void onSuccess(AuthUserSession authUserSession) {
            Log.i(IntegrationSettingsFragment.tag, "cognito userpool username " + authUserSession.getUsername());
            IntegrationSettingsFragment.this.cachedIdToken = authUserSession.getIdToken().getJWTToken();
            new CreateAwsThingViaLambda(IntegrationSettingsFragment.this.cachedIdToken).execute(new Void[0]);
        }

        public void onSignout() {
            Log.i(IntegrationSettingsFragment.tag, "authHandlerCalback onSignout");
        }

        public void onFailure(Exception e) {
            Log.i(IntegrationSettingsFragment.tag, "authHandlerCalback onFailure " + e.toString());
            if (e instanceof ActivityNotFoundException) {
                Toast.makeText(IntegrationSettingsFragment.this.getContext(), "Google Chrome could not be launched", 1).show();
            }
        }
    }

    private class CreateAwsThingViaLambda extends AsyncTask<Void, Void, Void> {
        private final String idToken;

        public CreateAwsThingViaLambda(String idToken2) {
            this.idToken = idToken2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            if (IntegrationSettingsFragment.this.lambdaWaitingDialog == null || !IntegrationSettingsFragment.this.lambdaWaitingDialog.isShowing()) {
                if (IntegrationSettingsFragment.this.lambdaWaitingDialog == null) {
                    View lambdaWaitingView = IntegrationSettingsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.lambda_waiting_dialog, null);
                    ((TextView) lambdaWaitingView.findViewById(R.id.subText)).setText(IntegrationSettingsFragment.this.getString(R.string.ModifyingAccount));
                    Builder alertDialogBuilder = new Builder(IntegrationSettingsFragment.this.getActivity());
                    alertDialogBuilder.setView(lambdaWaitingView).setCancelable(false);
                    IntegrationSettingsFragment.this.lambdaWaitingDialog = alertDialogBuilder.create();
                }
                IntegrationSettingsFragment.this.lambdaWaitingDialog.show();
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            IntegrationSettingsFragment.this.linkButtonTapped = false;
            if (IntegrationSettingsFragment.this.lambdaWaitingDialog != null && IntegrationSettingsFragment.this.lambdaWaitingDialog.isShowing()) {
                IntegrationSettingsFragment.this.lambdaWaitingDialog.dismiss();
            }
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voids) {
            try {
                Log.i(IntegrationSettingsFragment.tag, "CreateAwsThingViaLambda, doInBackground");
                CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(IntegrationSettingsFragment.this.getContext(), AwsConfig.IDENTITY_POOL_ID, AwsConfig.AWS_REGION);
                cognitoCachingCredentialsProvider.clear();
                HashMap hashMap = new HashMap();
                hashMap.put("cognito-idp.us-east-1.amazonaws.com/us-east-1_FWZ89EFTn", this.idToken);
                cognitoCachingCredentialsProvider.setLogins(hashMap);
                cognitoCachingCredentialsProvider.refresh();
                cognitoCachingCredentialsProvider.getIdentityId();
                Log.i(IntegrationSettingsFragment.tag, "cognitoCachingCredentialsProvider.getIdentityId() " + cognitoCachingCredentialsProvider.getIdentityId());
                AWSLambdaClient awsLambdaClient = new AWSLambdaClient((AWSCredentialsProvider) cognitoCachingCredentialsProvider);
                InvokeRequest invokeRequest = new InvokeRequest();
                invokeRequest.setFunctionName("onCertificateCreated");
                JsonObject jsonPayload = new JsonObject();
                jsonPayload.addProperty(ClientConstants.TOKEN_TYPE_ID, this.idToken);
                invokeRequest.setPayload(ByteBuffer.wrap(jsonPayload.toString().getBytes("UTF-8")));
                invokeRequest.setInvocationType(InvocationType.RequestResponse);
                JsonObject jsonResponse = null;
                ByteBuffer byteBuffer = awsLambdaClient.invoke(invokeRequest).getPayload();
                if (byteBuffer != null) {
                    try {
                        String str = new String(byteBuffer.array(), "UTF-8");
                        jsonResponse = new JsonParser().parse(str).getAsJsonObject();
                    } catch (UnsupportedOperationException e) {
                    }
                }
                int statusCode = -1;
                JsonObject jsonBody = null;
                if (jsonResponse != null) {
                    if (jsonResponse.has("statusCode")) {
                        statusCode = jsonResponse.get("statusCode").getAsInt();
                        Log.i(IntegrationSettingsFragment.tag, "jsonResponse, statusCode " + statusCode);
                        jsonBody = jsonResponse.get("body").getAsJsonObject();
                    }
                }
                if (statusCode == 201) {
                    IntegrationSettingsFragment.this.emailAddress = jsonBody.get("email").getAsString();
                    String thingName = jsonBody.get("thingName").getAsString();
                    final String certificate = jsonBody.get("certificate").getAsString();
                    String privateKey = jsonBody.get("privateKey").getAsString();
                    if (IntegrationSettingsFragment.this.integrationSettingsFragmentListener != null) {
                        IntegrationSettingsFragment.this.integrationSettingsFragmentListener.setEmailAddress(IntegrationSettingsFragment.this.emailAddress);
                    }
                    Handler access$800 = IntegrationSettingsFragment.this.handler;
                    final String str2 = thingName;
                    AnonymousClass1 r0 = new Runnable() {
                        public void run() {
                            if (IntegrationSettingsFragment.this.integrationSettingsFragmentListener != null) {
                                IntegrationSettingsFragment.this.integrationSettingsFragmentListener.setThingName(str2);
                            }
                        }
                    };
                    access$800.postDelayed(r0, 200);
                    Handler access$8002 = IntegrationSettingsFragment.this.handler;
                    final String str3 = privateKey;
                    AnonymousClass2 r02 = new Runnable() {
                        public void run() {
                            if (IntegrationSettingsFragment.this.integrationSettingsFragmentListener != null) {
                                IntegrationSettingsFragment.this.integrationSettingsFragmentListener.sendCertificates(certificate, str3);
                            }
                        }
                    };
                    access$8002.postDelayed(r02, 400);
                    Handler access$8003 = IntegrationSettingsFragment.this.handler;
                    AnonymousClass3 r03 = new Runnable() {
                        public void run() {
                            IntegrationSettingsFragment.this.connectLinkedSuccessfully();
                        }
                    };
                    access$8003.post(r03);
                } else if (statusCode == 500) {
                    final String errorMessage = jsonBody.get("errorMessage").getAsString();
                    JsonElement oldThingNameElement = jsonBody.get("oldThingName");
                    String oldThingName = oldThingNameElement != null ? oldThingNameElement.getAsString() : null;
                    Handler access$8004 = IntegrationSettingsFragment.this.handler;
                    final String str4 = oldThingName;
                    AnonymousClass4 r04 = new Runnable() {
                        public void run() {
                            IntegrationSettingsFragment.this.connectLinkingFailed(errorMessage, str4);
                        }
                    };
                    access$8004.post(r04);
                } else {
                    Log.i(IntegrationSettingsFragment.tag, "error, no lambda response");
                    Handler access$8005 = IntegrationSettingsFragment.this.handler;
                    AnonymousClass5 r05 = new Runnable() {
                        public void run() {
                            Toast.makeText(IntegrationSettingsFragment.this.getContext(), "Failed to receive response", 1).show();
                        }
                    };
                    access$8005.post(r05);
                }
            } catch (Exception e2) {
                Log.i(IntegrationSettingsFragment.tag, "CreateAwsThingViaLambda failed : " + e2.toString());
                Handler access$8006 = IntegrationSettingsFragment.this.handler;
                AnonymousClass6 r06 = new Runnable() {
                    public void run() {
                        Toast.makeText(IntegrationSettingsFragment.this.getContext(), "Failed to connect", 1).show();
                    }
                };
                access$8006.post(r06);
            }
            return null;
        }
    }

    private class DeleteAwsThingViaLambda extends AsyncTask<Void, Void, Void> {
        private final String idToken;
        private final String oldThingName;
        /* access modifiers changed from: private */
        public int responseStatusCode = -1;

        public DeleteAwsThingViaLambda(String idToken2, String oldThingName2) {
            this.idToken = idToken2;
            this.oldThingName = oldThingName2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            if (IntegrationSettingsFragment.this.lambdaWaitingDialog == null || !IntegrationSettingsFragment.this.lambdaWaitingDialog.isShowing()) {
                if (IntegrationSettingsFragment.this.lambdaWaitingDialog == null) {
                    View lambdaWaitingView = IntegrationSettingsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.lambda_waiting_dialog, null);
                    ((TextView) lambdaWaitingView.findViewById(R.id.subText)).setText(IntegrationSettingsFragment.this.getString(R.string.ModifyingAccount));
                    Builder alertDialogBuilder = new Builder(IntegrationSettingsFragment.this.getActivity());
                    alertDialogBuilder.setView(lambdaWaitingView).setCancelable(false);
                    IntegrationSettingsFragment.this.lambdaWaitingDialog = alertDialogBuilder.create();
                }
                IntegrationSettingsFragment.this.lambdaWaitingDialog.show();
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            IntegrationSettingsFragment.this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (IntegrationSettingsFragment.this.lambdaWaitingDialog != null && IntegrationSettingsFragment.this.lambdaWaitingDialog.isShowing()) {
                        IntegrationSettingsFragment.this.lambdaWaitingDialog.dismiss();
                    }
                    if (DeleteAwsThingViaLambda.this.responseStatusCode == 200) {
                        Log.i(IntegrationSettingsFragment.tag, "DeleteAwsThingViaLambda onPostExecute, invoking CreateAwsThingViaLambda");
                        new CreateAwsThingViaLambda(IntegrationSettingsFragment.this.cachedIdToken).execute(new Void[0]);
                    }
                }
            }, 3000);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voids) {
            try {
                Log.i(IntegrationSettingsFragment.tag, "DeleteAwsThingViaLambda, doInBackground");
                CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(IntegrationSettingsFragment.this.getContext(), AwsConfig.IDENTITY_POOL_ID, AwsConfig.AWS_REGION);
                cognitoCachingCredentialsProvider.clear();
                Map<String, String> logins = new HashMap<>();
                logins.put("cognito-idp.us-east-1.amazonaws.com/us-east-1_FWZ89EFTn", this.idToken);
                cognitoCachingCredentialsProvider.setLogins(logins);
                cognitoCachingCredentialsProvider.refresh();
                cognitoCachingCredentialsProvider.getIdentityId();
                Log.i(IntegrationSettingsFragment.tag, "cognitoCachingCredentialsProvider.getIdentityId() " + cognitoCachingCredentialsProvider.getIdentityId());
                AWSLambdaClient awsLambdaClient = new AWSLambdaClient((AWSCredentialsProvider) cognitoCachingCredentialsProvider);
                InvokeRequest invokeRequest = new InvokeRequest();
                invokeRequest.setFunctionName("removeBackendResource");
                JsonObject jsonPayload = new JsonObject();
                jsonPayload.addProperty(ClientConstants.TOKEN_TYPE_ID, this.idToken);
                jsonPayload.addProperty("thingName", this.oldThingName);
                invokeRequest.setPayload(ByteBuffer.wrap(jsonPayload.toString().getBytes("UTF-8")));
                invokeRequest.setInvocationType(InvocationType.RequestResponse);
                JsonObject jsonResponse = null;
                ByteBuffer byteBuffer = awsLambdaClient.invoke(invokeRequest).getPayload();
                if (byteBuffer != null) {
                    try {
                        jsonResponse = new JsonParser().parse(new String(byteBuffer.array(), "UTF-8")).getAsJsonObject();
                    } catch (UnsupportedOperationException e) {
                    }
                }
                this.responseStatusCode = -1;
                if (jsonResponse == null || !jsonResponse.has("statusCode")) {
                    Log.i(IntegrationSettingsFragment.tag, "failed to get responseCode");
                } else {
                    this.responseStatusCode = jsonResponse.get("statusCode").getAsInt();
                    Log.i(IntegrationSettingsFragment.tag, "jsonResponse, statusCode " + this.responseStatusCode);
                }
            } catch (Exception e2) {
                Log.i(IntegrationSettingsFragment.tag, "DeleteAwsThingViaLambda failed : " + e2.toString());
            }
            return null;
        }
    }

    public interface IntegrationSettingsFragmentListener {
        String getEmailAddress();

        String getThingName();

        void sendCertificates(String str, String str2);

        void setEmailAddress(String str);

        void setThingName(String str);
    }

    public static IntegrationSettingsFragment newInstance() {
        return new IntegrationSettingsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.emailAddress = this.integrationSettingsFragmentListener.getEmailAddress().trim();
        initCognitoAuth();
        Log.i(tag, "emailAddress " + this.emailAddress);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_integration_settings, container, false);
        this.accountLoginLayout = (LinearLayout) view.findViewById(R.id.accountLoginLayout);
        this.accountImage = (ImageView) view.findViewById(R.id.accountImage);
        this.accountImageText = (TextView) view.findViewById(R.id.accountImageText);
        this.emailText = (TextView) view.findViewById(R.id.emailText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        ((TextView) view.findViewById(R.id.headerText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.accountText1)).setTypeface(font);
        ((TextView) view.findViewById(R.id.accountText2)).setTypeface(font);
        this.accountImageText.setTypeface(font);
        this.emailText.setTypeface(font);
        ((TextView) view.findViewById(R.id.alexaText1)).setTypeface(font);
        ((TextView) view.findViewById(R.id.alexaText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.alexaText3)).setTypeface(font);
        ((TextView) view.findViewById(R.id.googleText1)).setTypeface(font);
        ((TextView) view.findViewById(R.id.googleText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.googleText3)).setTypeface(font);
        this.alexaDivider = view.findViewById(R.id.alexaDivider);
        this.googleDivider = view.findViewById(R.id.googleDivider);
        this.alexaLayout = (LinearLayout) view.findViewById(R.id.alexaLayout);
        this.googleLayout = (LinearLayout) view.findViewById(R.id.googleLayout);
        this.alexaClickedListener = new OnClickListener() {
            public void onClick(View view) {
                Log.i(IntegrationSettingsFragment.tag, "alexaLayout tapped");
                Intent launchIntent = IntegrationSettingsFragment.this.getActivity().getPackageManager().getLaunchIntentForPackage("com.amazon.dee.app");
                if (launchIntent != null) {
                    IntegrationSettingsFragment.this.startActivity(launchIntent);
                }
            }
        };
        this.googleClickedListener = new OnClickListener() {
            public void onClick(View view) {
                Log.i(IntegrationSettingsFragment.tag, "googleLayout tapped");
                Intent launchIntent = IntegrationSettingsFragment.this.getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.googleassistant");
                if (launchIntent != null) {
                    IntegrationSettingsFragment.this.startActivity(launchIntent);
                    return;
                }
                Intent launchIntent2 = IntegrationSettingsFragment.this.getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.chromecast.app");
                if (launchIntent2 != null) {
                    IntegrationSettingsFragment.this.startActivity(launchIntent2);
                }
            }
        };
        if (isValidEmail(this.emailAddress)) {
            this.accountImageText.setText(getString(R.string.ConnectLinked));
            this.emailText.setText(this.emailAddress);
            this.accountImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.checkHighlighted));
            setAlexaGoogleLayoutsEnabled(true);
        } else {
            this.accountImageText.setText(getString(R.string.LinkThisConnect));
            this.emailText.setText("");
            this.accountImage.setColorFilter(-11711155);
            this.accountLoginLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!IntegrationSettingsFragment.this.linkButtonTapped) {
                        IntegrationSettingsFragment.this.linkButtonTapped = true;
                        Log.i(IntegrationSettingsFragment.tag, "accountLoginLayout clicked, invoking cognitoAuth.getSession()");
                        IntegrationSettingsFragment.this.cognitoAuth.getSession();
                    }
                }
            });
            setAlexaGoogleLayoutsEnabled(false);
        }
        return view;
    }

    private void setAlexaGoogleLayoutsEnabled(boolean enabled) {
        if (enabled) {
            this.alexaLayout.setOnClickListener(this.alexaClickedListener);
            this.googleLayout.setOnClickListener(this.googleClickedListener);
            this.alexaLayout.setAlpha(1.0f);
            this.alexaDivider.setAlpha(1.0f);
            this.googleLayout.setAlpha(1.0f);
            this.googleDivider.setAlpha(1.0f);
            return;
        }
        this.alexaLayout.setOnClickListener(null);
        this.googleLayout.setOnClickListener(null);
        this.alexaLayout.setAlpha(0.3f);
        this.alexaDivider.setAlpha(0.3f);
        this.googleLayout.setAlpha(0.3f);
        this.googleDivider.setAlpha(0.3f);
    }

    /* access modifiers changed from: private */
    public void connectLinkedSuccessfully() {
        Log.i(tag, "connectLinkedSuccessfully");
        this.accountImageText.setText(getString(R.string.ConnectLinked));
        this.emailText.setText(this.emailAddress);
        this.accountImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.checkHighlighted));
        this.accountLoginLayout.setOnClickListener(null);
        setAlexaGoogleLayoutsEnabled(true);
    }

    /* access modifiers changed from: private */
    public void connectLinkingFailed(String errorMessage, @Nullable final String oldThingName) {
        Log.i(tag, "connectLinkingFailed, not handled" + errorMessage);
        if (!"maxThings".equals(errorMessage) || oldThingName == null) {
            Toast.makeText(getContext(), "Account Linking Failed", 0).show();
            return;
        }
        View promptDeleteView = getActivity().getLayoutInflater().inflate(R.layout.lambda_prompt_delete_dialog, null);
        promptDeleteView.findViewById(R.id.continueTextButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IntegrationSettingsFragment.this.promptDeleteDialog.dismiss();
                new DeleteAwsThingViaLambda(IntegrationSettingsFragment.this.cachedIdToken, oldThingName).execute(new Void[0]);
            }
        });
        promptDeleteView.findViewById(R.id.cancelTextButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IntegrationSettingsFragment.this.promptDeleteDialog.dismiss();
            }
        });
        Builder alertDialogBuilder = new Builder(getActivity());
        alertDialogBuilder.setView(promptDeleteView).setCancelable(false);
        this.promptDeleteDialog = alertDialogBuilder.create();
        this.promptDeleteDialog.show();
    }

    public void onResume() {
        super.onResume();
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

    private void initCognitoAuth() {
        this.cognitoAuth = new Auth.Builder().setAppClientId(AwsConfig.USERPOOL_ANDROID_APP_CLIENT_ID).setAppCognitoWebDomain(AwsConfig.COGNITO_DOMAIN_NAME).setApplicationContext(getContext()).setAuthHandler(new AuthHandlerCallback()).setSignInRedirect(getString(R.string.app_signin_redirect)).setSignOutRedirect(getString(R.string.app_signout_redirect)).build();
    }

    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IntegrationSettingsFragmentListener) {
            this.integrationSettingsFragmentListener = (IntegrationSettingsFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement IntegrationSettingsFragmentListener");
    }

    public void onDetach() {
        super.onDetach();
        this.integrationSettingsFragmentListener = null;
    }
}
