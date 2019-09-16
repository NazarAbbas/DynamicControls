package commdel.com;


import commdel.Models.ResponseDto;
import commdel.com.dcontrols.BuildConfig;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by mehtabk on 11-10-2016.
 */
public interface DataServiceApi {

    String HEADER_USERID = "UserId";
    String HEADER_FILENAME = "FileName";
    String HEADER_CASEID = "CaseId";

    public static final String BASE_URL = BuildConfig.SERVER_URL + "api/mobile/";


    @Multipart
    @POST("CaseAPI/SaveMedia")
    Call<ResponseDto> uploadCaseImage(@Header(HEADER_CASEID) String caseid, @Header(HEADER_FILENAME) String fileName, @Part MultipartBody.Part file);


}

