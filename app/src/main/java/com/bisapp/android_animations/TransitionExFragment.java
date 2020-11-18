package com.bisapp.android_animations;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.transition.ChangeImageTransform;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TransitionExFragment extends Fragment {

    private Scene firstScene;
    private Scene secondScene;
    private FrameLayout sceneRoot;
    private boolean transitionStarted;
    private TextView textView;
    private ImageView logo;
    private NavController navController;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transition, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logo = view.findViewById(R.id.logo);
        textView = view.findViewById(R.id.text_view);
        createScene(view);

        navController = Navigation.findNavController(view);


        final TransitionSet transitionSet = getTransitionSet();

        transitionStarted = false;
        view.findViewById(R.id.goBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionStarted = !transitionStarted;

                if (transitionStarted) {
                    TransitionManager.go(secondScene, transitionSet);
                } else {
                    TransitionManager.go(firstScene, transitionSet);
                }

            }
        });


        sceneRoot.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                createPopUpMenu(sceneRoot);

            }
        });

    }

    private void createPopUpMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.inflate(R.menu.menu_options);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fragment:
                        HashMap<View, String> map = new HashMap<>();
                        map.put(logo, getString(R.string.frag_logo_transition));
                        map.put(textView, getString(R.string.frag_text_transition));

                        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                .addSharedElements(map)
                                .build();
                        navController.navigate(R.id.action_transitionFragment_to_secondFragment, null, null, extras);

                        break;
                    case R.id.activity:
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                Pair.<View, String>create(logo,getString(R.string.frag_logo_transition)),
                                Pair.<View, String>create(textView,getString(R.string.frag_text_transition)));

                        ActivityNavigator.Extras extras1 = new ActivityNavigator.Extras.Builder()
                                .setActivityOptions(options)
                                .build();


                        startActivity(new Intent(getContext(), SecondActivity.class), options.toBundle());
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @NotNull
    private TransitionSet getTransitionSet() {
        new ChangeImageTransform();
        // final ChangeBounds changeBounds = new ChangeBounds();
        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        final ChangeBounds changeBounds = (ChangeBounds) transitionInflater.inflateTransition(R.transition.change_bounds);
        final TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(changeBounds);
        transitionSet.addTransition(new Fade());
        return transitionSet;
    }

    private void createScene(@NonNull View view) {
        // Create the scene root for the scenes in this app
        sceneRoot = view.findViewById(R.id.scene_root);

        // Create the scenes
        firstScene = Scene.getSceneForLayout(sceneRoot, R.layout.first_scene, getContext());
        secondScene = Scene.getSceneForLayout(sceneRoot, R.layout.second_scene, getContext());
    }


}