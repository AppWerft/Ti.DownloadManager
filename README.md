# Android DownloadManager

This is the Axway Titanium Downloadmanager. 

<img src="dmgr.gif" width=220 />

The download manager is a system service that handles long-running HTTP downloads. Clients may request that a URI be downloaded to a particular destination file. The download manager will conduct the download in the background, taking care of HTTP interactions and retrying downloads after failures or across connectivity changes and system reboots. 

This module extends the module from [m1ga](Ti.Filesystem.getFile(Ti.Filesystem.externalStorageDirectory, filename)) Thanks for this awesome work!

Here a short example:

```javascript
const dmg = require("de.appwerft.downloadmanager");
const URL = "https://avatars0.githubusercontent.com/u/2996237?s=460&v=4";
const localfile = Ti.Filesystem.getFile(Ti.Filesystem.externalStorageDirectory, URL.substring(url.lastIndexOf('/') + 1);

dmg.createRequest(URL);
 
request
	.setAllowedNetworks(Ti.Network.NETWORK_WIFI)
	.setTitle("netter Titel")
	.setdescription("nette Beschreibung")
	.setDestinationUri(localfile);
	// some other build methods
	.enqueue();
```
You can always read the state:

```javascript
dmg.getAllDownloads();
dmg.getPendingDownloads();
dmg.getFailedDownloads();
dmg.getPausedDownloads();
dmg.getRunningDownloads();
dmg.getSuccessfulDownloads();
```
### Events

For proceeding of callbacks you have two ways: app event listeners and hooks of module.

#### onDone 
This event will trigger by download of a single file:

```js
Ti.App.addEventListener('downloadmanager:onDone',function(e) {
	console.log(e.id);
});
```
Or:

```javascript
dmg.onDone = function(e) {
	console.log(e.id);
});

```
#### onComplete
This event will trigger by download of a single file:

```js
Ti.App.addEventListener('downloadmanager:onComplete',function(e) {
	console.log(e);
});
```
Or:

```javascript
dmg.onComplete = function(e) {
	console.log(e);
});

```


## Methods

The interface followes mosly the pattern of native class [DownloadMananger](https://developer.android.com/reference/android/app/DownloadManager). The old interface is still working too.

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
## Methods of module

### createRequest(Url) : Request
This method creates an empty request.

### enqueue(request) : long
Enqueue a new download. The download will start automatically once the download manager is ready to execute it and connectivity is available. It returns a long for id.

### getAllDownloads()
### getPendingDownloads()
### getFailedDownloads()
### getPausedDownloads()
### getRunningDownloads()
### getSuccessfulDownloads()
### getFileForDownloadedFile(id) : File
Returns the Uri of the given downloaded file id, if the file is downloaded successfully. Otherwise, null is returned.

### removeDownloadById(id) : int
Cancel downloads and remove them from the download manager. 
### removeDownloadsByIds([id1, id2 …]) : int
Cancel downloads and remove them from the download manager. Each download will be stopped if it was running, and it will no longer be accessible through the download manager. If there is a downloaded file, partial or complete, it is deleted.

#### Parameter
Array of Ids (numbers)
#### Returns
the number of downloads actually removed


### getMaxBytesOverMobile()
Returns maximum size, in bytes, of downloads that may go over a mobile connection; or null if there's no limit 

### getMimeTypeForDownloadedFile(number id)
Returns the media type of the given downloaded file id, if the file was downloaded successfully. 


## Methods of Request
### setNotificationVisibility()

This method is to control whether a system notification is shown while this download is running or when it is completed.
It can takes any of the following predefined values: 

* VISIBILITY\_HIDDEN
* VISIBILITY\_VISIBLE 
* VISIBILITY\_VISIBLE\_NOTIFY\_COMPLETED
* VISIBILITY\_VISIBLE\_NOTIFY\_ONLY\_COMPLETION

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

### setDestinationFile(String|File)

This method sets the destination path. As parameter you can use a TiFile (see example). 

```javascript
const filename = url.substring(url.lastIndexOf('/') + 1);
const tifile = Ti.Filesystem.getFile(Ti.Filesystem.externalStorageDirectory, filename);
request.setDestinationFile(tifile);
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

### setNotificationVisibility(int)

#####  	VISIBILITY\_VISIBLE
This download is visible but only shows in the notifications while it's in progress. 
#####   VISIBILITY\_HIDDEN
This download doesn't show in the UI or in the notifications. 
#####   VISIBILITY\_VISIBLE\_NOTIFY\_COMPLETED
This download is visible and shows in the notifications while in progress and after completion. 

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