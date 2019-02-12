package com.example.intotheabyss.activitywrapper

import android.app.Activity
import android.widget.TextView

fun displayText(activity: Activity, id: Int, text: String){
    val tv: TextView = activity.findViewById(id)
    tv.text = text
}