package de.dhbw.chaincar.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("imagejson")
    @Expose
    private Image[] imagejson;

    /**
     * No args constructor for use in serialization
     *
     */
    public ImageResponse() {
    }

    /**
     *
     * @param msg
     * @param imagejson
     * @param success
     * @param error
     */
    public ImageResponse(Integer success, Boolean error, String msg, Image[] imagejson) {
        super();
        this.success = success;
        this.error = error;
        this.msg = msg;
        this.imagejson = imagejson;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Image[] getImagejson() {
        return imagejson;
    }

    public void setImagejson(Image[] imagejson) {
        this.imagejson = imagejson;
    }

    public String toCompactString() {
        return "Success: " + success + "\nError: " + error + "\nMessage: " + msg;
    }
}