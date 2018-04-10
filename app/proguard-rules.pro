# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Retrofit 2.X
## https://square.github.io/retrofit/ ##
####第三方库#######
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
#
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#}
#
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#-keepattributes EnclosingMethod
## Gson specific classes
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#
## GreenDao rules
## Source: http://greendao-orm.com/documentation/technical-faq
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static java.lang.String TABLENAME;
#}
#-keep class **$Properties
#
## Glide specific rules #
## https://github.com/bumptech/glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#    **[] $VALUES;
#    public *;
#}
#
## fastjson proguard rules
## https://github.com/alibaba/fastjson
#-dontwarn com.alibaba.fastjson.**
#-keepattributes Signature
#-keepattributes *Annotation*
#
## ButterKnife 7
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keepclasseswithmembernames class * {
#    @butterknife.* <fields>;
#}
#-keepclasseswithmembernames class * {
#    @butterknife.* <methods>;
#}
#
#####proguard-support-v7-appcompat.pro
#-keep public class android.support.v7.widget.** { *; }
#-keep public class android.support.v7.internal.widget.** { *; }
#-keep public class android.support.v7.internal.view.menu.** { *; }
#
#-keep public class * extends android.support.v4.view.ActionProvider {
#    public <init>(android.content.Context);
#}
#
######proguard-support-design.pro
#-dontwarn android.support.design.**
#-keep class android.support.design.** { *; }
#-keep interface android.support.design.** { *; }
#-keep public class android.support.design.R$* { *; }
#
#####gsyvideoplayer
#-keep class tv.danmaku.ijk.** { *; }
#-dontwarn tv.danmaku.ijk.**
#-keep class com.shuyu.gsyvideoplayer.** { *; }
#-dontwarn com.shuyu.gsyvideoplayer.**
#
## banner 的混淆代码
#-keep class com.youth.banner.** {
#    *;
# }
#
# ##galleryfinal
# -keep class cn.finalteam.galleryfinal.widget.*{*;}
# -keep class cn.finalteam.galleryfinal.widget.crop.*{*;}
# -keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}
#######以下是公共的规则#########
# #泛型，解决出现类型转换错误的问题
# -keepattributes Signature
#
# #Serializable序列化
# -keepclassmembers class * implements java.io.Serializable {
#     static final long serialVersionUID;
#     private static final java.io.ObjectStreamField[] serialPersistentFields;
#     private void writeObject(java.io.ObjectOutputStream);
#     private void readObject(java.io.ObjectInputStream);
#     java.lang.Object writeReplace();
#     java.lang.Object readResolve();
# }
#
# #注解
# -keepattributes *Annotation*

#支付宝钱包
-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*

##微信支付
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}

##权限
-keep class pub.devrel.easypermissions.** {
    *;
}

###greendao
-keep class org.greenrobot.greendao.** {
    *;
}

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**

###galleryfinal
-keep class cn.finalteam.galleryfinal.** {
    *;
}
###com.android.support
-keep class com.android.support.** {
    *;
}

###com.baoyz.actionsheet
-keep class com.gk.mvp.view.custom.actionsheet.** {
    *;
}

###com.nostra13.universalimageloader
-keep class com.nostra13.universalimageloader.** {
    *;
}

###com.github.bumptech.glide
-keep class com.github.bumptech.glide.** {
    *;
}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

###com.scwang.smartrefresh.header
   -keep class com.scwang.smartrefresh.header.** {
       *;
}

###com.scwang.smartrefresh.header
-keep class com.scwang.smartrefresh.layout.** {
          *;
}

###retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keep class retrofit2.adapter.rxjava.** { *; }

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class okhttp3.logging.** { *; }

#okio
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-keep class okio.**{*;}

# ButterKnife 7
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

###gsyvideoplayer
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**
-keep class com.shuyu.gsyvideoplayer.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.**

###com.zhy.adapter.recyclerview
-keep class com.zhy.adapter.recyclerview.** { *; }
-keep class com.zhy.baseadapter_recyclerview.** {*;}

# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

###com.ms.square.android.expandabletextview
-keep class com.ms.square.android.expandabletextview.** {
    *;
 }


# fastjson proguard rules
# https://github.com/alibaba/fastjson

-dontwarn com.alibaba.fastjson.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.alibaba.fastjson.**{*;}

## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##
-dontwarn com.squareup.picasso.**
-keep class com.squareup.picasso.**{*;}
-keep class jp.wasabeef.picasso.transformations.**{*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**


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

-keep class com.gk.global.YXXConstants{
    *;
}

-keep class com.gk.http.**{*;}

-keep class com.gk.beans.**{*;}
