package com.wishill.wishill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.SocialMediaLogin.SocialMediaLoginAPI;
import com.wishill.wishill.api.recommendedColleges.SocialMediaLogin.SocialMediaLoginResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SocialMediaActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView tvSkip;
    TextView llMobile,llPartner;

    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 10;
    String sName,sEmail,socialId;
    String profileImage;
    LinearLayout llGoogle;
    LinearLayout llFb;
    CallbackManager callbackManager;
    LoginButton loginButton;

    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        tvSkip=findViewById(R.id.tv_skip);
        llMobile=findViewById(R.id.tv_mobile);
        llPartner=findViewById(R.id.tv_partner);
        llGoogle=findViewById(R.id.ll_google);
        loginButton=findViewById(R.id.login_button);
        llFb=findViewById(R.id.ll_fb);

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(APILinks.API_LINK)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        dialogProgress=new DialogProgress(SocialMediaActivity.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("email",
                "public_profile");
        loginButton.setReadPermissions(permissionNeeds);

        getReference();

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstrun", "false");
                editor.commit();
                Intent toSlider = new Intent(SocialMediaActivity.this, HomeActivity.class);
                toSlider.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toSlider);
                finish();
            }
        });
        llMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstrun", "false");
                editor.commit();
                Intent in=new Intent(SocialMediaActivity.this,MobileNumberActivity.class);
                startActivity(in);
                finish();
            }
        });
        llPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstrun", "false");
                editor.commit();
                Intent in=new Intent(SocialMediaActivity.this,PartnerLoginActivity.class);
                startActivity(in);
                finish();
            }
        });
        llGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        llFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String accessToken = loginResult.getAccessToken().getToken();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            sName = object.getString("name");
                                            sEmail = object.getString("email");
                                            socialId= object.getString("id");
                                            profileImage="https://graph.facebook.com/"+socialId+"/picture?type=large";
                                           // Toast.makeText(SocialMediaActivity.this,sName,Toast.LENGTH_SHORT).show();
                                            socialMediaLogin("Facebook");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e("Exception", e + "");
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.e("LoginActivity", exception.getCause().toString());
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            socialId= account.getId();
            sName = account.getDisplayName();
            sEmail = account.getEmail();
            profileImage=account.getPhotoUrl().toString();
            socialMediaLogin("Gmail");
        } catch (Exception e) {
            //Toast.makeText(SocialMediaActivity.this, e + "", Toast.LENGTH_SHORT).show();
            Log.e("Exception", e + "");
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                socialId= account.getId();
                sName = account.getDisplayName();
                sEmail = account.getEmail();
                profileImage="";
                socialMediaLogin("Gmail");
            }catch (Exception x){
                Toast.makeText(SocialMediaActivity.this, e + "", Toast.LENGTH_SHORT).show();
                Log.e("Exception", e + "");
            }
        }
    }


    private void socialMediaLogin(String type){
        dialogProgress.show();
        retrofit.create(SocialMediaLoginAPI.class).post(socialId,
                type,
                sEmail,
                sName,
                profileImage,
                referrer,
                referredInstitute,
                referredInstituteType)
                .enqueue(new Callback<SocialMediaLoginResponse>() {
                    @Override
                    public void onResponse(Call<SocialMediaLoginResponse> call, Response<SocialMediaLoginResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status=response.body().getSuccess();
                            if(status.equals("1")){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login", "true");
                                editor.putString("userType", "normal");
                                editor.putString("userId", response.body().getData().getUserID());
                                editor.putString("userTypeId", "0");
                                editor.commit();
                                Intent in=new Intent(SocialMediaActivity.this,HomeActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                finish();
                            }else{
                                Toast.makeText(SocialMediaActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SocialMediaActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialMediaLoginResponse> call, Throwable t) {
                        Toast.makeText(SocialMediaActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginManager.getInstance().logOut();
        sName = "";
        sEmail = "";

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    InstallReferrerClient mReferrerClient;
    public static String referrer = "";
    public static String referredInstitute = "";
    public static String referredInstituteType = "";
    private void getReference(){
        mReferrerClient = InstallReferrerClient.newBuilder(SocialMediaActivity.this).build();
        mReferrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established
                        try {
                            ReferrerDetails response = mReferrerClient.getInstallReferrer();

//                            response = mReferrerClient.getInstallReferrer();
                            String referrerString = response.getInstallReferrer();
                            Log.d("Reference",referrerString);

                            String[] referrerParts = referrerString.split("&");
                            switch (referrerParts.length){
                                case 1:
                                    referrer = referrerParts[0];
                                    break;
                                case 2:
                                    referrer = referrerParts[0];
                                    referredInstitute = referrerParts[1];
                                    break;
                                case 3:
                                    referrer = referrerParts[0];
                                    referredInstitute = referrerParts[1];
                                    referredInstituteType = referrerParts[2];
                                    break;
                                    default:
                                        break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mReferrerClient.endConnection();
                        break;
                    case
                            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app
                        break;
                    case
                            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection could not be established
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    private String getData(String key, String[] allData) {
        for (String selected : allData)
            if (selected.contains(key)) {
                return selected.split("=")[1];
            }
        return "";
    }

}
