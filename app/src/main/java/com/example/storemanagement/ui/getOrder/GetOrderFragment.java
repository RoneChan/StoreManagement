package com.example.storemanagement.ui.getOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.storemanagement.Control.OrderAdapter;
import com.example.storemanagement.R;
import com.example.storemanagement.UserDao;
import com.example.storemanagement.entity.FileDataStream;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Store;

import java.util.ArrayList;

public class GetOrderFragment extends Fragment {
    ArrayList<Order> orderArrayList = new ArrayList<>();
    OrderAdapter adapter;
    FileDataStream fileDataStream;
    ListView lv_order;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_get_order, container, false);

        if(orderArrayList.size() == 0){

        }else {
            lv_order = view.findViewById(R.id.lv_order_show);
            adapter = new OrderAdapter(container.getContext(), orderArrayList);
            lv_order.setAdapter(adapter);

            lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), OrderDetail.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}