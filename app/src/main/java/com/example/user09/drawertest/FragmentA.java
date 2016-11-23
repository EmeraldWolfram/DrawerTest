package com.example.user09.drawertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user09 on 11/16/2016.
 */

public class FragmentA extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private Paint p;
    private Bitmap iconA;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = new Paint();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view   =  inflater.inflate(R.layout.fragment_absent, null);

        iconA   = BitmapFactory.decodeResource(getResources(), R.drawable.trash_icon);
        if(iconA == null) {
            Toast.makeText(getContext(), "Icon is Null", Toast.LENGTH_LONG).show();
        }

        recyclerView    = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(new String[]{"E", "B", "C", "D"});
        recyclerView.setAdapter(adapter);
        initSwipe();
        return view;
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                //adapter.removeItem(position);
                if (direction == ItemTouchHelper.LEFT){
                    adapter.removeItem(position);
                } else {
                    adapter.addItem("New Entry");
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){


                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        RectF icon_dest = new RectF(
                                (float) itemView.getLeft() + width,
                                (float) itemView.getTop() + width,
                                (float) itemView.getLeft() + 2*width,
                                (float)itemView.getBottom() - width);

                        c.drawBitmap(iconA, null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        RectF icon_dest = new RectF(
                                (float) itemView.getRight() - 2*width ,
                                (float) itemView.getTop() + width,
                                (float) itemView.getRight() - width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(iconA, null, icon_dest,p);
                    }

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        //String[] myData;
        private ArrayList<String> myDataList;

        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView textView;
            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.my_text_view_text);
            }
        }

        public MyAdapter(String[] dataSet){

            myDataList  = new ArrayList<>();

            for(int i=0; i < dataSet.length ; i ++) {
                myDataList.add(dataSet[i]);
            }
            //myData  = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view, parent, false);
            // set the view's size, margins, paddings and layout parameters
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ImageView img   = (ImageView)v.findViewById(R.id.my_text_view_img);
                    img.setImageResource(R.drawable.ic_menu_manage);
                    Toast.makeText(getContext(), "This is right", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(myDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return myDataList.size();
        }

        public void addItem(String item){
            myDataList.add(item);
            notifyItemInserted(myDataList.size());
            notifyDataSetChanged();
            //notifyItemRangeInserted(0, myDataList.size());
        }

        public String removeItem(int position){
            String item = myDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, myDataList.size());

            return item;
        }
    }
}
