package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class Member28 extends AppCompatActivity {

    TextView name, engname, series, time, address, cinema, seat, ticket, total, number;
    Button back;
    ArrayList<String> chosenlist = new ArrayList<>();
    String ID, tot, chosenseat = "", serialnum, IP;
    int ticketnum, all;
    String result1, result2, result3, result4, result5, result6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member28);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        ticketnum = bundle.getInt("num");
        tot = bundle.getString("formula");
        all = bundle.getInt("total");
        serialnum = bundle.getString("serial");
        SharedPreferences List = getSharedPreferences("chosenlist2", MODE_PRIVATE);
        int num = List.getInt("num", 0);
        for (int i = 0; i < num; i++) {
            String item = List.getString("item_" + i, null);
            chosenlist.add(item);
        }
        Thread thread = new Thread(mutiThread);
        thread.start();

        name = (TextView) findViewById(R.id.name);
        engname = (TextView) findViewById(R.id.engname);
        series = (TextView) findViewById(R.id.series);
        series.setText("護 6+");
        time = (TextView) findViewById(R.id.time);
        address = (TextView) findViewById(R.id.address);
        cinema = (TextView) findViewById(R.id.cinema);
        for(int i = 0; i < chosenlist.size(); i++){
            if(i == 0){
                chosenseat = chosenseat + chosenlist.get(i);
            }
            else{
                chosenseat = chosenseat +  ", " + chosenlist.get(i);
            }
        }
        seat = (TextView) findViewById(R.id.seat);
        seat.setText(chosenseat);
        ticket = (TextView) findViewById(R.id.ticket);
        tot = tot.substring(0, tot.length() - 1);
        ticket.setText("票種：" + tot);
        total = (TextView) findViewById(R.id.total);
        total.setText("總計金額：NT$" + all);
        number = (TextView) findViewById(R.id.number);
        number.setText("取票序號：" + serialnum);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Member28.this,Member4.class);
                startActivity(intent);
            }
        });
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("movieinfo.php"));
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
                            String name = info.getString("Movie_Name");
                            String engname = info.getString("Movie_EngName");
                            String series = info.getString("Movie_Series");
                            String screeningtime = info.getString("Screenings_Time");
                            String theatername = info.getString("Theater_Name");
                            String auditoriumname = info.getString("Auditorium_Name");
                            result1 = name.toString();
                            result2 = engname.toString();
                            result3 = series.toString();
                            result4 = screeningtime.toString();
                            result5 = theatername.toString();
                            result6 = auditoriumname.toString();
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
                public void run() {
                    name.setText(result1);
                    engname.setText(result2);
                    series.setText(result3);
                    String s = "護 6+";
                    if(s.charAt(0) == '普'){
                        series.setBackgroundColor(Color.parseColor("#00DD00"));
                    }
                    else if(s.charAt(0) == '護'){
                        series.setBackgroundColor(Color.parseColor("#0066FF"));
                    }
                    else if(s.charAt(0) == '輔'){
                        series.setBackgroundColor(Color.parseColor("#FF8800"));
                    }
                    else if(s.charAt(0) == '限'){
                        series.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                    time.setText(result4);
                    address.setText(result5);
                    cinema.setText(result6);
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
            intent.setClass(Member28.this, Member4.class);
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