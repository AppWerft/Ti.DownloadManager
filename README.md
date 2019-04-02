# Android DownloadManager support for Titanum 

![preview](dmgr.gif)

Use the Android default DownloadManager to download files in the background. It will open the system download list when you click on the notification.

~~~javascript
var dmg = require("com.miga.downloadmanager");

var filename = url.substring(url.lastIndexOf('/') + 1);
var file = Ti.Filesystem.getFile(Ti.Filesystem.externalStorageDirectory, filename).nativePath;

dmg.setAllowedNetworks(Ti.Network.NETWORK_WIFI)

dmg.startDownload({
    url: url,
    filename: file,
    success: onDone,
    title: "Download",
    description: "Download " + filename
});
dmg.disableNotification();
dmg.setEventName("Name_des_Ready_Events");
dmg.getAllDownloads();
dmg.getPendingDownloads();
dmg.getFailedDownloads();
dmg.getPausedDownloads();
dmg.getRunningDownloads();
dmg.getSuccessfulDownloads();
dmg.getStatusOfDownload(url);


function onDone(){
	alert("done");
}
~~~

## Notification

A notification will shown. In case of supressing this entry must be in manifest:

```xml
<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
``` 

```js
dmg.disableNotification();
dmg.enableNotification();

```