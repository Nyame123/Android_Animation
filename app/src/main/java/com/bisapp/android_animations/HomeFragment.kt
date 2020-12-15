package com.bisapp.android_animations

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.bisapp.customrecyclerview.CustomRecyclerView
import com.google.android.material.transition.platform.MaterialElevationScale
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment(), CustomRecyclerView.BindViewsListener {


    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition();


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        category_recyclerview.doOnPreDraw { startPostponedEnterTransition() }
        ViewGroupCompat.setTransitionGroup(category_recyclerview,true)
        navController = findNavController(this)

        val headers = mutableListOf<String>()
        headers.add("View Property Animation")
        headers.add("Drawable Animation")
        headers.add("Zoom Animation")
        headers.add("container")
        category_recyclerview.setBindViewsListener(this)
        category_recyclerview.addModels(headers)



    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun bindViews(view: View?, objects: MutableList<*>?, position: Int) {

        val header = objects?.get(position) as String
        val path = view?.findViewById<TextView>(R.id.music)
        path?.text = header

        view?.setOnClickListener {
            if (position == 0)
                navController.navigate(R.id.action_homeFragment_to_FirstFragment)
            else if (position == 1)
                navController.navigate(R.id.action_homeFragment_to_SecondFragment)
            else if (position == 2)
                navController.navigate(R.id.action_homeFragment_to_zoomFragment)
            else if (position == 3) {
                ViewCompat.setTransitionName(view, header);

                var exit = MaterialElevationScale(false);
                exit.duration = 800
                var reenter = MaterialElevationScale(true);
                exit.duration = 800

                exitTransition = exit
                reenterTransition = reenter
                /*exitTransition = MaterialSharedAxis (MaterialSharedAxis.X,*//* growing= *//* true)
                reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X,*//* growing= *//* false)*/
                var map = mutableMapOf<View, String>()
                map.put(view, header)
                val extras = FragmentNavigator.Extras.Builder()
                        .addSharedElements(map)
                        .build()
                navController.navigate(R.id.action_homeFragment_to_transitionFragment, null, null, extras)
            }
        }
    }

}