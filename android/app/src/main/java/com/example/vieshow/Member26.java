package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Arrays;

public class Member26 extends AppCompatActivity {

    ImageButton back;
    TextView name, engname, series, time, address, cinema, formula, total;
    EditText enter;
    Spinner type, amount;
    Button choose, add, next;
    ArrayList<String> typelist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> pricelist = new ArrayList<>();
    String[] seatlist = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "J1", "J2", "J3", "J4", "J5", "J6", "J7", "J8"};
    ArrayList<String> emptyseatlist = new ArrayList<>(Arrays.asList(seatlist));
    String ID, tot = "", IP;
    int all = 0, ticketnum = 0;
    String result1, result2, result3, result4, result5, result6, result7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member26);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");

        SharedPreferences List1 = getSharedPreferences("namelist3", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            namelist.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("pricelist3", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            pricelist.add(item2);
        }

        Thread thread = new Thread(mutiThread);
        thread.start();

        CharSequence[] amountlist = getResources().getStringArray(R.array.amount_array2);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });
        name = (TextView) findViewById(R.id.name);
        engname = (TextView) findViewById(R.id.engname);
        series = (TextView) findViewById(R.id.series);
        time = (TextView) findViewById(R.id.time);
        address = (TextView) findViewById(R.id.address);
        cinema = (TextView) findViewById(R.id.cinema);
        type = (Spinner) findViewById(R.id.type);
        String ticket;
        for(int i = 0; i < namelist.size(); i++){
            ticket = namelist.get(i) + "($" + pricelist.get(i) + ")";
            typelist.add(ticket);
        }
        /*typelist.add("免費兌換券($0)");
        typelist.add("IMAX免費兌換券-日新($0)");
        typelist.add("IMAX免費兌換券威秀($0)");
        typelist.add("3D免費兌換券($0)");
        typelist.add("TITAN免費兌換券($0)");
        typelist.add("愛心票($155)");
        typelist.add("敬老票($155)");*/
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter1);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount = (Spinner) findViewById(R.id.amount);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount.setAdapter(adapter2);
        amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose = (Button) findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type.getSelectedItem().toString();
                    String s = "";
                    if(tot.contains(s2) == true){
                        int index = tot.indexOf(s2);
                        for(int i = index; tot.charAt(i) != '\n'; i++){
                            s += tot.charAt(i);
                        }
                        s += '\n';
                        int index2 = s.indexOf('x');
                        for(int i = index2 + 1; s.charAt(i) != '='; i++){
                            s1 += s.charAt(i);
                        }
                        tot = tot.replace(s, "");
                        Toast.makeText(Member26.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        if(s2.contains("雙人") == true){
                            n2 *= 2;
                        }
                        ticketnum -= n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member26.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount.getSelectedItem().toString();
                    String s2 = type.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member26.this, "您選擇了" + s1 + "張" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
                    if(s2.contains("雙人") == true){
                        n2 *= 2;
                    }
                    ticketnum += n2;
                    all += n3;
                    String s = "";
                    if(tot.contains(s2) == true){
                        int index = tot.indexOf(s2);
                        for(int i = index; tot.charAt(i) != '\n'; i++){
                            s += tot.charAt(i);
                        }
                        s += '\n';
                        int index2 = s.indexOf('x');
                        String s3 = "";
                        for(int i = index2 + 1; s.charAt(i) != '='; i++){
                            s3 += s.charAt(i);
                        }
                        tot = tot.replace(s, "");
                        int n4 = Integer.parseInt(p);
                        int n5 = Integer.parseInt(s3);
                        int n6 = n4 * n5;
                        if(s2.contains("雙人") == true){
                            n5 *= 2;
                        }
                        ticketnum -= n5;
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        formula = (TextView) findViewById(R.id.formula);
        total = (TextView) findViewById(R.id.total);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(ticketnum == 0){
                    Toast.makeText(Member26.this, "您尚未選擇票種與張數！", Toast.LENGTH_SHORT).show();
                }
                else if(ticketnum > 6){
                    Toast.makeText(Member26.this, "每筆交易最多可購買6張電影票！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(Member26.this,Member27.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putInt("num", ticketnum);
                    bundle.putString("formula", tot);
                    bundle.putInt("total", all);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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
                            String seat = info.getString("Seat");
                            result7 = seat.toString();
                            emptyseatlist.remove(result7);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result7 = e.toString();
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
            intent.setClass(Member26.this, Member4.class);
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