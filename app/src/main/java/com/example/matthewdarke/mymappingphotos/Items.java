package com.example.matthewdarke.mymappingphotos;

import java.io.Serializable;

/**
 * Created by matthewdarke on 3/24/15.
 */
public class Items implements Serializable {
    public static final long serialVersionUID = 7834767588211937458L;

    public static Items newInstance(String _name, String _quantity, String _pic) {

        Items items = new Items();
        items.setmName(_name);
        items.setmAddress(_quantity);
        items.setmPicture(_pic);
        return items;
    }

    private String mName;
    private String mQuantity;
    private String mPic;

    public Items() {


        mName = "";
        mQuantity = "";
        mPic = "";

    }

    private Items(String _name, String _quantity, String _pic) {

        mName = _name;
        mQuantity = _quantity;
        mPic = _pic;


    }


    public String getmName(String markerInf) {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mQuantity;
    }

    public void setmAddress(String mAddress) {
        this.mQuantity = mAddress;
    }

    public String getmPicture() {
        return mPic;
    }

    public void setmPicture(String mNumber) {
        this.mPic = mNumber;
    }

    @Override
    public String toString() {
        return mName;

    }

}
