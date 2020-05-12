package com.example.storemanagement.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.storemanagement.R;
import com.example.storemanagement.UserDao;
import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.Inventory;
import com.example.storemanagement.entity.Shelf;
import com.example.storemanagement.entity.Store;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public static ArrayList<Inventory> inventoryArrayList = new ArrayList<>();
    private Store ware = new Store();

    private EditText et_search_shelf, et_search_piles;
    Button btn_search, btn_edit, btn_confirm;
    private TextView tv_clothes_Id_show, tv_rest_number;
    private EditText et_clothes_id, et_clothes_number;

    String shelf;
    String piles;
    Clothes clothes;

    View view;

    UserDao userDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        et_search_shelf = view.findViewById(R.id.et_search_shelf);
        et_search_piles = view.findViewById(R.id.et_search_plies);
        btn_search = view.findViewById(R.id.btn_search_id2);
        tv_clothes_Id_show = view.findViewById(R.id.tv_clothes_id_show);
        tv_rest_number = view.findViewById(R.id.tv_rest_number2);
        et_clothes_id = view.findViewById(R.id.et_clothes_id);
        et_clothes_number = view.findViewById(R.id.et_clothes_number);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_confirm = view.findViewById(R.id.btn_confirm);


        userDao = new UserDao();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ware = userDao.getWare();

                shelf = et_search_shelf.getText().toString();
                piles = et_search_piles.getText().toString();

                if(shelf.equals("") || piles.equals("||")){
                    Toast.makeText(view.getContext(), "请输入数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    clothes = ware.getShelves().get(shelf).getPiles().get(Integer.parseInt(piles));
                }catch (Exception e){
                    Toast.makeText(view.getContext(), "该位置上无货物/无该位置", Toast.LENGTH_SHORT).show();
                    return;
                }

                String id = clothes.getId();
                tv_clothes_Id_show.setText(id);
                tv_rest_number.setText(clothes.getNumber() + "");

                view.findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tv_result).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tv_clothes_id).setVisibility(View.VISIBLE);
                tv_clothes_Id_show.setVisibility(View.VISIBLE);
                view.findViewById(R.id.tv_clothes_rest).setVisibility(View.VISIBLE);
                tv_rest_number.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.VISIBLE);

                et_clothes_id.setVisibility(View.INVISIBLE);
                et_clothes_number.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.tv_edit).setVisibility(View.INVISIBLE);
                btn_confirm.setVisibility(View.INVISIBLE);

            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.findViewById(R.id.tv_result).setVisibility(View.INVISIBLE);
                tv_clothes_Id_show.setVisibility(View.INVISIBLE);
                tv_rest_number.setVisibility(View.INVISIBLE);
                btn_edit.setVisibility(View.INVISIBLE);
                btn_edit.setVisibility(View.INVISIBLE);

                et_clothes_id.setText(clothes.getId());
                et_clothes_number.setText(clothes.getNumber() + "");

                et_clothes_id.setVisibility(View.VISIBLE);
                et_clothes_number.setVisibility(View.VISIBLE);
                view.findViewById(R.id.tv_edit).setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.VISIBLE);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curId = et_clothes_id.getText().toString();
                int curNumber = Integer.parseInt(et_clothes_number.getText().toString());
                Inventory inventory = new Inventory(clothes.getId(), curId, clothes.getNumber(), curNumber);

                if (curNumber == 0) {
                    //删除记录
                    int result = userDao.deleteClothes(curId);
                    if (result == 0) {
                        Toast.makeText(view.getContext(), "更改失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "更改成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //更新记录
                    int result = userDao.deleteClothes(curId);
                    result = userDao.updateClothes(clothes.getId(), curId, curNumber);
                    if (result == 0) {
                        Toast.makeText(view.getContext(), "更改失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "更改成功", Toast.LENGTH_SHORT).show();
                        if (curId.equals(clothes.getId())) {
                            if (curNumber != clothes.getNumber()) {
                                clothes.setNumber(curNumber);
                                ware.getShelves().get(shelf).getPiles().put(Integer.parseInt(piles), clothes);
                                //保存修改记录
                                inventoryArrayList.add(inventory);
                            }
                        } else {
                            //修改了id
                            ware.getShelves().get(shelf).getPiles().remove(clothes.getId());
                            clothes.setId(curId);
                            clothes.setNumber(curNumber);
                            ware.getShelves().get(shelf).getPiles().put(Integer.parseInt(piles), clothes);
                            //保存修改记录
                            inventoryArrayList.add(inventory);
                        }
                    }
                }
            }
        });

        return view;
    }
}