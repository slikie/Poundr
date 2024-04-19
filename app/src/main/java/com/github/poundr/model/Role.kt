package com.github.poundr.model

enum class Role(
    val nameTitleCase: String,
    val isGrindrSubscription: Boolean,
    val requireRestartWhenAdded: Boolean,
    val requireRestartWhenRemoved: Boolean,
) {
    FREE("Free", false, false, false, 12);

    constructor(
        nameTitleCase: String,
        isGrindrSubscription: Boolean,
        requireRestartWhenAdded: Boolean,
        requireRestartWhenRemoved: Boolean, i10: Int
    ) : this(
        nameTitleCase,
        if (i10 and 2 != 0) false else isGrindrSubscription,
        if (i10 and 4 != 0) true else requireRestartWhenAdded,
        if (i10 and 8 != 0) true else requireRestartWhenRemoved
    )
}