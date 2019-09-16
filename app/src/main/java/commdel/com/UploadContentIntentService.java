package commdel.com;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.io.File;
import java.util.LinkedList;

import commdel.Database.FileDBHandler;
import commdel.Models.ResponseDto;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class UploadContentIntentService extends IntentService {
    public static boolean DATA_SENDING = false;
    private final String TAG = "UploadContentIntentService";

    public UploadContentIntentService() {
        // TODO Auto-generated constructor stub
        super("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadContentIntentService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent myIntent) {
        if ((!DATA_SENDING)) {
            DATA_SENDING = true;
            LinkedList<Integer> imageIdList = FileDBHandler.getUnsendFileIds(getApplicationContext());
            if (imageIdList != null && imageIdList.size() > 0) {
                //sendTimelineNotification(imageIdList.size());
                for (int idx = 0; idx < imageIdList.size(); idx++) {
                    int rowId = imageIdList.get(idx);
                    try {
                        // get image all data from DB
                        final FileSavedModel fileSavedModel = FileDBHandler.getFileDetails(getApplicationContext(), rowId);
                        if (fileSavedModel == null) continue;

                        File file = new File(fileSavedModel.getFilePath());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                      /*  RequestBody requestFile = RequestBody.create(MediaType.parse("COL_IMAGE/jpeg"), fileSavedModel.getImageByte());
                        MultipartBody.Part body = MultipartBody.Part.createFormData("image", fileSavedModel.getImageName(), requestFile);*/

                        DataServiceApi api = new RestClient().getApi();
                        Call<ResponseDto> call = api.uploadCaseImage("123", fileSavedModel.getFileName(), fileToUpload);
                        ResponseDto responseDto = call.execute().body();
                        if (responseDto.getResponseCode() == 200) {
                            String x = "";
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DATA_SENDING = false;
    }
}