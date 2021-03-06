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

-keepnames class com.google.android.gms.maps.*
-keepnames class android.location.*
-keepnames class com.ontbee.legacyforks.cn.pedant.SweetAlert.*
-keep class com.google.android.gms.** { *; }
-keep class com.ontbee.legacyforks.cn.pedant.SweetAlert.** { *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.location
-keep public class * extends com.ontbee.legacyforks.cn.pedant.SweetAlert
-keep class com.ontbee.legacyforks.cn.pedant.SweetAlert.Rotate3dAnimation {
  public <init>(...);
}