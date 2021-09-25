package com.prognozrnm.data.repository

import androidx.room.withTransaction
import com.google.gson.Gson
import com.prognozrnm.data.db.ProspectorDatabase
import com.prognozrnm.data.db.dao.ResultDao
import com.prognozrnm.data.db.entities.*
import com.prognozrnm.data.db.entities.ResultList
import com.prognozrnm.data.network.ApiService
import com.prognozrnm.utils.platform.BaseRepository
import com.prognozrnm.utils.platform.ErrorHandler
import com.prognozrnm.utils.platform.State
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class ResultRepositoryImp(
    errorHandler: ErrorHandler,
    private val resultDao: ResultDao,
    private val api: ApiService,
    private val client: OkHttpClient
) : BaseRepository(errorHandler = errorHandler), ResultRepository {
    override suspend fun setObjGet(result: List<ObjDaoEntity>) {
        resultDao.setObjGet(result)
    }

    override suspend fun setObjGeoItem(result: List<ObjItemDaoEntity>) {
        resultDao.setObjGeoItem(result)
    }

    override suspend fun setResult(result: List<ResultObjDaoEntity>) {
        resultDao.setResult(result)
    }

    override suspend fun setResultItem(result: List<ResultItemDaoEntity>) {
        resultDao.setResultItem(result)
    }

    override suspend fun getLastIdObjGeo(): Int = resultDao.getLastIdObjGeo()

    override suspend fun getLastIdObjGeoItem(): Int = resultDao.getLastIdObjGeoItem()

    override suspend fun getLastIdResult(): Int = resultDao.getLastIdResult()

    override suspend fun getLastIdResultItem(): Int = resultDao.getLastIdResultItem()

    override suspend fun updateResult(item: ResultObjDaoEntity) {
        resultDao.updateResult(item)
    }

    override suspend fun updateObj(item: ObjDaoEntity) {
        resultDao.updateObj(item)
    }

    override suspend fun removeItemMap(itemMap: ObjDaoEntity) {
        resultDao.removeItemMap(itemMap)
    }

    override suspend fun getResultItemWithResultObj(
        idResult: Int,
        idCheckList: Int
    ): ResultObjDaoEntity? {
        //TODO обработать ситуацию когда не было найдено чек-листов по условию
        //item.resObjList.first().idCheckList == idCheckList
        var source = resultDao.getResultItemWithResultObj(idResult)
        return if (source.any()) {
            try {
                val item = source.last().resItem
                resultDao.updateResultItem(
                    ResultItemDaoEntity(item.id,item.resultId,item.id_results,ParamTypeResult.Wait,true)
                )
                source.last { item -> item.resObjList.first().idCheckList == idCheckList }.resObjList.last()
            } catch (e: NoSuchElementException) {
                null
            }
        } else null
    }

    override suspend fun getObjItemWithObjDaoDaoEntity(
        idResult: Int,
        idMapObj: Int
    ): ObjDaoEntity? {
        var source = resultDao.getObjItemWithObj(idResult)
        return if (source.any()) {
            try {
                source.last { item -> item.resObjList.first().id == idMapObj }.resObjList.last()
            } catch (e: NoSuchElementException) {
                null
            }
        } else null
    }

    override suspend fun getObjItemWithObjDaoDaoEntityList(idResult: Int): List<ObjDaoEntity> {
        var source = resultDao.getObjItemWithObj(idResult)
        return if (source.any()) {
            val item = source.last().resItem
            resultDao.updateObjItem(
                ObjItemDaoEntity(item.id,item.resultId,item.id_obj,ParamTypeResult.Wait,true)
            )
            var sourceList = emptyList<ObjDaoEntity>().toMutableList()
            source.forEach {
                sourceList.add(it.resObjList.first())
            }
            sourceList
        } else emptyList()
    }

    override suspend fun getResultItemWithResultObjAll(): List<ResultItemWithResultObjDaoEntity> =
        resultDao.getResultItemWithResultObj()

    override suspend fun getObjItemWithObjAll(): List<ObjItemWithObjDaoDaoEntity> =
        resultDao.getObjItemWithObjAll()

    override suspend fun getObjItemAll(): List<ObjItemDaoEntity> = resultDao.getObjItemAll()

    override suspend fun postResult(
        obj: ResultList,
        onSuccess: (String) -> Unit,
        onState: (State) -> Unit
    ) {
        //TODO переделать под retorfit
        execute(onSuccess = onSuccess, onState = onState) {
            val jsonObjectString = Gson().toJson(obj)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val request: Request = okhttp3.Request.Builder()
                .url("http://demo.girngm.ru/Prospector.API/api/CheckList/SynchronizeData")
                .post(requestBody)
                .build()
            var result = "OK"
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful)
                    onState(State.Error(throw IOException("Unexpected code $response")))
                result = "OK"
            }
            result
        }
//        execute(onSuccess = onSuccess, onState = onState) {
//            val jsonObjectString = Gson().toJson(obj)
//            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            val response = api.postResult(requestBody)
//            response
//        }
    }

    override suspend fun postPhotoGeo(
        obj: ObjDaoEntity,
        data:ByteArray,
        onSuccess: (String) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            val formBody = FormBody.Builder()
                .add("search", "Jurassic Park")
                .build()
            val request: Request = okhttp3.Request.Builder()
                .url("http://demo.girngm.ru/Prospector.API/api/CheckList/UploadPhotos?")
                .post(formBody)
                .build()
            var result = "OK"
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful)
                    onState(State.Error(throw IOException("Unexpected code $response")))
                result = "OK"
            }
            result
        }
    }

    override suspend fun getResultAll(): List<ResultItemDaoEntity> = resultDao.getResultAll()

    override suspend fun getResultItemAll(): List<ResultObjDaoEntity> = resultDao.getResultItemAll()

    override suspend fun updateResultItem(item: ResultItemDaoEntity) = resultDao.updateResultItem(
        item
    )

    override suspend fun updateObjItem(item: ObjItemDaoEntity) = resultDao.updateObjItem(item)
}
