package com.cat.www;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CatDetailsActivity extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_description;
    private TextView tv_weight;
    private TextView tv_temperament;
    private TextView tv_origin;
    private TextView tv_lifespan;
    private TextView tv_wikipedialink;
    private TextView tv_dogfriendlinesslevel;
    private ImageView mIvCat;
    private ImageView mIvCollect;
    private ActionBar actionBar;
    private CatListBean mCatData;
    private List<CatListBean> collectCat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catdetail);
        tv_name = this.findViewById(R.id.tv_name);
        tv_description = this.findViewById(R.id.tv_description);
        tv_weight = this.findViewById(R.id.tv_Weight);
        tv_temperament = this.findViewById(R.id.tv_Temperament);
        tv_origin = this.findViewById(R.id.tv_Origin);
        tv_lifespan = this.findViewById(R.id.tv_Lifespan);
        tv_wikipedialink = this.findViewById(R.id.tv_Wikipedialink);
        tv_dogfriendlinesslevel = this.findViewById(R.id.tv_Dogfriendlinesslevel);
        mIvCat = this.findViewById(R.id.iv_cat);
        mIvCollect = this.findViewById(R.id.iv_collect);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("SearchResult");
        }
        mIvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCatData != null) {
                    if (iscollcet) {
                        Toast.makeText(CatDetailsActivity.this, "you have liked it already", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        collectCat.add(mCatData);
                        FarvorityCatList.favorltyCat = collectCat;
                        mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.bg_selectcollect));
                        iscollcet = true;
                    }
                }
            }
        });
        initData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    private boolean iscollcet = false;

    private void initData() {
        mCatData = (CatListBean) getIntent().getSerializableExtra("catdetail");
        tv_name.setText("" + mCatData.getBreeds().get(0).getName());
        tv_description.setText("" + mCatData.getBreeds().get(0).getDescription());

        tv_weight.setText("" + mCatData.getBreeds().get(0).getWeight().toString());
        tv_temperament.setText("" + mCatData.getBreeds().get(0).getTemperament());
        tv_origin.setText("" + mCatData.getBreeds().get(0).getOrigin());
        tv_lifespan.setText("" + mCatData.getBreeds().get(0).getLife_span());
        tv_wikipedialink.setText("" + mCatData.getBreeds().get(0).getWikipedia_url());
        tv_dogfriendlinesslevel.setText("" + mCatData.getBreeds().get(0).getDog_friendly());

        setPicBitmap(mIvCat, "" + mCatData.getUrl());


        collectCat = FarvorityCatList.favorltyCat;


        if (collectCat == null) {
            collectCat = new ArrayList<>();

        } else {
            for (int i = 0; i <collectCat.size() ; i++) {
                if (collectCat.get(i).getId().equals(mCatData.getId())){
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.bg_selectcollect));
                    iscollcet = true;
                }else {

                }

            }

        }
    }

    private void setPicBitmap(final ImageView ivPic, final String pic_url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(
                            pic_url).openConnection();


                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(1000 * 10);
                    conn.connect();

                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);

                        CatDetailsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivPic.setImageBitmap(bitmap);
                            }
                        });
                    } else {
                        Toast.makeText(CatDetailsActivity.this, "no images found", Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
