package com.example.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.common.view.BaseFragment;

import java.util.ArrayList;

public class HomePageAdapter extends FragmentStateAdapter {

    private ArrayList<BaseFragment> listF;

    public HomePageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<BaseFragment> list) {
        super(fragmentManager, lifecycle);
        listF = new ArrayList<>(list);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listF.get(position);
    }

    @Override
    public int getItemCount() {
        return listF.size();
    }
}
