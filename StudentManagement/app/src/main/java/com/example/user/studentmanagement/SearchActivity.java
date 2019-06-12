package com.example.user.studentmanagement;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText name;
    private Button search;

    private TextView stuName;
    private TextView stuClass;
    private TextView stuMark;

    private LinearLayout result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        name = (EditText) findViewById(R.id.editText1);

        search = (Button) findViewById(R.id.button);

        stuName = (TextView) findViewById(R.id.stuName);
        stuClass = (TextView) findViewById(R.id.stuClass);
        stuMark = (TextView) findViewById(R.id.stuMark);

        result = (LinearLayout) findViewById(R.id.showResult);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String searchName = name.getText().toString();
                if (searchName.equals("")) {
                    Toast.makeText(getApplicationContext(),  getString(R.string.warning1), Toast.LENGTH_LONG).show();
                } else {
                    result.setVisibility(View.VISIBLE);
                    getTasks();
                }
            }
        });
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, Student> {

            @Override
            protected Student doInBackground(Void... voids) {
                final String searchName = name.getText().toString();

                Student student = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .studentDao()
                        .getStudent(searchName);
                return student;

            }

            @Override
            protected void onPostExecute(Student student) {
                super.onPostExecute(student);
                if (student == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.warning2), Toast.LENGTH_LONG).show();
                } else {

                    stuName.setText("Name : " + student.getStuName());
                    stuClass.setText("Class : " + student.getStuClass());
                    stuMark.setText("Mark : " + String.valueOf(student.getStuMark()));
                }

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
