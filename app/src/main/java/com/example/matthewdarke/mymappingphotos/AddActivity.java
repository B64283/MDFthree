package com.example.matthewdarke.mymappingphotos;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by matthewdarke on 3/24/15.
 */
public class AddActivity extends Activity {





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (savedInstanceState == null){
            AddFragment f1 = new AddFragment();
            getFragmentManager()
                    .beginTransaction()


                    .add(R.id.add_activity_id, f1)
                    .commit();


        }


        //actionBar for navagation back to main



        getActionBar().setDisplayHomeAsUpEnabled(true);



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }






}