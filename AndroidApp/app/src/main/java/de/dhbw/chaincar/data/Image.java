package de.dhbw.chaincar.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;

    private Bitmap decodedImage;

    /**
     * No args constructor for use in serialization
     *
     */
    public Image() {
    }

    /**
     *
     * @param image
     * @param id
     */
    public Image(Integer id, String image) {
        super();
        this.id = id;
        this.image = image;

        //decode base64 string to image
        byte[] imageBytes = Base64.decode(image, Base64.DEFAULT);
        this.decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        //USAGE: image.setImageBitmap(decodedImage);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getImageAsBitmap(){
        return decodedImage;
    }

}
