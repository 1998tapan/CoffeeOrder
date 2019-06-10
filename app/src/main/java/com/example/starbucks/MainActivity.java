package com.example.starbucks;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    Button plus,minus,orderbtn;
    EditText unameview,emailview;
    TextView quantityview,priceview,ordersummview;
    CheckBox whippedCreamView,chocolateView;
    int quantityvar=0,pricevar=0;
    String unamevar="";
    boolean isWhipCreamadded=false, isChocolateAdded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unameview=findViewById(R.id.unamevalue);
        emailview=findViewById(R.id.emailvalue);
        plus=findViewById(R.id.plusbtn);
        minus=findViewById(R.id.minusbtn);
        orderbtn=findViewById(R.id.orderbtn);
        quantityview=findViewById(R.id.quantityvalue);
        priceview=findViewById(R.id.pricevalue);
        quantityview.setText(String.valueOf(quantityvar)); //explicitly convert int to string or it will be resource id
       // priceview.setText(NumberFormat.getCurrencyInstance().format(pricevar));
        ordersummview=findViewById(R.id.ordersummvalue);
        whippedCreamView=findViewById(R.id.checkboxWhipCream);
        chocolateView=findViewById(R.id.checkboxChocolate);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayOrderSumm();
            }
        });
        whippedCreamView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managePrice();
            }
        });
        chocolateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managePrice();
            }
        });

    }

    void increment(){
        quantityvar=(Integer.parseInt(quantityview.getText().toString())+1);
        // or just use global var quantity to incre/decre and display it using setText
        quantityview.setText(String.valueOf(quantityvar));
        managePrice();
    }

    void decrement(){
        if(Integer.parseInt(quantityview.getText().toString())==0){
            Toast.makeText(this,"You can't drink negative cups of coffee !",Toast.LENGTH_SHORT).show();
        }
        else{
            quantityvar = (Integer.parseInt(quantityview.getText().toString()) - 1);
            quantityview.setText(String.valueOf(quantityvar));
            managePrice();
        }
    }

    void managePrice(){
        isWhipCreamadded = whippedCreamView.isChecked();
        isChocolateAdded = chocolateView.isChecked();
        pricevar=quantityvar * 10;
        if(isWhipCreamadded){
            pricevar+= 1;       //$1 for adding whip cream
        }
        if(isChocolateAdded){
            pricevar+=2;        //$2 for adding chocolate
        }
        priceview.setText(NumberFormat.getCurrencyInstance().format(pricevar));
        //alternate: "$"+ pricevar, not recommended
    }

    void displayOrderSumm(){
        ordersummview.setVisibility(View.VISIBLE);
        getUname();
        String orderdetailstring=" Name: "+unamevar+ "\n Whipped Cream: " + isWhipCreamadded + "\n Chocolate: " +
                isChocolateAdded + "\n Quantity: "+quantityvar + "\n Price: $"+pricevar;
        ordersummview.setText(orderdetailstring);
        sendEmail(orderdetailstring);
    }

    void sendEmail(String orderdetailstring){
        String uemail=emailview.getText().toString();
        Intent sendmail=new Intent(Intent.ACTION_SENDTO);
        sendmail.setData(Uri.parse("mailto:"));
        sendmail.putExtra(Intent.EXTRA_EMAIL,new String[]{uemail} );    //needs string array to read email
        sendmail.putExtra(Intent.EXTRA_SUBJECT,"Starbucks: Order Summary");
        sendmail.putExtra(Intent.EXTRA_TEXT,orderdetailstring);
        if(sendmail.resolveActivity(getPackageManager())!=null){
            startActivity(sendmail);
        }

    }

    void getUname(){
        unamevar=String.valueOf(unameview.getText());

    }
}
