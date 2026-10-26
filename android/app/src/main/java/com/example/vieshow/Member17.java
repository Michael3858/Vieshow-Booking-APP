package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Member17 extends AppCompatActivity {

    ImageButton back;
    Button search, booking;
    TextView name, series, engname, length;
    Spinner date, time;
    ArrayList<String> datelist = new ArrayList<>();
    ArrayList<String> timelist = new ArrayList<>();
    ArrayList<String> timelist1 = new ArrayList<>();
    ArrayList<String> timelist2 = new ArrayList<>();
    ArrayList<String> screeningIDlist = new ArrayList<>();
    ArrayList<String> auditoriumIDlist = new ArrayList<>();
    String[] seatlist = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "J1", "J2", "J3", "J4", "J5", "J6", "J7", "J8"};
    ArrayList<String> emptyseatlist = new ArrayList<>(Arrays.asList(seatlist));
    String ID, screeningID, IP;
    String result1, result2, result3, result4, result5, result6, result7, result8, result9, result10, result11, result12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member17);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        datelist.clear();
        timelist.clear();
        timelist1.clear();
        timelist2.clear();
        screeningIDlist.clear();
        auditoriumIDlist.clear();

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");

        SharedPreferences List1 = getSharedPreferences("datelist", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            datelist.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("timelist1", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            timelist1.add(item2);
        }
        SharedPreferences List3 = getSharedPreferences("timelist2", MODE_PRIVATE);
        int num3 = List3.getInt("num", 0);
        for (int i = 0; i < num3; i++) {
            String item3 = List3.getString("item_" + i, null);
            timelist2.add(item3);
        }
        SharedPreferences List4 = getSharedPreferences("screeningIDlist", MODE_PRIVATE);
        int num4 = List4.getInt("num", 0);
        for (int i = 0; i < num4; i++) {
            String item4 = List4.getString("item_" + i, null);
            screeningIDlist.add(item4);
        }
        SharedPreferences List5 = getSharedPreferences("auditoriumIDlist", MODE_PRIVATE);
        int num5 = List5.getInt("num", 0);
        for (int i = 0; i < num5; i++) {
            String item5 = List5.getString("item_" + i, null);
            auditoriumIDlist.add(item5);
        }
        if(timelist1.size() == 0){
            datelist.remove(0);
        }
        if(timelist2.size() == 0){
            datelist.remove(1);
        }
        for(int i = 0; i < timelist1.size(); i++){
            timelist.add(timelist1.get(i));
        }
        if(timelist1.size() == 0){
            for(int i = 0; i < timelist2.size(); i++){
                timelist.add(timelist2.get(i));
            }
        }
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });

        name = (TextView) findViewById(R.id.name);
        series = (TextView) findViewById(R.id.series);
        engname = (TextView) findViewById(R.id.engname);
        length = (TextView) findViewById(R.id.length);
        date = (Spinner) findViewById(R.id.date);
        /*datelist.add("2022 年 11 月 22 日 Tuesday");
        datelist.add("2022 年 11 月 23 日 Wednesday");*/
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, datelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date.setAdapter(adapter);
        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(timelist1.size() == 0 || timelist2.size() == 0){
                    timelist.clear();
                    if(timelist2.size() == 0){
                        for(int i = 0; i < timelist1.size(); i++){
                            timelist.add(timelist1.get(i));
                        }
                    }
                    else if(timelist1.size() == 0){
                        for(int i = 0; i < timelist2.size(); i++){
                            timelist.add(timelist2.get(i));
                        }
                    }
                }
                else{
                    if(position == 0){
                        timelist.clear();
                        for(int i = 0; i < timelist1.size(); i++){
                            timelist.add(timelist1.get(i));
                        }
                    }
                    else if(position == 1){
                        timelist.clear();
                        for(int i = 0; i < timelist2.size(); i++){
                            timelist.add(timelist2.get(i));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        time = (Spinner) findViewById(R.id.time);
        /*timelist.add("10:45");
        timelist.add("11:30");
        timelist.add("13:45");
        timelist.add("14:30");
        timelist.add("15:15");
        timelist.add("16:45");
        timelist.add("17:30");
        timelist.add("19:45");
        timelist.add("20:30");
        timelist.add("21:15");
        timelist.add("22:45");
        timelist.add("23:55");*/
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, timelist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter2);
        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyseatlist = new ArrayList<>(Arrays.asList(seatlist));
                Thread thread1 = new Thread(mutiThread1);
                thread1.start();
            }
        });
        booking = (Button) findViewById(R.id.booking);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Member17.this,Member19.class);
                if(date.getSelectedItemPosition() == 0){
                    screeningID = screeningIDlist.get(time.getSelectedItemPosition());
                }
                else if(date.getSelectedItemPosition() == 1){
                    screeningID = screeningIDlist.get(timelist1.size() + time.getSelectedItemPosition());
                }
                Bundle bundle = new Bundle();
                bundle.putString("ID", screeningID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Thread thread = new Thread(mutiThread);
        thread.start();
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("movie.php"));
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ID", "UTF-8")+"="+URLEncoder.encode(ID, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode == HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        for(int i = 0; i < dataJson.length(); i++){
                            JSONObject info = dataJson.getJSONObject(i);
                            String picURL = info.getString("Pic_URL");
                            String series = info.getString("Movie_Series");
                            String name = info.getString("Movie_Name");
                            String engname = info.getString("Movie_EngName");
                            String showtime = info.getString("Movie_Showtime");
                            String director = info.getString("Movie_Director");
                            String actor = info.getString("Movie_Actor");
                            String genre = info.getString("Movie_Genre");
                            String duration = info.getString("Movie_Duration");
                            String trailer_URL = info.getString("Trailer_URL");
                            String introduction = info.getString("Movie_Introduction");
                            result1 = picURL.toString();
                            result2 = series.toString();
                            result3 = name.toString();
                            result4 = engname.toString();
                            result5 = showtime.toString();
                            result6 = director.toString();
                            result7 = actor.toString();
                            result8 = genre.toString();
                            result9 = duration.toString();
                            result10 = trailer_URL.toString();
                            result11 = introduction.toString();
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result1 = e.toString();
                result2 = e.toString();
                result3 = e.toString();
                result4 = e.toString();
                result5 = e.toString();
                result6 = e.toString();
                result7 = e.toString();
                result8 = e.toString();
                result9 = e.toString();
                result10 = e.toString();
                result11 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    name.setText(result3);
                    series.setText(result2);
                    if(result2.charAt(0) == '普'){
                        series.setBackgroundColor(Color.parseColor("#00DD00"));
                    }
                    else if(result2.charAt(0) == '護'){
                        series.setBackgroundColor(Color.parseColor("#0066FF"));
                    }
                    else if(result2.charAt(0) == '輔'){
                        series.setBackgroundColor(Color.parseColor("#FF8800"));
                    }
                    else if(result2.charAt(0) == '限'){
                        series.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                    engname.setText(result4);
                    length.setText("片長：" + result9);
                }
            });
        }
    };
    private Runnable mutiThread1 = new Runnable(){
        public void run()
        {
            try {
                if(date.getSelectedItemPosition() == 0){
                    screeningID = screeningIDlist.get(time.getSelectedItemPosition());
                }
                else if(date.getSelectedItemPosition() == 1){
                    screeningID = screeningIDlist.get(timelist1.size() + time.getSelectedItemPosition());
                }
                URL url = new URL(ApiConfig.url("emptyseat.php"));
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ID", "UTF-8")+"="+URLEncoder.encode(screeningID, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode == HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        for(int i = 0; i < dataJson.length(); i++){
                            JSONObject info = dataJson.getJSONObject(i);
                            String seat = info.getString("Seat");
                            result12 = seat.toString();
                            emptyseatlist.remove(result12);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result12 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    SharedPreferences.Editor edit = getSharedPreferences("emptyseatlist", MODE_PRIVATE).edit();
                    edit.putInt("num", emptyseatlist.size());
                    for(int i = 0; i < emptyseatlist.size(); i++){
                        edit.putString("item_"+i, emptyseatlist.get(i));
                    }
                    edit.commit();
                    Intent intent = new Intent();
                    intent.setClass(Member17.this,Member18.class);
                    if(date.getSelectedItemPosition() == 0){
                        screeningID = screeningIDlist.get(time.getSelectedItemPosition());
                    }
                    else if(date.getSelectedItemPosition() == 1){
                        screeningID = screeningIDlist.get(timelist1.size() + time.getSelectedItemPosition());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", screeningID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };
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
            intent.setClass(Member17.this, Member4.class);
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