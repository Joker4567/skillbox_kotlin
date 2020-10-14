package com.skillbox.lists12.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.lists12.Adapter.PetAdapter
import com.skillbox.lists12.App
import com.skillbox.lists12.Interface.ItemClickListener
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.Service.FormState
import com.skillbox.lists12.Service.ItemOffsetDecoration
import com.skillbox.lists12.Service.inflate
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_user_list.*
import java.lang.Exception

class PetListFragment : Fragment(R.layout.fragment_user_list) {

    //region Поля
    private var state: FormState = FormState(emptyList<Pet>().toMutableList())
    private var petAdapter: PetAdapter? = null
    //endregion

    //region ЖЦ Фрагмента
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addUser() }
        petAdapter?.items = App.data?.getPetsList()
        try {
            state = savedInstanceState?.getParcelable(KEY_FRAGMENT) ?: error("unreachable")
            if (state != null) {
                App.data?.setPetsList(state?.petsList)
                petAdapter?.items = App.data?.getPetsList()
            }
        } catch (ex: Exception) {
        }
        petAdapter?.notifyDataSetChanged()
        checkEmptyList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        state.save(App.data?.getPetsList()?.toMutableList()!!)
        outState.putParcelable(KEY_FRAGMENT, state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        petAdapter = null
    }

    override fun onStart() {
        super.onStart()
        checkEmptyList()
    }
    //endregion

//    override fun setItemClick(item: Pet) {
//        petsList = listOf(item) + petsList
//        addItem()
//    }

    private fun initList() {
        petAdapter = PetAdapter { position -> deletePerson(position) }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        with(petsFragmentList) {
            adapter = petAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
            addItemDecoration(ItemOffsetDecoration(requireContext()))
        }

        petAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0 && positionStart == linearLayoutManager.findFirstCompletelyVisibleItemPosition()) {
                    linearLayoutManager.scrollToPosition(0)
                }
            }
        })
    }

    private fun deletePerson(position: Int) {
        //petsList = petsList.filterIndexed { index, user -> index != position }
        App.data?.removeItemClick(position)
        petAdapter?.items = App.data?.getPetsList()
        checkEmptyList()
        petAdapter?.notifyItemRemoved(position)
    }

    private fun addUser() {
        DialogFragment().show(activity!!.supportFragmentManager, "DialogFragment")
    }

    private fun addItem() {
        petAdapter?.items = App.data?.getPetsList()
        checkEmptyList()
        petAdapter?.notifyItemInserted(0)
    }

    private fun checkEmptyList() {
        if (App.data?.getPetsList()?.isEmpty()!!) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, EmptyListFragment())
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