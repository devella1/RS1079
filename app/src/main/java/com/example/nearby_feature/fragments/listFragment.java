package com.example.nearby_feature.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.nearby_feature.R;
import com.example.nearby_feature.adapter;
import com.example.nearby_feature.place;

import java.util.List;


public class listFragment extends Fragment {

    public List<place> list_places;
    private RecyclerView recyclerView;
    public adapter listAdapter;

    public listFragment() {

    }


    public static listFragment newInstance(String param1, String param2) {
        listFragment fragment = new listFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            list_places= (List<place>) bundle.getSerializable("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listAdapter = new adapter(list_places);
        recyclerView.setAdapter(listAdapter);

//        EditText et_testing = view.findViewById(R.id.testing);
//        et_testing.setText(list_places.get(0).getName());


        return view;
    }

}