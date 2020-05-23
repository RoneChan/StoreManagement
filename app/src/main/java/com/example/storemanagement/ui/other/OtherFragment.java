package com.example.storemanagement.ui.other;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.storemanagement.R;
import com.example.storemanagement.ui.CheckStack.CheckStack;
import com.example.storemanagement.ui.InputOrder.InputOrder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {

    TextView tv_export,tv_help;


    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        tv_export=view.findViewById(R.id.tv_export);
        tv_help=view.findViewById(R.id.tv_help);

        tv_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckStack.class);
                startActivity(intent);
            }
        });

        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InputOrder.class);
                startActivity(intent);
            }
        });

        return view;


    }

}
