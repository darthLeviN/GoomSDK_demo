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



# Using the jwt generation backend

the JWT generation backend automatically generates jwt signatures based on your ZoomSDK VideoSDK app-key and secret. This backend does nothing but that. the video streaming is done by zoom's servers not this backend.

1. Cd into ```videosdk-sample-signature-node.js```
2. Create file named ``.env `` with the variables shown below :
```
ZOOM_VIDEO_SDK_KEY=your_sdk_key
ZOOM_VIDEO_SDK_SECRET=your_sdk_secret
```
3. Run ```npm install```

4. Start the server by running ```npm run start```

5. Access the server at http://your_ip_address:4000

please beware that this backend is for testing only so run it in your local network for testing your android devices.

# How the Backend works :

the Godot app creates a json request to the backend. the backend responts with a json file containing the jwt signature and the received paylot for debugging purposes. Then the godot app hands the join info along with the jwt signature to the plugin and the plugin renders on top of godot.


# Android Rendering notes :

I would've preferred if there was support to send raw video data to godot and render them with godot. However such approach is not well supported with godot at the moment so we're using Zoom's built in renderer which uses Android Views.
