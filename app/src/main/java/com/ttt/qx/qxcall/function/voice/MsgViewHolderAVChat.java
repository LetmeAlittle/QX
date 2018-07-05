package com.ttt.qx.qxcall.function.voice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;
import com.ttt.qx.qxcall.R;


/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderAVChat extends MsgViewHolderBase {

    private ImageView typeImage;
    private TextView statusLabel;
    private TextView call_time_tv;

    public MsgViewHolderAVChat(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_avchat;
    }

    @Override
    protected void inflateContentView() {
        typeImage = findViewById(R.id.message_item_avchat_type_img);
        statusLabel = findViewById(R.id.message_item_avchat_state);
        call_time_tv = findViewById(R.id.call_time_tv);
    }

    @Override
    protected void bindContentView() {
        if (message.getAttachment() == null) {
            return;
        }

        layoutByDirection();

        refreshContent();
    }

    private void layoutByDirection() {
        AVChatAttachment attachment = (AVChatAttachment) message.getAttachment();
        if (isReceivedMessage()) {
            if (attachment.getType() == AVChatType.AUDIO) {
                typeImage.setImageResource(R.drawable.avchat_left_type_audio);
            } else {
//                typeImage.setImageResource(R.drawable.avchat_left_type_video);
            }
            statusLabel.setTextColor(context.getResources().getColor(R.color._363636));
            call_time_tv.setTextColor(context.getResources().getColor(R.color._363636));
        } else {
            if (attachment.getType() == AVChatType.AUDIO) {
                typeImage.setImageResource(R.drawable.avchat_right_type_audio);
            } else {
//                typeImage.setImageResource(R.drawable.avchat_right_type_video);
            }
            statusLabel.setTextColor(context.getResources().getColor(R.color._ffffff));
            call_time_tv.setTextColor(context.getResources().getColor(R.color._ffffff));
        }
    }

    private void refreshContent() {
        AVChatAttachment attachment = (AVChatAttachment) message.getAttachment();

        String textString = "";
        switch (attachment.getState()) {
            case Success: //成功接听
                call_time_tv.setVisibility(View.VISIBLE);
                textString = TimeUtil.secToTime(attachment.getDuration());
                break;
            case Missed: //未接听
            case Rejected: //主动拒绝
                call_time_tv.setVisibility(View.GONE);
                textString = context.getString(R.string.avchat_no_pick_up);
                break;
            default:
                call_time_tv.setVisibility(View.GONE);
                break;
        }

        statusLabel.setText(textString);
    }
}
