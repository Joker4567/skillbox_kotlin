package com.skillbox.lists12.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.lists12.Adapter.PetAdapter
import com.skillbox.lists12.Interface.ItemCliickListener
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.Service.FormState
import kotlinx.android.synthetic.main.fragment_user_list.*
import java.lang.Exception

class PetListFragment: Fragment(R.layout.fragment_user_list), ItemCliickListener {

    private var state: FormState = FormState(emptyList<Pet>().toMutableList())
    private var petsList = listOf(
        Pet.Cat(
            name = "Мурзик",
            avatarLink = "https://wallbox.ru/resize/320x240/wallpapers/main2/201705/trava-koski-kotata.jpg",
            weight = 4
        ),
        Pet.Dog(
            name = "Кузя",
            avatarLink = "https://i2.wp.com/sobaki-pesiki.ru/wp-content/gallery/shenki-gavayskiy-bishon/dynamic/32.jpg-nggid03387-ngg0dyn-365x230x100-00f0w010c011r110f110r010t010.jpg",
            poroda = true
        ),
        Pet.Cat(
            name = "Пушистик",
            avatarLink = "https://murk.in/gallery-resize/10/10439-ab655457-murk.in.jpg",
            weight = 2
        ),
        Pet.Dog(
            name = "Шарик",
            avatarLink = "https://img1.goodfon.ru/original/320x240/3/56/sobaka-schenok-leto-7432.jpg",
            poroda = false
        )
    )

    private var petAdapter: PetAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addUser() }
        petAdapter?.updatePets(petsList)
//        petAdapter?.notifyItemRangeInserted(0, petsList.size)
        try {
            state = savedInstanceState?.getParcelable(KEY_FRAGMENT) ?: error("unreachable")
            if(state != null){
                petsList = state?.petsList
                petAdapter?.updatePets(petsList)
            }
        } catch (ex:Exception){ }
        petAdapter?.notifyDataSetChanged()
        checkEmptyList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        state.save(petsList.toMutableList())
        outState.putParcelable(KEY_FRAGMENT, state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        petAdapter = null
    }

    private fun initList() {
        petAdapter = PetAdapter { position -> deletePerson(position) }
        with(petsFragmentList) {
            adapter = petAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deletePerson(position: Int) {
        petsList = petsList.filterIndexed { index, user -> index != position }
        petAdapter?.updatePets(petsList)
        petAdapter?.notifyItemRemoved(position)
        checkEmptyList()
    }

    private fun addUser() {
        DialogFragment().show(activity!!.supportFragmentManager, "DialogFragment")
    }

    fun addItem(){
        petAdapter?.updatePets(petsList)
        petAdapter?.notifyItemInserted(0)
        petsFragmentList?.scrollToPosition(0)
    }

    override fun setItemClick(item: Pet) {
        petsList = listOf(item) + petsList
        checkEmptyList()
        addItem()
    }

    fun checkEmptyList(){
        if(petsList.isEmpty())
        {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, EmptyListFragment(),"EmptyListFragment")
                ?.apply {
                    addToBackStack("EmptyListFragment")
                }
                ?.commit()
        } else {
            activity?.supportFragmentManager?.popBackStack("EmptyListFragment",0)
        }
    }

    companion object {
        const val KEY_FRAGMENT: String = "PetListFragment"
    }
}