package com.ttt.qx.qxcall.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by wyd on 2015/12/18.
 */
public class IntentUtil {
    public static void jumpIntent(Context ctx, Class<?> cls){
        Intent intent=new Intent(ctx,cls);
        ctx.startActivity(intent);
    }
}
