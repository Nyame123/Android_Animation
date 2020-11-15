package com.bisapp.android_animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.bisapp.customrecyclerview.CustomRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment(), CustomRecyclerView.BindViewsListener {

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController(this)

        val headers = mutableListOf<String>()
        headers.add("View Property Animation")
        headers.add("Drawable Animation")
        category_recyclerview.setBindViewsListener(this)
        category_recyclerview.addModels(headers)


    }


    override fun bindViews(view: View?, objects: MutableList<*>?, position: Int) {

        val header = objects?.get(position) as String
        val path = view?.findViewById<TextView>(R.id.music)
        path?.text = header

        view?.setOnClickListener {
            if (position == 0)
                navController.navigate(R.id.action_homeFragment_to_FirstFragment)
            else if (position == 1)
                navController.navigate(R.id.action_homeFragment_to_SecondFragment)
        }
    }

}