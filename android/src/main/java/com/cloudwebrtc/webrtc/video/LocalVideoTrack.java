package com.cloudwebrtc.webrtc.video;

import androidx.annotation.Nullable;

import com.cloudwebrtc.webrtc.LocalTrack;

import org.webrtc.VideoFrame;
import org.webrtc.VideoProcessor;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class LocalVideoTrack extends LocalTrack implements VideoProcessor {
    static private final String TAG = "DEVTO LocalVideoTrack";
    public interface ExternalVideoFrameProcessing extends VideoSink {
        void setSink(VideoSink videoSink);
    }

    public LocalVideoTrack(VideoTrack videoTrack) {
        super(videoTrack);
    }

    List<ExternalVideoFrameProcessing> processors = new ArrayList<>();

    public void addProcessor(ExternalVideoFrameProcessing processor) {
        Log.i(TAG, "add processor");
        synchronized (processors) {
            if (!processors.isEmpty()) {
                processors.get(processors.size()-1).setSink(processor);
            }
            processor.setSink(sink);
            processors.add(processor);
        }
    }

    public void removeProcessor(ExternalVideoFrameProcessing processor) {
        Log.i(TAG, "remove processor");
        synchronized (processors) {
            processors.remove(processor);
            if (!processors.isEmpty()) {
                processors.get(processors.size()-1).setSink(sink);
            }
        }
    }

    private VideoSink sink = null;

    @Override
    public void setSink(@Nullable VideoSink videoSink) {
        sink = videoSink;
        if (!processors.isEmpty()) {
            processors.get(processors.size()-1).setSink(sink);
        }
    }

    @Override
    public void onCapturerStarted(boolean b) {}

    @Override
    public void onCapturerStopped() {}

    @Override
    public void onFrameCaptured(VideoFrame videoFrame) {
        if (sink != null) {
            synchronized (processors) {
                if (!processors.isEmpty()) {
                    processors.get(0).onFrame(videoFrame);
                } else {
                    sink.onFrame(videoFrame);
                }
            }
        }
    }
}
