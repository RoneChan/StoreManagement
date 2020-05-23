package com.example.storemanagement.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.storemanagement.R;
import com.example.storemanagement.UserDao;

import java.sql.Connection;

public class SearchFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String Id;
    private int restNumber = -1;
    private String position="";
    EditText et_Id;
    Button btn_confirm;

    ImageView imageView;
    TextView tv_result_text,tv_rest_text,tv_position_text;
    ImageView background;
    static Connection conn = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        et_Id = view.findViewById(R.id.et_search_input_id);
        btn_confirm = view.findViewById(R.id.btn_search_id);

        tv_rest_text=view.findViewById(R.id.tv_rest_text);
        tv_result_text=view.findViewById(R.id.tv_result_text);
        background=view.findViewById(R.id.iv_background);
        tv_position_text=view.findViewById(R.id.tv_position_text);

        //改变输入的ID
        et_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //将结果不可视
                tv_result_text.setVisibility(View.INVISIBLE);
                tv_rest_text.setVisibility(View.INVISIBLE);
                background.setVisibility(View.INVISIBLE);
                tv_position_text.setVisibility(View.INVISIBLE);
            }
        });

        //确认按钮，查询结果
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = et_Id.getText().toString();
                if(Id.isEmpty()){
                    Toast.makeText(view.getContext(), "请输入服装ID号", Toast.LENGTH_SHORT).show();
                }else {
                    UserDao userDao = new UserDao();
                    restNumber = userDao.getRestNumber(Id);  //获取查询信息
                    position = userDao.getPosition(Id);
                    if (restNumber == -1) {
                        Toast.makeText(view.getContext(), "服装ID错误", Toast.LENGTH_SHORT).show();
                    } else {
                        //显示结果
                        tv_result_text.setVisibility(View.VISIBLE);
                        tv_rest_text.setText("剩余数量:"+restNumber);
                        tv_rest_text.setVisibility(View.VISIBLE);
                        tv_position_text.setText("位置:"+position);
                        tv_position_text.setVisibility(View.VISIBLE);
                        background.setVisibility(View.VISIBLE);
                       // Toast.makeText(view.getContext(), "找到" + restNumber, Toast.LENGTH_SHORT).show();
                    }
                }



                /*
                final Handler handler = new Handler() {
                    //Thread线程发出Handler消息，通知更新UI。
                    //这里接受并处理消息
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if (restNumber == -1) {
                            Toast.makeText(view.getContext(), "没有找到", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "找到" + restNumber, Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                try {
                    Connection c = jdbcUtil.createConnection();
                    String sql = "select RestNumber from clothes where ID='123'";
                    Statement stmt = c.createStatement();
                    ResultSet res = stmt.executeQuery(sql);
                    while (res.next()) {
                        restNumber = res.getInt("RestNumber");
                        handler.sendEmptyMessage(1);
                    }
                    if (!res.wasNull()) {
                        restNumber = res.getInt("RestNumber");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



/*
                try {
                    Connection connection = new Conn().getConn();
                    String sql = "select RestNumber from clothes where ID='123'";
                    Statement stmt = connection.createStatement();
                    ResultSet res = stmt.executeQuery(sql);
                    while (res.next()) {
                        restNumber = res.getInt("RestNumber")+100;
                        handler.sendEmptyMessage(1);
                    }
                    if (!res.wasNull()) {
                        restNumber = res.getInt("RestNumber");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



                /*

                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        //Connection conn = null;

                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/storemanagement", "root", "123");             //用户名 ，密码   用户名root 密码 123456
                            String sql = "select RestNumber from clothes where ID='123'";
                            //PreparedStatement ps = conn.prepareStatement(sql);
                            Statement stmt = conn.createStatement();
                            // ResultSet res = ps.executeQuery();
                            ResultSet res = stmt.executeQuery(sql);
                            while (res.next()) {
                                restNumber = res.getInt("RestNumber");
                                handler.sendEmptyMessage(1);
                            }
                            if (!res.wasNull()) {
                                restNumber = res.getInt("RestNumber");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };

                //run（）只是当普通方法调用，而不是开始一个新线程
                new Thread(run).start();

*/
            }
        });


        return view;
    }

}