package com.prognozrnm.data.repository

import com.prognozrnm.data.db.dao.CheckListDao
import com.prognozrnm.data.db.entities.CheckListDaoEntity
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.CheckList
import com.prognozrnm.data.entity.CheckListItem
import com.prognozrnm.data.network.ApiService
import com.prognozrnm.utils.platform.BaseRepository
import com.prognozrnm.utils.platform.ErrorHandler
import com.prognozrnm.utils.platform.State
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class CheckListRepositoryImp(
    errorHandler: ErrorHandler,
    private val client: OkHttpClient,
    private val urlHost: String,
    private val checkListDao: CheckListDao
) : BaseRepository(errorHandler = errorHandler), CheckListRepository {

    override suspend fun getCheckList(
        onSuccess: (List<CheckList>) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            val request = Request.Builder()
                .url(urlHost + "api/CheckList/GetCheckLists")
                .build()
            val json = client.newCall(request).execute().body!!.string()
            var list:MutableList<CheckList> = emptyList<CheckList>().toMutableList()

            val checkListType = Types.newParameterizedType(
                List::class.java,
                CheckListItem::class.java
            )
            val adapter =
                Moshi.Builder()
                    .build()
                    .adapter<List<CheckListItem>>(checkListType)
                    .nonNull()
            val root = JSONObject(json)
            val i: Iterator<String> = root.keys()
            while (i.hasNext()) {
                val id = i.next()
                val checkListItem = adapter.fromJson(root.getJSONArray(id).toString())
                list.add(
                    CheckList(
                        id.toInt(),
                        checkListItem!!
                    )
                )
            }
            list
        }
    }

    override suspend fun setCheckList(
        list: List<CheckListDaoEntity>,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            checkListDao.setCheckList(list)
        }
    }

    override suspend fun getCheckListLocal(): List<CheckListDaoEntity> {
        return checkListDao.getCheckList()
    }
}