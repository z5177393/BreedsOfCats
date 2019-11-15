package com.cat.www;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SearchF extends Fragment implements View.OnClickListener {
    private RecyclerView mRvCat;
    private Button mBtnSearch;
    private EditText mEtInputBeed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = View.inflate(getContext(), R.layout.fragment_home, null);
        mRvCat = homeView.findViewById(R.id.rv_catss_menu);
        mEtInputBeed = homeView.findViewById(R.id.et_inputname);
        mBtnSearch = homeView.findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);

        return homeView;
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvCat.setLayoutManager(manager);
        String beedString = mEtInputBeed.getText().toString().trim();
        if (TextUtils.isEmpty(beedString)){
            Toast.makeText(getActivity(), "input must not be null", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        ConnectUtil.getInstance().getdata("https://api.thecatapi.com/v1/images/search?breed_ids=" + beedString, new Callback() {
            @Override
            public void onSucceedData(String result) {
                try {
                    Type type = new TypeToken<List<CatListBean>>() {
                    }.getType();
                    final List<CatListBean> catlist = new Gson().fromJson(result, type);
                    if (catlist.size() == 0){
                        Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    CatRVAdapter catRVAdapter = new CatRVAdapter(getContext(), catlist);
                    catRVAdapter.setonItemClickListner(new CatRVAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(getContext(), CatDetailsActivity.class);
                            intent.putExtra("catdetail", catlist.get(position));
                            startActivity(intent);
                        }
                    });
                    mRvCat.setAdapter(catRVAdapter);

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailed(int i, String error) {

            }
        });


    }

    public void showOrHide() {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                showOrHide();
                initData();
                break;

        }
    }


}
