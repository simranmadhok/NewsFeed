package com.example.sndtcsi.newsfeed;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment
{
    protected RecyclerView recyclerView;
    protected NewsAdapter adapter;
    protected String API_KEY;
    String sourceTitle, sourceID;
    List<Articles> articleList;

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sourceTitle = bundle.getString("sourceTitle");
            sourceID = bundle.getString("sourceID");
        }

        getActivity().setTitle(sourceTitle);

        API_KEY = getString(R.string.API_KEY);

        recyclerView = root.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<News> call = apiService.getArticles(sourceID, API_KEY);

        call.enqueue(new Callback<News>()
        {
            @Override
            public void onResponse(Call<News> call, Response<News> response)
            {
                if (response != null && response.isSuccessful())
                {
                    articleList = response.body().getArticles();
                    populateRecycleView();
                }
                else
                {
                    Toast.makeText(getActivity(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t)
            {
                Log.e("TAG", "Error occurred: ", t);
                Toast.makeText(getActivity(), "Error in API CAll..", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                WebFragment webFragment = new WebFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", articleList.get(position).getUrl());
                bundle.putString("title", articleList.get(position).getTitle());
                webFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, webFragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        return root;
    }

    private void populateRecycleView()
    {
        if (articleList.isEmpty() || articleList.size() == 0)
        {
            recyclerView.setAdapter(null);
            Toast.makeText(getActivity(), "Error in List", Toast.LENGTH_SHORT).show();
        }
        else
            {
            adapter = new NewsAdapter(articleList, getActivity());
            recyclerView.setAdapter(adapter);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private MainFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}
