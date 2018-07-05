package com.ttt.qx.qxcall.function.voice;

/**
 * 音视频界面操作
 */
public interface AVChatUIListener {
    void onHangUp();
    void onRefuse();
    void onReceive();
    void toggleMute();
    void onSendGift();
    void onPackUp();
    void toggleSpeaker();
    void toggleRecord();
    void videoSwitchAudio();
    void audioSwitchVideo();
    void switchCamera();
    void closeCamera();
}
