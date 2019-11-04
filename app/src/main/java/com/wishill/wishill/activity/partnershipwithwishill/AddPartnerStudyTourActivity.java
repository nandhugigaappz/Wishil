package com.wishill.wishill.activity.partnershipwithwishill;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListAPI;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListData;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListResponse;
import com.wishill.wishill.api.recommendedColleges.getStudyTourCat.StudyTourCatListAPI;
import com.wishill.wishill.api.recommendedColleges.getStudyTourCat.StudyTourCatListData;
import com.wishill.wishill.api.recommendedColleges.getStudyTourCat.StudyTourCatListResponse;
import com.wishill.wishill.dialogBoxes.ListDialogeBox;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPartnerStudyTourActivity extends AppCompatActivity {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;


    DialogProgress dialogProgress;
    String partnerId;

    List<CountryListData> countryList;
    List<String> countryNameList;

    List<StudyTourCatListData> catList;
    List<String> catNameList;


    TextView btnSubmit;
    EditText edtPackageName;
    TextView tvCategory,tvCountry;
    EditText edtDestination;
    EditText edtPackageRate;
    EditText edtNumberOfDay;
    EditText edtNumberOfNights;
    TextView tvStartDate;
    TextView tvEndDate;

    String packageName;
    String destination;
    String packageRate;
    String numberOfDays;
    String numberOfNights;
    String startDate="";
    String endDate="";


    public  static  int selectedPosition=100;
    String countryName="";
    String catName;
    String catId;

    DatePickerDialog dpd;
    private Calendar calendar;
    private int year, month, day;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partner_study_tour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(AddPartnerStudyTourActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        partnerId=getIntent().getStringExtra("partnerId");

        btnSubmit=findViewById(R.id.btn_submit);
        edtPackageName=findViewById(R.id.edt_package_name);
        tvCategory=findViewById(R.id.tv_category);
        tvCountry=findViewById(R.id.tv_country);
        edtDestination=findViewById(R.id.edt_destination);
        edtPackageRate=findViewById(R.id.edt_package_rate);
        edtNumberOfDay=findViewById(R.id.edt_no_day);
        edtNumberOfNights=findViewById(R.id.edt_no_nights);
        tvStartDate=findViewById(R.id.tv_start_date);
        tvEndDate=findViewById(R.id.tv_end_date);

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
        getCountryList();
        getCatList();
        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(catList!=null){
                    selectedPosition = 100;
                    ListDialogeBox dialogBox = new ListDialogeBox(AddPartnerStudyTourActivity.this, catNameList, "Select Category", "addStudyTour");
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBox.show();
                    dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (selectedPosition != 100) {
                                catName = catList.get(selectedPosition).getPackcategoryname();
                                catId = catList.get(selectedPosition).getCategoryId();
                                tvCategory.setText(catName);
                            }
                        }
                    });
                }
            }
        });
        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countryList!=null){
                    selectedPosition = 100;
                    ListDialogeBox dialogBox = new ListDialogeBox(AddPartnerStudyTourActivity.this, countryNameList, "Select Country", "addStudyTour");
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBox.show();
                    dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (selectedPosition != 100) {
                                countryName = countryList.get(selectedPosition).getCountry();
                                tvCountry.setText(countryName);
                            }
                        }
                    });
                }

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageName=edtPackageName.getText().toString().trim();
                destination=edtDestination.getText().toString().trim();
                packageRate=edtPackageRate.getText().toString().trim();
                numberOfDays=edtNumberOfDay.getText().toString().trim();
                numberOfNights=edtNumberOfNights.getText().toString().trim();
                if(validatePackageName()){
                    if(validateCategory()){
                       if(validateCountry()){
                           if(validateDestination()){
                               if(validatePackageRate()){
                                   if(validateNumberOfDays()){
                                       if(validateNumberOfNights()){
                                           if(!startDate.equals("")){
                                               if(!endDate.equals("")){
                                                    if(compareDate()){
                                                        //postValues();
                                                    }else{
                                                        Toast.makeText(AddPartnerStudyTourActivity.this,"Select valid start date and end date",Toast.LENGTH_SHORT).show();
                                                    }
                                               }else{
                                                   Toast.makeText(AddPartnerStudyTourActivity.this,"Select end date.",Toast.LENGTH_SHORT).show();
                                               }
                                           }else{
                                               Toast.makeText(AddPartnerStudyTourActivity.this,"Select start date.",Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   }
                               }
                           }
                       }
                    }
                }
            }
        });
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Start";
                showDialogBox(999);
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="End";
                showDialogBox(999);
            }
        });
    }


    private boolean validatePackageName() {
        if (packageName.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Enter package name.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateCategory() {
        if (catName.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Select a category", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCountry() {
        if (countryName.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Select a country", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateDestination() {
        if (destination.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Enter Destination", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePackageRate() {
        if (packageRate.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Enter package rate", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateNumberOfDays() {
        if (numberOfDays.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Enter number of days", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateNumberOfNights() {
        if (numberOfNights.equals("")) {
            Toast.makeText(AddPartnerStudyTourActivity.this, "Enter number of nights", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public void showDialogBox(int x) {
        if (x == 999) {
            dpd = new DatePickerDialog(AddPartnerStudyTourActivity.this, myDateListener, year, month, day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();
        }
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            if (arg0.isShown())
                showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        String dayy = day + "";
        String monthh = month + "";
        if (dayy.length() == 1) {
            dayy = "0" + dayy;
        }
        if (monthh.length() == 1) {
            monthh = "0" + monthh;
        }
        if (type.equals("Start")) {
            startDate = year + "-" + monthh + "-" + dayy;
            tvStartDate.setText(new StringBuilder().append(dayy).append("-")
                    .append(monthh).append("-").append(year));
        } else if (type.equals("End")) {
            endDate = year + "-" + monthh + "-" + dayy;
            tvEndDate.setText(new StringBuilder().append(dayy).append("-")
                    .append(monthh).append("-").append(year));
        }
    }

    public boolean compareDate() {
        boolean value = false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = formatter.parse(startDate);
            Date date2 = formatter.parse(endDate);
            if (date1.compareTo(date2) < 0) {
                value = true;
            } else {
                value = false;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return value;
    }

 /*   private void postValues() {
        dialogProgress.show();
        retrofit.create(AddPartnerCollegeAPI.class).postStudyTour(
               "",
                partnerId,
                catId,
                packageName,
                destination,
                countryName,
                packageRate,
                numberOfDays,
                numberOfNights,
                startDate,
                endDate
        )
                .enqueue(new Callback<AddPartnerCollegeResponse>() {
                    @Override
                    public void onResponse(Call<AddPartnerCollegeResponse> call, Response<AddPartnerCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status = response.body().getSuccess();
                            String message = response.body().getMessage();
                            if(status.equals("1")){
                                finish();
                                Toast.makeText(AddPartnerStudyTourActivity.this,message,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddPartnerStudyTourActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddPartnerCollegeResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
*/

    private void getCountryList() {
        retrofit.create(CountryListAPI.class).post("")
                .enqueue(new Callback<CountryListResponse>() {
                    @Override
                    public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                        if (response.isSuccessful()) {
                            countryList=response.body().getCatList();
                            countryNameList = new ArrayList<>();
                            for (int i = 0; i < countryList.size(); i++) {
                                countryNameList.add(countryList.get(i).getCountry());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryListResponse> call, Throwable t) {

                    }
                });
    }



    private void getCatList() {
        retrofit.create(StudyTourCatListAPI.class).post("")
                .enqueue(new Callback<StudyTourCatListResponse>() {
                    @Override
                    public void onResponse(Call<StudyTourCatListResponse> call, Response<StudyTourCatListResponse> response) {
                        if (response.isSuccessful()) {
                            catList=response.body().getCatList();
                            catNameList=new ArrayList<>();
                            for (int i = 0; i < catList.size(); i++) {
                                catNameList.add(catList.get(i).getPackcategoryname());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StudyTourCatListResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
