package com.taovo.rjp.propertyanim;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.taovo.rjp.propertyanim.evaluator.BookValue;
import com.taovo.rjp.propertyanim.view.BookOpenView;

public class ChangePageActivity extends Activity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_page);
        mContext = this;

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new GridAdapter());
    }

    public class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = new BookOpenView(mContext);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup window = (ViewGroup) getWindow().getDecorView();
                    BookValue startValue = new BookValue(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    BookValue endValue = new BookValue(window.getLeft(), window.getTop(), window.getRight(), window.getBottom());

                    BookOpenView bookOpenView = new BookOpenView(mContext);
                    window.addView(bookOpenView);
                    bookOpenView.startAnim(startValue, endValue);
                }
            });

            return convertView;
        }
    }
}
