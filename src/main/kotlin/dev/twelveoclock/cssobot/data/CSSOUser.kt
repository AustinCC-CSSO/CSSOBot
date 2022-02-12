package dev.twelveoclock.cssobot.data

import kotlinx.serialization.Serializable

@Serializable
data class CSSOUser(
    val id: Long,
    val timeInMeetingInEpoch: Long = 0,
    val userIDs: Map<UserID, String> = mutableMapOf(),
    val points: Map<Point, Long> = mutableMapOf(),
)