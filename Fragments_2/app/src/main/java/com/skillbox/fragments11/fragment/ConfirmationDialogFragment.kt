package com.skillbox.fragments11.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.skillbox.fragment10.withArguments
import com.skillbox.fragments11.service.ItemSelectListener

class ConfirmationDialogFragment : DialogFragment() {

    var check:Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setMultiChoiceItems(
                tags.toTypedArray(),
                checked.toBooleanArray()
            ) { dialog, which, isChecked ->
                states[tags[which]] = isChecked
            }
            .setPositiveButton("Применить") { _, _ ->
                (activity as? ItemSelectListener)?.setList(states)
                check = true
            }
            .setNeutralButton("Отмена") { _, _ ->
                (activity as? ItemSelectListener)?.setList(statesOld.toMutableMap())
                check = true
            }
            .create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(!check)
            (activity as? ItemSelectListener)?.setList(statesOld.toMutableMap())
    }

    companion object {

        private lateinit var states: MutableMap<String, Boolean>
        private lateinit var statesOld: Map<String, Boolean>
        val tags = arrayListOf<String>()
        val checked = arrayListOf<Boolean>()

        fun newInstance(state: MutableMap<String, Boolean>): ConfirmationDialogFragment {
            return ConfirmationDialogFragment().withArguments {
                states = state
                statesOld = state.toMap()
                if(tags.any()) tags.clear()
                if(checked.any()) checked.clear()
                state.forEach{
                    tags.add(it.key)
                    checked.add(it.value)
                }
            }
        }
    }
}