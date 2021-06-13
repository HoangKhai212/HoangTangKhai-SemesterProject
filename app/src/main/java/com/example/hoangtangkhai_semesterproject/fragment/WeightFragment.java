package com.example.hoangtangkhai_semesterproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangtangkhai_semesterproject.MainActivity;
import com.example.hoangtangkhai_semesterproject.R;


public class WeightFragment extends Fragment {
    private EditText editAge;
    private EditText editWeight;
    private EditText editHeight;
    private EditText editFat;
    private TextView txtBMI, txtFFMI;
    private TextView txtRsBMI, txtRsFFMI;
    private Button caculate;
    private RadioButton btnMen, btnWomen;

    public WeightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_weight, container, false);
        init(v);

        caculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAge = editAge.getText().toString();
                String sHeight = editHeight.getText().toString();
                String sWeight = editWeight.getText().toString();
                String sFat = editFat.getText().toString();
                int age = 0;
                float height = 0;
                float weight = 0;
                float fat = 0;
                if(sAge.isEmpty() || sHeight.isEmpty() || sWeight.isEmpty() || sFat.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill the infomartion!", Toast.LENGTH_SHORT).show();
                }

                else {

                    age = Integer.parseInt(sAge);
                    if (age <18){
                        Toast.makeText(getActivity(), "This information does not apply to you ", Toast.LENGTH_SHORT).show();
                    }else {
                        height = Float.parseFloat(sHeight);
                        weight = Float.parseFloat(sWeight);
                        fat = Float.parseFloat(sFat);
                        float bmi = caculateBMI(weight,height);
                        float ffmi = caculateFFMI(weight,height,fat);
                        txtBMI.setText(bmi +"");
                        txtRsBMI.setText(getBMIText(bmi));
                        txtFFMI.setText(ffmi+"");
                        if(btnMen.isChecked()){
                            txtRsFFMI.setText(getFfmiTextForMen(ffmi));
                        }
                        else {
                            txtRsFFMI.setText(getFfmiTextForWomen(ffmi));
                        }
                    }
                }
            }
        });
        return v;
    }

    public void init(View v){
        editAge = v.findViewById(R.id.editAge);
        editHeight = v.findViewById(R.id.editHeight);
        editWeight = v.findViewById(R.id.editWeight);
        editFat = v.findViewById(R.id.editFat);
        caculate = v.findViewById(R.id.btnCalculate);
        txtBMI = v.findViewById(R.id.txtBMI);
        txtFFMI = v.findViewById(R.id.txtFFMI);
        txtRsBMI = v.findViewById(R.id.txtRsBMI);
        txtRsFFMI = v.findViewById(R.id.txtRsFFMI);
        btnMen = v.findViewById(R.id.rbMen);
        btnWomen = v.findViewById(R.id.rbWomen);
    }
    private float caculateBMI(float weight, float height){
        float bmi = 0;
        if(height == 0) return 0;
        bmi = weight/((height/100)*(height/100));
        return bmi;
    }
    private float caculateFFMI(float weight, float height, float fat){
        float ffmi = 0;
        if (fat == 0) return 0;
        ffmi = weight*(1-fat/100)/((height/100)*(height/100));
        return ffmi;
    }
    private String getBMIText(float bmi) {
        if (bmi < 18.5) {
            return "Under Weight";
        } else if (bmi < 25) {
            return "Nomal";
        } else if (bmi < 30) {
            return "Over Weight";
        } else {
            return "Obese";
        }
    }

    private String getFfmiTextForMen(double ffmi) {
        if (ffmi < 17) {
            return "Below average";
        } else if (ffmi < 19) {
            return "Average";
        } else if (ffmi < 21) {
            return "Above average";
        }else if (ffmi < 23) {
            return "Excellent";
        }else if (ffmi < 25) {
            return "Superior";
        }else if (ffmi < 27) {
            return "Suspicious";
        } else {
            return "Very suspicious";
        }
    }

    private String getFfmiTextForWomen(double ffmi) {
        if (ffmi < 14) {
            return "Below average";
        } else if (ffmi < 16) {
            return "Average";
        } else if (ffmi < 18) {
            return "Above average";
        }else if (ffmi < 20) {
            return "Excellent";
        }else if (ffmi < 22) {
            return "Superior";
        }else if (ffmi < 24) {
            return "Suspicious";
        } else {
            return "Very suspicious";
        }
    }
}