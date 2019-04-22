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

New new interface followes mosly the pattern of native class [DownloadMananger](https://developer.android.com/reference/android/app/DownloadManager). The old interface is still working too.

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

### Events
The back communication from downloader to the JS-layer works by Ti.App event. 
There are two events:

#### DownloadReady
Will fired if a single download is ready. You will get the id.


#### DownloadComplete
Will fired if all downloads are completed.

A request without parameters works with defaults properties. You can modify by some methods:

```js
const request = dmg.createRequest('https://avatars0.githubusercontent.com/u/2996237?s=460&v=4');
dmg.enqueue(request
	.setNotificationVisibility(dmg.VISIBILITY_VISIBLE )
	.setTitle("mytitle")
	.setDescription("longer Text"););
```
 

## Methods of Request
### setNotificationVisibility()

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
const request = dmg.createRequest("https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwj_qcCMpuPhAhXKjqQKHSStAyQQjRx6BAgBEAU&url=https%3A%2F%2Fwww.reddit.com%2Fr%2Faww%2Fcomments%2F44kods%2Ffluffy_ginger_sweet_cat_baby%2F&psig=AOvVaw2eJv4WrHs6itSdoJJeMZyN&ust=1556008655862323");
request.setNotificationVisibility(dmg.VISIBILITY_VISIBLE);
// or
dmg.notificationvisibility = dmg.VISIBILITY_VISIBLE);

```
### addRequestHeader(key, value)

Add an HTTP header to be included with the download request. The header will be added to the end of the list.

```javascript
request.addRequestHeader("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
```

### setAllowedNetworkTypes()
Restrict the types of networks over which this download may proceed. By default, all network types are allowed. Consider using `setAllowedOverMetered(boolean)` instead, since it's more flexible.

As of Build.VERSION\_CODES.N, setting only the NETWORK_WIFI flag here is equivalent to calling setAllowedOverMetered(boolean) with false.

```javascript
request.setAllowedNetworkTypes(Ti.Network.NETWORK_MOBILE | Ti.Network.NETWORK_WIFI);
```

### setAllowedOverMetered(boolean)
Set whether this download may proceed over a metered network connection. By default, metered networks are allowed.


### setAllowedOverMetered(boolean)
Set whether this download may proceed over a metered network connection. By default, metered networks are allowed.


### setAllowedOverRoaming(boolean) 
Set whether this download may proceed over a roaming connection. By default, roaming is allowed.

### setRequiresCharging(boolean)

Specify that to run this download, the device needs to be plugged in. This defaults to false.


### setRequiresDeviceIdle(boolean)

Specify that to run, the download needs the device to be in idle mode. This defaults to false.

Idle mode is a loose definition provided by the system, which means that the device is not in use, and has not been in use for some time.


### setDescription()
### setTitle()

```javascript
request.Description("Mein lustige Runterlade");
request.Description("Mein lustiger Titel");

```
### setMimetype()
Sets the mime type in internal database.

### allowScanningByMediaScanner

If the file to be downloaded is to be scanned by MediaScanner, this method should be called before `enqueue` is called.


### enqueue()

The standard way is to use `dmg.enqueue(request);`; you can call `request.enqueue()` too. It returns the `id`.