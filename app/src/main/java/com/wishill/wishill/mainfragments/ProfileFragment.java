package com.wishill.wishill.mainfragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.CollegeListActivity;
import com.wishill.wishill.activity.HomeActivity;
import com.wishill.wishill.activity.MyFollowersList;
import com.wishill.wishill.activity.NormalUserEditProfileActivity;
import com.wishill.wishill.activity.SchoolDetails;
import com.wishill.wishill.activity.SchoolListActivity;
import com.wishill.wishill.activity.SocialMediaActivity;
import com.wishill.wishill.activity.SubCategoryActivity;
import com.wishill.wishill.activity.partnershipwithwishill.AddPartnerCollegeActivity;
import com.wishill.wishill.activity.partnershipwithwishill.ParnershipOTPVerificationActivity;
import com.wishill.wishill.activity.partnershipwithwishill.PartnerEditProfile;
import com.wishill.wishill.activity.profile.RecivedEnqList;
import com.wishill.wishill.activity.profile.SendEnqListActivity;
import com.wishill.wishill.activity.profile.UserFollowingList;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.getProfile.ProfileAPI;
import com.wishill.wishill.api.recommendedColleges.getProfile.ProfileDetailsData;
import com.wishill.wishill.api.recommendedColleges.getProfile.ProfileResponse;
import com.wishill.wishill.api.recommendedColleges.partnerEnquiriesList.partnerEnquiriesListData;
import com.wishill.wishill.api.recommendedColleges.postpinboard.PostPinBoardAPI;
import com.wishill.wishill.api.recommendedColleges.postpinboard.PostPinBoardResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.CircleTransform;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;


    LinearLayout llLogOut;
    LinearLayout llLogin;
    LinearLayout llPartner;
    SharedPreferences sharedPreferences;

    LinearLayout llProfile;
    LinearLayout llMyInstitution;
    TextView tvMyInstitution;
    LinearLayout llSavedJobs;
    LinearLayout llCreateResume;

    TextView tvName;
    TextView tvLocation;
    TextView tvUserType;

    String userID;
    String userType;

    public static List<partnerEnquiriesListData> enqList;
    DialogProgress dialogProgress;
    ProfileDetailsData profileData;
    String institutionStatus = "";
    String institutionID;


    //partner
    LinearLayout llPartnerHead;
    LinearLayout llPartnerFollowing;
    LinearLayout llPartnerFollowers;
    LinearLayout llPartnerEnq;
    ImageView ivPartnerProfileImage;
    TextView tvPartnerFollowingCount;
    TextView tvPartnerFollowersCount;
    TextView tvPartnerEnqCount;
    LinearLayout llPinBoard;
    LinearLayout llInvitePeople;
    LinearLayout llJobPost;
    LinearLayout llWishillAdd;

    //user
    LinearLayout llUserHead;
    LinearLayout llUserFollowing;
    LinearLayout llUserEnq;
    LinearLayout llDiscoverColleges;
    LinearLayout llNotifications;
    LinearLayout llWishlist;
    ImageView ivUserProfileImage;
    TextView tvUserFollowingCount;
    TextView tvUserEnqCount;
    public Dialog alertDialog;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("wishill", getActivity().MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", "");
        userType = sharedPreferences.getString("userType", "");
        dialogProgress = new DialogProgress(getActivity());
        llPartner = v.findViewById(R.id.ll_partner);
        llLogOut = v.findViewById(R.id.ll_logout);
        llLogOut.setVisibility(View.GONE);
        llLogin = v.findViewById(R.id.ll_login);
        llLogin.setVisibility(View.GONE);

        tvName = v.findViewById(R.id.tv_name);
        tvLocation = v.findViewById(R.id.tv_location);
        tvUserType = v.findViewById(R.id.tv_user_type);

        llProfile = v.findViewById(R.id.ll_edit_profile);
        llMyInstitution = v.findViewById(R.id.ll_my_institution);
        tvMyInstitution = v.findViewById(R.id.tv_my_institution);
        llSavedJobs = v.findViewById(R.id.ll_saved_jobs);
        llCreateResume = v.findViewById(R.id.ll_create_resume);


        llPartnerHead = v.findViewById(R.id.ll_partner_head);
        llPartnerFollowing = v.findViewById(R.id.ll_partner_following);
        llPartnerFollowers = v.findViewById(R.id.ll_partner_followers);
        llPartnerEnq = v.findViewById(R.id.ll_partner_enq);
        ivPartnerProfileImage = v.findViewById(R.id.iv_partner_profile);
        tvPartnerFollowingCount = v.findViewById(R.id.tv_partner_following_count);
        tvPartnerFollowersCount = v.findViewById(R.id.tv_partner_followers_count);
        tvPartnerEnqCount = v.findViewById(R.id.tv_partner_enq_count);
        llPinBoard=v.findViewById(R.id.ll_pin_board);
        llInvitePeople=v.findViewById(R.id.ll_invite_people);
        llJobPost=v.findViewById(R.id.ll_job_post);
        llWishillAdd=v.findViewById(R.id.ll_wishill_ads);
        llDiscoverColleges=v.findViewById(R.id.ll_discover_colleges);
        llNotifications=v.findViewById(R.id.ll_notifications);
        llWishlist=v.findViewById(R.id.ll_wishlist);

        llUserHead = v.findViewById(R.id.ll_user_head);
        llUserFollowing = v.findViewById(R.id.ll_user_following);
        llUserEnq = v.findViewById(R.id.ll_user_enq);
        ivUserProfileImage = v.findViewById(R.id.iv_user_profile);
        tvUserFollowingCount = v.findViewById(R.id.tv_user_following_count);
        tvUserEnqCount = v.findViewById(R.id.tv_user_enq_count);

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

        llPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), SocialMediaActivity.class);
                startActivity(in);
            }
        });
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), NormalUserEditProfileActivity.class);
                startActivity(in);
             /*   if (userType.equals("normal")) {
                    Intent in = new Intent(getActivity(), NormalUserEditProfileActivity.class);
                    startActivity(in);
                }else{
                    Intent in=new Intent(getActivity(), PartnerEditProfile.class);
                    in.putExtra("profileImage",APILinks.IMAGE_LINK + "" + sharedPreferences.getString("profileImage", ""));
                    in.putExtra("name",profileData.getFirstName()+" "+profileData.getLastName());
                    in.putExtra("email",profileData.getUserEmail());
                    in.putExtra("mobile",profileData.getUserMobile());
                    startActivity(in);
                }*/
            }
        });
        ivUserProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), NormalUserEditProfileActivity.class);
                startActivity(in);
            }
        });
        ivPartnerProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), NormalUserEditProfileActivity.class);
                startActivity(in);
               /* Intent in=new Intent(getActivity(), PartnerEditProfile.class);
                in.putExtra("profileImage",APILinks.IMAGE_LINK + "" + sharedPreferences.getString("profileImage", ""));
                in.putExtra("name",profileData.getFirstName()+" "+profileData.getLastName());
                in.putExtra("email",profileData.getUserEmail());
                in.putExtra("mobile",profileData.getUserMobile());
                startActivity(in);*/
            }
        });
        llPartnerFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), UserFollowingList.class);
                startActivity(in);
            }
        });
        llUserFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), UserFollowingList.class);
                startActivity(in);
            }
        });
        llUserEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), SendEnqListActivity.class);
                startActivity(in);
            }
        });
        llPartnerEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), RecivedEnqList.class);
                in.putExtra("institutionId", institutionID);
                in.putExtra("institutionType", sharedPreferences.getString("userTypeId", ""));
                startActivity(in);
            }
        });
        llPartnerFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MyFollowersList.class);
                in.putExtra("institutionId", institutionID);
                in.putExtra("institutionType", sharedPreferences.getString("userTypeId", ""));
                startActivity(in);
            }
        });
        llMyInstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTypeId = sharedPreferences.getString("userTypeId", "");
                if (institutionStatus.equals("Pending")) {
                    if (userTypeId.equals("1")) {
                        successDialog("Add Details", "Thank you for added your institutions with wishill.com.Our backend team verify your details and will contact you within two business days.Any further enquiry feel free contact via mail, contact@wishill.com");
                    } else if (userTypeId.equals("2")) {
                        successDialog("Add Details", "Thank you for added your institutions with wishill.com.Our backend team verify your details and will contact you within two business days.Any further enquiry feel free contact via mail, contact@wishill.com");
                    } else if (userTypeId.equals("3")) {
                        successDialog("Add Details", "Thank you for added your institutions with wishill.com.Our backend team verify your details and will contact you within two business days.Any further enquiry feel free contact via mail, contact@wishill.com");
                    }
                } else if (institutionStatus.equals("Not Added")) {
                    if (userTypeId.equals("1")) {
                        Intent in = new Intent(getActivity(), AddPartnerCollegeActivity.class);
                        in.putExtra("partnerId", userID);
                        in.putExtra("userType", "1");
                        startActivity(in);
                    } else if (userTypeId.equals("2")) {
                        Intent in = new Intent(getActivity(), AddPartnerCollegeActivity.class);
                        in.putExtra("partnerId", userID);
                        in.putExtra("userType", "2");
                        startActivity(in);
                    } else if (userTypeId.equals("3")) {

                    }
                } else if (institutionStatus.equals("Added")) {
                    if (userTypeId.equals("1")) {
                        //go to college details page
                        Intent in = new Intent(getActivity(), CollegeDetailsActivity.class);
                        in.putExtra("collegeID", institutionID);
                        startActivity(in);
                    } else if (userTypeId.equals("2")) {
                        //got to school details page
                        Intent in = new Intent(getActivity(), SchoolDetails.class);
                        in.putExtra("schoolID", institutionID);
                        startActivity(in);
                    } else if (userTypeId.equals("3")) {
                        // got to study tour detail page
                    }
                }

            }
        });

        llSavedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llCreateResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llPinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEnquiry();
            }
        });
        llInvitePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llWishillAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon !!", Toast.LENGTH_SHORT).show();
            }
        });
        llDiscoverColleges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SubCategoryActivity.class);
                intent.putExtra("catID","1");
                startActivity(intent);
            }
        });

        llWishlist.setVisibility(View.GONE);
        llNotifications.setVisibility(View.GONE);
        llInvitePeople.setVisibility(View.GONE);
        return v;
    }


    private void getProfile(String userType) {
        retrofit.create(ProfileAPI.class).post(
                userID,
                userType)
                .enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            profileData = response.body().getProfileData();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", profileData.getFirstName() + " " + profileData.getLastName());
                            editor.putString("city", profileData.getCity());
                            editor.putString("profileImage", response.body().getProfileUrl() + profileData.getUserImage());
                            editor.putString("userEmail", profileData.getUserEmail());
                            editor.putString("userMobile", profileData.getUserMobile());
                            editor.commit();
                            setValues();

                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getString("login", "false").equals("true")) {
            if (userType.equals("normal")) {
                getProfile("0");
            } else {
                String userTypeId = sharedPreferences.getString("userTypeId", "");
                getProfile(userTypeId);
            }
        } else {
            llLogOut.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        }
    }


    public void setValues() {
        /*   HomeActivity.llMenuOne.setVisibility(View.GONE);*/
        llLogin.setVisibility(View.VISIBLE);
        llLogOut.setVisibility(View.GONE);
        if (userType.equals("normal")) {
            llPinBoard.setVisibility(View.GONE);
            // TODO: 15/10/2019 unhide saved jobs and create resume
//            llSavedJobs.setVisibility(View.VISIBLE);
//            llCreateResume.setVisibility(View.VISIBLE);
            llProfile.setVisibility(View.VISIBLE);
            llPartnerHead.setVisibility(View.GONE);
            llUserHead.setVisibility(View.VISIBLE);
            llMyInstitution.setVisibility(View.GONE);
            tvUserType.setText("Student");
            llJobPost.setVisibility(View.GONE);
            llWishillAdd.setVisibility(View.GONE);
            llDiscoverColleges.setVisibility(View.VISIBLE);


            if(profileData.getUserLoginType().equals("Facebook")||profileData.getUserLoginType().equals("Gmail")){
                //user image
                Glide.with(ivUserProfileImage.getContext()).load(profileData.getSocial_userImg())
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(ivUserProfileImage.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar)
                        .into(ivUserProfileImage);
            }else{
                //user image
                Glide.with(ivUserProfileImage.getContext()).load(APILinks.IMAGE_LINK + "" + sharedPreferences.getString("profileImage", ""))
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(ivUserProfileImage.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar)
                        .into(ivUserProfileImage);
            }


            //set counts
            tvUserFollowingCount.setText(profileData.getFollowCount() + "");
            tvUserEnqCount.setText(profileData.getEnquiryCount() + "");

        } else {
            llPinBoard.setVisibility(View.VISIBLE);
            llProfile.setVisibility(View.VISIBLE);
            llPartnerHead.setVisibility(View.VISIBLE);
            llUserHead.setVisibility(View.GONE);
            llMyInstitution.setVisibility(View.VISIBLE);
            llSavedJobs.setVisibility(View.GONE);
            llCreateResume.setVisibility(View.GONE);
            llJobPost.setVisibility(View.VISIBLE);
            llWishillAdd.setVisibility(View.VISIBLE);
            llDiscoverColleges.setVisibility(View.GONE);
            String userTypeId = sharedPreferences.getString("userTypeId", "");
            if (userTypeId.equals("1")) {
                tvUserType.setText("College Partner");
            } else if (userTypeId.equals("2")) {
                tvUserType.setText("School Partner");
            } else if (userTypeId.equals("3")) {
                tvUserType.setText("Study Tour Partner");
            }

            //partner image
            Glide.with(ivPartnerProfileImage.getContext()).load(APILinks.IMAGE_LINK + "" + sharedPreferences.getString("profileImage", ""))
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(ivPartnerProfileImage.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_avatar)
                    .into(ivPartnerProfileImage);


            //institution status
            institutionStatus = profileData.getInstitutionStatus();
            institutionID = profileData.getInstitutionID();
            if (institutionStatus.equals("Pending")) {
                if (userTypeId.equals("1")) {
                    tvMyInstitution.setText("Add College Details");
                } else if (userTypeId.equals("2")) {
                    tvMyInstitution.setText("Add School Details");
                } else if (userTypeId.equals("3")) {
                    tvMyInstitution.setText("Add Study Tour Details");
                }
            } else if (institutionStatus.equals("Added")) {
                if (userTypeId.equals("1")) {
                    tvMyInstitution.setText("My College");
                } else if (userTypeId.equals("2")) {
                    tvMyInstitution.setText("My School");
                } else if (userTypeId.equals("3")) {
                    tvMyInstitution.setText("My Study Tour");
                }
            } else if (institutionStatus.equals("Not Added")) {
                if (userTypeId.equals("1")) {
                    tvMyInstitution.setText("Add College");
                } else if (userTypeId.equals("2")) {
                    tvMyInstitution.setText("Add School");
                } else if (userTypeId.equals("3")) {
                    tvMyInstitution.setText("Add Study Tour");
                }
            } else {
                if (userTypeId.equals("1")) {
                    tvMyInstitution.setText("Add College");
                } else if (userTypeId.equals("2")) {
                    tvMyInstitution.setText("Add School");
                } else if (userTypeId.equals("3")) {
                    tvMyInstitution.setText("Add Study Tour");
                }
            }

            //set counts
            tvPartnerFollowingCount.setText(profileData.getFollowCount() + "");
            tvPartnerFollowersCount.setText(profileData.getReceivedfollowCount() + "");
            tvPartnerEnqCount.setText(profileData.getReceivedenquiryCount() + "");
        }


        //common values
        //name
        if (sharedPreferences.getString("userName", "").equals(" ")) {
            tvName.setText(profileData.getUserMobile());
        } else {
            tvName.setText(sharedPreferences.getString("userName", ""));
        }

        //location
        String city = sharedPreferences.getString("city", "");
        if (!city.equals("")) {
            tvLocation.setVisibility(View.VISIBLE);
            tvLocation.setText(city);
        } else {
            tvLocation.setVisibility(View.GONE);
        }

    }


    private void successDialog(String title, String message) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_success_popup);
        TextView mButtonCancel = (TextView) dialog.findViewById(R.id.btn_ivalid_clent_okay);
        TextView tvSuccessMessage = dialog.findViewById(R.id.tv_success_message);
        tvSuccessMessage.setText(message);
        TextView tvHeading = dialog.findViewById(R.id.tv_heading);
        tvHeading.setText(title);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    //contact
    private void sendEnquiry() {
        alertDialog = new Dialog(getActivity());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.pin_board_design, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_owner);
        final EditText comment = alert_layout.findViewById(R.id.edt_comment);
        TextView submit = alert_layout.findViewById(R.id.tv_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strComment= comment.getText().toString();
                        if(!strComment.equals("")){
                            dialogProgress.show();
                            alertDialog.dismiss();
                            pinBoardAPI(strComment);
                        }else{
                            Toast.makeText(getActivity(),"Type a content",Toast.LENGTH_LONG).show();
                        }

            }
        });
        alertDialog.setContentView(alert_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    private void pinBoardAPI(final String message) {
        dialogProgress.show();
        retrofit.create(PostPinBoardAPI.class).post(sharedPreferences.getString("userTypeId", ""),
                userID,
                message)
                .enqueue(new Callback<PostPinBoardResponse>() {
                    @Override
                    public void onResponse(Call<PostPinBoardResponse> call, Response<PostPinBoardResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostPinBoardResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
}
