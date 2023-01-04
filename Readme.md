# About

This demo is about integrating Zoom's VideoSDK into godot .
Currently only android is supported.

# Getting Started :
First you need to compile the Plugin, put it in the godot projet and then export the godot project.

Then head to "Using the jwt generation backend" and start the testing backend for the app to work.

# Note
Because the LineEdit in godot doesn't work properly on mobile yet, before exporting try setting the default values you want (like the ip address of your test backend) in the placeholder export variables of the Scenes containing the lineEdit nodes.

# Android - Plugin Compilation guide.

the plugin is in the AndroidStudioProject folder. the easiest way to compile this project is to open it with AndroidStudio and press 'Build > Make Project'. however first you will need to download Zoom's VideoSDK from your Zoom developer account and then place the 'mobilertc' folder in 'AndroidStudioProject'.

links :
https://marketplace.zoom.us/docs/sdk/video/android/getting-started/integrate/

# Android - Project Compilation guide.

1. Install Android Build Template (skip if you have already done it)
First you need to open the project in the ``` godot_project ``` folder with your godot engine. then press Project > Install Android Build Template. this will copy some .aar files in ``` godot_project/android/build/libs/ ```. if you odn't want to use these go ahead and replace them with whatever you want to use for your build.

2. Edit build.gradle to resolve a build conflict.
In the 'godot_project/android/build/build.gradle' file find where it says ``` packagingOptions { ```, then edit it so it includes the options shown below :

```

packagingOptions {
        pickFirst 'lib/x86/libc++_shared.so'
        pickFirst 'lib/x86_64/libc++_shared.so'
        pickFirst 'lib/armeabi-v7a/libc++_shared.so'
        pickFirst 'lib/arm64-v8a/libc++_shared.so'

```

3. Add .aar plugin Files :
You need to copy the output of your plugin build and the mobilertc depndency to ```godot_project/android/plugins```. the .aar file for the output of your build will probably be located at ```/AndroidStudioProject/GoomSDKVideoPlugin/build/outputs/aar/GoomSDKVideoPlugin-debug.aar```. the mobilertc depndency will probably be located at ```AndroidStudioProject/mobilertc/mobilertc.aar```. finally if the names of your files are different edit the names or ```GoomSDK.gdap``` so that they match.

4. Now you can go ahead and export your project for Android.




