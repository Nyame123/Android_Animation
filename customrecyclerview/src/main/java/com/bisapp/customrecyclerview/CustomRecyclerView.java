package com.bisapp.customrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bisapp.customrecyclerview.layoutmanager.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerView extends RecyclerView {

    private LayoutInflater inflater;
    private int innerContent;
    private int backgroundResource;
    private CustomRecyclerView.CustomAdapter adapter;
    private List<Object> objectList = new ArrayList<>();
    private View innerContentLayout;
    private BindViewsListener bindViewsListener;
    private WrapContentLinearLayoutManager layoutManager;
    private boolean isDividerPresent;
    private LayoutManager customLayoutManager;

    public CustomRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void getResourceAttributes(AttributeSet attributeSet) {
        inflater = LayoutInflater.from(getContext());
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomRecyclerView, 0, 0);
        try {
            backgroundResource = typedArray.getResourceId(R.styleable.CustomRecyclerView_CustomRecyclerViewBackground, 0);
            innerContent = typedArray.getResourceId(R.styleable.CustomRecyclerView_CustomRecyclerViewInnerContentLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    private void init(AttributeSet attributeSet) {
        getResourceAttributes(attributeSet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            if (backgroundResource != 0) {
                setBackground(ResourcesCompat.getDrawable(getResources(), backgroundResource, null));
            }


        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setPadding(8, 8, 8, 8);
        layoutManager = new WrapContentLinearLayoutManager(getContext());
        setLayoutManager(layoutManager);
        setNestedScrollingEnabled(true);
        setItemAnimator(new DefaultItemAnimator());

        if (isDividerPresent) {
            addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        }
        setLayoutParams(footerParams);


        adapter = new CustomAdapter();
        setAdapter(adapter, true);


    }

    public CustomRecyclerView setCustomLayoutManager(LayoutManager layoutManager) {
        this.customLayoutManager = layoutManager;
        setLayoutManager(customLayoutManager);
        setNestedScrollingEnabled(true);
        setItemAnimator(new DefaultItemAnimator());

        if (isDividerPresent) {
            addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        }

        adapter = new CustomAdapter();
        setAdapter(adapter, true);
        return this;
    }

    /**
     * Fake List to show how the content display
     *
     * @param size the size of the model
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView fakeList(int size) {
        this.objectList.clear(); //empty before;

        for (int i = 0; i < size; i++)
            this.objectList.add("");

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        return this;
    }

    /**
     * Add Divider to the recyclerview
     *
     * @param isDividerPresent the flag to show divider
     **/
    public CustomRecyclerView setDivider(boolean isDividerPresent) {
        this.isDividerPresent = isDividerPresent;
        return this;
    }

    /**
     * Pass the List of model here and should be called at first time
     *
     * @param list the list of model
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView addModels(List<?> list) {
        this.objectList.clear(); //empty before;
        this.objectList.addAll(list);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        return this;
    }

    /**
     * Add model here
     *
     * @param o the model
     */
    public void addModel(Object o) {
        this.objectList.add(o);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    private void setAdapter(CustomAdapter adapter, boolean notifyDataChanged) {
        setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setBindViewsListener(BindViewsListener bindViewsListener) {
        this.bindViewsListener = bindViewsListener;
    }

    public interface BindViewsListener {
        void bindViews(View view, List<?> objects, int position);
    }

    private class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (innerContent == 0) {
                throw new IllegalArgumentException("Required the inner content Layout for the recyclerview");
            }


            innerContentLayout = inflater.inflate(innerContent, parent, false);
            return new CustomRecyclerView.CustomAdapter.ViewHolder(innerContentLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (bindViewsListener != null) {
                bindViewsListener.bindViews(holder.itemView, objectList, position);
            }
        }

        @Override
        public int getItemCount() {
            return objectList.size();
        }


        private class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

            }
        }
    }
}
