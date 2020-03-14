package com.example.nct1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class TwoFragment extends Fragment {
    public TwoFragment(){

    }
    public static TwoFragment newInstance(){
        return new TwoFragment();
    }
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        return view;
    }

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        insertNestedFragment();
    }
    private void insertNestedFragment(){
        Fragment childFragment1 = new ChildFragment();
        FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
        transaction1.replace(R.id.child_fragment_container1, childFragment1).commit();
        Fragment childFragment2 = new ChildFragment();
        FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
        transaction2.replace(R.id.child_fragment_container2, childFragment2).commit();
    }*/
}
