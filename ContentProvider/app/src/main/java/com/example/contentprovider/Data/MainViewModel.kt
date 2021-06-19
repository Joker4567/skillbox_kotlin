package com.example.contentprovider.Data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)
    private val contactLiveEvent = MutableLiveData<List<BaseContact>>()
    private val loadLiveEvent = MutableLiveData<Boolean>(false)

    val contactLiveData: LiveData<List<BaseContact>>
        get() = contactLiveEvent
    val loadLiveData: LiveData<Boolean>
        get() = loadLiveEvent

    fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadLiveEvent.postValue(true)
                contactLiveEvent.postValue(repository.getAllContacts())
            } catch (e: Exception) {
                contactLiveEvent.postValue(arrayListOf())
            } finally {
                loadLiveEvent.postValue(false)
            }
        }
    }

    fun getContactFullInfo(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadLiveEvent.postValue(true)
                contactLiveEvent.postValue(arrayListOf(repository.getContactInfo(contact) as BaseContact))
            } catch (e: Exception) {
                contactLiveEvent.postValue(arrayListOf())
            } finally {
                loadLiveEvent.postValue(false)
            }
        }
    }

    fun deleteContact() {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = contactLiveData.value!!.first()
            try {
                if (repository.deleteContact(contact) > 0)
                    contactLiveEvent.postValue(arrayListOf())
            } catch (e: Exception) {

            } finally {
                loadLiveEvent.postValue(false)
            }
        }
    }

    fun addContact(contact: BaseContact) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addContact(contact)
                loadLiveEvent.postValue(true)
            } catch (e: Exception) {

            }
        }
    }
}