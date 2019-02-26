package com.hb.silverlining.fcm

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.hb.silverlining.utils.LOGApp
import com.hb.silverlining.utils.SharedPreferenceUtil

/**
 * @author RJ
 */

class InstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        FirebaseInstanceId.getInstance().token?.let {
            LOGApp.e("onTokenRefresh:: ", it)
            SharedPreferenceUtil.saveDeviceToken(this, it)
        }
    }
}
