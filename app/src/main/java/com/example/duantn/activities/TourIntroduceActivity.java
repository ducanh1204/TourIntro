package com.example.duantn.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duantn.R;
import com.example.duantn.adapter.FeedbackAdapter;
import com.example.duantn.adapter.ShowLocationInformation;
import com.example.duantn.morder.Feedback;
import com.example.duantn.morder.TourInfor;
import com.example.duantn.network.RetrofitService;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourIntroduceActivity extends BaseActivity implements View.OnClickListener {


    private String tour_name, avatar, route;
    private int rating,ratingFeedback;
    private ImageView img_tour;
    private TextView textViewRoute;
    private ImageView imgAvatar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ShowLocationInformation showLocationInformation;
    private RecyclerView rv1, rv2;
    public static List<TourInfor> locationList;
    private List<Feedback> feedbackList = new ArrayList<>();
    private FeedbackAdapter feedbackAdapter;
    private ImageView img_star1, img_star2, img_star3, img_star4, img_star5;
    private ImageView imgf_star1, imgf_star2, imgf_star3, imgf_star4, imgf_star5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_introduce);
        initDialogLoading();
        showDialogLoading();
        getIntentExtras();
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        setAdapter();
        setRecycleView();
        getRetrofit();
    }

    private void getIntentExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            tour_name = bundle.getString("tour_name");
            avatar = bundle.getString("avatar");
            rating = bundle.getInt("rating", 0);
            route = bundle.getString("router");

        }
    }

    private void initView() {
        img_tour = findViewById(R.id.img_tour);
        Glide.with(this).load("https://webtourintro.herokuapp.com/" + avatar).into(img_tour);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(tour_name);
        textViewRoute = findViewById(R.id.textViewRoute);
        textViewRoute.setText(route);
        findViewById(R.id.btn_start).setOnClickListener(this);

        img_star1 = findViewById(R.id.img_star1);
        img_star2 = findViewById(R.id.img_star2);
        img_star3 = findViewById(R.id.img_star3);
        img_star4 = findViewById(R.id.img_star4);
        img_star5 = findViewById(R.id.img_star5);
        List<ImageView> imageViewList = Arrays.asList(new ImageView[]{img_star1, img_star2, img_star3, img_star4, img_star5});
        for (int i = 0; i < imageViewList.size(); i++) {
            imageViewList.get(i).setImageResource(R.drawable.no_selected_star);
        }
        for (int i = 0; i < rating; i++) {
            imageViewList.get(i).setImageResource(R.drawable.selected_star);
        }

        imgf_star1 = findViewById(R.id.imgf_star1);
        imgf_star2 = findViewById(R.id.imgf_star2);
        imgf_star3 = findViewById(R.id.imgf_star3);
        imgf_star4 = findViewById(R.id.imgf_star4);
        imgf_star5 = findViewById(R.id.imgf_star5);

        List<ImageView> imageViewList2 = Arrays.asList(new ImageView[]{imgf_star1, imgf_star2, imgf_star3, imgf_star4, imgf_star5});
        imageViewList2.get(0).setImageResource(R.drawable.selected_star);
        for (int i = 1; i < imageViewList2.size(); i++) {
            imageViewList2.get(i).setImageResource(R.drawable.no_selected_star);
        }
        for (int i = 0; i < imageViewList2.size(); i++) {
            final int finalI = i;
            imageViewList2.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < imageViewList2.size(); i++) {
                        imageViewList2.get(i).setImageResource(R.drawable.no_selected_star);
                    }
                    for (int j = 0; j < finalI + 1; j++) {
                        imageViewList2.get(j).setImageResource(R.drawable.selected_star);
                        ratingFeedback = j + 1;
                    }
                }
            });
        }

    }


    private void setAdapter() {
        locationList = new ArrayList<>();
        showLocationInformation = new ShowLocationInformation(locationList, this, new ShowLocationInformation.OnClickItemListener() {
            @Override
            public void onClicked(int position) {

            }

            @Override
            public void onSwitched(boolean isChecked) {

            }
        });
        feedbackList.add(new Feedback("","Nguyễn Đức Anh","12/04/2000",3,"OK"));
        feedbackList.add(new Feedback("","Nguyễn Văn Cường","12/03/2020",4,"Đẹp"));
        feedbackList.add(new Feedback("","Nguyễn Văn B","22/12/2018",5,"Mượt"));
        feedbackList.add(new Feedback("","Nguyễn Văn C","17/11/2019",1,"Lag"));
        feedbackList.add(new Feedback("","Nguyễn Văn X","12/04/2000",5,"OK"));
        feedbackAdapter = new FeedbackAdapter(feedbackList,this);
    }

    private void setRecycleView() {
        rv1 = findViewById(R.id.rv1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager);
        rv1.setAdapter(showLocationInformation);


        rv2 = findViewById(R.id.rv2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        rv2.setLayoutManager(linearLayoutManager2);
    }

    private void getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tourintro.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        retrofitService.getTourInfor(getIdLanguage(), getIdTour()).enqueue(new Callback<List<TourInfor>>() {
            @Override
            public void onResponse(Call<List<TourInfor>> call, Response<List<TourInfor>> response) {
                if (response.body().size() != 0) {
                    int currentSize = locationList.size();
                    locationList.addAll(response.body());
                    showLocationInformation.notifyItemRangeInserted(currentSize, locationList.size());
                    rv2.setAdapter(feedbackAdapter);
                    dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<List<TourInfor>> call, Throwable t) {
                Toast.makeText(TourIntroduceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (isConnected(false)) {
            switch (v.getId()) {
                case R.id.btn_start:
                    createAlertDialog();
                    break;
                case R.id.imgAvatar:
                    showDialogLogout(this, getFullName());
            }
        } else {
            showDialogNoInternet();
        }
    }

    private void createAlertDialog() {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.title_alert));
        b.setMessage(getResources().getString(R.string.content_alert));
        b.setPositiveButton(getResources().getString(R.string.label_btn_Yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(TourIntroduceActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("enableAudio", true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        b.setNegativeButton(getResources().getString(R.string.label_btn_No), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(TourIntroduceActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("enableAudio", false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        AlertDialog al = b.create();
        al.show();
        al.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.color_btn_alertDialog));
        al.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.color_btn_alertDialog));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}