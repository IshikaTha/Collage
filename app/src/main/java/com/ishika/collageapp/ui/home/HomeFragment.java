package com.ishika.collageapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.ishika.collageapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class HomeFragment extends Fragment {


    private SliderLayout sliderLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1);
        setSliderView();
        return view;
    }

    private void setSliderView() {
        for(int i = 0; i<6; i++){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            switch (i){
                case 0:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/IMG-20180514-0154.png?alt=media&token=9b6ab671-851e-4a23-80e9-743c03762361");
                    break;
                case 1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/WhatsApp_Image_2021-05-08_at_08.13.55_1.png?alt=media&token=e6923e00-3745-43dd-9294-45332e94a9a9");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/WhatsApp_Image_2021-05-08_at_08.15.07_1.png?alt=media&token=3142fd46-d31f-4eb8-aa22-0464672f6981");
                    break;
                case 3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/WhatsApp_Image_2021-05-08_at_08.17.39.png?alt=media&token=5ed3a346-8dd1-49fc-a9a0-ab4b15775290");
                    break;
                case 4:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/WhatsApp%20Image%202021-05-08%20at%2008.23.51.jpeg?alt=media&token=41073bbc-98c3-4009-a675-b50690af2cfa");
                    break;
                case 5:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/WhatsApp%20Image%202021-05-08%20at%2019.19.13.jpeg?alt=media&token=70fa132d-86f4-4719-a7ff-abe91d5b7581");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }
    }
}