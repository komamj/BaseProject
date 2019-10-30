package com.oppo.cac.base.data.entities

import com.google.gson.annotations.SerializedName
import com.oppo.cac.base.BuildConfig

data class News(
    val title: String,
    val date: String
) {
    @SerializedName("thumbnail_pic_s")
    private val thumbnailOne: String? = null
    @SerializedName("thumbnail_pic_s02")
    private val thumbnailTwo: String? = null
    @SerializedName("thumbnail_pic_s03")
    private val thumbnailThree: String? = null

    val thumbnailUrls: MutableList<String>
        get() {
            val result = mutableListOf<String>()
            convertThumbnailToList(result, thumbnailOne, thumbnailTwo, thumbnailThree)
            return result
        }

    private fun convertThumbnailToList(
        result: MutableList<String>,
        thumbnailOne: String?,
        thumbnailTwo: String?,
        thumbnailThree: String?
    ) {
        if (result.isNullOrEmpty()) {
            thumbnailOne?.run {
                result.add(appendUrl(thumbnailOne))
            }
            thumbnailTwo?.run {
                result.add(appendUrl(thumbnailTwo))
            }
            thumbnailThree?.run {
                result.add(appendUrl(thumbnailThree))
            }
        }
    }

    private fun appendUrl(thumbnailOne: String?) = "${BuildConfig.BASE_URL}$thumbnailOne"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (title != other.title) return false
        if (date != other.date) return false
        if (thumbnailOne != other.thumbnailOne) return false
        if (thumbnailTwo != other.thumbnailTwo) return false
        if (thumbnailThree != other.thumbnailThree) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + (thumbnailOne?.hashCode() ?: 0)
        result = 31 * result + (thumbnailTwo?.hashCode() ?: 0)
        result = 31 * result + (thumbnailThree?.hashCode() ?: 0)
        return result
    }


}
