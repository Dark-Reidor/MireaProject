package ru.mirea.linnik.mireaproject.ui.permission;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.mirea.linnik.mireaproject.R;

import static android.app.Activity.RESULT_OK;

public class PermissionFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView textViewAzimuth;
    private TextView textViewPitch;
    private TextView textViewRoll;
    private Button buttonPhoto;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonPlay;
    private Button buttonUnplay;

    private ImageView imageView;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final int CAMERA_REQUEST = 0;
    private boolean isWork = false;
    private Uri imageUri;

    private MediaRecorder mediaRecorder;
    public static String fileName;

    private final static int RECORD_AUDIO = 0;
    public static String DIRECTORY_MUSIC = "Music";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permission, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        textViewAzimuth = root.findViewById(R.id.azimuth);
        textViewPitch = root.findViewById(R.id.pitch);
        textViewRoll = root.findViewById(R.id.roll);
        imageView = root.findViewById(R.id.imagePhoto);
        buttonPhoto = root.findViewById(R.id.btn_photo);
        buttonPlay = root.findViewById(R.id.play_recording);
        buttonStart = root.findViewById(R.id.btn_start_recording);
        buttonStop = root.findViewById(R.id.btn_stop_recording);
        buttonUnplay = root.findViewById(R.id.button_unplay_record);

        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        fileName = cw.getExternalFilesDir(DIRECTORY_MUSIC) + "/record.3gpp";

        int cameraPermissionStatus = ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus
                == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.
                            CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }

        View.OnClickListener photoClickListener = v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (isWork)
            {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String authorities = getContext().getPackageName() + ".fileprovider";
                imageUri = FileProvider.getUriForFile(getContext(), authorities, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        };

        buttonPhoto.setOnClickListener(photoClickListener);

        View.OnClickListener startClickListener = v -> {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);
            } else {
                try {
                    releaseRecorder();

                    File outFile = new File(fileName);
                    if (outFile.exists()) {
                        outFile.delete();
                    }

                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile(fileName);
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    Toast.makeText(getContext(), "Recording is started", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        buttonStart.setOnClickListener(startClickListener);

        View.OnClickListener stopClickListener = v -> {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
            }
            Toast.makeText(getContext(), "Recording is stopped", Toast.LENGTH_SHORT).show();
        };

        buttonStop.setOnClickListener(stopClickListener);

        View.OnClickListener playClickListener = v -> getActivity().startService(new Intent(getContext(), RecorderService.class));

        buttonPlay.setOnClickListener(playClickListener);

        View.OnClickListener unplayClickListener = v -> getActivity().stopService(new Intent(getContext(), RecorderService.class));

        buttonUnplay.setOnClickListener(unplayClickListener);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth = event.values[0];
        float pitch = event.values[1];
        float roll = event.values[2];

        textViewAzimuth.setText(String.valueOf(azimuth));
        textViewPitch.setText(String.valueOf(pitch));
        textViewRoll.setText(String.valueOf(roll));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView.setImageURI(imageUri);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isWork = true;
            } else {
                isWork = false;
            }
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseRecorder();
    }
}