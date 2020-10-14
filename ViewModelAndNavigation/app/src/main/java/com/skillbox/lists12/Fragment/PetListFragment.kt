package com.skillbox.lists12.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.lists12.Adapter.PetAdapter
import com.skillbox.lists12.App
import com.skillbox.lists12.Interface.ItemClickListener
import com.skillbox.lists12.Models.Pet
import com.skillbox.lists12.R
import com.skillbox.lists12.Utils.ItemOffsetDecoration
import com.skillbox.lists12.ViewModel.PetListViewModel
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_template_object_add.*
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlin.random.Random

class PetListFragment : Fragment(R.layout.fragment_user_list), ItemClickListener {

    //region Поля
    private var petAdapter: PetAdapter? = null
    //endregion

    //region ЖЦ Фрагмента
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        App.fragmentPet = this
        initList()
        addFab.setOnClickListener { showChooseDialog() }
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        petAdapter = null
    }
    //endregion

    private fun initList() {
        //как передать два itemClickListener ? для одного нажатия и долгого
        petAdapter = PetAdapter(
            onItemClick = { position ->
            showDetail(position)
        },
            onRemoveItemClick = {position ->
            removePet(position)
        })
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

    private fun removePet(position: Int) {
        App.activity?.petListViewModel?.removePet(position)
    }

    private fun showDetail(position: Int) {
        //Переход в детальную информацию по питомцу используя Navigation
        val action = PetListFragmentDirections.actionPetsFragmentListToDetailPetFragment(position)
        findNavController().navigate(action)
    }

    private fun showChooseDialog() {
        DialogFragment().show(requireActivity().supportFragmentManager, "DialogFragment")
    }

    //если список пуст, то показываем заглушку
    private fun checkEmptyList() {
        if (!App.activity?.petListViewModel?.pets?.value?.isEmpty()!!) {
            hideEmptyGroup.visibility = View.GONE
            contentGroup.visibility = View.VISIBLE
        } else {
            hideEmptyGroup.visibility = View.VISIBLE
            contentGroup.visibility = View.GONE
        }
    }

    private fun observeViewModelState() {
        App.activity?.petListViewModel?.pets
            ?.observe(viewLifecycleOwner) { newPets ->
                petAdapter?.items = newPets
                checkEmptyList()
            }

        App.activity?.petListViewModel?.showToast
            ?.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Элемент удалён", Toast.LENGTH_SHORT).show()
            }
    }

    override fun setItemClick(item: Pet) {
        App.activity?.petListViewModel?.addPet(item)
    }
}