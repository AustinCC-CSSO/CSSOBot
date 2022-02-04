package dev.twelveoclock.cssobot.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val timeInMeetingInEpoch: Long,
    val points: Map<Point, Long>,
)