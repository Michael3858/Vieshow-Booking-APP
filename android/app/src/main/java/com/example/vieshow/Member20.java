package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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

public class Member20 extends AppCompatActivity {

    ImageButton back;
    CheckBox check;
    Button booking;
    ArrayList<String> classifylist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> pricelist = new ArrayList<>();
    ArrayList<String> classifylist2 = new ArrayList<>();
    ArrayList<String> namelist2 = new ArrayList<>();
    ArrayList<String> pricelist2 = new ArrayList<>();
    String ID, IP;
    String result1, result2, result3, result4, result5, result6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member20);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        classifylist.clear();
        namelist.clear();
        pricelist.clear();
        classifylist2.clear();
        namelist2.clear();
        pricelist2.clear();

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        //Toast.makeText(Member20.this, ID, Toast.LENGTH_SHORT).show();

        Thread thread = new Thread(mutiThread);
        thread.start();

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });
        check = (CheckBox) findViewById(R.id.check);

        booking = (Button) findViewById(R.id.booking);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked() == true){
                    Intent intent = new Intent();
                    intent.setClass(Member20.this,Member21.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Member20.this, "請先勾選同意上述規定!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("tickettype.php"));
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
                            String classify = info.getString("Type_Classify");
                            String name = info.getString("Type_Name");
                            String price = info.getString("Type_Price");
                            result1 = classify.toString();
                            result2 = name.toString();
                            result3 = price.toString();
                            classifylist.add(result1);
                            namelist.add(result2);
                            pricelist.add(result3);
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
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    SharedPreferences.Editor edit1 = getSharedPreferences("classifylist", MODE_PRIVATE).edit();
                    edit1.putInt("num", classifylist.size());
                    for(int i = 0; i < classifylist.size(); i++){
                        edit1.putString("item_"+i, classifylist.get(i));
                    }
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("namelist", MODE_PRIVATE).edit();
                    edit2.putInt("num", namelist.size());
                    for(int i = 0; i < namelist.size(); i++){
                        edit2.putString("item_"+i, namelist.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("pricelist", MODE_PRIVATE).edit();
                    edit3.putInt("num", pricelist.size());
                    for(int i = 0; i < pricelist.size(); i++){
                        edit3.putString("item_"+i, pricelist.get(i));
                    }
                    edit3.commit();
                    Thread thread1 = new Thread(mutiThread1);
                    thread1.start();
                }
            });
        }
    };
    private Runnable mutiThread1 = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("meals.php"));
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
                            String classify = info.getString("Meal_Classify");
                            String name = info.getString("Meal_Name");
                            String price = info.getString("Meal_Price");
                            result4 = classify.toString();
                            result5 = name.toString();
                            result6 = price.toString();
                            classifylist2.add(result4);
                            namelist2.add(result5);
                            pricelist2.add(result6);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result4 = e.toString();
                result5 = e.toString();
                result6 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    SharedPreferences.Editor edit4 = getSharedPreferences("classifylist2", MODE_PRIVATE).edit();
                    edit4.putInt("num", classifylist2.size());
                    for(int i = 0; i < classifylist2.size(); i++){
                        edit4.putString("item_"+i, classifylist2.get(i));
                    }
                    edit4.commit();
                    SharedPreferences.Editor edit5 = getSharedPreferences("namelist2", MODE_PRIVATE).edit();
                    edit5.putInt("num", namelist2.size());
                    for(int i = 0; i < namelist2.size(); i++){
                        edit5.putString("item_"+i, namelist2.get(i));
                    }
                    edit5.commit();
                    SharedPreferences.Editor edit6 = getSharedPreferences("pricelist2", MODE_PRIVATE).edit();
                    edit6.putInt("num", pricelist2.size());
                    for(int i = 0; i < pricelist2.size(); i++){
                        edit6.putString("item_"+i, pricelist2.get(i));
                    }
                    edit6.commit();
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
            intent.setClass(Member20.this, Member4.class);
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