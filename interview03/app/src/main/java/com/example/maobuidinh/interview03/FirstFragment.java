package com.example.maobuidinh.interview03;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.maobuidinh.interview03.R.layout;

/**
 * Created by mao.buidinh on 5/20/2016.
 */
public class FirstFragment extends Fragment implements View.OnClickListener {

    private Button nextButton;
    private EditText edtMsg;
    private TextView txtTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout.first_fragment, container, false);

        nextButton = (Button) view.findViewById(R.id.button_first);
        nextButton.setOnClickListener(this);

        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        edtMsg = (EditText) view.findViewById(R.id.edit_first_msg);
//        edtMsg.setText("" + getTag());
        return view;
    }




    @Override
    public void onClick(View v) {
//        Toast.makeText(getActivity(), "onClick Button in Fragment " + getTag(), Toast.LENGTH_SHORT).show();
        String name =edtMsg.getText().toString();
        String greeting = String.format("Hello, %s!", name);
        txtTitle.setText(greeting);
    }
}
