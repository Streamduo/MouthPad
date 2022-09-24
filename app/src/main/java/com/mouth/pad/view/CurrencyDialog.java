package com.mouth.pad.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.mouth.pad.R;


//通用提示dialog
public class CurrencyDialog {

    private Dialog dialog;
    private DialogClickListener dialogClickListener;

    public void showDialog(Context context, String centerStr, String left, String right) {
        dialog = new Dialog(context, R.style.dialog_bottom_full);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(context, R.layout.dialog_currency_notitle, null);
        TextView teCenter = (TextView) view.findViewById(R.id.te_center);
        TextView teLeft = (TextView) view.findViewById(R.id.te_left);
        TextView teRight = (TextView) view.findViewById(R.id.te_right);
        teCenter.setText(centerStr);
        teLeft.setText(left);
        teRight.setText(right);

        teLeft.setOnClickListener(view1 -> {
            if (dialogClickListener != null)
                dialogClickListener.onLeftClick();
        });
        teRight.setOnClickListener(view12 -> {
            if (dialogClickListener != null)
                dialogClickListener.onRightClick();
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        dialog.show();
    }

    public void showDialog(Context context, String centerStr, String left, String right, boolean isCancel) {
        dialog = new Dialog(context, R.style.dialog_bottom_full);
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(context, R.layout.dialog_currency_notitle, null);
        TextView teCenter = (TextView) view.findViewById(R.id.te_center);
        TextView teLeft = (TextView) view.findViewById(R.id.te_left);
        TextView teRight = (TextView) view.findViewById(R.id.te_right);
        teCenter.setText(centerStr);
        teLeft.setText(left);
        teRight.setText(right);

        teLeft.setOnClickListener(view1 -> {
            if (dialogClickListener != null)
                dialogClickListener.onLeftClick();
        });
        teRight.setOnClickListener(view12 -> {
            if (dialogClickListener != null)
                dialogClickListener.onRightClick();
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        dialog.show();
    }

    public void showDialog(Context context, String centerStr,
                           String left, String right, int leftColor,
                           int rightColor, boolean isCancel) {
        dialog = new Dialog(context, R.style.dialog_bottom_full);
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(context, R.layout.dialog_currency_notitle, null);
        TextView teCenter = (TextView) view.findViewById(R.id.te_center);
        TextView teLeft = (TextView) view.findViewById(R.id.te_left);
        TextView teRight = (TextView) view.findViewById(R.id.te_right);
        teCenter.setText(centerStr);
        teLeft.setTextColor(ContextCompat.getColor(context, leftColor));
        teLeft.setText(left);
        teRight.setTextColor(ContextCompat.getColor(context, rightColor));
        teRight.setText(right);

        teLeft.setOnClickListener(view1 -> {
            if (dialogClickListener != null)
                dialogClickListener.onLeftClick();
        });
        teRight.setOnClickListener(view12 -> {
            if (dialogClickListener != null)
                dialogClickListener.onRightClick();
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
        void onLeftClick();

        void onRightClick();
    }

}
