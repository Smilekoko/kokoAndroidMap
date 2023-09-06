@file:Suppress("unused")

package com.shiki.koko.base.file

import android.content.Context
import android.os.Environment

inline val Context.cacheDirPath: String
    get() = if (isExternalStorageWritable || !isExternalStorageRemovable)
        externalCacheDirPath.orEmpty()
    else
        internalCacheDirPath

inline val Context.externalCacheDirPath: String?
    get() = externalCacheDir?.absolutePath


inline val Context.internalCacheDirPath: String
    get() = cacheDir.absolutePath

//sd卡是否可写
inline val isExternalStorageWritable: Boolean
    get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

//sd卡是否可读
inline val isExternalStorageReadable: Boolean
    get() = Environment.getExternalStorageState() in setOf(
        Environment.MEDIA_MOUNTED,
        Environment.MEDIA_MOUNTED_READ_ONLY
    )

inline val isExternalStorageRemovable: Boolean
    get() = Environment.isExternalStorageRemovable()