package br.com.henrique.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.henrique.testpag.R

class BaseNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_navigation)
    }
}