package com.example.nearby_feature.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nearby_feature.R;
public class help extends AppCompatActivity {
    private static final String TAG = "Help";
    private Button next;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

            next=findViewById(R.id.next);
            viewPager=findViewById(R.id.viewPager);
            int layouts[]={R.layout.activity_pointers,R.layout.activity_theme,R.layout.activity_voice,R.layout.act_auth,R.layout.act_buff,R.layout.act_det,R.layout.act_list,R.layout.act_nav,R.layout.act_shar};
            viewPager.setAdapter(new PagerAdapter() {
                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, int position) {
                    LayoutInflater layoutInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    View view=layoutInflater.inflate(layouts[position],container,false);
                    container.addView(view);
                    return view;
                }

                @Override
                public int getCount() {
                    return layouts.length;
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view==object;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    View view=(View)object;
                    container.removeView(view);
                }
            });
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Log.e(TAG,"OnPageSelected: "+position);
                    if(position==9)
                    {
                        next.setText("Get Started");
                    }
                    else
                    {
                        next.setText("Next");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current=viewPager.getCurrentItem();
                    if(current<layouts.length-1)
                    {
                        viewPager.setCurrentItem(current+1);
                    }
                    else
                    {
                        launchDashboard();
                    }
                }
            });}
        private void launchDashboard() {
            startActivity(new Intent(help.this,MainActivity.class));
        }



}