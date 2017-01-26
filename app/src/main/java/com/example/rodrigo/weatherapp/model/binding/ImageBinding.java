package com.example.rodrigo.weatherapp.model.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.model.Weather;
import com.squareup.picasso.Picasso;

/**
 * ImageBinding.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class ImageBinding {
    @BindingAdapter({"android:src"})
    public static void setImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
            .load(url)
            .into(imageView);
    }

    @BindingAdapter({"android:background"})
    public static void setBackground(LinearLayout layout, Weather weather) {
        Integer minTemperature = Integer.valueOf(weather.getTempMinC().replace("ºC", ""));
		Integer maxTemperature = Integer.valueOf(weather.getTempMaxC().replace("ºC", ""));
        Double quotient = (double) ((minTemperature + maxTemperature) / 2);
        Integer medium = quotient.intValue();

        int drawableId;
        if (medium <= 0) {
            drawableId = R.drawable.background_gray;
        } else if (medium > 0 && medium <= 15) {
            drawableId = R.drawable.background_blue;
        } else if (medium > 15 && medium <= 30) {
            drawableId = R.drawable.background_yellow;
        } else {
            drawableId = R.drawable.background_red;
        }
        layout.setBackgroundResource(drawableId);
    }
}