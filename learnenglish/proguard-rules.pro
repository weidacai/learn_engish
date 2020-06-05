##通过指定数量的优化执行
#-optimizationpasses 5
##混淆时不会产生形形色色的类名
##-dontusemixedcaseclassnames
##指定不去忽略非公共的库类
#-dontskipnonpubliclibraryclasses
##-dontpreverify
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## modify 修改合并
#-keep public class * extends android.view.View {
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#    public void set*(...);
#}
#
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
##保护类中的所有方法名
##-keepclassmembers class * {
##   public <methods>;
##}
#
#-keepattributes *Annotation*
#
#-libraryjars libs/zlsdk.aar
#-dontwarn android.support.annotation.**
#-keep class android.support.annotation.** { *; }
#-keep public class * extends android.support.annotation.**
#-keep public class * extends android.app.Fragment
#
##butterknife
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }