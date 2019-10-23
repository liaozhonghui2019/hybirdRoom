/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.egrand.web.qrcode.camera

import android.hardware.Camera
import android.os.Handler
import android.util.Log

internal class AutoFocusCallback : Camera.AutoFocusCallback {

    private var autoFocusHandler: Handler? = null
    private var autoFocusMessage: Int = 0

    fun setHandler(autoFocusHandler: Handler?, autoFocusMessage: Int) {
        this.autoFocusHandler = autoFocusHandler
        this.autoFocusMessage = autoFocusMessage
    }

    override fun onAutoFocus(success: Boolean, camera: Camera) {
        if (autoFocusHandler != null) {
            val message = autoFocusHandler!!.obtainMessage(autoFocusMessage, success)
            // Simulate continuous autofocus by sending a focus request every
            // AUTOFOCUS_INTERVAL_MS milliseconds.
            //Log.d(TAG, "Got auto-focus callback; requesting another");
            autoFocusHandler!!.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS)
            autoFocusHandler = null
        } else {
            Log.d(TAG, "Got auto-focus callback, but no handler for it")
        }
    }

    companion object {

        private val TAG = AutoFocusCallback::class.java.simpleName

        private val AUTOFOCUS_INTERVAL_MS = 1500L
    }

}
