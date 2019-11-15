package com.cat.www;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cat.www.R;
import com.cat.www.CatRVAdapter;
import com.cat.www.CatListBean;
import com.cat.www.FarvorityCatList;

import java.util.List;

public class FavoriteF extends Fragment {

    private RecyclerView mRvFavoriteMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favotiteView = View.inflate(getContext(), R.layout.fragment_favorite,null);
        mRvFavoriteMenu = favotiteView.findViewById(R.id.rv_favorite_menu);
        return favotiteView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        List<CatListBean>  collectCat = FarvorityCatList.favorltyCat;
        if (collectCat == null){
            Toast.makeText(getActivity(), "you don't have any favorites at the moment", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvFavoriteMenu.setLayoutManager(manager);
        CatRVAdapter catRVAdapter = new CatRVAdapter(getContext(), collectCat);
        mRvFavoriteMenu.setAdapter(catRVAdapter);
    }
}
