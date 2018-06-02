package com.debruyckere.florian.mynews.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.controller.activity.MainActivity;
import com.debruyckere.florian.mynews.controller.activity.WebActivity;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by Debruyckère Florian on 10/05/2018.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<News> mNewsArrayList = new ArrayList<>();

    public NewsAdapter(){}

    public NewsAdapter(ArrayList<News> pNews){
        mNewsArrayList = pNews;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i("NEWS ADAPTER","on creating");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_cell,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News mNew = mNewsArrayList.get(position);
        holder.display(mNew);
    }

    @Override
    public int getItemCount() {
        return mNewsArrayList.size();
    }

    /*--------------
        View Holder
     ---------------*/


    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mImageView;
        private final TextView mTitle;
        private final TextView mTheme;
        private final TextView mDate;
        private String mURL= "";
        private News currentNew;

        private NewsViewHolder(final View newsView){
            super(newsView);

            mImageView = newsView.findViewById(R.id.cell_image);
            mTitle = newsView.findViewById(R.id.cell_title);
            mTheme = newsView.findViewById(R.id.cell_theme);
            mDate = newsView.findViewById(R.id.cell_date);

            newsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(newsView.getContext(),mTitle.getText(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(newsView.getContext(), WebActivity.class);
                    intent.putExtra("URL",mURL);
                    newsView.getContext().startActivity(intent);
                }
            });

        }

        private void display(News pNew){
            DateFormat formater = DateFormat.getDateInstance();

            currentNew = pNew;
            mTitle.setText(pNew.getTitle());
            mTheme.setText(pNew.getTheme());
            mDate.setText(formater.format(pNew.getDate()));
            mURL = pNew.getUrl();
            /*try {
                new DownloadImageTask(mImageView).execute(pNew.getImage());
            }catch (Exception e){
                Log.d("NO IMAGE"," no image found");
            }*/
        }
    }

    /*--------
      Task
     ---------*/

    public class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

        private  ImageView mImageView;

        public DownloadImageTask(ImageView pImageView){
            mImageView = pImageView;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
            this.cancel(true);
        }

        @Override
        protected Bitmap doInBackground(String... pUrl) {
            String url = pUrl[0];
            Bitmap mImage = null;

            try{
                InputStream in = new java.net.URL(url).openStream();
                mImage = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                e.printStackTrace();
            }

            return mImage;
        }
    }
}
