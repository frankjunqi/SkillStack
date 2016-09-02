package parcelableserializable.parcelableserializable.com.parcelableserializabledemo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kjh08490 on 2016/9/2.
 */

public class ParcelableTest implements Parcelable {
    public String sceneryId;
    public String sceneryName;
    public String address;
    public String addressServiceH5Map;//景区详情页面，地址的h5的服务类的地图链接
    public String cityId;
    public String cityName;
    public String grade;
    public String tcPrice;
    public String facePrice;
    public String traffic;
    public String shopping;
    public String ticketPrice;
    public String themeID;
    public String themeName;
    public String longitude; // "120.72505395",
    public String latitude; // "31.15940010",
    public String viewcount;
    public String nBigReasonUrl;
    public String productType;// 1：景区，2：玩乐
    public String poiUrl;
    public String cooperationLogoUrl;
    public String historyUrl;
    public String homeUrl;
    public String videoUrl;
    public String goWithUrl;


    protected ParcelableTest(Parcel in) {
        sceneryId = in.readString();
        sceneryName = in.readString();
        address = in.readString();
        addressServiceH5Map = in.readString();
        cityId = in.readString();
        cityName = in.readString();
        grade = in.readString();
        tcPrice = in.readString();
        facePrice = in.readString();
        traffic = in.readString();
        shopping = in.readString();
        ticketPrice = in.readString();
        themeID = in.readString();
        themeName = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        viewcount = in.readString();
        nBigReasonUrl = in.readString();
        productType = in.readString();
        poiUrl = in.readString();
        cooperationLogoUrl = in.readString();
        historyUrl = in.readString();
        homeUrl = in.readString();
        videoUrl = in.readString();
        goWithUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sceneryId);
        dest.writeString(sceneryName);
        dest.writeString(address);
        dest.writeString(addressServiceH5Map);
        dest.writeString(cityId);
        dest.writeString(cityName);
        dest.writeString(grade);
        dest.writeString(tcPrice);
        dest.writeString(facePrice);
        dest.writeString(traffic);
        dest.writeString(shopping);
        dest.writeString(ticketPrice);
        dest.writeString(themeID);
        dest.writeString(themeName);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(viewcount);
        dest.writeString(nBigReasonUrl);
        dest.writeString(productType);
        dest.writeString(poiUrl);
        dest.writeString(cooperationLogoUrl);
        dest.writeString(historyUrl);
        dest.writeString(homeUrl);
        dest.writeString(videoUrl);
        dest.writeString(goWithUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
        @Override
        public ParcelableTest createFromParcel(Parcel in) {
            return new ParcelableTest(in);
        }

        @Override
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };
}
