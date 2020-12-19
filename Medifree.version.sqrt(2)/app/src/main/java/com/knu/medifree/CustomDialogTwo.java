package com.knu.medifree;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


        import static androidx.fragment.app.DialogFragment.STYLE_NORMAL;

public class CustomDialogTwo extends Dialog implements View.OnClickListener {
    private TextView btn_ok;
    private Context mContext;

    public CustomDialogTwo( Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_two);
        btn_ok = (TextView) findViewById(R.id.dialog_btn_ok);
        btn_ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_ok:
                dismiss();
                break;
        }
    }
}




