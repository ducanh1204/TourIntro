package com.example.duantn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.duantn.R;
import com.example.duantn.adapter.TourAdapter;
import com.example.duantn.morder.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourListActivity extends BaseActivity {


    private ViewPager2 viewPager2;
    private List<Tour> tourList;
    private TourAdapter tourAdapter;
    private EditText edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);
        viewPager2 = findViewById(R.id.viewPager2);
        edt_search = findViewById(R.id.edt_search);

        tourList = new ArrayList<>();
        tourList.add(new Tour(R.drawable.img_tour, 1, "Ha Noi City Tour", "Đi qua các điểm danh lam thắng cảnh nổi tiếng của thành phố: Bảo tàng lịch sử quân đội Việt Nam - Hoàng thành Thăng Long - Đền Quán Thánh - Chùa Trấn Quốc - Lăng Chủ tịch Hồ Chí Minh - Văn Miếu - Nhà tù Hỏa Lò - Nhà thờ Lớn - Bảo tàng Phụ nữ Việt Nam và dừng chân tại điểm Nhà hát Lớn thành phố."));
        tourList.add(new Tour(R.drawable.bg_splash, 2, "Ha Long Tour", "Đi qua các điểm danh lam thắng cảnh nổi tiếng của thành phố: Bảo tàng lịch sử quân đội Việt Nam - Hoàng thành Thăng Long - Đền Quán Thánh - Chùa Trấn Quốc - Lăng Chủ tịch Hồ Chí Minh - Văn Miếu - Nhà tù Hỏa Lò - Nhà thờ Lớn - Bảo tàng Phụ nữ Việt Nam và dừng chân tại điểm Nhà hát Lớn thành phố."));
        tourList.add(new Tour(R.drawable.main_background, 3, "Ba Na Hill Tour", "Đi qua các điểm danh lam thắng cảnh nổi tiếng của thành phố: Bảo tàng lịch sử quân đội Việt Nam - Hoàng thành Thăng Long - Đền Quán Thánh - Chùa Trấn Quốc - Lăng Chủ tịch Hồ Chí Minh - Văn Miếu - Nhà tù Hỏa Lò - Nhà thờ Lớn - Bảo tàng Phụ nữ Việt Nam và dừng chân tại điểm Nhà hát Lớn thành phố."));
        tourList.add(new Tour(R.drawable.bg_splash, 4, "Sapa  Tour", "Đi qua các điểm danh lam thắng cảnh nổi tiếng của thành phố: Bảo tàng lịch sử quân đội Việt Nam - Hoàng thành Thăng Long - Đền Quán Thánh - Chùa Trấn Quốc - Lăng Chủ tịch Hồ Chí Minh - Văn Miếu - Nhà tù Hỏa Lò - Nhà thờ Lớn - Bảo tàng Phụ nữ Việt Nam và dừng chân tại điểm Nhà hát Lớn thành phố."));
        tourList.add(new Tour(R.drawable.img_tour, 5, "Ha Noi City Tour", "Đi qua các điểm danh lam thắng cảnh nổi tiếng của thành phố: Bảo tàng lịch sử quân đội Việt Nam - Hoàng thành Thăng Long - Đền Quán Thánh - Chùa Trấn Quốc - Lăng Chủ tịch Hồ Chí Minh - Văn Miếu - Nhà tù Hỏa Lò - Nhà thờ Lớn - Bảo tàng Phụ nữ Việt Nam và dừng chân tại điểm Nhà hát Lớn thành phố."));
        tourList.add(new Tour(R.drawable.main_background, 5, "Ha Noi City Tour", "Đi qua các điểm danh lam thắng cảnh nổi tiếng của thành phố: Bảo tàng lịch sử quân đội Việt Nam - Hoàng thành Thăng Long - Đền Quán Thánh - Chùa Trấn Quốc - Lăng Chủ tịch Hồ Chí Minh - Văn Miếu - Nhà tù Hỏa Lò - Nhà thờ Lớn - Bảo tàng Phụ nữ Việt Nam và dừng chân tại điểm Nhà hát Lớn thành phố."));

        setAdapter();

        setViewPager2();

        setEdt_search();

    }

    private void setAdapter() {
        tourAdapter = new TourAdapter(tourList, this);
    }

    private void setViewPager2() {
        viewPager2.setAdapter(tourAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(100));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
    }

    private void setEdt_search() {
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tourAdapter.getFilter().filter(edt_search.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);
                }
            }
        });


    }
}