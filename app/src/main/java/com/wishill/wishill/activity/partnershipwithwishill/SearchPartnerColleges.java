package com.wishill.wishill.activity.partnershipwithwishill;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.SearchPaertnerCollegeListAdapter;
import com.wishill.wishill.api.recommendedColleges.assignpartnercollege.AssignPartnerCollegeAPI;
import com.wishill.wishill.api.recommendedColleges.assignpartnercollege.AssignPartnerCollegeResponse;
import com.wishill.wishill.api.recommendedColleges.searchpartnercolleges.SearchListPartnerCollegeData;
import com.wishill.wishill.api.recommendedColleges.searchpartnercolleges.SearchListPartnerCollegeResponse;
import com.wishill.wishill.api.recommendedColleges.searchpartnercolleges.SearchPartnerCollegeListAPI;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPartnerColleges extends AppCompatActivity {
    RecyclerView rvList;
    ProgressBar progress;
    EditText edtSearch;
    ImageView ivBack;
    ImageView ivSearch;
    LinearLayoutManager mLayoutManager;
    TextView tvNoItem;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    List<SearchListPartnerCollegeData> searchList;
    SearchPaertnerCollegeListAdapter searchListAdapter;

    String partnerId;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_partner_colleges);

        rvList = findViewById(R.id.rv_movie_list);
        progress =  findViewById(R.id.progress);
        edtSearch=findViewById(R.id.edt_search);
        ivBack=findViewById(R.id.iv_back);
        ivSearch=findViewById(R.id.iv_search);
        mLayoutManager = new LinearLayoutManager(SearchPartnerColleges.this);
        rvList.setLayoutManager(mLayoutManager);
        dialogProgress=new DialogProgress(SearchPartnerColleges.this);
        tvNoItem=findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        partnerId=getIntent().getStringExtra("partnerId");
        userType=getIntent().getStringExtra("userType");

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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value=edtSearch.getText().toString().trim();
                if(!value.equals("")){
                    closeKeyboard();
                    getSearchList(value);
                    rvList.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    tvNoItem.setVisibility(View.GONE);
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("Value",charSequence.toString());
                getSearchList(charSequence.toString());
                rvList.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tvNoItem.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String value=edtSearch.getText().toString().trim();
                    if(!value.equals("")){
                        closeKeyboard();
                        getSearchList(value);
                        rvList.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        tvNoItem.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }

    private void getSearchList(String value) {
        retrofit.create(SearchPartnerCollegeListAPI.class).post(value,userType)
                .enqueue(new Callback<SearchListPartnerCollegeResponse>() {
                    @Override
                    public void onResponse(Call<SearchListPartnerCollegeResponse> call, final Response<SearchListPartnerCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                searchList=response.body().getSearchList();
                                if(searchList!=null){
                                    rvList.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);
                                    searchListAdapter=new SearchPaertnerCollegeListAdapter(searchList, SearchPartnerColleges.this, new SearchPaertnerCollegeListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            String itemID=searchList.get(position).getItemID();
                                            assignCollege(itemID);

                                        }
                                    });
                                    rvList.setAdapter(searchListAdapter);
                                }

                            }else{
                                rvList.setVisibility(View.GONE);
                                progress.setVisibility(View.GONE);
                                tvNoItem.setVisibility(View.GONE);
                            }

                        } else {
                            rvList.setVisibility(View.GONE);
                            progress.setVisibility(View.GONE);
                            tvNoItem.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchListPartnerCollegeResponse> call, Throwable t) {
                        rvList.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        tvNoItem.setVisibility(View.GONE);
                    }
                });
    }


    private void assignCollege(String itemId) {
        dialogProgress.show();
        retrofit.create(AssignPartnerCollegeAPI.class).post(itemId,partnerId,userType)
                .enqueue(new Callback<AssignPartnerCollegeResponse>() {
                    @Override
                    public void onResponse(Call<AssignPartnerCollegeResponse> call, final Response<AssignPartnerCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getSuccess()==1){
                                dialogProgress.dismiss();
                                finish();
                                AddPartnerCollegeActivity.activity.finish();
                            }
                            Toast.makeText(SearchPartnerColleges.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<AssignPartnerCollegeResponse> call, Throwable t) {

                    }
                });
    }




    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
