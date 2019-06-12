package com.example.user.studentmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText studentName;
    private EditText className;
    private EditText mark;

    private Button register;
    private Button listView;
    private Button search;

    String stuName, stuClassName;
    int stuMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentName = (EditText) findViewById(R.id.editText1);
        className = (EditText) findViewById(R.id.editText2);
        mark = (EditText) findViewById(R.id.editText3);

        register = (Button) findViewById(R.id.registerButton);
        listView = (Button) findViewById(R.id.listButton);
        search = (Button) findViewById(R.id.searchButton);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listViewIntent = new Intent(MainActivity.this, ShowListActivity.class);
                startActivity(listViewIntent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });
    }

    private void saveTask() {

        stuName = studentName.getText().toString().trim();
        stuClassName = className.getText().toString().trim();
        stuMark = Integer.parseInt(mark.getText().toString());

        if (stuName.isEmpty()) {
            studentName.setError("Enter name");
            studentName.requestFocus();
            return;
        }

        if (stuClassName.isEmpty()) {
            className.setError("Enter class name");
            className.requestFocus();
            return;
        }
        SaveTask st = new SaveTask();
        st.execute();
    }

    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            //creating a task
            Student student = new Student();
            student.setStuName(stuName);
            student.setStuClass(stuClassName);
            student.setStuMark(stuMark);

            //adding to database
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .studentDao()
                    .insert(student);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }
}
