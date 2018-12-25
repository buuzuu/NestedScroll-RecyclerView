package com.example.hritik.nested_recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;

public class MainActivity extends AppCompatActivity {


    private boolean isShowingCardHeaderShadow;
    List<Model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        generateModelList();

        final RecyclerView rv = findViewById(R.id.card_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new MyAdapter(MainActivity.this, modelList));
        rv.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));

        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrolledToTop = lm.findFirstVisibleItemPosition() == 0
                        && lm.findViewByPosition(0).getTop() == 0;

                if (!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow) {

                    isShowingCardHeaderShadow = true;

                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                } else {

                    isShowingCardHeaderShadow = false;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }


            }
        });

        AdvancedNestedScrollView advancedNestedScrollView = findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldscrollX, int oldscrollY) {
                if (scrollX == 0 && oldscrollY > 0) {
                    // Reset the recycler view scroll positioneach time the card return too its starting position
                    rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }
            }
        });
    }

    private void showOrHideView(View view, boolean isShow) {

        view.animate().alpha(isShow ? 1f : 0f).setDuration(100).setInterpolator(new DecelerateInterpolator());


    }

    private void generateModelList() {

        modelList.add(new Model("http://i.imgur.com/l9ON9ym.jpg", "Jessica"));
        modelList.add(new Model("https://hdqwalls.com/download/celine-eberhardt-hot-model-09-1440x2560.jpg", "Amanda"));
        modelList.add(new Model("http://www.hdiphone6wallpaper.com/wp-content/uploads/Girl/Girl%20iPhone%206%20Wallpapers%2073.jpg", "Sasha"));
        modelList.add(new Model("http://hdphonewallpapers.com/content/byLECr1IFJl5MnshASaTaiuaBaWQwxxP1ux5ShOCTgIXYYNcJVwdYz53DuClUkUR.jpg", "Ronda"));
        modelList.add(new Model("http://idesigniphone.net/wallpapers/04836.jpg", "Alica"));

    }
}
