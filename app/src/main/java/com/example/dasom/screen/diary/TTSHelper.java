package com.example.dasom.screen.diary;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.Locale;

public class TTSHelper {

    private static final String TTS_ID = "dasom";

    private TextToSpeech textToSpeech;
    private UtteranceProgressListener utteranceProgressListener;
    private Bundle ttsParams;

    private Context mContext;

    private OnSetupFinishListener onSetupFinishListener;

    public interface OnSetupFinishListener {
        void onSetupFinish(boolean isSuccessful);
    }

    public TTSHelper(Context context) {
        this.mContext = context;
    }

    public void startTTS(String text, Runnable onFinish) {
        textToSpeech.stop();
        utteranceProgressListener = new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                if (onFinish != null) onFinish.run();
            }

            @Override
            public void onError(String utteranceId) {

            }
        };
        textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams, TTS_ID);
    }

    public void stopTTS() {
        textToSpeech.stop();
    }

    public void setupTTSHelper(OnSetupFinishListener listener) {
        onSetupFinishListener = listener;
        setupTTS(() -> onSetupFinishListener.onSetupFinish(true));
    }

    private void setupTTS(Runnable onSuccess) {
        textToSpeech = new TextToSpeech(mContext, status -> {
            if (status == TextToSpeech.ERROR)
                onSetupFinishListener.onSetupFinish(false);
            else {
                textToSpeech.setLanguage(Locale.KOREAN);
                ttsParams = new Bundle();
                ttsParams.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
                textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);
                onSuccess.run();
            }
        });
    }

    public void destroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
}
