package commdel.com;

import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.multidex.MultiDexApplication;
import android.util.TypedValue;

import com.vdoers.dynamiccontrolslibrary.Utils.FileUtilsPath;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.Utils.ThemeColor;
import com.vdoers.dynamiccontrolslibrary.mControls.DataObject;

import commdel.com.dcontrols.R;

public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Permissions.dataObject = new DataObject();
        ThemeColor.themeColor = getResources().getColor(R.color.app_theme_color);
        FileUtilsPath.setImagesFolderName("CroppedImages");
        FileUtilsPath.setImageHeightWidth(1000, 1000);
    }
}
