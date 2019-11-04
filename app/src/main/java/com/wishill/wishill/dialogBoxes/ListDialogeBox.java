package com.wishill.wishill.dialogBoxes;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.activity.NormalUserEditProfileActivity;
import com.wishill.wishill.activity.partnershipwithwishill.AddPartnerScoolActivity;
import com.wishill.wishill.activity.partnershipwithwishill.AddPartnerStudyTourActivity;
import com.wishill.wishill.adapter.DialogeListNewAdapter;
import com.wishill.wishill.utilities.RecyclerItemClickListener;

import java.util.List;


public class ListDialogeBox extends Dialog {
    Context context;
    RecyclerView rv_list;
    TextView tv_head;
    List<String> list;
    String title;
    String from;

    public ListDialogeBox(Context context, List<String> list, String title, String from) {
        super(context);
        this.context = context;
        this.list = list;
        this.title = title;
        this.from = from;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list_style);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        tv_head = (TextView) findViewById(R.id.tv_head);
        tv_head.setText(title);
        DialogeListNewAdapter adapter = new DialogeListNewAdapter(list, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setAdapter(adapter);
        rv_list.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (from.equals("addCollege")) {
                           /* AddPartnerCollegeActivity.selectedPosition = position;*/
                        }else if(from.equals("addSchool")){
                            AddPartnerScoolActivity.selectedPosition=position;
                        }else if(from.equals("addStudyTour")){
                            AddPartnerStudyTourActivity.selectedPosition=position;
                        }else if(from.equals("editUserProfile")){
                            NormalUserEditProfileActivity.selectedPosition=position;
                        }
                        ListDialogeBox.this.dismiss();
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}
