package br.com.kevinlucas.whatsappmvvm.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import br.com.kevinlucas.whatsappmvvm.view.adapter.SlidingTabLayout
import br.com.kevinlucas.whatsappmvvm.view.adapter.TabAdapter
import br.com.kevinlucas.whatsappmvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var mViewModel: MainViewModel
    private lateinit var mSlidingTabLayout: SlidingTabLayout
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar_main)
        mToolbar.title = "WhatsApp"
        setSupportActionBar(mToolbar)

        mSlidingTabLayout = findViewById(R.id.stl_tabs)
        mViewPager = findViewById(R.id.vp_page)

        mSlidingTabLayout.setSelectedIndicatorColors(
            ContextCompat.getColor(
                this,
                R.color.colorAccent
            )
        )
        mSlidingTabLayout.setDistributeEvenly(true)

        val tabAdapter = TabAdapter(supportFragmentManager)
        mViewPager.adapter = tabAdapter
        mSlidingTabLayout.setViewPager(vp_page)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setListeners()

        observer()
    }

    fun setListeners() {

    }

    fun signOut() {
        mViewModel.logout()
    }

    fun observer() {
        mViewModel.signOut().observe(this, Observer {
            if (it.success()) {
                val it = Intent(this, LoginActivity::class.java)
                startActivity(it)
                finish()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(v: View) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_exit -> {
                signOut()
                true
            }
            R.id.action_settings -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}