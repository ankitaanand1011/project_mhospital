package sketch.m_hospital.com.m_hospital.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.togoto.imagezoomcrop.cropoverlay.CropOverlayView;
import io.togoto.imagezoomcrop.cropoverlay.edge.Edge;
import io.togoto.imagezoomcrop.photoview.IGetImageBounds;
import io.togoto.imagezoomcrop.photoview.PhotoView;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.SignupScreen;
import sketch.m_hospital.com.m_hospital.R;

/**
 * @author GT
 */
public class ImageCropActivity extends Activity {
    public static final String TAG = "ImageCropActivity";
    PhotoView mImageView;
    CropOverlayView mCropOverlayView;
    ImageView btnDone, btn_cut;
    View mMoveResizeText;
    String from;
    private ContentResolver mContentResolver;
    private final int IMAGE_MAX_SIZE = 1024;
    Uri thisImage;
    GlobalClass global;
    ProgressBar pb_crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_crop);
        global = (GlobalClass)getApplicationContext();
        mContentResolver = getContentResolver();
        mImageView = (PhotoView) findViewById(R.id.iv_photo);
        mCropOverlayView = (CropOverlayView) findViewById(R.id.crop_overlay);
        btnDone = (ImageView) findViewById(R.id.btn_done);
        btn_cut = (ImageView) findViewById(R.id.btn_cut);
        mMoveResizeText = findViewById(R.id.tv_move_resize_txt);
        pb_crop = (ProgressBar)findViewById(R.id.pb_crop);
        pb_crop.setVisibility(View.INVISIBLE);
        from = getIntent().getStringExtra("from").toString().trim();
      /*  btnDone.setOnClickListener(btnDoneListerner);
        btn_cut.setOnClickListener(btnCutListerner);*/
        mImageView.setImageBoundsListener(new IGetImageBounds() {
            @Override
            public Rect getImageBounds() {
                return mCropOverlayView.getImageBounds();
            }
        });
        thisImage = getIntent().getParcelableExtra("img_path");
        init( thisImage);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_crop.setVisibility(View.VISIBLE);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        saveOutput();
                    }
                });
                t.start();
            }
        });

        btn_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(ImageCropActivity.this, SignupScreen.class);
                    i.putExtra("secret", "no");
                    startActivity(i);
                    finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init(Uri mImageUri) {
        Bitmap bitmap = getBitmap(mImageUri);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        float minScale = mImageView.setMinimumScaleToFit(drawable);
        mImageView.setMaximumScale(minScale * 3);
        mImageView.setMediumScale(minScale * 2);
        mImageView.setScale(minScale);
        mImageView.setImageDrawable(drawable);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mMoveResizeText.getLayoutParams();
        lp.setMargins(0, Math.round(Edge.BOTTOM.getCoordinate()) + 20, 0, 0);
        mMoveResizeText.setLayoutParams(lp);
    }

    private boolean saveOutput() {
        Bitmap photo  = mImageView.getCroppedImage();
        File myDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getBaseContext().getString(R.string.app_name));
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "IMG_" + timeStamp + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            String getPath = file.getParent();
            Log.d("M", "path "+getPath);
            global.setCropped_Picture(photo);
            //file is empty
            global.setPic_path(file);
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bStream);

            if(from.equals("takephoto")) {
                Intent anotherIntent = new Intent(this, SignupScreen.class);
                anotherIntent.putExtra("secret", from);
                anotherIntent.putExtra("image_path",getPath);
                startActivity(anotherIntent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Bitmap getBitmap(Uri uri) {
        InputStream in = null;
        Bitmap returnedBitmap = null;
        try {
            in = mContentResolver.openInputStream(uri);
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, o2);
            in.close();
            String s = uri.getPath().substring(uri.getPath().lastIndexOf(".") + 1);
            global.setPic_extension(s);
            Log.d("M" , "path 1 "+uri.getPath());
            //First check
            ExifInterface ei = new ExifInterface(uri.getPath());
            //ExifInterface ei = new ExifInterface(uri.toString());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    returnedBitmap = rotateImage(bitmap, 90);
                    bitmap.recycle();
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    returnedBitmap = rotateImage(bitmap, 180);
                    bitmap.recycle();
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    returnedBitmap = rotateImage(bitmap, 270);
                    bitmap.recycle();
                    break;
                default:
                    returnedBitmap = bitmap;
            }
            return returnedBitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBackPressed() {
    }
}
