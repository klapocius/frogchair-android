package com.frogchair.android.common.utils

import java.util.*

object TimeUtils {

    fun formatHour(milliSec: Long) = String.format("%tT", milliSec - TimeZone.getDefault().rawOffset)

}