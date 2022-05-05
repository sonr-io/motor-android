package io.sonr.motor;

import android.os.*;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import sonr.*;

public class SonrMotorPlugin implements FlutterPlugin, MethodCallHandler {
  // ^ Initialize Sonr Plugin Vars ^
  private MethodChannel methodChannel;
  private MethodChannel eventChannel;

  // ^ Register Method channel to dart library ^
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    // Initialize Channels
    methodChannel =
        new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "io.sonr.motor/action");

    eventChannel =
        new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "io.sonr.motor/event");

    // Register for Methods
    methodChannel.setMethodCallHandler(this);
  }

  // ^ Handle dart library calls ^
  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    // Switch by Call Method
    switch (call.method) {
      // Starts the Node
      case "start":
        final byte[] initArgs = call.arguments();
        Sonr.start(initArgs);
        result.success(null);
        break;

      // Resumes the Node
      case "resume":
        Sonr.resume();
        result.success(null);
        break;

      // Pauses the Node
      case "pause":
        Sonr.pause();
        result.success(null);
        break;

      // Stops the Node
      case "stop":
        Sonr.stop();
        result.success(null);
        break;

      // ! Method not found
      default:
        result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
  }
}
