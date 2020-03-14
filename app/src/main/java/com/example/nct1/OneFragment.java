package com.example.nct1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class OneFragment extends Fragment{
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        Button bdangnhap = (Button) view.findViewById(R.id.dangnhap);
        bdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), dangnhap.class);
                startActivity(intent);
            }
        });
        Button bplaylist = (Button)view.findViewById(R.id.playlist);
        bplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), dangnhap.class);
                startActivity(intent);
            }
        });
        Button bvideo = (Button)view.findViewById(R.id.video);
        bvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), dangnhap.class);
                startActivity(intent);
            }
        });
        Button bthuviennhac = (Button)view.findViewById(R.id.thuviennhac);
        bthuviennhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ThuVienNhac.class);
                startActivity(intent);
            }
        });
        return view;
    }
}