package commdel.com;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.vdoers.Database.StateCityDBHandler;
import com.vdoers.Models.StateCityList;
import com.vdoers.Models.StateCityListResponse;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowListResponse;

import java.io.InputStream;

import commdel.Database.JSONWorkflowDBHandler;
import commdel.com.dcontrols.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            String json = AssetJSONFile(this, "workflowlistresponse.json");
            Gson gson = new Gson();
            JsonWorkflowListResponse jsonWorkflowListResponse = gson.fromJson(json, JsonWorkflowListResponse.class);
            JSONWorkflowDBHandler.delete(this);
            JSONWorkflowDBHandler.saveData(this, jsonWorkflowListResponse.getJsonWorkflowList());

            json = AssetJSONFile(this, "statecity.json");
            StateCityListResponse stateCityListResponse = gson.fromJson(json, StateCityListResponse.class);
            StateCityDBHandler.deleteData(this);
            StateCityDBHandler.save(this, stateCityListResponse.getStateCityList());

            Intent intent = new Intent(this, DynamicControlRendererActivity.class);
            intent.putExtra(DynamicControlRendererActivity.WORKFLOW_ID, 63);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception ex) {
            String x = "";
        }


    }

    public static String AssetJSONFile(Context context, String fileName) {
        try {
            AssetManager manager = context.getAssets();
            InputStream file = manager.open(fileName);
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();
            return new String(formArray);
        } catch (Exception ex) {
            String x = "";

        }
        return "";
    }

}
