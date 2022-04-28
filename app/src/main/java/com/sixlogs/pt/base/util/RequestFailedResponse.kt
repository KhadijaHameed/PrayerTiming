package com.sixlogs.pt.base.util

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.sixlogs.pt.R
import com.sixlogs.pt.data.remoteRepo.Resource

fun Fragment.requestFailed(resouse: Resource.Failure, view: View) {
    if (resouse.isNetworkError) {
        view.showSnackBar(
            resources.getString(R.string.internnetConnection)
        )
    } else {
        alertDialog(requireActivity(), resouse.errorBody.toString(), "")
    }
}

fun Activity.requestFailed(resouse: Resource.Failure, view: View) {
    if (resouse.isNetworkError) {
        view.showSnackBar(
            resources.getString(R.string.internnetConnection)
        )
    } else {// 403 error shows when twilio's subscription ends
        if (resouse.errorCode==403)
            alertDialog(this, "Unauthorized - you are not authenticated to perform this request", "")
        else
        alertDialog(this, resouse.errorBody.toString(), "")
    }
}