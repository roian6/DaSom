package com.example.dasom.screen.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceHelper {

    private static final String TTS_ID = "dasom";

    private SpeechRecognizer speechRecognizer;
    private RecognitionListener recognitionListener;
    private Intent sttIntent;

    private TextToSpeech textToSpeech;
    private UtteranceProgressListener utteranceProgressListener;
    private Bundle ttsParams;

    private Context mContext;

    private OnSetupFinishListener onSetupFinishListener;

    public interface OnSetupFinishListener {
        void onSetupFinish(boolean isSuccessful);
    }

    public VoiceHelper(Context context) {
        this.mContext = context;
    }

    public void startSTT() {
        textToSpeech.stop();
        speechRecognizer.cancel();
        speechRecognizer.startListening(sttIntent);
    }

    public void stopSTT() {
        speechRecognizer.stopListening();
    }

    public void startTTS(String text) {
        utteranceProgressListener = null;
        textToSpeech.stop();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams, TTS_ID);
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

    public void setupVoice(OnSetupFinishListener listener) {
        onSetupFinishListener = listener;
        setupTTS(this::setupSTT);
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

    private void setupSTT() {
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
        speechRecognizer.setRecognitionListener(recognitionListener);
        onSetupFinishListener.onSetupFinish(true);
    }

    public interface OnSpeechEndListener {
        void onSpeechEnd();
    }

    public interface OnSpeechResultListener {
        void onSpeechResult(String result);
    }

    public interface OnSpeechErrorListener {
        void onSpeechError(int e);
    }

    public void setupRecognitionListener(OnSpeechEndListener end, OnSpeechResultListener result, OnSpeechErrorListener e) {
        this.recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                end.onSpeechEnd();
            }

            @Override
            public void onError(int error) {
                e.onSpeechError(error);
            }

            @Override
            public void onResults(Bundle results) {
                String key = SpeechRecognizer.RESULTS_RECOGNITION;
                ArrayList<String> mResult = results.getStringArrayList(key);

                if (mResult == null) {
                    result.onSpeechResult("");
                    return;
                }

                String[] rs = new String[mResult.size()];
                mResult.toArray(rs);
                String speech = rs[0];

                result.onSpeechResult(speech);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };
    }

    public void destroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }

        if (speechRecognizer != null) {
            speechRecognizer.cancel();
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
    }
}
