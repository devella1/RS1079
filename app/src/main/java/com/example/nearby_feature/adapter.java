package com.example.nearby_feature;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{
    private List<place> data;
    private static adapter.Listener listener;
    public  static interface Listener{
        public void onClick(int p);
    }
    public adapter(List<place> a){
        this.data=a;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView=v;
        }

    }



    public static  void setListener(adapter.Listener l){
        listener=l;
    }

    public adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforplacelist,parent,false);
        return new adapter.ViewHolder(cv);

    }
    public void onBindViewHolder(ViewHolder holder , @SuppressLint("RecyclerView") int position){
        place a= (place)data.get(position);
        CardView c=holder.cardView;

        TextView q=c.findViewById(R.id.placeName1);
        q.setText(a.getName());

        TextView m=c.findViewById(R.id.placeAddress1);
        m.setText("address");

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });

        ImageButton direction=c.findViewById(R.id.placeDirections1);

        ImageButton call= c.findViewById(R.id.placeCall1);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
    public int getItemCount(){

        return data.size();
    }
}