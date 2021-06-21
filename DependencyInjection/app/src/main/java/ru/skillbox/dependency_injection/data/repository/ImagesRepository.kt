package ru.skillbox.dependency_injection.data.repository

import ru.skillbox.dependency_injection.data.Image

interface ImagesRepository {
    suspend fun getImages(): List<Image>
    suspend fun saveImage(name: String, url: String)
    suspend fun deleteImage(id: Long)
}