package com.kt.myshortapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class VideoRecordActivity extends AppCompatActivity implements Callback, OnClickListener {
    private VideoView videoView = null;
    private MediaController mc = null;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    public MediaRecorder mediaRecorder = new MediaRecorder();
    private Camera mCamera;
    private Button btnStart;
    private static final int PERMISSION_CODE = 1000;
    private MediaPlayer player;
    private ProgressDialog progressDialog;
    private FFmpeg ffmpeg;

    private File dir;
    private String fileName, mergeVideoPath;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        mCamera = Camera.open();
        // mCamera.setDisplayOrientation(90);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }


    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {

                    initview();

                } else {
                    // permission denied
                }
                return;
            }
        }
    }


    @SuppressLint("NewApi")
    protected void startRecording() throws IOException {

        try {
            AssetFileDescriptor afd = getAssets().openFd("ab_fark_nahi.mp3");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mCamera == null)
            mCamera = Camera.open();


        Date date = new Date();
        fileName = "/rec" + System.currentTimeMillis() + ".mp4";
        File file = new File(dir, fileName);

        mediaRecorder = new MediaRecorder();
        mCamera.lock();
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

        /**********************************************************************/
        int width = 320;//640, 320
        int height = 240;//352, 240
        mediaRecorder.setVideoSize(width, height);
        mediaRecorder.setVideoEncodingBitRate(1700000);
        mediaRecorder.setVideoFrameRate(30);
        /**********************************************************************/

//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setOutputFile(dir + fileName);
        mediaRecorder.setOrientationHint(90);
        mediaRecorder.prepare();

        player.start();

        mediaRecorder.start();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                btnStart.setText("Start");
                mediaRecorder.stop();
                mediaRecorder.release();
                player.release();
                mediaRecorder = null;
                player = null;

                mergeAudioVideo();

            }
        });

        refreshGallery(file);

    }


    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    protected void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mCamera.release();
            // mCamera.lock();
        }
    }

    private void releaseMediaRecorder() {

        if (mediaRecorder != null) {
            mediaRecorder.reset(); // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        } else {
            initview();
        }
    }

    private void initview() {

        File sdCard = Environment.getExternalStorageDirectory();
        dir = new File(sdCard.getAbsolutePath() + "/MyShortApp/MSVideos");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        copyAssets(this, dir);

        if (mCamera != null) {
            Parameters params = mCamera.getParameters();
            mCamera.setParameters(params);
            mCamera.setDisplayOrientation(90);
            Log.i("Surface", "Created");
        } else {
            Toast.makeText(getApplicationContext(), "Camera not available!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (btnStart.getText().toString().equalsIgnoreCase("Start")) {
                    btnStart.setText("Stop");
                    try {
                        startRecording();
                    } catch (IOException e) {
                        String message = e.getMessage();
                        Log.i(null, "Problem " + message);
                        mediaRecorder.release();
                        e.printStackTrace();
                    }
                } else {
                    btnStart.setText("Start");
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    player.release();
                    mediaRecorder = null;
                    player = null;
                }
                break;

            default:
                break;
        }
    }


    void mergeAudioVideo() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCanceledOnTouchOutside(false);
        ffmpeg = FFmpeg.getInstance(this.getApplicationContext());
        // loadFFMpegBinary();

        try {
            mergeFunction();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void loadFFMpegBinary() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    showUnsupportedExceptionDialog();

                    Log.v(" output ", "Fail");
                }

                @Override
                public void onSuccess() {
                    super.onSuccess();
                    Log.v(" output ", "Successs");
                }

            });
        } catch (FFmpegNotSupportedException e) {
            showUnsupportedExceptionDialog();
        }
    }


    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(("FFMPEG"))
                .setMessage(("Not Supported"))
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }

    private void mergeFunction() throws IOException, InterruptedException {

        ArrayList<String> cmd = new ArrayList<String>();

        fileName = dir.getPath() + fileName;
        Log.v("Krishn ", "fileName b " + fileName);

//        ffmpeg -i video.mp4 -i audio.wav -c:v copy -c:a aac output.mp4
//        ffmpeg -i video.mp4 -i audio.m4a -c:v copy -c:a copy output.mp4

        cmd.add("-i");
        cmd.add(new File(fileName).getCanonicalPath());
        cmd.add("-i");
//        cmd.add(String.valueOf(getAssets().openFd("ab_fark_nahi.mp3")));
        cmd.add(new File(dir.getPath() + "/ab_fark_nahi.mp3").getCanonicalPath());
        cmd.add("-c:v");
        cmd.add("copy");
        cmd.add("-c:a");
        cmd.add("aac");

        Date date = new Date();
        String mergeFileName = "/recMerge" + System.currentTimeMillis() + ".mp4";

        Log.v("Krishn ", "fileName " + fileName);
        Log.v("Krishn ", "mergeFileName " + mergeFileName);


        File fileOut = new File(mergeFileName);

        File file = new File(dir, mergeFileName);
        mergeVideoPath = file.getPath();
        Log.v("Krishn ", "mergeVideoPath " + mergeVideoPath);
        cmd.add(file.getCanonicalPath());
        execFFmpegBinary(cmd.toArray(new String[0]));
    }

    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Toast.makeText(VideoRecordActivity.this, "File Not Created", Toast.LENGTH_SHORT).show();
                    Log.v("Krishn", "FAILED with output " + s);

                    progressDialog.setMessage("FAILED\n" + s);
                }

                @Override
                public void onSuccess(String s) {
                    Toast.makeText(VideoRecordActivity.this, "File Successfully Created", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(VideoRecordActivity.this, PreviewActivity.class);
                    intent.putExtra("videoPath", mergeVideoPath);
                    startActivity(intent);
                }

                @Override
                public void onProgress(String s) {
                    progressDialog.setMessage("Processing");
                }

                @Override
                public void onStart() {

                    Log.d("FFMPEG", "Started command : ffmpeg " + command);
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();
                }

                @Override
                public void onFinish() {
                    Log.d("FFMPEG", "Finished command : ffmpeg " + command);
                    progressDialog.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
        }
    }


    public static void copyAssets(Context context, File dir) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(dir, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                        in = null;
                    } catch (IOException e) {

                    }
                }
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                        out = null;
                    } catch (IOException e) {

                    }
                }
            }
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}