package cz.vvoleman.phr.base.ui.view

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId)
