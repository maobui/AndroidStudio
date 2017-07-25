package com.example.maobuidinh.wallpapers.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.maobuidinh.wallpapers.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by mao.buidinh on 7/25/2017.
 */

public class Utils {

    private String TAG = Utils.class.getSimpleName();
    private Context mContext;
    private PrefManager mPrefManager;

    // constructor
    public Utils(Context context) {
        this.mContext = context;
        mPrefManager = new PrefManager(mContext);
    }

    /*
     * getting screen width
     */
    @SuppressWarnings("deprecation")
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                display.getSize(point);
            }
        } catch (NoSuchMethodError ignore) {
            // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    public void saveImageToSDCard(Bitmap bitmap) {
        File myDir = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                mPrefManager.getGalleryName());

        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Wallpaper-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText( mContext,
                    mContext.getString(R.string.toast_saved).replace("#", "\"" + mPrefManager.getGalleryName() + "\""),
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Wallpaper saved to: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_saved_failed),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setAsWallpaper(Bitmap bitmap) {
        try {
            WallpaperManager wm = WallpaperManager.getInstance(mContext);

            wm.setBitmap(bitmap);
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_wallpaper_set),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_wallpaper_set_failed),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
