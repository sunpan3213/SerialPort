package com.zkxl.mark.base

import android.text.Editable
import android.text.TextWatcher

/**
 * Create by Panda on 2019/4/4
 */
abstract class BaseTextWatcherActivity:BaseActivity(),TextWatcher{

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}