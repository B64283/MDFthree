package com.example.matthewdarke.mymappingphotos;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matthewdarke on 3/24/15.
 */
public class DetailFragment extends Fragment {

    public static final String EXTRA_ITEM = "com.example.matthewdarke.DetailActivity.EXTRA_ITEM";
    public ArrayList<Items> mItemsArrayList = new ArrayList<>();
    public String mItm;
    ImageView mImageView;

    public DetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v;
        v = inflater.inflate(R.layout.detail_fragment, container, false);

        //mItemsArrayList = MainActivity.itemsArray;
        TextView mItmName = (TextView) v.findViewById(R.id.textView_Name);
        TextView mItmQuant = (TextView) v.findViewById(R.id.textView_model);
        ImageView mItmPic = (ImageView) v.findViewById(R.id.imageViewPic);


        Intent intent = getActivity().getIntent();


        mItemsArrayList = (ArrayList<Items>) intent.getSerializableExtra("itemsArray");


        //bundles data and getSerializableExtras above
        Bundle data = intent.getExtras();


        if (data != null) {
///gets key value & set text


            String firstName = data.getString("mName");


            String firstAddress = data.getString("mQuantity");
            //String firstPic = data.getString("mPic");

            mItmName.setText(firstName);
            mItmQuant.setText(firstAddress);
            //mItmPic.setImageURI(Uri.parse(firstPic));
        }


        return v;
    }


}
