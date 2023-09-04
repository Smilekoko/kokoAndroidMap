package com.shiki.koko.base

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Activity.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun Context.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(content: String) {
    Toast.makeText(requireContext(), content, Toast.LENGTH_SHORT).show()
}