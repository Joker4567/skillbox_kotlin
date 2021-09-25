package com.prognozrnm.presentation.detail_work

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.ParamResult
import com.prognozrnm.data.entity.WorkList.Work
import com.prognozrnm.data.network.PhotoUpload
import com.prognozrnm.data.repository.CheckListRepository
import com.prognozrnm.data.repository.ResultRepository
import com.prognozrnm.presentation.models.ParamInputType
import com.prognozrnm.utils.platform.BaseViewModel

class DetailWorkViewModel(
    private val workItem: Work,
    private val repository: CheckListRepository,
    val repositoryResult: ResultRepository
) : BaseViewModel() {

    val resultProcess: ProcessingOfResults
    var listCheckList = MutableLiveData<List<CheckListItemDaoEntity>>()
    var source: MutableList<CheckListItemDaoEntity> =
        emptyList<CheckListItemDaoEntity>().toMutableList()

    init {
        loadCheckListLocalDB()
        resultProcess = ProcessingOfResults(repositoryResult, workItem)
    }

    private fun loadCheckListLocalDB() {
        launchIO {
            var sourceWorkList =
                repository.getCheckListLocal()[(workItem.checklistType - 1)].checkList.toMutableList()
            setWorkCheckListList(sourceWorkList)
        }
    }

    //TODO При фильтрации использовать source.filter()
    private fun setWorkCheckListList(item: List<CheckListItemDaoEntity>) {
        launch {
            var sourceWorkList = emptyList<CheckListItemDaoEntity>().toMutableList()
            //удалём элементы если они не пришли из сервера, но есть в чек-листа
            item.forEach {
                if (workItem.params.contains(it.id))
                    sourceWorkList.add(it)
            }
            listCheckList.postValue(sourceWorkList)
            source.addAll(sourceWorkList)
            loadResult()
        }
    }

    //Загрузка результатов в ячейки (ранее занесенные данные)
    private fun loadResult() {
        source.forEach {
            launchIO {
                it.resultText =
                    repositoryResult.getResultItemWithResultObj(workItem.resultId, it.id)?.result
                        ?: ""
                launch { listCheckList.postValue(source) }
            }
        }
    }

    fun checkResult(result: ParamResult) {
        when (ParamInputType.valueOf(result.inputType)) {
            ParamInputType.Boolean -> {
                resultProcess.bool(result, latitude, longitude)
            }
            ParamInputType.Number -> {
                resultProcess.number(result, latitude, longitude)
            }
            ParamInputType.Text -> {
                resultProcess.text(result, latitude, longitude)
            }
            ParamInputType.Select -> {
            }
            ParamInputType.Seconds -> {
                resultProcess.seconds(result, latitude, longitude)
            }
            ParamInputType.Time -> {
                resultProcess.time(result, latitude, longitude)
            }
            ParamInputType.DateTime -> {
                resultProcess.date(result, latitude, longitude)
            }
        }
    }

    fun uploadPhoto(item: CheckListItemDaoEntity, data: ByteArray, context: Context) {
        PhotoUpload.sendPhoto(data, workItem.resultId, item.id, context)
    }

    private var latitude = 0.0
    private var longitude = 0.0
    fun addItemGeoLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude

    }
}