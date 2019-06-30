package com.example.jigokushoujo.appdomhogar;

public class CardItem {
    private int mTitleResource;
    private int mTextResource;
    private int mPriceResource;
    private int imgID;

    public CardItem(int mTextResource, int ImgID) {
        this.mTextResource = mTextResource;
    }

    //Constructor
    public CardItem(int title, int text, int price, int image) {
        mTitleResource = title;
        mTextResource = text;
        mPriceResource = price;
        imgID = image;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getmTitleResource() {
        return mTitleResource;
    }

    public void setmTitleResource(int mTitleResource) {
        this.mTitleResource = mTitleResource;
    }

    public int getmTextResource() {
        return mTextResource;
    }

    public void setmTextResource(int mTextResource) {
        this.mTextResource = mTextResource;
    }

    public int getmPriceResource() {
        return mPriceResource;
    }

    public void setmPriceResource(int mPriceResource) {
        this.mPriceResource = mPriceResource;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }
}
