package com.skillbox.constraintlayoutandtoolbars

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolbarActivity : AppCompatActivity(R.layout.activity_toolbar) {

    override fun onStart() {
        super.onStart()
        Init()
    }

    private fun Init(){
        toolbar.setNavigationOnClickListener {
            toast("Кнопка навигации")
        }

        val searchItem = toolbar.menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                toast("Строка поиска открыта")
                textQuery.text = ""
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                toast("Строка поиска закрыта")
                textQuery.text = "Пользователь закончил поиск"
                return true
            }
        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(!query.isNullOrEmpty())
                        textQuery.text = "Нажата кнопка поиск с запросом = $query"
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(!newText.isNullOrEmpty())
                        textQuery.text = "Быстрый поиск включающий = $newText"
                    else
                        textQuery.text = ""
                    return true
                }
            })

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action2 -> {
                    toast("Пункт меню 1 (кнопка на панели)")
                    true
                }
                R.id.action3 -> {
                    toast("Пункт меню 2 (всплывающий список)")
                    true
                }
                else -> false
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
