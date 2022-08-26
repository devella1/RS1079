package com.example.nearby_feature;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{
    private List<place> data=new ArrayList<>();
    private static adapter.Listener listener;
    public  static interface Listener{
        public void onClick(int p);
    }
    public adapter(List<place> a){
        data=a;
    }

    public void filterList(ArrayList<place> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        data = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
    public void onBindViewHolder(adapter.ViewHolder holder , @SuppressLint("RecyclerView") int position){
        place a= (place)data.get(position);
        CardView c=holder.cardView;
        //place a=new place("myplace","12.33","12.33","dkdk
        TextView q=c.findViewById(R.id.placeName);
        q.setText((String)a.getName());

        TextView m=c.findViewById(R.id.placeAddress);
        m.setText("mmm");
        
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });





    }
    public int getItemCount(){

        return data.size();
    }
}
