package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.util.Arrays;

public class Member21 extends AppCompatActivity {

    ImageButton back;
    TextView name, engname, series, time, address, cinema, formula, total;
    Spinner type1, type2, type3, type4, type5, type6, type7, type8, amount1, amount2, amount3, amount4, amount5, amount6, amount7, amount8;
    Button choose1, choose2, choose3, choose4, choose5, choose6, choose7, choose8, next;
    ArrayList<String> typelist1 = new ArrayList<>();
    ArrayList<String> typelist2 = new ArrayList<>();
    ArrayList<String> typelist3 = new ArrayList<>();
    ArrayList<String> typelist4 = new ArrayList<>();
    ArrayList<String> typelist5 = new ArrayList<>();
    ArrayList<String> typelist6 = new ArrayList<>();
    ArrayList<String> typelist7 = new ArrayList<>();
    ArrayList<String> typelist8 = new ArrayList<>();
    ArrayList<String> classifylist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> pricelist = new ArrayList<>();
    ArrayList<String> classifylist2 = new ArrayList<>();
    ArrayList<String> namelist2 = new ArrayList<>();
    ArrayList<String> pricelist2 = new ArrayList<>();
    String[] seatlist = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "J1", "J2", "J3", "J4", "J5", "J6", "J7", "J8"};
    ArrayList<String> emptyseatlist = new ArrayList<>(Arrays.asList(seatlist));
    String ID, tot="", IP;
    int all = 0, ticketnum = 0;
    String result1, result2, result3, result4, result5, result6, result7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member21);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        //Toast.makeText(Member21.this, ID, Toast.LENGTH_SHORT).show();
        SharedPreferences List1 = getSharedPreferences("classifylist", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            classifylist.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("namelist", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            namelist.add(item2);
        }
        SharedPreferences List3 = getSharedPreferences("pricelist", MODE_PRIVATE);
        int num3 = List3.getInt("num", 0);
        for (int i = 0; i < num3; i++) {
            String item3 = List3.getString("item_" + i, null);
            pricelist.add(item3);
        }
        SharedPreferences List4 = getSharedPreferences("classifylist2", MODE_PRIVATE);
        int num4 = List4.getInt("num", 0);
        for (int i = 0; i < num4; i++) {
            String item4 = List4.getString("item_" + i, null);
            classifylist2.add(item4);
        }
        SharedPreferences List5 = getSharedPreferences("namelist2", MODE_PRIVATE);
        int num5 = List5.getInt("num", 0);
        for (int i = 0; i < num5; i++) {
            String item5 = List5.getString("item_" + i, null);
            namelist2.add(item5);
        }
        SharedPreferences List6 = getSharedPreferences("pricelist2", MODE_PRIVATE);
        int num6 = List6.getInt("num", 0);
        for (int i = 0; i < num6; i++) {
            String item6 = List6.getString("item_" + i, null);
            pricelist2.add(item6);
        }

        Thread thread = new Thread(mutiThread);
        thread.start();

        CharSequence[] amountlist = getResources().getStringArray(R.array.amount_array);

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
        String ticket;
        for(int i = 0; i < classifylist.size(); i++){
            if(classifylist.get(i).equals("優惠套票")){
                ticket = namelist.get(i) + "($" + pricelist.get(i) + ")";
                typelist1.add(ticket);
            }
            else if(classifylist.get(i).equals("銀行優惠票種")){
                ticket = namelist.get(i) + "($" + pricelist.get(i) + ")";
                typelist2.add(ticket);
            }
            else if(classifylist.get(i).equals("一般票種")){
                ticket = namelist.get(i) + "($" + pricelist.get(i) + ")";
                typelist3.add(ticket);
            }
            else if(classifylist.get(i).equals("會員票種")){
                ticket = namelist.get(i) + "($" + pricelist.get(i) + ")";
                typelist4.add(ticket);
            }
        }
        String meals;
        for(int i = 0; i < classifylist2.size(); i++){
            if(classifylist2.get(i).equals("飲料類")){
                meals = namelist2.get(i) + "($" + pricelist2.get(i) + ")";
                typelist5.add(meals);
            }
            else if(classifylist2.get(i).equals("熱食類")){
                meals = namelist2.get(i) + "($" + pricelist2.get(i) + ")";
                typelist6.add(meals);
            }
            else if(classifylist2.get(i).equals("銀行優惠餐")){
                meals = namelist2.get(i) + "($" + pricelist2.get(i) + ")";
                typelist7.add(meals);
            }
            else if(classifylist2.get(i).equals("爆米花類")){
                meals = namelist2.get(i) + "($" + pricelist2.get(i) + ")";
                typelist8.add(meals);
            }
        }
        type1 = (Spinner) findViewById(R.id.type1);
        /*typelist1.add("優惠單人套票($370)");
        typelist1.add("黑豹2雙人電影套票B($939)");*/
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type1.setAdapter(adapter1);
        type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount1 = (Spinner) findViewById(R.id.amount1);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount1.setAdapter(adapter2);
        amount1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose1 = (Button) findViewById(R.id.choose1);
        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount1.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type1.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount1.getSelectedItem().toString();
                    String s2 = type1.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "張" + s2, Toast.LENGTH_SHORT);
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
        type2 = (Spinner) findViewById(R.id.type2);
        /*typelist2.add("台新銀行平日優惠($217)");
        typelist2.add("花旗 Prestige平日2D PLUS($186)");
        typelist2.add("花旗一般卡平日2D PLUS($186)");
        typelist2.add("花旗饗樂生活卡平日2D/3D PLUS($186)");
        typelist2.add("HSBC銀行平日優惠($217)");*/
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type2.setAdapter(adapter3);
        type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount2 = (Spinner) findViewById(R.id.amount2);
        ArrayAdapter adapter4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount2.setAdapter(adapter4);
        amount2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose2 = (Button) findViewById(R.id.choose2);
        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount2.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type2.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        ticketnum -= n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount2.getSelectedItem().toString();
                    String s2 = type2.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "張" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        ticketnum -= n5;
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        type3 = (Spinner) findViewById(R.id.type3);
        /*typelist3.add("全票($280)");
        typelist3.add("優待票($280)");*/
        ArrayAdapter adapter5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist3);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type3.setAdapter(adapter5);
        type3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount3 = (Spinner) findViewById(R.id.amount3);
        ArrayAdapter adapter6 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount3.setAdapter(adapter6);
        amount3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose3 = (Button) findViewById(R.id.choose3);
        choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount3.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type3.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        ticketnum -= n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount3.getSelectedItem().toString();
                    String s2 = type3.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "張" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        ticketnum -= n5;
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        type4 = (Spinner) findViewById(R.id.type4);
        //typelist4.add("iShow會員票($250)");
        ArrayAdapter adapter7 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist4);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type4.setAdapter(adapter7);
        type4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount4 = (Spinner) findViewById(R.id.amount4);
        ArrayAdapter adapter8 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount4.setAdapter(adapter8);
        amount4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose4 = (Button) findViewById(R.id.choose4);
        choose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount4.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type4.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        ticketnum -= n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount4.getSelectedItem().toString();
                    String s2 = type4.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "張" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        ticketnum -= n5;
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        type5 = (Spinner) findViewById(R.id.type5);
        /*typelist5.add("大可樂($64)");
        typelist5.add("中可樂($55)");
        typelist5.add("小可樂($51)");*/
        ArrayAdapter adapter9 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist5);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type5.setAdapter(adapter9);
        type5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount5 = (Spinner) findViewById(R.id.amount5);
        ArrayAdapter adapter10 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount5.setAdapter(adapter10);
        amount5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose5 = (Button) findViewById(R.id.choose5);
        choose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount5.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type5.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount5.getSelectedItem().toString();
                    String s2 = type5.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "份" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        type6 = (Spinner) findViewById(R.id.type6);
        /*typelist6.add("熱狗($110)");
        typelist6.add("巧納棒($110)");*/
        ArrayAdapter adapter11 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist6);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type6.setAdapter(adapter11);
        type6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount6 = (Spinner) findViewById(R.id.amount6);
        ArrayAdapter adapter12 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount6.setAdapter(adapter12);
        amount6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose6 = (Button) findViewById(R.id.choose6);
        choose6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount6.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type6.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount6.getSelectedItem().toString();
                    String s2 = type6.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "份" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        type7 = (Spinner) findViewById(R.id.type7);
        /*typelist7.add("訂票花旗中可小爆加購49($49)");
        typelist7.add("訂票台新中可小爆($80)");*/
        ArrayAdapter adapter13 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist7);
        adapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type7.setAdapter(adapter13);
        type7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount7 = (Spinner) findViewById(R.id.amount7);
        ArrayAdapter adapter14 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount7.setAdapter(adapter14);
        amount7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose7 = (Button) findViewById(R.id.choose7);
        choose7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount7.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type7.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount7.getSelectedItem().toString();
                    String s2 = type7.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "份" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        type8 = (Spinner) findViewById(R.id.type8);
        /*typelist8.add("大爆米花($123)");
        typelist8.add("中爆米花($115)");
        typelist8.add("小爆米花($106)");*/
        ArrayAdapter adapter15 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist8);
        adapter15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type8.setAdapter(adapter15);
        type8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        amount8 = (Spinner) findViewById(R.id.amount8);
        ArrayAdapter adapter16 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount8.setAdapter(adapter16);
        amount8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        choose8 = (Button) findViewById(R.id.choose8);
        choose8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount8.getSelectedItemPosition() == 0){
                    String s1 = "";
                    String s2 = type8.getSelectedItem().toString();
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
                        Toast.makeText(Member21.this, "您已取消選擇" + s2, Toast.LENGTH_SHORT).show();
                        formula.setText(tot);
                        int n = s2.indexOf('$');
                        String p = s2.substring(n + 1, s2.length() - 1);
                        int n1 = Integer.parseInt(p);
                        int n2 = Integer.parseInt(s1);
                        int n3 = n1 * n2;
                        all -= n3;
                        total.setText("合計：" + all);
                    }
                    else{
                        Toast.makeText(Member21.this, "數量不可為0", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String s1 = amount8.getSelectedItem().toString();
                    String s2 = type8.getSelectedItem().toString();
                    Toast toast = Toast.makeText(Member21.this, "您選擇了" + s1 + "份" + s2, Toast.LENGTH_SHORT);
                    toast.show();
                    int n = s2.indexOf('$');
                    String p = s2.substring(n + 1, s2.length() - 1);
                    int n1 = Integer.parseInt(p);
                    int n2 = Integer.parseInt(s1);
                    int n3 = n1 * n2;
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
                        all -= n6;
                    }
                    tot = tot + s2 + "x" + s1 + "=" + n3 + "\n";
                    formula.setText(tot);
                    total.setText("合計：" + all);
                }
            }
        });
        formula = (TextView) findViewById(R.id.formula);
        //formula.setText("全票 x 2 = 560\n大可樂 x 1 = 64\n中可樂 x 1 = 55\n巧納棒 x 1 = 110\n中爆米花 x 1 = 115");
        total = (TextView) findViewById(R.id.total);
        //total.setText("合計：904");
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ticketnum == 0){
                    Toast.makeText(Member21.this, "您尚未選擇票種與張數！", Toast.LENGTH_SHORT).show();
                }
                else if(ticketnum > 10){
                    Toast.makeText(Member21.this, "每筆交易最多可購買10張電影票！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(Member21.this,Member22.class);
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
            intent.setClass(Member21.this, Member4.class);
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