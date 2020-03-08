package com.szczecin.voicerecoder.app.utils

sealed class Recording {
    object Permission : Recording()
    object PermissionAccept : Recording()
    object StartRecording : Recording()
    object StopRecording : Recording()
}