package com.prognozrnm.presentation.geo_location

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.prognozrnm.data.db.ProspectorDatabase
import com.prognozrnm.data.db.entities.ObjDaoEntity
import com.prognozrnm.data.db.entities.ObjItemDaoEntity
import com.prognozrnm.data.db.entities.ParamTypeResult
import com.prognozrnm.data.network.PhotoUpload
import com.prognozrnm.data.repository.ResultRepository
import com.prognozrnm.utils.platform.BaseViewModel

class GeoLocationViewModel(
    private val idResult: Int,
    private val repositoryResult: ResultRepository
) : BaseViewModel() {
    var geoMaps = MutableLiveData<MutableList<ObjDaoEntity>>()

    init {
        checkGeoMaps()
    }

    private fun checkGeoMaps() {
        launchIO {
            val list = repositoryResult.getObjItemWithObjDaoDaoEntityList(idResult)
            setList(list)
        }
    }

    private fun setList(list: List<ObjDaoEntity>) {
        launch {
            geoMaps.postValue(list.toMutableList())
        }
    }

    fun addGeoBlock() {
        launchIO {
            ProspectorDatabase.instance.withTransaction {
                repositoryResult.setObjGet(
                    listOf(
                        ObjDaoEntity(
                            0, 0.0, 0.0, System.currentTimeMillis() / 1000L
                        )
                    )
                )
                val idItem = repositoryResult.getLastIdObjGeo()
                repositoryResult.setObjGeoItem(
                    listOf(
                        ObjItemDaoEntity(0, idResult, idItem, ParamTypeResult.Wait, true)
                    )
                )
                val listMap = repositoryResult.getObjItemWithObjDaoDaoEntityList(idResult)
                launch { geoMaps.postValue(listMap.toMutableList()) }
            }
        }
    }

    fun removeGeoBlock(itemMap: ObjDaoEntity): List<ObjDaoEntity>? {
        launchIO {
            repositoryResult.removeItemMap(itemMap)
        }
        return if (geoMaps.value != null && geoMaps.value!!.contains(itemMap)) {
            geoMaps.value?.remove(itemMap)
            geoMaps.value
        } else null
    }

    fun addItemGeoLocation(item: ObjDaoEntity) {
        launchIO {
            ProspectorDatabase.instance.withTransaction {
                repositoryResult.updateObj(item)
                val list = repositoryResult.getObjItemWithObjDaoDaoEntityList(idResult)
                launch { geoMaps.postValue(list.toMutableList()) }
            }
        }
    }

    fun uploadPhoto(item: ObjDaoEntity, data: ByteArray, context: Context) {
        PhotoUpload.sendPhoto(data, idResult, item.id, context)
    }
}