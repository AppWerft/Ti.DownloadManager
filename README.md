# Android DownloadManager support for Titanum 

<img src="dmgr.gif" width=220 />

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

# New interface

New new interface followes the pattern of native class `DownloadMananger`.

Every download creates a new download object and returns an id for subsequent operations:

```js
const req = dmg.createRequest('https://avatars0.githubusercontent.com/u/2996237?s=460&v=4');
const id = dmg.enqueue(req);
```

You can remove by:

```js
dmg.remove(id);
// or
dmg.remove([id1,id2,id3 … ]);
```

A request without parameters works with defaults properties. You can modify by some methods:

```js
const request = dmg.createRequest('https://avatars0.githubusercontent.com/u/2996237?s=460&v=4');
dmg.enqueue(request
	.setNotificationVisibility(dmg.VISIBILITY_VISIBLE )
	.setTitle("mytitle")
	.setDescription("longer Text"););
```
 

# Methods of Request
## setNotificationVisibility()

This method is to control whether a system notification is shown while this download is running or when it is completed.
It can takes any of the following predefined values: 

* VISIBILITY\_HIDDEN
* VISIBILITY\_VISIBLE 
* VISIBILITY\_VISIBLE\_NOTIFY\_COMPLETED

If set to VISIBILITY\_HIDDEN, this requires the permission android.permission.DOWNLOAD\_WITHOUT\_NOTIFICATION.

```xml
<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
``` 

```js
dmg.setNotificationVisibility(dmg.VISIBILITY_VISIBLE);
// or
dmg.notificationvisibility = dmg.VISIBILITY_VISIBLE);

```