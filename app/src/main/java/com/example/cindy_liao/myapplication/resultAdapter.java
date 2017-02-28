package com.example.cindy_liao.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cindy_liao on 26/02/2017.
 */

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.resutltViewHolder> {
    private String[] movieArray;
    private final onClickHandler mOnClickHandler;

    interface onClickHandler{
        void click(String s);
    }

    public resultAdapter(onClickHandler handler){
        mOnClickHandler=handler;
    }

    public class resutltViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView resutltTextView;
        public resutltViewHolder(View v){
            super(v);
            resutltTextView=(TextView) v.findViewById(R.id.tv_movieItem);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int posithon = getAdapterPosition();
            String movieData = movieArray[posithon];
            mOnClickHandler.click(movieData);
        }


    }

    @Override
    public resutltViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.moview_item_list,parent,false);
        resutltViewHolder viewHolder=new resutltViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(resutltViewHolder holder, int position) {
        String movie = movieArray[position];
        holder.resutltTextView.setText(movie);
    }

    @Override
    public int getItemCount() {
        if (movieArray==null)
            return 0;
        else
            return movieArray.length;
    }

    public void setMovieArray (String[] movies){
        movieArray=movies;
        notifyDataSetChanged();
    }
}
