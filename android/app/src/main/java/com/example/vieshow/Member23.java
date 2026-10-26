package com.example.vieshow;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
public class Member23 extends AppCompatActivity {

    ImageButton back;
    TextView name, engname, series, time, address, cinema, seat, formula, total;
    Button button1, button2;
    ArrayList<String> chosenlist = new ArrayList<>();
    ArrayList<String> typeIDlist = new ArrayList<>();
    ArrayList<String> typenamelist = new ArrayList<>();
    ArrayList<String> mealIDlist = new ArrayList<>();
    ArrayList<String> mealnamelist = new ArrayList<>();
    String ID, tot, chosenseat = "", IP, acc, str;
    int ticketnum, all, money;
    int IDcount;
    String result1, result2, result3, result4, result5, result6, result7, response, response2, response3;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate;
    Dialog dialog;
    View viewdialog;
    Button topup, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member23);

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
        name = (TextView) findViewById(R.id.name);
        engname = (TextView) findViewById(R.id.engname);
        series = (TextView) findViewById(R.id.series);
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
        formula = (TextView) findViewById(R.id.formula);
        //formula.setText("全票 x 2 = 560\n大可樂 x 1 = 64\n中可樂 x 1 = 55\n巧納棒 x 1 = 110\n中爆米花 x 1 = 115");
        formula.setText(tot + "訂票手續費：" + 20 * ticketnum);
        total = (TextView) findViewById(R.id.total);
        //total.setText("合計：904");
        all += (20 * ticketnum);
        total.setText("合計：" + all);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all > money){
                    getDialog();
                }
                else{
                    curDate = new Date(System.currentTimeMillis());
                    str = formatter.format(curDate);
                    booking_record();
                    for(int i = 0; i < chosenlist.size(); i++){
                        String seat = chosenlist.get(i);
                        booking_seat(seat);
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
                            booking_ticket_type(typeID, s3);
                        }
                    }
                    for(int i = 0; i < mealIDlist.size(); i++){
                        if(tot.contains(mealnamelist.get(i))){
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
                            booking_meals(mealID, s3);
                        }
                    }
                    int m = money - all;
                    paymoney(m);
                    Intent intent = new Intent();
                    intent.setClass(Member23.this,Member4.class);
                    Toast.makeText(Member23.this, "您已成功完成訂票!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Member23.this,Member24.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", ID);
                bundle.putInt("num", ticketnum);
                bundle.putString("formula", tot);
                bundle.putInt("total", all);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void getDialog() {
        dialog = new Dialog(Member23.this);
        viewdialog = getLayoutInflater().inflate(R.layout.dialog2, null);
        dialog.setContentView(viewdialog);
        topup = viewdialog.findViewById(R.id.topup);
        cancel = viewdialog.findViewById(R.id.cancel);
        dialog.show();
        dialog.getWindow().setLayout(1100,1400);
        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Member23.this,Member36.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
    private void paymoney(int money) {
        try {
            jsonObject5.put("acc", acc);
            jsonObject5.put("money", money);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send5();
        for(int i = 0; i < 100000000; i++);
    }

    private void send5() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost5();
            }
        }).start();
    }

    JSONObject jsonObject5 = new JSONObject();

    private void executeHttpPost5() {
        String path = ApiConfig.url("paymoney.php");
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
            String Json = jsonObject5.toString();

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
                URL url = new URL(ApiConfig.url("money.php"));
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
                String post_data = URLEncoder.encode("acc", "UTF-8")+"="+URLEncoder.encode(acc, "UTF-8");
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
                            String money = info.getString("User_Money");
                            result7 = money.toString();
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
                    money = Integer.parseInt(result7);
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
            intent.setClass(Member23.this, Member4.class);
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