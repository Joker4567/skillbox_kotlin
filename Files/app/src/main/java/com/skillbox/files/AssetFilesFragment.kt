package com.skillbox.files

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_assets.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.RandomAccessFile
import kotlin.coroutines.coroutineContext

class AssetFilesFragment: Fragment(R.layout.fragment_assets) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playSound.setOnClickListener {
            MediaPlayer.create(requireContext(), R.raw.clock_ticking).start()
        }

        val fileList = resources.assets.list("")
            .orEmpty()
            .joinToString()
        Log.d("AssetFilesFragment", fileList)

        try {
            val text = resources.assets.open("folder1/test-file.txt")
                .bufferedReader()
                .use {
                    it.readText()
                }
            Log.d("AssetFilesFragment", text)
        } catch (t: Throwable) {

        }

    }

}