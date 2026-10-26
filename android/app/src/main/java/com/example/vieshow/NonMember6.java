package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class NonMember6 extends AppCompatActivity {

    ImageButton back;
    ImageView pic;
    String ID;
    TextView series, name, engname, date, director, actor, type, length, trailer, synopsis;
    String result1, result2, result3, result4, result5, result6, result7, result8, result9, result10, result11;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member6);

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
        series = (TextView) findViewById(R.id.series);
        name = (TextView) findViewById(R.id.name);
        engname = (TextView) findViewById(R.id.engname);
        date = (TextView) findViewById(R.id.date);
        director = (TextView) findViewById(R.id.director);
        actor = (TextView) findViewById(R.id.actor);
        type = (TextView) findViewById(R.id.type);
        length = (TextView) findViewById(R.id.length);
        trailer = (TextView) findViewById(R.id.trailer);
        synopsis = (TextView) findViewById(R.id.synopsis);
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
                    Picasso.with(NonMember6.this).load(result1).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fit().into(pic);
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
                    name.setText(result3);
                    engname.setText(result4);
                    date.setText("上映日期：" + result5);
                    director.setText("導演：" + result6);
                    actor.setText("演員：" + result7);
                    type.setText("類型：" + result8);
                    length.setText("片長：" + result9);
                    trailer.setText("預告片：" + result10);
                    trailer.setAutoLinkMask(Linkify.WEB_URLS);
                    trailer.setMovementMethod(LinkMovementMethod.getInstance());
                    synopsis.setText(result11);
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
            intent.setClass(NonMember6.this, NonMember1.class);
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