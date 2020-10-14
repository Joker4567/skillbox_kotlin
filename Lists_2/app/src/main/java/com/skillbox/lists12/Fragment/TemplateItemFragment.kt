package com.skillbox.lists12.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.skillbox.lists12.App
import com.skillbox.lists12.Interface.ItemClickListener
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import kotlinx.android.synthetic.main.fragment_template_object_add.*

class TemplateItemFragment : Fragment(R.layout.fragment_template_object_add) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (TYPE_FORM) {
            Pet.Cat.TYPE_MODEL -> {
                imageView.setImageResource(R.drawable.cat)
                textView2.text = "Как будут звать котика?"
                //editTextCatName - имя кота
                //editVeightCat - вес кота
                groupCat.visibility = View.VISIBLE
                groupDog.visibility = View.GONE
                btAddDog.setOnClickListener {
                    App.data?.setItemClick(Pet.Cat(
                        6,
                        editTextCatName.text.toString(),
                        "https://avatars.mds.yandex.net/get-pdb/879261/33c2fa7f-cfb1-42b8-b6f5-bf3347fa8157/s375",
                        editVeightCat.text.toString().toInt()
                    ))
                    Navigation.findNavController(requireActivity(), R.id.mainFragment)
                        .navigate(R.id.petsFragmentList)
                }
            }
            Pet.Dog.TYPE_MODEL -> {
                imageView.setImageResource(R.drawable.dog)
                textView2.text = "Введите кличку собаки"
                switchPoroda.text = "Дворняга"
                switchPoroda.setOnCheckedChangeListener { _, isChecked ->
                    switchPoroda.text = if (isChecked) "Породистая" else "Дворняга"
                }
                //switchPoroda - породистый или нет
                //editTextCatName - кличка собаки
                groupCat.visibility = View.GONE
                groupDog.visibility = View.VISIBLE
                btAddDog.setOnClickListener {
                    App.data?.setItemClick(Pet.Dog(
                        5,
                        editTextCatName.text.toString(),
                        "https://vk.vkfaces.com/855620/v855620165/abe/sueUi09u90o.jpg",
                        switchPoroda.isChecked
                    ))
                    Navigation.findNavController(requireActivity(), R.id.mainFragment)
                        .navigate(R.id.petsFragmentList)
                }
            }
        }
    }

    companion object {

        private var TYPE_FORM = 0

        fun newInstance(
            type_form: Int
        ): TemplateItemFragment {
            TYPE_FORM = type_form
            return TemplateItemFragment()
        }
    }
}