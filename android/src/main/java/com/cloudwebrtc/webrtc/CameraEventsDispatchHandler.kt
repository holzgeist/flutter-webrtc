/*
 * Copyright 2023-2024 LiveKit, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudwebrtc.webrtc

import org.webrtc.CameraVideoCapturer.CameraEventsHandler
import android.util.Log

/**
 * Dispatches CameraEventsHandler callbacks to registered handlers.
 */
class CameraEventsDispatchHandler : CameraEventsHandler {
    private val handlers = mutableSetOf<CameraEventsHandler>()
    private val TAG = "CameraEventsDispatchHandler"

    @Synchronized
    fun registerHandler(handler: CameraEventsHandler) {
        Log.i(TAG, "add handler $handler")
        handlers.add(handler)
    }

    @Synchronized
    fun unregisterHandler(handler: CameraEventsHandler) {
        Log.i(TAG, "remove handler $handler")
        handlers.remove(handler)
    }

    override fun onCameraError(errorDescription: String) {
        val handlersCopy = handlers.toMutableSet()
        Log.i(TAG, "onCameraError $errorDescription ${handlersCopy.size}")
        for (handler in handlersCopy) {
            Log.i(TAG, "onCameraError $errorDescription $handler")
            handler.onCameraError(errorDescription)
        }
    }

    override fun onCameraDisconnected() {
        val handlersCopy = handlers.toMutableSet()
        Log.i(TAG, "onCameraDisconnected ${handlersCopy.size}")
        for (handler in handlersCopy) {
            Log.i(TAG, "onCameraDisconnected $handler")
            handler.onCameraDisconnected()
        }
    }

    override fun onCameraFreezed(errorDescription: String) {
        val handlersCopy = handlers.toMutableSet()
        Log.i(TAG, "onCameraFreezed ${handlersCopy.size}")
        for (handler in handlersCopy) {
            Log.i(TAG, "onCameraFreezed $handler")
            handler.onCameraFreezed(errorDescription)
        }
    }

    override fun onCameraOpening(cameraName: String) {
        val handlersCopy = handlers.toMutableSet()
        Log.i(TAG, "onCameraOpening $cameraName ${handlersCopy.size}")
        for (handler in handlersCopy) {
            Log.i(TAG, "onCameraOpening $cameraName $handler")
            handler.onCameraOpening(cameraName)
        }
    }

    override fun onFirstFrameAvailable() {
        val handlersCopy = handlers.toMutableSet()
        Log.i(TAG, "onFirstFrameAvailable ${handlersCopy.size}")
        for (handler in handlersCopy) {
            Log.i(TAG, "onFirstFrameAvailable $handler")
            handler.onFirstFrameAvailable()
        }
    }

    override fun onCameraClosed() {
        val handlersCopy = handlers.toMutableSet()
        Log.i(TAG, "onCameraClosed ${handlersCopy.size}")
        for (handler in handlersCopy) {
            Log.i(TAG, "onCameraClosed $handler")
            handler.onCameraClosed()
        }
    }
}

