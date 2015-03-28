package com.example.matthewdarke.mymappingphotos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by matthewdarke on 3/24/15.
 */
public class AddFragment extends Fragment {

    private static final int REQUEST_TAKE_PICTURE = 0x01001;
AddActivity addAct;

   public Uri mImageUri;
    ImageView mImageView;
    private Items items;

    //create new ContactsArrayList
    public ArrayList<Items> mItems = new ArrayList<>();

    private EditText ItemName;
    private EditText ItemQuantity;


    public AddFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v;
        v = inflater.inflate(R.layout.add_fragment, container, false);

        mImageView = (ImageView)v.findViewById(R.id.imageViewPic);

        ItemName = (EditText) v.findViewById(R.id.name_edit);
        ItemQuantity = (EditText) v.findViewById(R.id.editText2);

        Button addBtn = (Button) v.findViewById(R.id.b_add);
        Button picAdd = (Button) v.findViewById(R.id.buttonPic);

        picAdd.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick (View v){

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getImageUri();
                if(mImageUri != null) {

                    //We can launch this intent without setting any extras if want a thumbnail,
                    // but we want the full size image. To get that, we must set MediaStore.EXTRA_OUTPUT
                    // extra which is a URI that points to our output file
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                }
                startActivityForResult(intent, REQUEST_TAKE_PICTURE);
            }







        });


        addBtn.setOnClickListener(new View.OnClickListener() {


            // add info to listview
            @Override
            public void onClick(View v) {
                String firstName = ItemName.getText().toString();
                String firstAddress = ItemQuantity.getText().toString();
                String firstPic = mImageUri.getPath();

                items = new Items();
                items.setmName(firstName);
                items.setmAddress(firstAddress);
                items.setmPicture(firstPic);
                mItems.add(items);

//set the extras directly to the intent and let it store the data in its own internal bundle
                //putExtra is used to pass data from one activity to the next.
                Intent intent = new Intent();
                intent.putExtra("conData", mItems);


                getActivity().setResult(Activity.RESULT_OK, intent);

                getActivity().finish();
            }

        });

        return v;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PICTURE) {
            if(mImageUri != null) {
                mImageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
               addImageToGallery(mImageUri);



            } else {
                mImageView.setImageBitmap((Bitmap)data.getParcelableExtra("data"));
            }
        }
    }


    private Uri getImageUri() {

        //unique name for our images as to not overwrite any other images on the device

        String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appDir = new File(imageDir, "CameraExample");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return Uri.fromFile(image);
    }


    public void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        getActivity().sendBroadcast(scanIntent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

}