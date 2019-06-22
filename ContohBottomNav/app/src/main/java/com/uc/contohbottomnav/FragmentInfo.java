package com.uc.contohbottomnav;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInfo extends Fragment {

    public FragmentInfo() {
        // Required empty public constructor
    }

    private CardView cvinfohow, cvinfoassets, cvinfopoints;
    private AlphaAnimation btnKlik = new AlphaAnimation(1F, 0.6F);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_info, container, false);

        cvinfohow = v.findViewById(R.id.cv_info_how_to_use);
        cvinfoassets = v.findViewById(R.id.cv_info_assets);
        cvinfopoints = v.findViewById(R.id.cv_info_points);

        cvinfohow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i  = new Intent(getActivity(), InfoHow.class);
                startActivity(i);
            }
        });

        cvinfoassets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i  = new Intent(getActivity(), InfoAssets.class);
                startActivity(i);
            }
        });

        cvinfopoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i  = new Intent(getActivity(), InfoPoint.class);
                startActivity(i);
            }
        });

        return v;
    }

}
