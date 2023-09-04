package com.shiki.koko.amap

import android.content.Context
import com.amap.api.maps.MapsInitializer


/**
 * 更新隐私合规状态,需要在初始化地图之前完成
 * @param  context: 上下文
 * @param  isContains: 隐私权政策是否包含高德开平隐私权政策  true是包含
 * @param  isShow: 隐私权政策是否弹窗展示告知用户 true是展示
 * @since  8.1.0
 */
fun updatePrivacyShow(context: Context?, isContains: Boolean = true, isShow: Boolean = true) {
    MapsInitializer.updatePrivacyShow(context, isContains, isShow)
}

/**
 * 更新同意隐私状态,需要在初始化地图之前完成
 * @param context: 上下文
 * @param isAgree: 隐私权政策是否取得用户同意  true是用户同意
 * @since 8.1.0
 */
fun updatePrivacyAgree(context: Context?, isAgree: Boolean = true) {
    MapsInitializer.updatePrivacyAgree(context, isAgree)
}