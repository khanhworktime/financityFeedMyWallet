package com.financity.feedmywallet.Component;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.financity.feedmywallet.R;

public class LoadingDialog {
    Context context;
    Dialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void ShowDialog(String title){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txTitle = dialog.findViewById(R.id.txTitle);

        txTitle.setText(title);
        dialog.create();
        dialog.show();
    }

    public void HideDialog(){
        dialog.dismiss();
    }
}
