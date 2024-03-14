package com.test.picasso;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.OutputStream;

public class PicMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        String url = "https://play-lh.googleusercontent.com/5LIMaa7WTNy34bzdFhBETa2MRj7mFJZWb8gCn_uyxQkUvFx_uOFCeQjcK16c6WpBA3E=s48-rw";
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            downloadAndSaveImage(this,url,"sdasa.jpg");
        });
    }

    public void downloadAndSaveImage(final Context context, String imageUrl, String imageName) {
        // 使用Picasso下载图片
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // 保存图片到系统相册
                saveImageToGallery(context, bitmap, imageName);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                // 处理下载失败
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // 准备加载图片
            }
        });
    }

    private void saveImageToGallery(Context context, Bitmap bitmap, String imageName) {
        // 创建一个包含图片元数据的ContentValues对象
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        // 根据Android版本设置图片存储位置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }

        // 插入图片元数据到MediaStore
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try {
            // 获取Uri对应的输出流，用于写入图片数据
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            Log.e("xxxx","==success==");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("xxxx","==fail=="+e.getMessage());
        }
    }
}
