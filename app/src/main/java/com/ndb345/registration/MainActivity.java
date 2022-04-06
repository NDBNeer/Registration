package com.ndb345.registration;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    RadioButton graduated,ungraduated;
    CheckBox accommodation,medical_insurance;
    Spinner coursespin,selectedcourse;
    TextView total_hours_val,total_fees_val,hours_val,fees_val,lab;
    Button register,remove;
    ArrayList<Course> cList=new ArrayList<>();
    ArrayList<String>cTypes=new ArrayList<>();
    ArrayList<Course>tempList=new ArrayList<>();
    ArrayList<String>tempNames=new ArrayList<>();
    double med_val=0,acc_val=0,hoursval=0,totalhours=0,totalfees=0,temptotalhours =0,temptotalfees=0;
    String flag="";
    int j,n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Calling method of findview by id and appending data into array list
        fillData();
        layoutfindViewById();
        //radio and button click event
        graduated.setOnClickListener(this);
        ungraduated.setOnClickListener(this);
        register.setOnClickListener(this);
        remove.setOnClickListener(this);
        if(graduated.isChecked())
        {
            flag="graduated";
        }
        selectedcourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                n=i;
                remove.setEnabled(true);
                register.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //setting spinner
        ArrayAdapter aa=new ArrayAdapter(this, R.layout.spinnerdes,cTypes);
        coursespin.setAdapter(aa);
        coursespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                j = i;
                remove.setEnabled(false);
                register.setEnabled(true);
                hours_val.setText(String.valueOf(cList.get(i).getCoursehours()));
                fees_val.setText(String.valueOf(cList.get(i).getCoursefees()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //method for appending data into array list
    private void fillData() {
        // Fill data into array list
        cList.add(new Course("Java",1300,6));
        cList.add(new Course("Swift",1500,5));
        cList.add(new Course("iOS",1350,5));
        cList.add(new Course("Android",1400,7));
        cList.add(new Course("Database",1000,4));

            for(Course pz:cList)
            cTypes.add(pz.getCoursename());
    }

    //method for initialize layout objects
    private void layoutfindViewById() {
        name=findViewById(R.id.name);
        graduated=findViewById(R.id.graduated);
        ungraduated=findViewById(R.id.ungraduated);
        accommodation=findViewById(R.id.accommodation);
        medical_insurance=findViewById(R.id.medical_insurance);
        coursespin=findViewById(R.id.coursespin);
        total_hours_val=findViewById(R.id.total_hours_val);
        total_fees_val=findViewById(R.id.total_fees_val);
        register=findViewById(R.id.register);
        hours_val=findViewById(R.id.hours_val);
        fees_val=findViewById(R.id.fees_val);
        lab=findViewById(R.id.lab);
        selectedcourse=findViewById(R.id.selectedcourse);
        remove=findViewById(R.id.remove);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.graduated:
                flag="graduated";
                break;
            case R.id.ungraduated:
                flag="ungraduated";
                break;
            case R.id.remove:
                lab.setText("");
                checkboxval();
                if(!tempList.isEmpty())
                {Log.d("cList.get(n)",String.valueOf(tempList.get(n)));
                temptotalhours-=tempList.get(n).getCoursehours();
                temptotalfees-=tempList.get(n).getCoursefees();
                totalhours-=tempList.get(n).getCoursehours();
                totalfees-=tempList.get(n).getCoursefees();
                total_hours_val.setText(String.valueOf(totalhours));
                total_fees_val.setText(String.valueOf(totalfees));
                tempNames.remove(n);
                tempList.remove(n);
                ArrayAdapter ab = new ArrayAdapter(this, R.layout.spinnerdes, tempNames);
                selectedcourse.setAdapter(ab);}
                break;
            case R.id.register:
                checkboxval();
                temptotalhours+=cList.get(j).getCoursehours();
                temptotalfees+=cList.get(j).getCoursefees();
                //Toast.makeText(this,name.getText().toString(),Toast.LENGTH_SHORT).show();
                if(!tempList.contains(cList.get(j))) {
                    lab.setText("");
                    if (flag.equals("graduated")) {
                        if (temptotalhours <= 21) {
                            totalhours+=cList.get(j).getCoursehours();
                            totalfees+=cList.get(j).getCoursefees();
                            tempList.add(cList.get(j));
                            tempNames.add(cList.get(j).getCoursename());
                            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinnerdes, tempNames);
                            selectedcourse.setAdapter(aa);
                        }
                        else
                        {lab.setText("Maximum Hours");
                        }
                    } else if (flag.equals("ungraduated")) {
                        if (temptotalhours <= 19) {
                            totalhours+=cList.get(j).getCoursehours();
                            totalfees+=cList.get(j).getCoursefees();
                            tempList.add(cList.get(j));
                            tempNames.add(cList.get(j).getCoursename());
                            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinnerdes, tempNames);
                            selectedcourse.setAdapter(aa);
                        }
                        else
                        {lab.setText("Maximum Hours");
                        }
                    } else {
                        hoursval = 0;
                        totalhours=0;
                        totalfees=0;
                        temptotalhours=0;
                        lab.setText("");
                    }

                }else
                {
                    lab.setText("This course is already added");
                }
                total_hours_val.setText(String.valueOf(totalhours));
                total_fees_val.setText(String.valueOf(totalfees+acc_val+med_val));
                break;
        }
    }
    // assigning value for accomodation and medical insurance if selected
    private void checkboxval() {
        if (accommodation.isChecked()) {
            acc_val = 1000;
        }
        else
        {
            acc_val = 0;
        }
        if (medical_insurance.isChecked()) {
            med_val = 700;
        }
        else
        {
            med_val = 0;
        }
    }
}
