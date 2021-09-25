package com.prognozrnm.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.prognozrnm.data.db.entities.ObjDaoEntity
import com.prognozrnm.presentation.R
import kotlinx.android.synthetic.main.fragment_param_object_map.*

fun itemGeoMapList(clickGeoAdd: (ObjDaoEntity) -> Unit, clickGeoRemove: (ObjDaoEntity) -> Unit, clickPhotoMap: (ObjDaoEntity) -> Unit) =
    adapterDelegateLayoutContainer<ObjDaoEntity, Any>(R.layout.fragment_param_object_map) {

        bind {
            //region Определить геолокацию
            tvAddLocation_obj_map.setOnClickListener {
                clickGeoAdd.invoke(item)
            }
            ivAddLocation_obj_map.setOnClickListener {
                clickGeoAdd.invoke(item)
            }
            //endregion
            //region Удалить данный элемент
            tvDelete_obj_map.setOnClickListener {
                clickGeoRemove.invoke(item)
            }
            ivDelete_obj_map.setOnClickListener {
                clickGeoRemove.invoke(item)
            }
            //endregion
            //Отправить фото
            paramPhoto.setOnClickListener {
                clickPhotoMap.invoke(item)
            }
            paramFieldLatitude.setText(item.latitude.toString())
            paramFieldLongitude.setText(item.longitude.toString())
        }
    }