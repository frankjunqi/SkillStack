package parcelableserializable.parcelableserializable.com.parcelableserializabledemo.logansquare;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class LoganSquareTest {

    @JsonField
    public String sceneryId;
    @JsonField
    public String sceneryName;
    @JsonField
    public String address;
    @JsonField
    public String addressServiceH5Map;//景区详情页面，地址的h5的服务类的地图链接
    @JsonField
    public String cityId;
    @JsonField
    public String cityName;
    @JsonField
    public String grade;
    @JsonField
    public String tcPrice;
    @JsonField
    public String facePrice;
    @JsonField
    public String traffic;
    @JsonField
    public String shopping;
    @JsonField
    public String ticketPrice;
    @JsonField
    public String themeID;
    @JsonField
    public String themeName;
    @JsonField
    public String longitude; // "120.72505395",
    @JsonField
    public String latitude; // "31.15940010",
    @JsonField
    public String viewcount;
    @JsonField
    public String nBigReasonUrl;
    @JsonField
    public String productType;// 1：景区，2：玩乐
    @JsonField
    public String poiUrl;
    @JsonField
    public String cooperationLogoUrl;
    @JsonField
    public String historyUrl;
    @JsonField
    public String homeUrl;
    @JsonField
    public String videoUrl;
    @JsonField
    public String goWithUrl;


}