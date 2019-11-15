package com.cat.www;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class FramlayoutActivity extends AppCompatActivity implements View.OnClickListener {


    private FrameLayout mFlContain;
    private Button mBtSearch;
    private Button mBtLove;
    private FragmentManager sfm;
    private SearchF searchFragment;
    private FavoriteF myOrderCarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlContain = findViewById(R.id.fragmentC);
        mBtSearch = findViewById(R.id.btn_Search);
        mBtLove = findViewById(R.id.btn_Love);
        mBtSearch.setOnClickListener(this);
        mBtLove.setOnClickListener(this);

        initFragmentMag();
    }

    private void initFragmentMag() {
        sfm = getSupportFragmentManager();
        FragmentTransaction ft = sfm.beginTransaction();
        searchFragment = new SearchF();
        myOrderCarFragment = new FavoriteF();
        currFramgment = searchFragment;
        ft.add(R.id.fragmentC, searchFragment, "home");
        ft.commit();
        mBtSearch.setEnabled(false);
        mBtLove.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Search:
                switchFragment(searchFragment);
                mBtLove.setEnabled(true);
                mBtSearch.setEnabled(false);
                break;
            case R.id.btn_Love:
                switchFragment(myOrderCarFragment);
                mBtSearch.setEnabled(true);
                mBtLove.setEnabled(false);
                break;
        }
    }


    private Fragment currFramgment;

    private void switchFragment(Fragment addFragment) {
        FragmentTransaction fragmentTransaction = sfm.beginTransaction();

        if (addFragment.isAdded()) {
            fragmentTransaction.hide(currFramgment).show(addFragment);
        } else {
            fragmentTransaction.add(R.id.fragmentC, addFragment);
            fragmentTransaction.hide(currFramgment);

        }
        currFramgment = addFragment;
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FarvorityCatList.favorltyCat = null;
    }
}
