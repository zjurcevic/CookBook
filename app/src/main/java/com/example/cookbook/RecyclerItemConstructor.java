package com.example.cookbook;

public class RecyclerItemConstructor {

    private String mImageUrl;
    private String mTitle;
    private String mDescription;
    private String mRecipeUrl;

    public RecyclerItemConstructor(String mImageUrl, String mTitle, String mDescription, String mRecipeUrl) {
        this.mImageUrl = mImageUrl;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mRecipeUrl = mRecipeUrl;
    }

    public String getmRecipeUrl() {
        return mRecipeUrl;
    }

    public void setmRecipeUrl(String mRecipeUrl) {
        this.mRecipeUrl = mRecipeUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
