package com.prognozrnm.data.network

data class FormData(
    var name: String,
    var data: ByteArray,
    var fileName: String,
    var type: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormData

        if (name != other.name) return false
        if (!data.contentEquals(other.data)) return false
        if (fileName != other.fileName) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}