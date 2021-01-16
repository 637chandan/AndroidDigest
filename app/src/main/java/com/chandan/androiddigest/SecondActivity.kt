package com.chandan.androiddigest

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chandan.androiddigest.databinding.DataActivityBinding


class SecondActivity : AppCompatActivity() {
    lateinit var  dataBinding: DataActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding  =  DataBindingUtil.setContentView(this, R.layout.data_activity)
        dataBinding.person = Person
        Glide.with(dataBinding.imageView2.context).load(Person.image)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(dataBinding.imageView2)

    }

}