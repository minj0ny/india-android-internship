package com.example.user.mlkitocr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.backBtn)
    Button mBackButton;

    @BindView(R.id.saveBtn)
    Button mSaveButton;

    @BindView(R.id.result)
    TextView mResultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            stringBuilder.append("");
        } else {
            stringBuilder.append(bundle.getString("result"));
        }

        String result = stringBuilder.toString();
        mResultTV.setText(result);

        writeTextFile(result);
    }

    private void writeTextFile(String result) {

        try {
            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            sdPath += "/Doodle_output";

            File file = new File(sdPath);
            file.mkdirs();


            FileOutputStream fos = new FileOutputStream(sdPath + "/" + System.currentTimeMillis() + ".txt", true);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(result);
            writer.flush();
            writer.close();
            fos.close();

            Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fail!", Toast.LENGTH_SHORT).show();
        }

    }
}
