package com.skillbox.datetimepermission.Models

import org.threeten.bp.Instant

data class Message(
    val id: Long,
    val text: String,
    var dateTime: org.threeten.bp.Instant
)