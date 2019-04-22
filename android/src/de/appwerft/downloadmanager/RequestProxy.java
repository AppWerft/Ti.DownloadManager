/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2017 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package de.appwerft.downloadmanager;

import ti.modules.titanium.filesystem.FileProxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.io.TiFile;
import org.appcelerator.titanium.io.TiFileFactory;

import android.app.DownloadManager;
import android.net.Uri;

// This proxy can be created by calling Dell.createExample({message: "hello world"})
@Kroll.proxy(creatableInModule = TiDownloadmanagerModule.class)
public class RequestProxy extends KrollProxy {

	// Standard Debugging variables
	private static final String LCAT = "ExampleProxy";
	private static final boolean DBG = TiConfig.LOGD;
	public DownloadManager.Request request;

	// Constructor
	public RequestProxy() {
		super();
	}

	public void handleCreationArgs(@Kroll.argument(optional = true) KrollModule createdInModule, Object[] args) {
		if (args != null && args[0] instanceof String) {
			String uri = (String) args[0];
			try {
				new URI(uri);
				request = new DownloadManager.Request(Uri.parse(uri));
			} catch (URISyntaxException e) {
				Log.e(LCAT, e.getMessage());
			}
			super.handleCreationArgs(createdInModule, args);
		}
	}

	@Kroll.method
	public Long enqueue() {
		return TiDownloadmanagerModule.dMgr.enqueue(request);
	}

	@Kroll.method
	@Kroll.setProperty
	public RequestProxy setAllowedOverRoaming(boolean allow) {
		request.setAllowedOverRoaming(allow);
		return this;
	}

	@Kroll.method
	@Kroll.setProperty
	public RequestProxy setRequiresCharging(boolean allow) {
		request.setRequiresCharging(allow);
		return this;
	}

	@Kroll.method
	@Kroll.setProperty
	public RequestProxy setRequiresDeviceIdle(boolean allow) {

		request.setRequiresDeviceIdle(allow);
		return this;
	}

	@Kroll.method
	public RequestProxy addRequestHeader(String k, String v) {
		request.addRequestHeader(k, v);
		return this;
	}

	@Kroll.method
	public RequestProxy setAllowedNetworkTypes(int flags) {
		request.setAllowedNetworkTypes(flags);
		return this;
	}

	@Kroll.method
	@Kroll.setProperty
	public RequestProxy setDescription(String descr) {
		request.setDescription(descr);
		return this;
	}

	@Kroll.method
	public RequestProxy setDestinationUri(Object readPath) {
		/* this is the example pattern for importing files in Titanium: 
		 * reads all kinds of files
		 * */
		try {
			TiBaseFile inputFile = null;
			if (readPath instanceof TiFile) {  
				inputFile = TiFileFactory.createTitaniumFile(((TiFile) readPath).getFile().getAbsolutePath(), false);
			} else {
				if (readPath instanceof FileProxy) {
					inputFile = ((FileProxy) readPath).getBaseFile();
				} else {
					if (readPath instanceof TiBaseFile) {
						inputFile = (TiBaseFile) readPath;
					} else {
						// Assume path provided
						inputFile = TiFileFactory.createTitaniumFile(readPath.toString(), false);
					}
				}
			}
			request.setDestinationUri(Uri.fromFile(inputFile.getNativeFile()));
			return this;

		} catch (Exception e) {
			HashMap<String, Object> errEvent = new HashMap<String, Object>();
			errEvent.put(TiC.PROPERTY_SUCCESS, false);
			errEvent.put("message", e.getMessage());
			return this;
		}

	}

	@Kroll.method
	@Kroll.setProperty
	public RequestProxy setMimeType(String m) {
		request.setMimeType(m);
		return this;
	}

	@Kroll.method
	public RequestProxy setNotificationVisibility(int v) {
		request.setNotificationVisibility(v);
		return this;
	}

	@Kroll.method
	public RequestProxy setTitle(String t) {
		request.setTitle(t);
		return this;
	}

	@Kroll.method
	public RequestProxy allowScanningByMediaScanner() {
		request.allowScanningByMediaScanner();
		return this;
	}

}