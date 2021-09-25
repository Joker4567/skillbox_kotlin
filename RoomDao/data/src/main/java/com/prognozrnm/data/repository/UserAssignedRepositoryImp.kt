package com.prognozrnm.data.repository

import com.prognozrnm.data.db.dao.UserAssignedDao
import com.prognozrnm.data.db.entities.UserAssignedDaoEntity
import com.prognozrnm.data.entity.UserAssigned
import com.prognozrnm.data.network.ApiService
import com.prognozrnm.utils.platform.BaseRepository
import com.prognozrnm.utils.platform.ErrorHandler
import com.prognozrnm.utils.platform.State

class UserAssignedRepositoryImp(
    errorHandler: ErrorHandler,
    private val api:ApiService,
    private val userAssignedDao:UserAssignedDao
) : BaseRepository(errorHandler = errorHandler), UserAssignedRepository {

    override suspend fun getUserAssigned (
        idUser: String,
        onSuccess: (UserAssigned) -> Unit,
        onState: (State) -> Unit
    )  {
        execute(onSuccess = onSuccess, onState = onState) {
            api.userAssigned(idUser)
        }
    }

    override suspend fun setUserAssigned(
        list: UserAssignedDaoEntity,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            userAssignedDao.setUserAssigned(list)
        }
    }

    override suspend fun getUserAssignedLocal(userId:String): UserAssignedDaoEntity {
        return userAssignedDao.getUserAssigned(userId)
    }
}