package com.trustorg.lastnews;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NewsViewHolder> {
    private List<News> mNewsList;
    private OnItemClickListener mListener;
    private Context mContext;

    public MyAdapter(List<News> newsList, OnItemClickListener listener){
        mNewsList = newsList;
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View newsView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(newsView);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.NewsViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.mTitle.setText(news.getTitle());
        holder.mType.setText(news.getType());
        holder.mDate.setText(news.getDate());
        holder.mSection.setText(news.getSection());
        holder.mAuthor.setText(news.getAuthor());
        if ((news.getAuthor()!=null)){
            holder.mAuthor.setVisibility(View.GONE);
        }
        holder.bind(mNewsList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mType;
        TextView mDate;
        TextView mSection;
        TextView mAuthor;

        public NewsViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.news_title);
            mType = v.findViewById(R.id.news_type);
            mDate = v.findViewById(R.id.news_date);
            mSection = v.findViewById(R.id.news_section);
            mAuthor = v.findViewById(R.id.news_author);
        }

        public void bind(final News news, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(news);
                }
            });
        }
    }

    public void clear(){
        mNewsList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<News> newsList){
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }
}