package com.movielist.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.movielist.R;
import com.movielist.view.view_interfaces.RateListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RateDialog extends DialogFragment {

    private ImageView[] mImageViews;
    private RateListener mRateListener;
    private int rating;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        rating = 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_lview,null);
        ViewGroup container = view.findViewById(R.id.dialog_container);
        mImageViews = new ImageView[10];

        for (int i = 0; i < 10; i++){

            mImageViews[i] = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (28 * getResources().getDisplayMetrics().density),
                    (int) (28 * getResources().getDisplayMetrics().density));
            params.topMargin = (int)(8 * getResources().getDisplayMetrics().density);
            mImageViews[i].setLayoutParams(params);

            mImageViews[i].setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViews[i].setImageResource(R.drawable.ic_star_border_white_24dp);

            int finalI = i;

            mImageViews[i].setOnClickListener(v -> {
                for (int j = 0; j < 10; j++) {
                    if (j <= finalI) {
                        rating = finalI;
                        mImageViews[j].setImageResource(R.drawable.ic_star_white_24dp);
                    } else {
                        mImageViews[j].setImageResource(R.drawable.ic_star_border_white_24dp);
                    }
                }
            });
            container.addView(mImageViews[i]);
        }

        builder.setView(view);
        builder.setMessage("Rate");
        builder.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("rate", (dialog, which) -> mRateListener.rate(rating  + 1));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {

            mRateListener = (RateListener) context;

        }catch (ClassCastException e){

            Log.e("ClassCastError","Activity must implement RateListener");

        }
    }
}
