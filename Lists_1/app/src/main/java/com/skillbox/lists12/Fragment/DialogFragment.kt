package com.skillbox.lists12.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import kotlinx.android.synthetic.main.fragment_dialog_choose.*

class DialogFragment : BottomSheetDialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imgViewCat_dialog.setOnClickListener {
            //Добавляем в коллекцию кота
            showFragment(Pet.Cat.TYPE_MODEL)
            hideDialog()
        }
        imgViewDog_dialog.setOnClickListener {
            //Добавляем в коллекцию собаку
            showFragment(Pet.Dog.TYPE_MODEL)
            hideDialog()
        }
    }

    private fun hideDialog(){
        activity?.supportFragmentManager?.findFragmentByTag("DialogFragment")
            ?.let { it as? DialogFragment }
            ?.dismiss()
    }

    private fun showFragment(type:Int){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, TemplateItemFragment.newInstance(type))
            ?.apply {
                addToBackStack("TemplateItemFragment")
            }
            ?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_choose, container, false)
    }
}