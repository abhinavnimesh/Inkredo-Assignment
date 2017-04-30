package com.animator_abhi.inkredoassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EmiActivity extends AppCompatActivity {
    Button emiCalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);

        final EditText P = (EditText)findViewById(R.id.principal);

        final EditText Y = (EditText)findViewById(R.id.tenure);
      
        final TextView TAMOUNT= (TextView) findViewById(R.id.tI);




        final TextView result = (TextView) findViewById(R.id.emi) ;


        emiCalcBtn = (Button) findViewById(R.id.btn_calculate);

        emiCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st1 = P.getText().toString();
                String st2 ="36";
                String st3 = Y.getText().toString();


                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);

                float Principal = calPric(p);

                float Rate = calInt(i);

                float Months = Float.parseFloat(st3);

                float Dvdnt = calDvdnt( Rate, Months);

                float FD = calFinalDvdnt (Principal, Rate, Dvdnt);

                float D = calDivider(Dvdnt);

                float emi = calEmi(FD, D);

                float TA = calTa (emi, Months);

                float ti = calTotalInt(TA, Principal);



                result.setText(String.valueOf(emi));




                TAMOUNT.setText(String.valueOf(TA));

            }
        });
    }

    public  float calPric(float p) {

        return (float) (p);

    }

    public  float calInt(float i) {

        return (float) (i/12/100);

    }



    public  float calDvdnt(float Rate, float Months) {

        return (float) (Math.pow(1+Rate, Months));

    }

    public  float calFinalDvdnt(float Principal, float Rate, float Dvdnt) {

        return (float) (Principal * Rate * Dvdnt);

    }

    public  float calDivider(float Dvdnt) {

        return (float) (Dvdnt-1);

    }

    public  float calEmi(float FD, Float D) {

        return (float) (FD/D);

    }

    public  float calTa(float emi, Float Months) {

        return (float) (emi*Months);

    }

    public  float calTotalInt(float TA, float Principal) {

        return (float) (TA - Principal);

    }
}