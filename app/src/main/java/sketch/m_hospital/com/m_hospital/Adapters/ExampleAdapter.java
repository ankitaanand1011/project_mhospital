/*
 * Copyright 2017 L4 Digital LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wbc.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.m_hospital.com.m_hospital.Fragments.FragG1_Encyclopedia_Details;
import sketch.m_hospital.com.m_hospital.R;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> implements FastScroller.SectionIndexer {

    Context context;
    ArrayList<HashMap<String , String>> arrayList;
    FragmentTransaction fragmentTransaction;

    public ExampleAdapter(ArrayList<HashMap<String , String>> arrayLi ,  FragmentTransaction fragmentTra) {
        this.arrayList = arrayLi;
        this.fragmentTransaction = fragmentTra;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_example, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(arrayList.get(position).get("disease_name"));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("disease" ,arrayList.get(position).get("disease_details") );
                Fragment fragment=new FragG1_Encyclopedia_Details();
                Bundle bundle = new Bundle();
                bundle.putString("disease_name", arrayList.get(position).get("disease_name"));
                bundle.putString("disease_details",arrayList.get(position).get("disease_details"));
                bundle.putString("doctor_id",arrayList.get(position).get("doctor_id"));
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(arrayList.get(position).get("disease_name").charAt(0));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;

        }

        public void bind(String item) {
            mTextView.setText(item);
        }
    }
}
