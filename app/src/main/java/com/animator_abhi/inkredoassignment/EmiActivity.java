package com.animator_abhi.inkredoassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EmiActivity extends AppCompatActivity {
    Button emiCalcBtn;
    TableLayout t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);

        final EditText P = (EditText)findViewById(R.id.principal);

        final EditText Y = (EditText)findViewById(R.id.tenure);

      //  final TextView TAMOUNT= (TextView) findViewById(R.id.tI);
       /* final TextView tvpricipal= (TextView) findViewById(R.id.p_Amount);
        final TextView tvmonth= (TextView) findViewById(R.id.month);
        TextView tvemi= (TextView) findViewById(R.id.emi);
        TextView tvtotal= (TextView) findViewById(R.id.tAmount);*/
        t1= (TableLayout) findViewById(R.id.tableLayout1);
     final    SQLiteDatabase db=openOrCreateDatabase("Demofile", MODE_APPEND, null);

        db.execSQL("create table if not exists Inkredo( principal Real , month Real , emi real , total real ) ");
      //  SharedPreferences sp=getSharedPreferences("SHARED", 0);
        //String msg=sp.getString("STAT", "NOT INITIALISED");





      //  final TextView result = (TextView) findViewById(R.id.emi) ;


        emiCalcBtn = (Button) findViewById(R.id.btn_calculate);

        emiCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.execSQL("delete from Inkredo");

                String st1 = P.getText().toString();
                String st2 ="36";
                String st3 = Y.getText().toString();

                if (TextUtils.isEmpty(st1)) {
                    P.setError("Enter Prncipal Amount");
                    P.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(st3)) {
                    P.setError("Enter tenure (months)");
                    P.requestFocus();
                    return;
                }

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);

                float Principal = calPric(p);

                float Rate = calInt(i);

                float Months = Float.parseFloat(st3);
                int  lp = (int) (Months-3);
                if(lp<=0){lp=1;}
                else
                    lp=(int) (Months-3);

                for(int loop=lp; loop<=Months+3; loop++) {

                    float Dvdnt = calDvdnt(Rate, (float)loop);

                    float FD = calFinalDvdnt(Principal, Rate, Dvdnt);

                    float D = calDivider(Dvdnt);

                    float emi = calEmi(FD, D);

                    float TA = calTa(emi, (float) loop);

                    float ti = calTotalInt(TA, Principal);
                    db.execSQL("insert into Inkredo(principal,month,emi,total)values('"+Principal+"','"+loop+"','"+emi+"','"+TA+"')");

                }

                TableRow tr;
                String q = "select*from Inkredo";
                Cursor c = db.rawQuery(q, null);
                String principal,month,emi,total;
                while (c.moveToNext()) {
                    principal = c.getString(0);
                    month = c.getString(1);
                    emi = c.getString(2);
                    total = c.getString(3);

                    tr = new TableRow(EmiActivity.this);
                    TextView tv1 = new TextView(EmiActivity.this);
                    tv1.setText(principal);
                    TextView tv2 = new TextView(EmiActivity.this);
                    tv2.setText(month);
                    TextView tv3 = new TextView(EmiActivity.this);
                    tv3.setText(emi);
                    TextView tv4 = new TextView(EmiActivity.this);
                    tv4.setText(total);
                    tr.addView(tv1);
                    tr.addView(tv2);
                    tr.addView(tv3);
                    tr.addView(tv4);
                    t1.addView(tr);
                }

                Toast.makeText(EmiActivity.this, "data loaded", Toast.LENGTH_SHORT).show();



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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final    SQLiteDatabase db=openOrCreateDatabase("Demofile", MODE_APPEND, null);

        db.execSQL("drop table Inkredo");
        db.close();

    }


}