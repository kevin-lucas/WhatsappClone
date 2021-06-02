package br.com.kevinlucas.whatsappmvvm.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.kevinlucas.whatsappmvvm.view.ContactFragment
import br.com.kevinlucas.whatsappmvvm.view.TalkFragment

// A simple ViewPager adapter class for paging through fragments
class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val titles = listOf("Conversas", "Contatos")

    override fun getCount(): Int = titles.size

    override fun getItem(position: Int): Fragment {

        var fragment = Fragment()

        when (position) {
            0 -> {
                fragment = TalkFragment()
            }
            1 -> {
                fragment = ContactFragment()
            }
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
