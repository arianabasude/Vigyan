package com.urstrulygsw.machineleaning;

import android.media.FaceDetector;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResultDialog extends DialogFragment {

    private Button okButton;
    private TextView txtResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_result,container,false);
        String result="";
        okButton=view.findViewById(R.id.result_ok_button);
        txtResult=view.findViewById(R.id.result_text_view);

        Bundle bundle=getArguments();
        result=bundle.getString(FaceDetection.strRESULT);
        txtResult.setText(result);
        //MADE-9
        //till this point message must display

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //MADE-10
            }
        });
        return view;
    }
}
