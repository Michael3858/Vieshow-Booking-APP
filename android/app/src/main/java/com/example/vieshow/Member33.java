package com.example.vieshow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Member33 extends AppCompatActivity {

    ListView listView;
    ArrayList<String> timelist1 = new ArrayList<>();
    ArrayList<String> statelist = new ArrayList<>();
    ArrayList<String> addresslist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> timelist2 = new ArrayList<>();
    ArrayList<String> totallist = new ArrayList<>();
    ArrayList<String> numberlist = new ArrayList<>();
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> finishlist = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date curDate;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member33);

        curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);

        SharedPreferences List1 = getSharedPreferences("list8", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            timelist1.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("list9", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            statelist.add(item2);
        }
        SharedPreferences List3 = getSharedPreferences("list10", MODE_PRIVATE);
        int num3 = List3.getInt("num", 0);
        for (int i = 0; i < num3; i++) {
            String item3 = List3.getString("item_" + i, null);
            addresslist.add("影城：" + item3);
        }
        SharedPreferences List4 = getSharedPreferences("list11", MODE_PRIVATE);
        int num4 = List4.getInt("num", 0);
        for (int i = 0; i < num4; i++) {
            String item4 = List4.getString("item_" + i, null);
            namelist.add("電影：" + item4);
        }
        SharedPreferences List5 = getSharedPreferences("list12", MODE_PRIVATE);
        int num5 = List5.getInt("num", 0);
        for (int i = 0; i < num5; i++) {
            String item5 = List5.getString("item_" + i, null);
            timelist2.add("場次：" + item5);
            try {
                Date date1 = formatter.parse(str);
                Date date2 = formatter.parse(item5);
                if(date1.getTime() > date2.getTime()){
                    finishlist.add(Integer.toString(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        SharedPreferences List6 = getSharedPreferences("list13", MODE_PRIVATE);
        int num6 = List6.getInt("num", 0);
        for (int i = 0; i < num6; i++) {
            String item6 = List6.getString("item_" + i, null);
            totallist.add("總計金額：$" + item6);
        }
        SharedPreferences List7 = getSharedPreferences("list14", MODE_PRIVATE);
        int num7 = List7.getInt("num", 0);
        for (int i = 0; i < num7; i++) {
            String item7 = List7.getString("item_" + i, null);
            numberlist.add("取票序號：" + item7);
        }
        SharedPreferences List8 = getSharedPreferences("list15", MODE_PRIVATE);
        int num8 = List8.getInt("num", 0);
        for (int i = 0; i < num8; i++) {
            String item8 = List8.getString("item_" + i, null);
            IDlist.add(item8);
        }

        /*timelist1.add("2022-11-23 21:08");
        statelist.add("已取消訂票");
        addresslist.add("影城：台中大遠百威秀影城");
        namelist.add("電影：(數位)黑豹2：瓦干達萬歲 (保護級)");
        timelist2.add("場次：2022-11-23 10:45");
        totallist.add("總計金額：$20");
        numberlist.add("取票序號：WXRQCBJ");
        timelist1.add("2022-11-22 22:05");
        statelist.add("已付款");
        addresslist.add("影城：台中大遠百威秀影城");
        namelist.add("電影：(數位)黑豹2：瓦干達萬歲 (保護級)");
        timelist2.add("場次：2022-11-24 22:45");
        totallist.add("總計金額：$200");
        numberlist.add("取票序號：WNLWFSQ");*/

        listView = findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(this,timelist1,statelist,addresslist,namelist,timelist2,totallist,numberlist);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = false;
                for(int i = 0; i < finishlist.size(); i++){
                    if(position == Integer.parseInt(finishlist.get(i))){
                        b = true;
                        break;
                    }
                }
                if((!statelist.get(position).equals("已取消")) && !b){
                    Intent intent = new Intent();
                    intent.setClass(Member33.this,Member34.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", IDlist.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rtimelist1;
        ArrayList<String> rstatelist;
        ArrayList<String> raddresslist;
        ArrayList<String> rnamelist;
        ArrayList<String> rtimelist2;
        ArrayList<String> rtotallist;
        ArrayList<String> rnumberlist;

        MyAdapter (Context context, ArrayList<String> timelist1, ArrayList<String> statelist, ArrayList<String> addresslist, ArrayList<String> namelist, ArrayList<String> timelist2, ArrayList<String> totallist, ArrayList<String> numberlist){
            super(context,R.layout.listview_style5,R.id.title1,timelist1);
            this.context = context;
            this.rtimelist1 = timelist1;
            this.rstatelist = statelist;
            this.raddresslist = addresslist;
            this.rnamelist = namelist;
            this.rtimelist2 = timelist2;
            this.rtotallist = totallist;
            this.rnumberlist = numberlist;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.listview_style5,parent,false);
            TextView textView1 = row.findViewById(R.id.title1);
            TextView textView2 = row.findViewById(R.id.title2);
            TextView textView3 = row.findViewById(R.id.title3);
            TextView textView4 = row.findViewById(R.id.title4);
            TextView textView5 = row.findViewById(R.id.title5);
            TextView textView6 = row.findViewById(R.id.title6);
            TextView textView7 = row.findViewById(R.id.title7);
            textView1.setText(rtimelist1.get(position));
            textView2.setText(rstatelist.get(position));
            textView3.setText(raddresslist.get(position));
            textView4.setText(rnamelist.get(position));
            textView5.setText(rtimelist2.get(position));
            textView6.setText(rtotallist.get(position));
            textView7.setText(rnumberlist.get(position));
            return row;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.action_home) {
            // 按下「設定」要做的事
            Intent intent = new Intent();
            intent.setClass(Member33.this, Member4.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_help) {
            // 按下「使用說明」要做的事
            Toast.makeText(this, "使用說明", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_about) {
            // 按下「關於」要做的事
            Toast.makeText(this, "關於", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}