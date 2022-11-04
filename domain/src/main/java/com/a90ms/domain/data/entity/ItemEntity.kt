package com.a90ms.domain.data.entity

import com.a90ms.domain.data.dto.ItemDto

data class ItemEntity(
    val title: String,
    val link: String,
    val thumbnail: String,
    val sizeheight: String,
    val sizewidth: String
) {
    fun toDto() = ItemDto(
        title = title,
        link = link,
        thumbnail = thumbnail,
        sizeheight = sizeheight,
        sizewidth = sizewidth
    )
}
