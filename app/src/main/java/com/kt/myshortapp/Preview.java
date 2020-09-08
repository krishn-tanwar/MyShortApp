/*
package com.kt.myshortapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.greetune.databinding.PreviewBinding;
import com.kavya.greetune.activity.MainActivity;
import com.kavya.greetune.util.AppConstants;
import com.kavya.greetune.util.AppSession;
import com.kavya.greetune.util.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * created by krishn on 15/03/18.
 *//*


public class Preview extends Fragment implements AppConstants, View.OnClickListener {
    private Context context;

    private Bundle bundle;
    private String videoPath = "";
    Bitmap bitmap;

    DisplayMetrics dm;
    SurfaceView sur_View;
    MediaController media_Controller;
    byte b[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        previewBinding = DataBindingUtil.inflate(inflater, R.layout.preview, container, false);
        View view = previewBinding.getRoot();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        context = getActivity();
        appSession = new AppSession(context);
        utilities = Utilities.getInstance(context);
        setHasOptionsMenu(true);
        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("videoPath")) {
                videoPath = bundle.getString("videoPath");
                Log.v("videoPath ", "videoPath " + videoPath);

            } else if (bundle.containsKey("image")) {

                byte[] byteArray = bundle.getByteArray("image");
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                Log.v("bitmap ", "preview bitmap " + bitmap);
            }
        }
        intiView(view);
        initToolbar();
//        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
//        Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
    }

    void intiView(View view) {
        try {
            File mFolder = new File(Environment.getExternalStorageDirectory(), "DevGreetings");
            if (!mFolder.exists()) {
                boolean b = mFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap != null && !bitmap.equals("")) {
            Log.v("bitmap ", "preview bitmap " + bitmap);
            previewBinding.ivImageEdit.setImageBitmap(bitmap);
            previewBinding.vvVideo.setVisibility(View.GONE);
            previewBinding.ivImageEdit.setVisibility(View.VISIBLE);
        } else if (videoPath != null && !videoPath.equals("")) {
            Log.v("videoPath ", "preview videoPath " + videoPath);
            previewBinding.ivImageEdit.setVisibility(View.GONE);
            previewBinding.vvVideo.setVisibility(View.VISIBLE);
            playVideo();

        }
        previewBinding.tvSendGreetune.setOnClickListener(this);
        previewBinding.tvSaveGreetune.setOnClickListener(this);

        previewBinding.tvRateGreetune.setOnClickListener(this);
        previewBinding.tvShareGreetune.setOnClickListener(this);
        previewBinding.tvExit.setOnClickListener(this);

        previewBinding.rlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(" rlImage ", " rlImage click");
            }
        });
    }

    public void playVideo() {
        media_Controller = new MediaController(getActivity());
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
//            previewBinding.vvVideo.setMinimumWidth(width);
//            previewBinding.vvVideo.setMinimumHeight(height);
        previewBinding.vvVideo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        previewBinding.vvVideo.setMediaController(media_Controller);
        previewBinding.vvVideo.setVideoPath(videoPath);
        previewBinding.vvVideo.start();
    }

    private void initToolbar() {
        androidx.appcompat.app.ActionBar actionBar = ((MainActivity) context).getSupportActionBar();
        actionBar.show();
        ((MainActivity) getActivity()).createBackButton(getString(R.string.preview));
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(getResources().getColor(R.color.transparent));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //   ((SplashActivity) getActivity()).setNavigationIconColor(AppConstants.DARK);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.back_arrow));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        initToolbarGreetune();

        if (videoPath != null && !videoPath.equals("")) {
            Uri uri = Uri.parse(videoPath);

            File nf = context.getExternalFilesDir("Temp");

            if (nf.exists()) {
                nf.delete();
            }

          */
/*  File nf = new File(Environment.getExternalStorageDirectory() +"/"+"GreetuneVideo/"+uri.getLastPathSegment());

            if(nf.exists()){
                nf.delete();
            }*//*

        }


    }

    public void initToolbarGreetune() {
        androidx.appcompat.app.ActionBar actionBar = ((MainActivity) context).getSupportActionBar();
        actionBar.show();
        ((MainActivity) getActivity()).createBackButton(getString(R.string.greetune));
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(getResources().getColor(R.color.transparent));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //   ((SplashActivity) getActivity()).setNavigationIconColor(AppConstants.DARK);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.back_arrow));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mFilterAction = menu.findItem(R.id.menu_dot);
        mFilterAction.setVisible(true);

        mFilterAction2 = menu.findItem(R.id.menu_arrow);
        mFilterAction2.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_dot:
                if (isMenuOpen) {
                    isMenuOpen = false;
                    previewBinding.cvMenu.setVisibility(View.VISIBLE);
                } else {
                    isMenuOpen = true;
                    previewBinding.cvMenu.setVisibility(View.GONE);
                }
                break;
            case R.id.menu_arrow:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == previewBinding.tvSendGreetune) {
            try {
                if (videoPath != null && !videoPath.equals("")) {
                    shareVideo();
                } else {
                    shareImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (view == previewBinding.tvSaveGreetune) {
            try {
                if (videoPath != null && !videoPath.equals("")) {
                    saveVideo();
                } else {
                    saveImage();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (view == previewBinding.tvRateGreetune) {
            previewBinding.cvMenu.setVisibility(View.GONE);
            rateUs(getActivity(), getResources().getString(R.string.yay_greetune));

        } else if (view == previewBinding.tvShareGreetune) {
            previewBinding.cvMenu.setVisibility(View.GONE);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, PLAY_STOR_URL);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, ""));
        } else if (view == previewBinding.tvExit) {
            previewBinding.cvMenu.setVisibility(View.GONE);
            dailogExitConfirm(context);
        }
    }

    ProgressDialog pd;

    public void saveImage() {
        pd = new ProgressDialog(context);
        pd.setMessage("saving your image");
        pd.show();
        RelativeLayout savingLayout = previewBinding.rlImage;
        File file = saveBitMap(context, savingLayout);
        if (file != null) {
            pd.cancel();
            Log.i("TAG", "Drawing saved to the gallery!");
            Toast.makeText(context, getString(R.string.image_saved), Toast.LENGTH_SHORT).show();
        } else {
            pd.cancel();
            Log.i("TAG", "Oops! Image could not be saved.");
            Toast.makeText(context, getString(R.string.image_not_saved), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveVideo() {


        Uri videoUri = Uri.parse(videoPath);

        Log.d("Path", videoUri.getLastPathSegment() + "");

        File file = new File(getActivity().getCacheDir(), "DevGreetingVideo/" + videoUri.getLastPathSegment());

        try {
            FileInputStream fin = new FileInputStream(file);
            byte b[] = new byte[(int) file.length()];
            fin.read(b);

            File nf = new File(Environment.getExternalStoragePublicDirectory("DevGreetings"), "DevGreetingVideo");
            */
/*File nf = new File(context.getExternalFilesDir(
                    "Temp"), videoUri.getLastPathSegment());*//*


            String filename = nf.getPath() + File.separator + videoUri.getLastPathSegment();
            File pictureFile = new File(filename);
            FileOutputStream fw = new FileOutputStream(pictureFile);
            fw.write(b);
            fw.flush();
            fw.close();

            MediaScannerConnection.scanFile(context, new String[]{nf.getPath()}, new String[]{"video/mp4"}, null);

            //addVideo(file);
            Toast.makeText(context, getString(R.string.video_saved), Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Uri addVideo(File videoFile) {
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Video.Media.TITLE, "My video title");
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
        return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }


    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }


    // share

    private String getVideoPath(Uri uri) {
        String[] data = {MediaStore.Video.Media.DATA};
        CursorLoader loader = new CursorLoader(context, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private File exportFile(File src, File dst) throws IOException {

        //if folder does not exist
        if (!dst.exists()) {
            if (!dst.mkdir()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File expFile = new File(dst.getPath() + File.separator + "Vedio_" + timeStamp + ".mp4");
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(expFile).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }

        return expFile;
    }

    public byte[] getVideoBytes(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        for (int readNum; (readNum = fis.read(b)) != -1; ) {
            bos.write(b, 0, readNum);
        }
        byte[] bytes = bos.toByteArray();
        return bytes;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (videoPath != null && !videoPath.equals("")) {
            playVideo();
        }
    }
}
*/
