package sketch.m_hospital.com.m_hospital.Utils;

import android.net.Uri;

import java.io.File;

/**
 * Created by nazia on 14-05-2017.
 */

public class Utils {
    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

}
