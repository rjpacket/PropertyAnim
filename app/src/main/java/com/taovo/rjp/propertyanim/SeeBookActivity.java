package com.taovo.rjp.propertyanim;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taovo.rjp.propertyanim.view.BookView;

public class SeeBookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_book);

        TextView tvPageOne = (TextView) findViewById(R.id.page_one);
        TextView tvPageTwo = (TextView) findViewById(R.id.page_two);

        BookView bookView = (BookView) findViewById(R.id.book_view);

        bookView.setTextViews(tvPageOne, tvPageTwo);
    }

    public Bitmap getViewBitmap(View view) {
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
