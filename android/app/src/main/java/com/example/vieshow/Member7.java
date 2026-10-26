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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Member7 extends AppCompatActivity {

    Button button1, button2;
    ImageButton back;
    String result1, result2, result3, result4, result5, result6;
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> imagelist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> engnamelist = new ArrayList<>();
    ArrayList<String> serieslist = new ArrayList<>();
    ArrayList<String> showtimelist = new ArrayList<>();
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member7);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

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
                Thread thread2 = new Thread(mutiThread2);
                thread2.start();
            }
        });
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("movielist.php"));
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

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
                            String id = info.getString("Movie_ID");
                            String image = info.getString("Pic_URL");
                            String name = info.getString("Movie_Name");
                            String engname = info.getString("Movie_EngName");
                            String series = info.getString("Movie_Series");
                            String showtime = info.getString("Movie_Showtime");
                            result1 = id.toString();
                            result2 = image.toString();
                            result3 = name.toString();
                            result4 = engname.toString();
                            result5 = series.toString();
                            result6 = showtime.toString();
                            IDlist.add(result1);
                            imagelist.add(result2);
                            namelist.add(result3);
                            engnamelist.add(result4);
                            serieslist.add(result5);
                            showtimelist.add(result6);
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
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    SharedPreferences.Editor edit1 = getSharedPreferences("ListString1", MODE_PRIVATE).edit();
                    edit1.putInt("num", IDlist.size());
                    for(int i = 0; i < IDlist.size(); i++){
                        edit1.putString("item_"+i, IDlist.get(i));
                    }
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("ListString2", MODE_PRIVATE).edit();
                    edit2.putInt("num", imagelist.size());
                    for(int i = 0; i < imagelist.size(); i++){
                        edit2.putString("item_"+i, imagelist.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("ListString3", MODE_PRIVATE).edit();
                    edit3.putInt("num", namelist.size());
                    for(int i = 0; i < namelist.size(); i++){
                        edit3.putString("item_"+i, namelist.get(i));
                    }
                    edit3.commit();
                    SharedPreferences.Editor edit4 = getSharedPreferences("ListString4", MODE_PRIVATE).edit();
                    edit4.putInt("num", engnamelist.size());
                    for(int i = 0; i < engnamelist.size(); i++){
                        edit4.putString("item_"+i, engnamelist.get(i));
                    }
                    edit4.commit();
                    SharedPreferences.Editor edit5 = getSharedPreferences("ListString5", MODE_PRIVATE).edit();
                    edit5.putInt("num", serieslist.size());
                    for(int i = 0; i < serieslist.size(); i++){
                        edit5.putString("item_"+i, serieslist.get(i));
                    }
                    edit5.commit();
                    SharedPreferences.Editor edit6 = getSharedPreferences("ListString6", MODE_PRIVATE).edit();
                    edit6.putInt("num", showtimelist.size());
                    for(int i = 0; i < showtimelist.size(); i++){
                        edit6.putString("item_"+i, showtimelist.get(i));
                    }
                    edit6.commit();
                    Intent intent = new Intent();
                    intent.setClass(Member7.this,Member6.class);
                    startActivity(intent);
                }
            });
        }
    };
    private Runnable mutiThread2 = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("movielist.php"));
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

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
                            String id = info.getString("Movie_ID");
                            String image = info.getString("Pic_URL");
                            String name = info.getString("Movie_Name");
                            String engname = info.getString("Movie_EngName");
                            String series = info.getString("Movie_Series");
                            String showtime = info.getString("Movie_Showtime");
                            result1 = id.toString();
                            result2 = image.toString();
                            result3 = name.toString();
                            result4 = engname.toString();
                            result5 = series.toString();
                            result6 = showtime.toString();
                            IDlist.add(result1);
                            imagelist.add(result2);
                            namelist.add(result3);
                            engnamelist.add(result4);
                            serieslist.add(result5);
                            showtimelist.add(result6);
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
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    SharedPreferences.Editor edit1 = getSharedPreferences("ListString1", MODE_PRIVATE).edit();
                    edit1.putInt("num", IDlist.size());
                    for(int i = 0; i < IDlist.size(); i++){
                        edit1.putString("item_"+i, IDlist.get(i));
                    }
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("ListString2", MODE_PRIVATE).edit();
                    edit2.putInt("num", imagelist.size());
                    for(int i = 0; i < imagelist.size(); i++){
                        edit2.putString("item_"+i, imagelist.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("ListString3", MODE_PRIVATE).edit();
                    edit3.putInt("num", namelist.size());
                    for(int i = 0; i < namelist.size(); i++){
                        edit3.putString("item_"+i, namelist.get(i));
                    }
                    edit3.commit();
                    SharedPreferences.Editor edit4 = getSharedPreferences("ListString4", MODE_PRIVATE).edit();
                    edit4.putInt("num", engnamelist.size());
                    for(int i = 0; i < engnamelist.size(); i++){
                        edit4.putString("item_"+i, engnamelist.get(i));
                    }
                    edit4.commit();
                    SharedPreferences.Editor edit5 = getSharedPreferences("ListString5", MODE_PRIVATE).edit();
                    edit5.putInt("num", serieslist.size());
                    for(int i = 0; i < serieslist.size(); i++){
                        edit5.putString("item_"+i, serieslist.get(i));
                    }
                    edit5.commit();
                    SharedPreferences.Editor edit6 = getSharedPreferences("ListString6", MODE_PRIVATE).edit();
                    edit6.putInt("num", showtimelist.size());
                    for(int i = 0; i < showtimelist.size(); i++){
                        edit6.putString("item_"+i, showtimelist.get(i));
                    }
                    edit6.commit();
                    Intent intent = new Intent();
                    intent.setClass(Member7.this,Member8.class);
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
            intent.setClass(Member7.this, Member4.class);
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