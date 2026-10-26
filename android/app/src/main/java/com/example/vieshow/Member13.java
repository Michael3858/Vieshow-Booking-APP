package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

public class Member13 extends AppCompatActivity {

    ImageButton back;
    ImageView pic, seatpic;
    String ID;
    TextView name, engname, introduction, accessibility, seat, phoneNum, address, transportation, map;
    String result1, result2, result3, result4, result5, result6, result7, result8, result9, result10, result11;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member13);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });

        pic = (ImageView) findViewById(R.id.pic);
        name = (TextView) findViewById(R.id.name);
        engname = (TextView) findViewById(R.id.engname);
        introduction = (TextView) findViewById(R.id.introduction);
        accessibility = (TextView) findViewById(R.id.accessibility);
        seat = (TextView) findViewById(R.id.seat);
        seatpic = (ImageView) findViewById(R.id.seatpic);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        address = (TextView) findViewById(R.id.address);
        transportation = (TextView) findViewById(R.id.transportation);
        map = (TextView) findViewById(R.id.map);
        Thread thread = new Thread(mutiThread);
        thread.start();
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("theater.php"));
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
                            String name = info.getString("Theater_Name");
                            String engname = info.getString("Theater_EngName");
                            String introduction = info.getString("Theater_Intro");
                            String accessibility = info.getString("Theater_Accessibility");
                            String seat = info.getString("Theater_Seat");
                            String seatpicURL = info.getString("SeatPic_URL");
                            String phonenum = info.getString("Theater_PhoneNum");
                            String address = info.getString("Theater_Address");
                            String transport = info.getString("Theater_Transport");
                            String mapURL = info.getString("Map_URL");
                            result1 = picURL.toString();
                            result2 = name.toString();
                            result3 = engname.toString();
                            result4 = introduction.toString();
                            result5 = accessibility.toString();
                            result6 = seat.toString();
                            result7 = seatpicURL.toString();
                            result8 = phonenum.toString();
                            result9 = address.toString();
                            result10 = transport.toString();
                            result11 = mapURL.toString();
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
                    Picasso.with(Member13.this).load(result1).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fit().into(pic);
                    name.setText(result2);
                    engname.setText(result3);
                    introduction.setText(result4);
                    accessibility.setText(result5);
                    seat.setText(result6);
                    if(!result7.equals("")){
                        Picasso.with(Member13.this).load(result7).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(1000,1000).centerInside().into(seatpic);
                    }
                    else{
                        seatpic.setVisibility(View.GONE);
                    }
                    phoneNum.setText("服務專線：\n" + result8);
                    address.setText("影城地址：\n" + result9);
                    transportation.setText("交通資訊：\n" + result10);
                    map.setText("地圖：\n" + result11);
                    map.setAutoLinkMask(Linkify.WEB_URLS);
                    map.setMovementMethod(LinkMovementMethod.getInstance());
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
            intent.setClass(Member13.this, Member4.class);
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