package com.example.seedsmartapp.ml;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CropModelHelper {

    private Interpreter interpreter;

    public CropModelHelper(Context context) {
        try {
            interpreter = new Interpreter(loadModelFile(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MappedByteBuffer loadModelFile(Context context) throws Exception {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("crop_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public int predict(float[] input) {

        float[][] output = new float[1][22]; // ⚠️ change if needed

        interpreter.run(new float[][]{input}, output);

        int maxIndex = 0;
        float max = output[0][0];

        for (int i = 1; i < output[0].length; i++) {
            if (output[0][i] > max) {
                max = output[0][i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}