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

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-dontwarn org.xmlpull.v1.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

#MPCharts
-keep class com.github.mikephil.charting.** { *; }

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Matisse
#-dontwarn com.bumptech.glide.**
-dontwarn com.squareup.picasso.**

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\NAZAR\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-ignorewarnings
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepattributes *

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
        public static int d(...);
        public static int w(...);
        public static int v(...);
        public static int i(...);
}

-adaptclassstrings
-adaptresourcefilecontents **.xml

-keep public class * extends MultiDexApplication {
    public void onCreate();
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep public class AnnotationDatabaseImpl
-keep public class android.content. {
<fields>;
<methods>;
}

-keepclassmembers class * implements android.os.Parcelable { static android.os.Parcelable$Creator *; }
-keepclassmembers class **.R$* { public static <fields>; }
-keepclasseswithmembernames class * { native <methods>; }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * extends android.app.Activity {
	public void *(android.view.View);
}

-keep class android.support.customtabs.** { *; }

#Retrofit 2.X
-keep class retrofit2.** { *; }
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepattributes *Annotation*

## GreenRobot EventBus specific rules ##
-keepclassmembers class ** {
    public void onEvent*(***);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    public <init>(java.lang.Throwable);
}

# Don't warn for missing support classes
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn javax.**
-dontwarn io.realm.**

#Android support v7
-keep class android.support.v7.** { *; }
-keep class android.support.v7.app.** { *; }
-keep class android.support.v7.internal.** { *; }
-keep class android.support.v7.widget.** { *; }
-keep interface android.support.v7.** { *; }
-keep interface android.support.v7.app.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep interface android.support.v7.widget.** { *; }


#Android support v4
-keep class android.support.v4.** { *; }
-keep class android.support.v4.app.** { *; }
-keep class android.support.v4.app.FragmentManager { *; }
-keep class android.support.v4.app.FragmentPagerAdapter { *; }
-keep class android.support.v4.view.ViewPager { *; }
-keep interface android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep interface android.support.v4.app.FragmentManager { *; }
-keep interface android.support.v4.app.FragmentPagerAdapter { *; }
-keep interface android.support.v4.view.ViewPager { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep class sun.misc.Unsafe.** { *; }

# Hide warnings about references to newer platforms in the library
-dontwarn android.support.v7.**
-dontwarn android.support.v4.**

#Android support design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#EventBus 3.0.x
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#picasso
-keep class com.squareup.picasso.Utils { *; }

-keep class javamail.** {*;}
-keep class javax.mail.** {*;}
-keep class javax.activation.** {*;}

-keep class com.sun.mail.dsn.** {*;}
-keep class com.sun.mail.handlers.** {*;}
-keep class com.sun.mail.smtp.** {*;}
-keep class com.sun.mail.util.** {*;}
-keep class mailcap.** {*;}
-keep class mimetypes.** {*;}
-keep class myjava.awt.datatransfer.** {*;}
-keep class org.apache.harmony.awt.** {*;}
-keep class org.apache.harmony.misc.** {*;}

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
  public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.greenrobot.greendao.database.**
-dontwarn rx.**

#GSON
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

#pro guard settings for android annotation
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#RxJava 1
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
-dontnote rx.internal.util.PlatformDependent

#Reactive Network
-dontwarn com.github.pwittchen.reactivenetwork.library.ReactiveNetwork
-dontwarn io.reactivex.functions.Function
-dontwarn rx.internal.util.**
-dontwarn sun.misc.Unsafe

#Okio
-dontwarn okio.**

#Eventbus 3
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

#GSON
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

#Android support v7
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

#Android support design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#Jsoup
-keeppackagenames org.jsoup.nodes

#Sqlite
-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }

#Mqtt
-keep class org.eclipse.paho.clent.mqttv3.** {*;}
-keep class org.eclipse.paho.client.mqttv3.*$* { *; }
-keep class org.eclipse.paho.client.mqttv3.logging.JSR47Logger {
    *;
}

#Play services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames class * implements android.os.Parcelable {
  public static final ** CREATOR;
}
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}
-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}
-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}
-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep public @com.google.android.gms.common.util.DynamiteApi class * {
  public <fields>;
  public <methods>;
}

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**
-dontwarn android.security.NetworkSecurityPolicy

#Appsflyer
-dontwarn com.appsflyer.AFKeystoreWrapper
-dontwarn com.appsflyer.**
-keep public class com.google.firebase.iid.FirebaseInstanceId {
    public *;
}

#USB Mass Storage
-keep class com.github.mjdev.libaums.** { *; }

#openfilechooser
-keepclassmembers class * extends android.webkit.WebChromeClient {
     public void openFileChooser(...);
}

-dontwarn com.amazon.**

-keep class com.onesignal.JobIntentService$* {*;}
-keep class com.onesignal.SyncJobService* {*;}

-keep public class net.lingala.** { *; }

-dontwarn com.amazon.**

#Wasabi
-keep class com.intertrust.wasabi.** { *; }

# ReactiveNetwork
-dontwarn com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
-dontwarn io.reactivex.functions.Function
-dontwarn rx.internal.util.**
-dontwarn sun.misc.Unsafe

# ReactivePermission
-dontwarn com.ruangguru.core.utils.rx.permission.RxPermissions
-dontwarn com.ruangguru.core.utils.rx.permission.RxPermissionsFragment

## RX2
-dontnote io.reactivex.**

# COMMON KOTLIN
-dontnote kotlin.**
-dontwarn kotlin.**
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontnote kotlinx.**
-keep class kotlinx.coroutines.**

# Sdk ZOPIM
-keep public interface com.zendesk.** { *; }
-keep public class com.zendesk.** { *; }
-dontwarn java.awt.**

# taptargetview
-keep class android.support.v7.widget.Toolbar { *** mMenuView; }
-keep class android.support.v7.widget.ActionMenuView { *** mPresenter; }
-keep class android.support.v7.widget.ActionMenuPresenter { *** mOverflowButton; }