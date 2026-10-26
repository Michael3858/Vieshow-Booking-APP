package com.example.vieshow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Member6 extends AppCompatActivity {

    ListView listView;
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> imagelist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> engnamelist = new ArrayList<>();
    ArrayList<String> serieslist = new ArrayList<>();
    ArrayList<String> showtimelist = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> Title = new ArrayList<>();
    ArrayList<String> sec = new ArrayList<>();
    ArrayList<String> third = new ArrayList<>();
    ArrayList<String> fourth = new ArrayList<>();
    ArrayList<String> movieID = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member6);

        SharedPreferences List1 = getSharedPreferences("ListString1", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            IDlist.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("ListString2", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            imagelist.add(item2);
        }
        SharedPreferences List3 = getSharedPreferences("ListString3", MODE_PRIVATE);
        int num3 = List3.getInt("num", 0);
        for (int i = 0; i < num3; i++) {
            String item3 = List3.getString("item_" + i, null);
            namelist.add(item3);
        }
        SharedPreferences List4 = getSharedPreferences("ListString4", MODE_PRIVATE);
        int num4 = List4.getInt("num", 0);
        for (int i = 0; i < num4; i++) {
            String item4 = List4.getString("item_" + i, null);
            engnamelist.add(item4);
        }
        SharedPreferences List5 = getSharedPreferences("ListString5", MODE_PRIVATE);
        int num5 = List5.getInt("num", 0);
        for (int i = 0; i < num5; i++) {
            String item5 = List5.getString("item_" + i, null);
            serieslist.add(item5);
        }
        SharedPreferences List6 = getSharedPreferences("ListString6", MODE_PRIVATE);
        int num6 = List6.getInt("num", 0);
        for (int i = 0; i < num6; i++) {
            String item6 = List6.getString("item_" + i, null);
            showtimelist.add(item6);
        }
        curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        try {
            Date date1 = formatter.parse(str);
            for (int i = 0; i < imagelist.size(); i++) {
                Date date2 = formatter.parse(showtimelist.get(i));
                if(date1.getTime() >= date2.getTime()){
                    image.add(imagelist.get(i));
                    Title.add(namelist.get(i));
                    sec.add(engnamelist.get(i));
                    third.add(serieslist.get(i));
                    fourth.add(showtimelist.get(i));
                    movieID.add(IDlist.get(i));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*image.add("https://www.vscinemas.com.tw/vsweb/upload/film/film_20221018013.jpg");
        Title.add("黑豹2：瓦干達萬歲");
        sec.add("BLACK PANTHER WAKANDA FOREVER");
        third.add("護 6+");
        fourth.add("2022-11-09");
        image.add("https://www.vscinemas.com.tw/vsweb/upload/film/film_20221108001.jpg");
        Title.add("新．超人力霸王 + 超人力霸王：再見了！超人力霸王");
        sec.add("SHIN ULTRAMAN AND EPISODE 39");
        third.add("普 0+");
        fourth.add("2022-11-18");
        image.add("https://www.vscinemas.com.tw/vsweb/upload/film/film_20221031013.jpg");
        Title.add("錯位的青春");
        sec.add("A GIRL ON THE SHORE");
        third.add("輔 15+");
        fourth.add("2022-11-18");
        image.add("https://www.vscinemas.com.tw/vsweb/upload/film/film_20220427003.jpg");
        Title.add("藍色是最溫暖的顏色");
        sec.add("BLUE IS THE WARMEST COLOR");
        third.add("限 18+");
        fourth.add("2022-05-27");*/

        listView = findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(this,image,Title,sec,third,fourth);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID = movieID.get(position);
                Intent intent = new Intent();
                intent.setClass(Member6.this,Member9.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", ID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rimage;
        ArrayList<String> rTitle;
        ArrayList<String> rsec;
        ArrayList<String> rthird;
        ArrayList<String> rfourth;

        MyAdapter (Context context, ArrayList<String> image, ArrayList<String> title, ArrayList<String> sec, ArrayList<String> third, ArrayList<String> fourth){
            super(context,R.layout.listview_style,R.id.title1,title);
            this.context = context;
            this.rimage = image;
            this.rTitle = title;
            this.rsec = sec;
            this.rthird = third;
            this.rfourth = fourth;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.listview_style,parent,false);
            ImageView imageView = row.findViewById(R.id.title1);
            TextView textView = row.findViewById(R.id.title2);
            TextView textView1 = row.findViewById(R.id.title3);
            TextView textView2 = row.findViewById(R.id.title4);
            TextView textView3 = row.findViewById(R.id.title5);
            Picasso.with(Member6.this).load(rimage.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fit().into(imageView);
            textView.setText(rTitle.get(position));
            textView1.setText(rsec.get(position));
            textView2.setText(rthird.get(position));
            if(rthird.get(position).charAt(0) == '普'){
                textView2.setTextColor(Color.parseColor("#00DD00"));
            }
            else if(rthird.get(position).charAt(0) == '護'){
                textView2.setTextColor(Color.parseColor("#0066FF"));
            }
            else if(rthird.get(position).charAt(0) == '輔'){
                textView2.setTextColor(Color.parseColor("#FF8800"));
            }
            else if(rthird.get(position).charAt(0) == '限'){
                textView2.setTextColor(Color.parseColor("#FF0000"));
            }
            textView3.setText(rfourth.get(position));
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
            intent.setClass(Member6.this, Member4.class);
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