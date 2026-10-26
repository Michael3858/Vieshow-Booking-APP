package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Member24 extends AppCompatActivity {

    ImageButton back;
    TextView address, name, time, amount, total, remind, way, email, money;
    EditText enter, cardnum, securitycode;
    Spinner bill, month, year;
    CheckBox check;
    Button confirm;
    ArrayList<String> chosenlist = new ArrayList<>();
    ArrayList<String> typeIDlist = new ArrayList<>();
    ArrayList<String> typenamelist = new ArrayList<>();
    ArrayList<String> mealIDlist = new ArrayList<>();
    ArrayList<String> mealnamelist = new ArrayList<>();
    String ID, tot, acc, IP, str;
    int ticketnum, all;
    int IDcount;
    String result1, result2, result3, result4, result5, result6, response, response2, response3;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member24);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        ticketnum = bundle.getInt("num");
        tot = bundle.getString("formula");
        all = bundle.getInt("total");
        SharedPreferences List = getSharedPreferences("chosenlist", MODE_PRIVATE);
        int num = List.getInt("num", 0);
        for (int i = 0; i < num; i++) {
            String item = List.getString("item_" + i, null);
            chosenlist.add(item);
        }

        SharedPreferences user1 = getSharedPreferences("account",MODE_PRIVATE);
        acc = user1.getString("acc","");

        getticketID();
        getalltickettype();
        getallmeals();
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
        address = (TextView) findViewById(R.id.address);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        amount = (TextView) findViewById(R.id.amount);
        amount.setText("票數：" + ticketnum);
        total = (TextView) findViewById(R.id.total);
        total.setText("總計：" + all);
        String[] billlist = {"捐贈", "歸戶", "紙本"};
        bill = (Spinner) findViewById(R.id.bill);
        remind = (TextView) findViewById(R.id.remind);
        way = (TextView) findViewById(R.id.way);
        enter = (EditText) findViewById(R.id.enter);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, billlist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bill.setAdapter(adapter1);
        bill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    remind.setText("您目前選擇的開立方式是捐贈");
                    way.setText("愛心捐贈");
                    enter.setHint("請輸入愛心碼");
                }
                if(position == 1){
                    remind.setText("您目前選擇的開立方式是歸戶");
                    way.setText("個人載具");
                    enter.setHint("請輸入手機條碼或自然人憑證");
                }
                if(position == 2){
                    remind.setText("您目前選擇的開立方式是紙本");
                    way.setText("統一編號");
                    enter.setHint("非必填");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        email = (TextView) findViewById(R.id.email);
        email.setText(acc);
        cardnum = (EditText) findViewById(R.id.cardnum);
        securitycode = (EditText) findViewById(R.id.securitycode);
        String[] monthlist = {"月份", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        month = (Spinner) findViewById(R.id.month);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, monthlist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter2);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] yearlist = {"年份", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};
        year = (Spinner) findViewById(R.id.year);
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearlist);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter3);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        money = (TextView) findViewById(R.id.money);
        money.setText("NT$" + all);
        check = (CheckBox) findViewById(R.id.check);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(cardnum.getText().toString().equals("")){
                    Toast.makeText(Member24.this, "請輸入信用卡卡號!", Toast.LENGTH_SHORT).show();
                }
                else if(securitycode.getText().toString().equals("")){
                    Toast.makeText(Member24.this, "請輸入卡片安全碼!", Toast.LENGTH_SHORT).show();
                }
                else if(month.getSelectedItemPosition() == 0 || year.getSelectedItemPosition() == 0){
                    Toast.makeText(Member24.this, "請選擇卡片到期日!", Toast.LENGTH_SHORT).show();
                }
                else if(check.isChecked() == false){
                    Toast.makeText(Member24.this, "請先勾選同意上述政策!", Toast.LENGTH_SHORT).show();
                }
                else{
                    curDate = new Date(System.currentTimeMillis());
                    str = formatter.format(curDate);
                    booking_record();
                    //for(int i = 0; i < 1000000; i++);
                    for(int i = 0; i < chosenlist.size(); i++){
                        String seat = chosenlist.get(i);
                        //Toast.makeText(Member24.this, seat, Toast.LENGTH_SHORT).show();
                        booking_seat(seat);
                        //for(int j = 0; j < 1000000; j++);
                    }
                    for(int i = 0; i < typeIDlist.size(); i++){
                        if(tot.contains(typenamelist.get(i))){
                            int index = tot.indexOf(typenamelist.get(i));
                            String s = "";
                            for(int j = index; tot.charAt(j) != '\n'; j++){
                                s += tot.charAt(j);
                            }
                            s += '\n';
                            int index2 = s.indexOf('x');
                            String s3 = "";
                            for(int j = index2 + 1; s.charAt(j) != '='; j++){
                                s3 += s.charAt(j);
                            }
                            String typeID = typeIDlist.get(i);
                            //Toast.makeText(Member24.this, typeID + " " + s3, Toast.LENGTH_SHORT).show();
                            booking_ticket_type(typeID, s3);
                            //for(int j = 0; j < 1000000; j++);
                        }
                    }
                    for(int i = 0; i < mealIDlist.size(); i++){
                        if(tot.contains(mealnamelist.get(i))){
                            //Toast.makeText(Member24.this, mealnamelist.get(i), Toast.LENGTH_SHORT).show();
                            int index = tot.indexOf(mealnamelist.get(i));
                            String s = "";
                            for(int j = index; tot.charAt(j) != '\n'; j++){
                                s += tot.charAt(j);
                            }
                            s += '\n';
                            int index2 = s.indexOf('x');
                            String s3 = "";
                            for(int j = index2 + 1; s.charAt(j) != '='; j++){
                                s3 += s.charAt(j);
                            }
                            String mealID = mealIDlist.get(i);
                            //Toast.makeText(Member24.this, mealID + " " + s3, Toast.LENGTH_SHORT).show();
                            booking_meals(mealID, s3);
                            //for(int j = 0; j < 1000000; j++);
                        }
                    }
                    Intent intent = new Intent();
                    intent.setClass(Member24.this,Member4.class);
                    Toast.makeText(Member24.this, "您已成功完成訂票!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }
    private void getticketID() {
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
        String path=ApiConfig.url("getticketID.php"); //[local API server]需改成自己的ip
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
                int count = info.getInt("IDcount");
                IDcount = count;
                IDcount++;
            }
        }
        return sb.toString();
    }
    private void getalltickettype() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        response2 = executeHttpGet2();
                    }
                }

        ).start();
    }

    private String executeHttpGet2() {

        HttpURLConnection con=null;
        InputStream in=null;
        String path=ApiConfig.url("getalltickettype.php"); //[local API server]需改成自己的ip
        try {
            con= (HttpURLConnection) new URL(path).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200){

                in=con.getInputStream();
                return parseInfo2(in);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String parseInfo2(InputStream in) throws IOException, JSONException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
            JSONArray dataJson = new JSONArray(line);
            for (int i = 0; i < dataJson.length(); i++) {
                JSONObject info = dataJson.getJSONObject(i);
                String ID = info.getString("Type_ID");
                String Name = info.getString("Type_Name");
                typeIDlist.add(ID);
                typenamelist.add(Name);
            }
        }
        return sb.toString();
    }
    private void getallmeals() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        response3 = executeHttpGet3();
                    }
                }

        ).start();
    }

    private String executeHttpGet3() {

        HttpURLConnection con=null;
        InputStream in=null;
        String path=ApiConfig.url("getallmeals.php"); //[local API server]需改成自己的ip
        try {
            con= (HttpURLConnection) new URL(path).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200){

                in=con.getInputStream();
                return parseInfo3(in);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String parseInfo3(InputStream in) throws IOException, JSONException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
            JSONArray dataJson = new JSONArray(line);
            for (int i = 0; i < dataJson.length(); i++) {
                JSONObject info = dataJson.getJSONObject(i);
                String ID = info.getString("Meal_ID");
                String Name = info.getString("Meal_Name");
                mealIDlist.add(ID);
                mealnamelist.add(Name);
            }
        }
        return sb.toString();
    }
    private void booking_record() {
        String TicketID = Integer.toString(IDcount);
        String UserEmail = acc;
        String ScreeningsID = ID;
        String BookingPayment = "線上付款";
        String BookingTime = str;
        String TotalPrice = Integer.toString(all);
        String State = "已付款";
        try {
            jsonObject.put("Ticket_ID", TicketID);
            jsonObject.put("User_Email", UserEmail);
            jsonObject.put("Screenings_ID", ScreeningsID);
            jsonObject.put("Booking_Payment", BookingPayment);
            jsonObject.put("Booking_Time", BookingTime);
            jsonObject.put("Total_Price", TotalPrice);
            jsonObject.put("State", State);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send();
        for(int i = 0; i < 100000000; i++);
    }

    private void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost();
            }
        }).start();
    }

    JSONObject jsonObject = new JSONObject();

    private void executeHttpPost() {
        String path = ApiConfig.url("bookingrecord2.php");
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("accept", "application/json");

            // 往伺服器裡面傳送資料
            String Json = jsonObject.toString();

            System.out.println("-----------" + Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: " + conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void booking_seat(String Seat) {
        String TicketID = Integer.toString(IDcount);
        try {
            jsonObject2.put("Ticket_ID", TicketID);
            jsonObject2.put("Seat", Seat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send2();
        for(int i = 0; i < 100000000; i++);
    }

    private void send2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost2();
            }
        }).start();
    }

    JSONObject jsonObject2 = new JSONObject();

    private void executeHttpPost2() {
        String path = ApiConfig.url("bookingseat.php");
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("accept", "application/json");

            // 往伺服器裡面傳送資料
            String Json = jsonObject2.toString();

            System.out.println("-----------" + Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: " + conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void booking_ticket_type(String typeID, String num) {
        String TicketID = Integer.toString(IDcount);
        try {
            jsonObject3.put("Ticket_ID", TicketID);
            jsonObject3.put("Type_ID", typeID);
            jsonObject3.put("Num", num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send3();
        for(int i = 0; i < 100000000; i++);
    }

    private void send3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost3();
            }
        }).start();
    }

    JSONObject jsonObject3 = new JSONObject();

    private void executeHttpPost3() {
        String path = ApiConfig.url("bookingtickettype.php");
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("accept", "application/json");

            // 往伺服器裡面傳送資料
            String Json = jsonObject3.toString();

            System.out.println("-----------" + Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: " + conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void booking_meals(String mealID, String num) {
        String TicketID = Integer.toString(IDcount);
        try {
            jsonObject4.put("Ticket_ID", TicketID);
            jsonObject4.put("Meal_ID", mealID);
            jsonObject4.put("Num", num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send4();
        for(int i = 0; i < 100000000; i++);
    }

    private void send4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost4();
            }
        }).start();
    }

    JSONObject jsonObject4 = new JSONObject();

    private void executeHttpPost4() {
        String path = ApiConfig.url("bookingmeals.php");
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("accept", "application/json");

            // 往伺服器裡面傳送資料
            String Json = jsonObject4.toString();

            System.out.println("-----------" + Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: " + conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    address.setText("影城：" + result5);
                    name.setText("電影：" + result1);
                    time.setText("場次：" + result4);
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
            intent.setClass(Member24.this, Member4.class);
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