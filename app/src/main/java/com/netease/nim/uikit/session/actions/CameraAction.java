package com.netease.nim.uikit.session.actions;

import com.netease.nim.uikit.R;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class CameraAction extends PickImageAction {

    public CameraAction(ImageActionType imageActionType) {
        super(R.drawable.activity_nchat_xiangji, R.string.input_panel_take, true,imageActionType);
    }

    @Override
    protected void onPicked(File file) {
        IMMessage message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), file, file.getName());
        sendMessage(message);
    }
}

