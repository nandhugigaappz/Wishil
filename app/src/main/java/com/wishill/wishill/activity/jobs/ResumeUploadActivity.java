package com.wishill.wishill.activity.jobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.SubCategoryActivity;
import com.wishill.wishill.api.recommendedColleges.resumeUpload.ResumeUploadAPI;
import com.wishill.wishill.api.recommendedColleges.resumeUpload.ResumeUploadResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResumeUploadActivity extends AppCompatActivity {
    WebView fileView;
    TextView chooseFile,UploadFile;
    ArrayList<String> docPaths=new ArrayList<>();
    DialogProgress progress;
    SharedPreferences sharedPreferences;
    String candidate_id;
    String filePath;
    TextView toolbarTitle;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        fileView=(WebView) findViewById(R.id.pdfView);
        chooseFile=(TextView) findViewById(R.id.choose);
        UploadFile=findViewById(R.id.upload);
        progress    = new DialogProgress(ResumeUploadActivity.this);
        sharedPreferences = getSharedPreferences("wishill", MODE_PRIVATE);
        candidate_id = sharedPreferences.getString("candidate_user_id", "");
        initRetrofit();
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FilePickerBuilder.Companion.getInstance()
                        .setMaxCount(1)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.LibAppTheme)
                        .pickFile(ResumeUploadActivity.this);*/
                ActivityCompat.requestPermissions(ResumeUploadActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

            }
        });


        UploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filePath.equals("")){
                    uploadFile(filePath);
                }else {
                    Toast.makeText(ResumeUploadActivity.this, "Pick resume to continue.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    docPaths.clear();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));


                    for (String path : docPaths) {
                        File file = new File(path);
                        Uri fileUri = Uri.fromFile(file);
                        filePath=path;

                    }
                }
                break;
            case 11:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    //String path = getPath(uri);
                    String path = uri.toString();
                    //String path = getRealPathFromURI(getContext(),uri);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload

                    File file = new File(path);
                    Uri fileUri = data.getData();
                    filePath=path;
                    chooseFile.setVisibility(View.GONE);
                    //pdfToBitmap(file);
                    //fileName.setText(file.getName());
                    //f.fromUri(Uri)
                    //fileView.fromUri(fileUri);

                }
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ResumeUploadActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    void generateImageFromPdf(Uri pdfUri) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
        }
    }

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";
    private void saveImage(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if(!folder.exists())
                folder.mkdirs();

            File direct =
                    new File(Environment
                            .getExternalStoragePublicDirectory("Wishill")
                            .getAbsolutePath() + "/" );
            if (!direct.exists()) {
                direct.mkdir();
            }
            File file = new File(direct, "pdf.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
           // fileName.setImageBitmap(bmp);
        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    11);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(ResumeUploadActivity.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadFile(String path) {
        // Map is used to multipart the file using okhttp3.RequestBody


        File file = new File(path);

        // Parsing any Media type file
        // RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), candidate_id);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file.getAbsolutePath());
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("resume", file.getName(), requestBody1);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        progress.show();
        ResumeUploadAPI getResponse = retrofit.create(ResumeUploadAPI.class);
        Call<ResumeUploadResponse> call=getResponse.post(requestBody,fileToUpload);
        call.enqueue(new Callback<ResumeUploadResponse>() {
            @Override
            public void onResponse(Call<ResumeUploadResponse> call, Response<ResumeUploadResponse> response) {
                progress.dismiss();
                if (response.isSuccessful()){
                    Toast.makeText(ResumeUploadActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResumeUploadResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ResumeUploadActivity.this, "Some errors occurred", Toast.LENGTH_SHORT).show();

            }
        });
        /*retrofit.create(ResumeUploadAPI.class)
                .post(Candidate_user_id, fileToUpload)
                .enqueue(new Callback<ResumeUploadResponse>() {
                    @Override
                    public void onResponse(Call<ResumeUploadResponse> call, Response<ResumeUploadResponse> response) {
                        progress.dismiss();
                        if (response.isSuccessful()){
                            Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResumeUploadResponse> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Some errors occurred", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void initRetrofit() {
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
                .baseUrl(APILinks.API_LINK_JOBS_CANDIDATE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
