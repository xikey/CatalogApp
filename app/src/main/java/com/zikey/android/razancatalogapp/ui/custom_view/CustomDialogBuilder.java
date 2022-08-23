package com.zikey.android.razancatalogapp.ui.custom_view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;

import com.google.android.material.snackbar.Snackbar;
import com.razanpardazesh.com.resturantapp.tools.FontChanger;
import com.zikey.android.razancatalogapp.R;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

/**
 * Created by Zikey on 10/04/2017.
 */

public class CustomDialogBuilder {


    public AlertDialog showInputTextDialog(AppCompatActivity context, String title, String note, final OnDialogListener onYesClickListener) {

        String output = null;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.layout_input_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        TextView dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);

        if (!TextUtils.isEmpty(title))
            dialogTitle.setText(title);

        if (!TextUtils.isEmpty(note)) {
            userInputDialogEditText.setText(note);
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        if (onYesClickListener != null) {

                            if (TextUtils.isEmpty(userInputDialogEditText.getText().toString())) {
                                onYesClickListener.onOK(null);
                            } else
                                onYesClickListener.onOK(userInputDialogEditText.getText().toString());
                        }

                    }
                })

                .setNegativeButton("انصراف",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        return alertDialogAndroid;
    }


    public AlertDialog showInputTextDialog(AppCompatActivity context, String title, final OnDialogListener onYesClickListener) {

        String output = null;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.layout_input_dialog_simple, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        TextView dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);

        if (!TextUtils.isEmpty(title))
            dialogTitle.setText(title);


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        if (onYesClickListener != null) {

                            if (TextUtils.isEmpty(userInputDialogEditText.getText().toString())) {
                                onYesClickListener.onOK(null);
                            } else
                                onYesClickListener.onOK(userInputDialogEditText.getText().toString());
                        }

                    }
                })

                .setNegativeButton("انصراف",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        return alertDialogAndroid;
    }


    public AlertDialog showInputTextDialog_NumbersOnly(AppCompatActivity context, String title, final OnDialogListener onYesClickListener) {

        String output = null;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.layout_input_dialog_simple_numbers, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        TextView dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);

        if (!TextUtils.isEmpty(title))
            dialogTitle.setText(title);

        FontChanger fontChanger = new FontChanger();
        fontChanger.applyMainFont(mView);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        if (onYesClickListener != null) {

                            if (TextUtils.isEmpty(userInputDialogEditText.getText().toString())) {
                                onYesClickListener.onOK(null);
                            } else
                                onYesClickListener.onOK(userInputDialogEditText.getText().toString());
                        }

                    }
                })

                .setNegativeButton("انصراف",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        return alertDialogAndroid;
    }


    public AlertDialog showSMSvalidationDialog(final AppCompatActivity context, String title, String note, String question, boolean hasCountDown, final OnDialogListener onDialogListener) {

        String output = null;

        FontChanger fontChanger = new FontChanger();
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.layout_input_dialog, null);
        fontChanger.applyMainFont(mView);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        TextView dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);
        TextView dialogQuestion = (TextView) mView.findViewById(R.id.txtQuestion);
        final RelativeLayout lyCountDown = (RelativeLayout) mView.findViewById(R.id.lyCountDown);
        final TextView countDown = (TextView) mView.findViewById(R.id.txtCountDown);

        fontChanger.applyTitleFont(countDown);

        if (!TextUtils.isEmpty(title))
            dialogTitle.setText(title);

        if (!TextUtils.isEmpty(question))
            dialogQuestion.setText(question);


        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    countDown.setText(String.valueOf(millisUntilFinished / 1000));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                lyCountDown.setVisibility(View.GONE);
                Toast.makeText(context, "در صورتی که کد فعال سازی ارسال نشد، عملیات عضویت را مجددا تکرار نمایید.", Toast.LENGTH_SHORT).show();
            }
        }.start();


        if (!TextUtils.isEmpty(note)) {
            userInputDialogEditText.setText(note);
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setNeutralButton("تلاش مجدد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogListener != null) {
                            if (lyCountDown.getVisibility() == View.GONE) {
                                onDialogListener.onNeutral(null);
                                lyCountDown.setVisibility(View.VISIBLE);
                                new CountDownTimer(60000, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        try {
                                            countDown.setText(String.valueOf(millisUntilFinished / 1000));
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onFinish() {
                                        lyCountDown.setVisibility(View.GONE);
                                        Toast.makeText(context, "در صورتی که کد فعال سازی ارسال نشد، عملیات عضویت را مجددا تکرار نمایید.", Toast.LENGTH_SHORT).show();
                                    }
                                }.start();
                            }

                        }

                    }
                })
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        if (onDialogListener != null) {

                            if (TextUtils.isEmpty(userInputDialogEditText.getText().toString())) {
                                onDialogListener.onOK(null);
                            } else
                                onDialogListener.onOK(userInputDialogEditText.getText().toString());

                            return;
                        }

                    }
                })

                .setNegativeButton("انصراف",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {


                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        return alertDialogAndroid;
    }

    public AlertDialog showPickerDialog(AppCompatActivity context, String title, final CharSequence items[], final OnPickerDialogListener onPickerDialoglistener) {


        AlertDialog.Builder pickerDialog = new AlertDialog.Builder(context);
        pickerDialog.setTitle(title);
        pickerDialog.setCancelable(true);
        if (items == null || items.length == 0)
            return null;
        pickerDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < items.length; i++) {
                    if (which == i) {
                        if (onPickerDialoglistener != null)

                            onPickerDialoglistener.onPick(which);
                    }
                }
            }
        });

        AlertDialog alertDialogAndroid = pickerDialog.create();
        alertDialogAndroid.show();

        return alertDialogAndroid;
    }

    public CustomAlertDialog showAlert(AppCompatActivity context, String message) {
        return CustomAlertDialog.Show(context, "توجه", message, null, "بستن", null, null);
    }

    public CustomAlertDialog showAlert(AppCompatActivity context, String message, boolean hideStatusbar) {
        return CustomAlertDialog.Show(context, "توجه", message, null, "بستن", hideStatusbar, null, null);
    }


    public CustomAlertDialog showAlert(AppCompatActivity context, Throwable error) {
        String err = "";
        if (error == null || error.getMessage() == null)
            err = error.toString();
        return CustomAlertDialog.Show(context, "توجه", err, null, "بستن", null, null);
    }

    public CustomAlertDialog showAlert(AppCompatActivity context, String message, CustomAlertDialog.OnCancelClickListener okAction) {
        return CustomAlertDialog.Show(context, "توجه", message, null, "بستن", null, okAction);
    }

    public CustomAlertDialog showYesNOCustomAlert(AppCompatActivity context, String title, String question, String submitText, String cancelText, CustomAlertDialog.OnActionClickListener yesAction, CustomAlertDialog.OnCancelClickListener noAction) {

        return CustomAlertDialog.Show(context, title, question, submitText, cancelText, yesAction, noAction);

    }

    public CustomAlertDialog showYesNOCustomAlert(AppCompatActivity context, String title, String question, String submitText, String cancelText, boolean hideStatusbar, CustomAlertDialog.OnActionClickListener yesAction, CustomAlertDialog.OnCancelClickListener noAction) {

        return CustomAlertDialog.Show(context, title, question, submitText, cancelText, hideStatusbar, yesAction, noAction);

    }


    public CustomAlertDialog showYesNOCustomAlert_HTML(AppCompatActivity context, String title, String question, String submitText, String cancelText, CustomAlertDialog.OnActionClickListener yesAction, CustomAlertDialog.OnCancelClickListener noAction) {

        return CustomAlertDialog.Show(context, true, title, question, submitText, cancelText, yesAction, noAction);

    }




    public CustomAlertDialog showCustomAlert(AppCompatActivity context, String title, String content, String cancelText) {
        return CustomAlertDialog.Show(context, title, content, null, cancelText, null, null);
    }

    public CustomAlertDialog showCustomAlert(AppCompatActivity context, String title, String content, String cancelText, CustomAlertDialog.OnCancelClickListener onCancelClickListener) {
        if (context == null)
            return null;
        return CustomAlertDialog.Show(context, title, content, null, cancelText, null, onCancelClickListener);
    }




    public void showToast(FragmentActivity activity, String title, String subTitle, MotionToastStyle toastType) {

        FontChanger fontChanger = new FontChanger();

        MotionToast.Companion.createColorToast(activity,
                title,
                subTitle,
                toastType,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                fontChanger.getTitleFont(activity));

    }




    public interface OnDialogListener {

        void onOK(String input);

        void onNeutral(String input);

    }

    public interface OnPickerDialogListener {
        void onPick(int position);
    }



}
