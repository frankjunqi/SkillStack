package parcelableserializable.parcelableserializable.com.parcelableserializabledemo.logansquare;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class LoganResponse {
    @JsonField
    public List<LoganSquareTest> respones;


}