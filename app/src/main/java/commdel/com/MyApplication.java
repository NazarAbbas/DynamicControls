package commdel.com;

import android.support.multidex.MultiDexApplication;

import com.vdoers.dynamiccontrolslibrary.Utils.FileUtilsPath;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.mControls.DataObject;

public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Permissions.dataObject = new DataObject();
        FileUtilsPath.setImagesFolderName("TestingNewImages");
    }
}
