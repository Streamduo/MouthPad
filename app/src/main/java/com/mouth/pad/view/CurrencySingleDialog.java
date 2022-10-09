package com.mouth.pad.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mouth.pad.R;


//通用提示dialog
public class CurrencySingleDialog {

    private Dialog dialog;
    private DialogClickListener dialogClickListener;

    public void showDialog(Context context, String centerStr, boolean isCancel) {
        dialog = new Dialog(context, R.style.dialog_bottom_full);
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(context, R.layout.dialog_currency_single, null);
        TextView teCenter = (TextView) view.findViewById(R.id.te_center_text);
        TextView teAscertain = (TextView) view.findViewById(R.id.te_ascertain);
        teCenter.setText(centerStr);

        teAscertain.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (dialogClickListener != null) {
                dialogClickListener.onAscertainClick();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        dialog.show();
    }

    public void showDialog(Context context, String centerStr, String bottomStr, boolean isCancel) {
        dialog = new Dialog(context, R.style.dialog_bottom_full);
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(context, R.layout.dialog_currency_single, null);
        TextView teCenter = (TextView) view.findViewById(R.id.te_center_text);
        TextView teAscertain = (TextView) view.findViewById(R.id.te_ascertain);
        teCenter.setText(centerStr);
        teAscertain.setText(bottomStr);

        teAscertain.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (dialogClickListener != null) {
                dialogClickListener.onAscertainClick();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        dialog.show();
    }

    public void setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public interface DialogClickListener {
        void onAscertainClick();
    }
}
