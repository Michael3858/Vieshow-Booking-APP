package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NonMember11 extends AppCompatActivity {

    Spinner cinema;
    Button search;
    ArrayList<String> cinemalist = new ArrayList<>();
    ArrayList<String> theaterIDlist = new ArrayList<>();
    ArrayList<String> movieIDlist = new ArrayList<>();
    ArrayList<String> movienamelist = new ArrayList<>();
    ArrayList<String> screeningtimelist = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate;
    String str, str2, ID, IP;
    String result1, result2, result3, result4, result5, response;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member11);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = c.getTime();
        str2 = formatter.format(tomorrow);

        cinemalist.add("請選擇影城");
        getalltheaterID();
        cinema = (Spinner) findViewById(R.id.cinema);
        /*cinemalist.add("台北信義威秀影城");
        cinemalist.add("MUVIE CINEMAS台北松仁威秀");
        cinemalist.add("MUVIE CINEMAS台北松仁威秀(MUCROWN)");
        cinemalist.add("台北京站威秀影城");
        cinemalist.add("板橋大遠百威秀影城");
        cinemalist.add("中和環球影城");
        cinemalist.add("林口MITSUI OUTLET PARK威秀影城");
        cinemalist.add("林口MITSUI OUTLET PARK威秀影城(Mappa)");
        cinemalist.add("桃園統領威秀影城");
        cinemalist.add("新竹大遠百威秀影城");
        cinemalist.add("新竹大遠百威秀影城(GC)");
        cinemalist.add("新竹巨城威秀影城");
        cinemalist.add("頭份尚順威秀影城");
        cinemalist.add("台中大遠百威秀影城");
        cinemalist.add("台中Tiger City威秀影城");
        cinemalist.add("台中Tiger City威秀影城(GC)");
        cinemalist.add("台中大魯閣新時代威秀影城");
        cinemalist.add("台南大遠百威秀影城");
        cinemalist.add("台南FOCUS威秀影城");
        cinemalist.add("台南南紡威秀影城");
        cinemalist.add("台南南紡威秀影城(GC)");
        cinemalist.add("高雄大遠百威秀影城");
        cinemalist.add("高雄大遠百威秀影城(GC)");
        cinemalist.add("花蓮新天堂樂園威秀影城");*/
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cinemalist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cinema.setAdapter(adapter);
        cinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                if(cinema.getSelectedItemPosition() == 0){
                    Toast.makeText(NonMember11.this, "請選擇影城!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Thread thread = new Thread(mutiThread);
                    thread.start();
                }
            }
        });
    }

    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                ID = theaterIDlist.get(cinema.getSelectedItemPosition() - 1);
                URL url = new URL(ApiConfig.url("screeninglist.php"));
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
                String post_data = URLEncoder.encode("ID", "UTF-8")+"="+URLEncoder.encode(ID, "UTF-8")+"&"
                        +URLEncoder.encode("date1", "UTF-8")+"="+URLEncoder.encode(str, "UTF-8")+"&"
                        +URLEncoder.encode("date2", "UTF-8")+"="+URLEncoder.encode(str2, "UTF-8");
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
                            String MovieID = info.getString("Movie_ID");
                            String MovieName = info.getString("Movie_Name");
                            String Screeningtime = info.getString("Screenings_Time");
                            result3 = MovieID.toString();
                            result4 = MovieName.toString();
                            result5 = Screeningtime.toString();
                            movieIDlist.add(result3);
                            movienamelist.add(result4);
                            screeningtimelist.add(result5);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result3 = e.toString();
                result4 = e.toString();
                result5 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    SharedPreferences.Editor edit1 = getSharedPreferences("movieIDlist", MODE_PRIVATE).edit();
                    edit1.putInt("num", movieIDlist.size());
                    for(int i = 0; i < movieIDlist.size(); i++){
                        edit1.putString("item_"+i, movieIDlist.get(i));
                    }
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("movienamelist", MODE_PRIVATE).edit();
                    edit2.putInt("num", movienamelist.size());
                    for(int i = 0; i < movienamelist.size(); i++){
                        edit2.putString("item_"+i, movienamelist.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("screeningtimelist", MODE_PRIVATE).edit();
                    edit3.putInt("num", screeningtimelist.size());
                    for(int i = 0; i < screeningtimelist.size(); i++){
                        edit3.putString("item_"+i, screeningtimelist.get(i));
                    }
                    edit3.commit();
                    Intent intent = new Intent();
                    intent.setClass(NonMember11.this,NonMember12.class);
                    startActivity(intent);
                }
            });
        }
    };

    private void getalltheaterID() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        response = executeHttpGet();
                    }
                }

        ).start();
    }

    private String executeHttpGet() {

        HttpURLConnection con=null;
        InputStream in=null;
        String path=ApiConfig.url("getalltheaterID.php"); //[local API server]需改成自己的ip
        try {
            con= (HttpURLConnection) new URL(path).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200){

                in=con.getInputStream();
                return parseInfo(in);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String parseInfo(InputStream in) throws IOException, JSONException {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb=new StringBuilder();
        String line=null;
        while ((line=br.readLine())!=null){
            sb.append(line+"\n");
            JSONArray dataJson = new JSONArray(line);
            for(int i = 0; i < dataJson.length(); i++){
                JSONObject info = dataJson.getJSONObject(i);
                String theaterID = info.getString("Theater_ID");
                String theaterName = info.getString("Theater_Name");
                result1 = theaterID.toString();
                result2 = theaterName.toString();
                theaterIDlist.add(result1);
                cinemalist.add(result2);
            }
        }
        return sb.toString();
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
            intent.setClass(NonMember11.this, NonMember1.class);
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