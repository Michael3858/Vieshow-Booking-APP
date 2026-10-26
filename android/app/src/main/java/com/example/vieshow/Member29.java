package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

public class Member29 extends AppCompatActivity {

    Button button1, button2;
    ImageButton back;
    ArrayList<String> timelist1 = new ArrayList<>();
    ArrayList<String> statelist = new ArrayList<>();
    ArrayList<String> namelist1 = new ArrayList<>();
    ArrayList<String> namelist2 = new ArrayList<>();
    ArrayList<String> timelist2 = new ArrayList<>();
    ArrayList<String> pricelist = new ArrayList<>();
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> timelist1_2 = new ArrayList<>();
    ArrayList<String> statelist_2 = new ArrayList<>();
    ArrayList<String> namelist1_2 = new ArrayList<>();
    ArrayList<String> namelist2_2 = new ArrayList<>();
    ArrayList<String> timelist2_2 = new ArrayList<>();
    ArrayList<String> pricelist_2 = new ArrayList<>();
    ArrayList<String> numberlist = new ArrayList<>();
    ArrayList<String> IDlist_2 = new ArrayList<>();
    String IP, Account;
    String result1, result2, result3, result4, result5, result6, result7, result8, result9, result10, result11, result12, result13, result14, result15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member29);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");
        SharedPreferences user1 = getSharedPreferences("account", MODE_PRIVATE);
        Account = user1.getString("acc", "");

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(mutiThread);
                thread.start();
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread1 = new Thread(mutiThread1);
                thread1.start();
            }
        });
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("bookinglist.php"));
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
                String post_data = URLEncoder.encode("acc", "UTF-8")+"="+URLEncoder.encode(Account, "UTF-8");
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
                            String time = info.getString("Booking_Time");
                            String state = info.getString("State");
                            String theatername = info.getString("Theater_Name");
                            String moviename = info.getString("Movie_Name");
                            String screeningstime = info.getString("Screenings_Time");
                            String totalprice = info.getString("Total_Price");
                            String ID = info.getString("Ticket_ID");
                            result1 = time.toString();
                            result2 = state.toString();
                            result3 = theatername.toString();
                            result4 = moviename.toString();
                            result5 = screeningstime.toString();
                            result6 = totalprice.toString();
                            result7 = ID.toString();
                            timelist1.add(result1);
                            statelist.add(result2);
                            namelist1.add(result3);
                            namelist2.add(result4);
                            timelist2.add(result5);
                            pricelist.add(result6);
                            IDlist.add(result7);
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
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    SharedPreferences.Editor edit1 = getSharedPreferences("list1", MODE_PRIVATE).edit();
                    edit1.putInt("num", timelist1.size());
                    for(int i = 0; i < timelist1.size(); i++){
                        edit1.putString("item_"+i, timelist1.get(i));
                    }
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("list2", MODE_PRIVATE).edit();
                    edit2.putInt("num", statelist.size());
                    for(int i = 0; i < statelist.size(); i++){
                        edit2.putString("item_"+i, statelist.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("list3", MODE_PRIVATE).edit();
                    edit3.putInt("num", namelist1.size());
                    for(int i = 0; i < namelist1.size(); i++){
                        edit3.putString("item_"+i, namelist1.get(i));
                    }
                    edit3.commit();
                    SharedPreferences.Editor edit4 = getSharedPreferences("list4", MODE_PRIVATE).edit();
                    edit4.putInt("num", namelist2.size());
                    for(int i = 0; i < namelist2.size(); i++){
                        edit4.putString("item_"+i, namelist2.get(i));
                    }
                    edit4.commit();
                    SharedPreferences.Editor edit5 = getSharedPreferences("list5", MODE_PRIVATE).edit();
                    edit5.putInt("num", timelist2.size());
                    for(int i = 0; i < timelist2.size(); i++){
                        edit5.putString("item_"+i, timelist2.get(i));
                    }
                    edit5.commit();
                    SharedPreferences.Editor edit6 = getSharedPreferences("list6", MODE_PRIVATE).edit();
                    edit6.putInt("num", pricelist.size());
                    for(int i = 0; i < pricelist.size(); i++){
                        edit6.putString("item_"+i, pricelist.get(i));
                    }
                    edit6.commit();
                    SharedPreferences.Editor edit7 = getSharedPreferences("list7", MODE_PRIVATE).edit();
                    edit7.putInt("num", IDlist.size());
                    for(int i = 0; i < IDlist.size(); i++){
                        edit7.putString("item_"+i, IDlist.get(i));
                    }
                    edit7.commit();
                    Intent intent = new Intent();
                    intent.setClass(Member29.this,Member30.class);
                    startActivity(intent);
                }
            });
        }
    };
    private Runnable mutiThread1 = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("bookinglist2.php"));
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
                String post_data = URLEncoder.encode("acc", "UTF-8")+"="+URLEncoder.encode(Account, "UTF-8");
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
                            String time = info.getString("Booking_Time");
                            String state = info.getString("State");
                            String theatername = info.getString("Theater_Name");
                            String moviename = info.getString("Movie_Name");
                            String screeningstime = info.getString("Screenings_Time");
                            String totalprice = info.getString("Total_Price");
                            String serialnumber = info.getString("Serial_Number");
                            String ID = info.getString("Ticket_ID");
                            result8 = time.toString();
                            result9 = state.toString();
                            result10 = theatername.toString();
                            result11 = moviename.toString();
                            result12 = screeningstime.toString();
                            result13 = totalprice.toString();
                            result14 = serialnumber.toString();
                            result15 = ID.toString();
                            timelist1_2.add(result8);
                            statelist_2.add(result9);
                            namelist1_2.add(result10);
                            namelist2_2.add(result11);
                            timelist2_2.add(result12);
                            pricelist_2.add(result13);
                            numberlist.add(result14);
                            IDlist_2.add(result15);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result8 = e.toString();
                result9 = e.toString();
                result10 = e.toString();
                result11 = e.toString();
                result12 = e.toString();
                result13 = e.toString();
                result14 = e.toString();
                result15 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    SharedPreferences.Editor edit1 = getSharedPreferences("list8", MODE_PRIVATE).edit();
                    edit1.putInt("num", timelist1_2.size());
                    for(int i = 0; i < timelist1_2.size(); i++){
                        edit1.putString("item_"+i, timelist1_2.get(i));
                    }
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("list9", MODE_PRIVATE).edit();
                    edit2.putInt("num", statelist_2.size());
                    for(int i = 0; i < statelist_2.size(); i++){
                        edit2.putString("item_"+i, statelist_2.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("list10", MODE_PRIVATE).edit();
                    edit3.putInt("num", namelist1_2.size());
                    for(int i = 0; i < namelist1_2.size(); i++){
                        edit3.putString("item_"+i, namelist1_2.get(i));
                    }
                    edit3.commit();
                    SharedPreferences.Editor edit4 = getSharedPreferences("list11", MODE_PRIVATE).edit();
                    edit4.putInt("num", namelist2_2.size());
                    for(int i = 0; i < namelist2_2.size(); i++){
                        edit4.putString("item_"+i, namelist2_2.get(i));
                    }
                    edit4.commit();
                    SharedPreferences.Editor edit5 = getSharedPreferences("list12", MODE_PRIVATE).edit();
                    edit5.putInt("num", timelist2_2.size());
                    for(int i = 0; i < timelist2_2.size(); i++){
                        edit5.putString("item_"+i, timelist2_2.get(i));
                    }
                    edit5.commit();
                    SharedPreferences.Editor edit6 = getSharedPreferences("list13", MODE_PRIVATE).edit();
                    edit6.putInt("num", pricelist_2.size());
                    for(int i = 0; i < pricelist_2.size(); i++){
                        edit6.putString("item_"+i, pricelist_2.get(i));
                    }
                    edit6.commit();
                    SharedPreferences.Editor edit7 = getSharedPreferences("list14", MODE_PRIVATE).edit();
                    edit7.putInt("num", numberlist.size());
                    for(int i = 0; i < numberlist.size(); i++){
                        edit7.putString("item_"+i, numberlist.get(i));
                    }
                    edit7.commit();
                    SharedPreferences.Editor edit8 = getSharedPreferences("list15", MODE_PRIVATE).edit();
                    edit8.putInt("num", IDlist_2.size());
                    for(int i = 0; i < IDlist_2.size(); i++){
                        edit8.putString("item_"+i, IDlist_2.get(i));
                    }
                    edit8.commit();
                    Intent intent = new Intent();
                    intent.setClass(Member29.this,Member33.class);
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
            intent.setClass(Member29.this, Member4.class);
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