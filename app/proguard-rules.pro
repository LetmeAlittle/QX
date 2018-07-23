# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Program_Files\Android\new_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#网易云相关混淆

-ignorewarnings
-dontwarn com.netease.**
-keep class com.netease.** {*;}
#如果你使用全文检索插件，需要加入
-dontwarn org.apache.lucene.**
-keep class org.apache.lucene.** {*;}
#Butter Knife 混淆配置
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Retrofit混淆配置
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid混淆配置
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }

# Gson混淆配置
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

#EventBus混淆配置
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep class de.greenrobot.event.**{*;}
-dontwarn de.greenrobot.event.**
-keepclassmembers class ** {
    public void onEvent*(**);
}

#定义的实体类混淆配置
-keep class com.ttt.qx.qxcall.function.login.model.entity.**{*;}
-keep class com.ttt.qx.qxcall.function.alipay.**{*;}
-keep class com.ttt.qx.qxcall.function.wxpay.**{*;}
-keep class com.ttt.qx.qxcall.function.home.model.entity.**{*;}
-keep class com.ttt.qx.qxcall.function.register.model.entity.**{*;}
-keep class com.ttt.qx.qxcall.function.start.model.entity.**{*;}
-keep class com.ttt.qx.qxcall.function.find.model.entity.**{*;}
-keep class com.ttt.qx.qxcall.function.listen.model.entity.**{*;}
-keep class com.ttt.qx.qxcall.function.message.entity.**{*;}

#glide不混淆配置
  -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }
    #以下是友盟混淆配置
        -dontshrink
        -dontoptimize
        -dontwarn com.google.android.maps.**
        -dontwarn android.webkit.WebView
        -dontwarn com.umeng.**
        -dontwarn com.tencent.weibo.sdk.**
        -dontwarn com.facebook.**
        -keep public class javax.**
        -keep public class android.webkit.**
        -dontwarn android.support.v4.**
        -keep enum com.facebook.**
        -keepattributes Exceptions,InnerClasses,Signature
        -keepattributes *Annotation*
        -keepattributes SourceFile,LineNumberTable

        -keep public interface com.facebook.**
        -keep public interface com.tencent.**
        -keep public interface com.umeng.socialize.**
        -keep public interface com.umeng.socialize.sensor.**
        -keep public interface com.umeng.scrshot.**

        -keep public class com.umeng.socialize.* {*;}


        -keep class com.facebook.**
        -keep class com.facebook.** { *; }
        -keep class com.umeng.scrshot.**
        -keep public class com.tencent.** {*;}
        -keep class com.umeng.socialize.sensor.**
        -keep class com.umeng.socialize.handler.**
        -keep class com.umeng.socialize.handler.*
        -keep class com.umeng.weixin.handler.**
        -keep class com.umeng.weixin.handler.*
        -keep class com.umeng.qq.handler.**
        -keep class com.umeng.qq.handler.*
        -keep class UMMoreHandler{*;}
        -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
        -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
        -keep class im.yixin.sdk.api.YXMessage {*;}
        -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
        -keep class com.tencent.mm.sdk.** {
           *;
        }
        -keep class com.tencent.mm.opensdk.** {
           *;
        }
        -keep class com.tencent.wxop.** {
           *;
        }
        -keep class com.tencent.mm.sdk.** {
           *;
        }
        -dontwarn twitter4j.**
        -keep class twitter4j.** { *; }

        -keep class com.tencent.** {*;}
        -dontwarn com.tencent.**
        -keep class com.kakao.** {*;}
        -dontwarn com.kakao.**
        -keep public class com.umeng.com.umeng.soexample.R$*{
            public static final int *;
        }
        -keep public class com.linkedin.android.mobilesdk.R$*{
            public static final int *;
        }
        -keepclassmembers enum * {
            public static **[] values();
            public static ** valueOf(java.lang.String);
        }

        -keep class com.tencent.open.TDialog$*
        -keep class com.tencent.open.TDialog$* {*;}
        -keep class com.tencent.open.PKDialog
        -keep class com.tencent.open.PKDialog {*;}
        -keep class com.tencent.open.PKDialog$*
        -keep class com.tencent.open.PKDialog$* {*;}
        -keep class com.umeng.socialize.impl.ImageImpl {*;}
        -keep class com.sina.** {*;}
        -dontwarn com.sina.**
        -keep class  com.alipay.share.sdk.** {
           *;
        }

        -keepnames class * implements android.os.Parcelable {
            public static final ** CREATOR;
        }

        -keep class com.linkedin.** { *; }
        -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
        -keepattributes Signature
        #统计
       -keepclassmembers class * {
          public <init> (org.json.JSONObject);
       }
       -keep public class com.ttt.qx.qxcall.R$*{
       public static final int *;
       }
       -keepclassmembers enum * {
           public static **[] values();
           public static ** valueOf(java.lang.String);
       }
       #支付宝混淆配置开始
       -keep class com.alipay.android.app.IAlixPay{*;}
       -keep class com.alipay.android.app.IAlixPay$Stub{*;}
       -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
       -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
       -keep class com.alipay.sdk.app.PayTask{ public *;}
       -keep class com.alipay.sdk.app.AuthTask{ public *;}
       -keep class com.alipay.sdk.app.H5PayCallback {
           <fields>;
           <methods>;
       }
       -keep class com.alipay.android.phone.mrpc.core.** { *; }
       -keep class com.alipay.apmobilesecuritysdk.** { *; }
       -keep class com.alipay.mobile.framework.service.annotation.** { *; }
       -keep class com.alipay.mobilesecuritysdk.face.** { *; }
       -keep class com.alipay.tscenter.biz.rpc.** { *; }
       -keep class org.json.alipay.** { *; }
       -keep class com.alipay.tscenter.** { *; }
       -keep class com.ta.utdid2.** { *;}
       -keep class com.ut.device.** { *;}
        #支付宝混淆配置结束
     #出现Okio异常错误处理配置
     -keep class org.codehaus.mojo.animal_sniffer.**{*;}
     -dontwarn org.codehaus.mojo.animal_sniffer.**

    -keep class java.nio.file.**{*;}
    -dontwarn java.nio.file.**
    -keep class com.alipay.**{*;}
    -dontwarn com.alipay.**

     -keep class com.ttt.qx.qxcall.QXCallApplication {*;}
         -dontwarn com.ttt.qx.qxcall.QXCallApplication