package com.skillbox.lists12.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.skillbox.lists12.App
import com.skillbox.lists12.Interface.ItemClickListener
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.ViewModel.PetListViewModel
import kotlinx.android.synthetic.main.fragment_template_object_add.*
import kotlin.random.Random

class TemplateItemFragment : Fragment(R.layout.fragment_template_object_add) {

    private val petListViewModel: PetListViewModel by viewModels()

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
                    (App.fragmentPet as? ItemClickListener)?.setItemClick(
                        Pet.Cat(
                            Random(1000).nextInt(),
                            editTextCatName.text.toString(),
                            "https://avatars.mds.yandex.net/get-pdb/879261/33c2fa7f-cfb1-42b8-b6f5-bf3347fa8157/s375",
                            editVeightCat.text.toString().toInt()
                        )
                    )
                    val action = TemplateItemFragmentDirections.actionTemplateFragmentToPetsFragmentList()
                    findNavController().navigateUp()
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
                    (App.fragmentPet as? ItemClickListener)?.setItemClick(Pet.Dog(
                        Random(1000).nextInt(),
                        editTextCatName.text.toString(),
                        "https://vk.vkfaces.com/855620/v855620165/abe/sueUi09u90o.jpg",
                        switchPoroda.isChecked
                    ))
                    val action = TemplateItemFragmentDirections.actionTemplateFragmentToPetsFragmentList()
                    findNavController().navigateUp()
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