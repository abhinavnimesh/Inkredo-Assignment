package com.animator_abhi.inkredoassignment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ITrueCallback, View.OnClickListener {
private TrueClient mTrueClient;
private EditText mProfileName;
private EditText   mProfilePhone;
private EditText   mProfileEmail;
//private EditText   mProfileAddress;
//private EditText   mProfileJob;

private String mTruecallerRequestNonce;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfileName = (EditText) findViewById(R.id.name);
        mProfilePhone = (EditText) findViewById(R.id.phone);
        mProfileEmail = (EditText) findViewById(R.id.email);
     //   mProfileAddress = (EditText) findViewById(R.id.address);
      //  mProfileJob = (EditText) findViewById(R.id.job);

        TrueButton trueButton = (TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton);
        boolean usable = trueButton.isUsable();

        if (usable) {
        mTrueClient = new TrueClient(this, this);
        trueButton.setTrueClient(mTrueClient);
        } else {
        trueButton.setVisibility(View.GONE);
        }

       // findViewById(R.id.customButton).setOnClickListener(this);
        }

@Override
protected void onResume() {
        super.onResume();

        mTruecallerRequestNonce = mTrueClient.generateRequestNonce();
        }

@Override
protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (null != mTrueClient && mTrueClient.onActivityResult(requestCode, resultCode, data)) {
        return;
        }

        super.onActivityResult(requestCode, resultCode, data);
        }



    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
        final String fullName = trueProfile.firstName + " " + trueProfile.lastName;
        mProfileName.setText(fullName);
        mProfilePhone.setText(trueProfile.phoneNumber);
        mProfileEmail.setText(trueProfile.email);
      //  mProfileAddress.setText(trueProfile.city);
        final List<String> jobComponents = new ArrayList<>(2);
        if (!TextUtils.isEmpty(trueProfile.jobTitle)) {
            jobComponents.add(trueProfile.jobTitle);
        }
        if (!TextUtils.isEmpty(trueProfile.companyName)) {
            jobComponents.add(trueProfile.companyName);
        }
      //  mProfileJob.setText(TextUtils.join(" @ ", jobComponents));

        if (mTruecallerRequestNonce.equals(trueProfile.requestNonce)) {
            Toast.makeText(this, "The request nonce matches", Toast.LENGTH_SHORT).show();
        }
        Intent i= new Intent(this,EmiActivity.class);
        i.putExtra("number",trueProfile.phoneNumber);
        i.putExtra("email",trueProfile.email);
        startActivity(i);
    }

    @Override
public void onFailureProfileShared(@NonNull final TrueError trueError) {
        Toast.makeText(this, "Failed sharing - Reason: " + trueError.getErrorType(), Toast.LENGTH_LONG).show();
        }

@Override
public void onClick(final View v) {

        }
        }
