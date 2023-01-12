package com.frogchair.android.feature.fight

enum class CurrentLine {
    L1,
    L2,
    L3;

    fun nextLine() = when (this) {
        L1 -> L2
        L2 -> L3
        L3 -> L3
    }
}