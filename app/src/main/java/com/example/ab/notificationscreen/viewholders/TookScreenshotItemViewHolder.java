/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.ab.notificationscreen.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ab.notificationscreen.HeadingImageView;
import com.example.ab.notificationscreen.R;

public class TookScreenshotItemViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

  public ImageView thumbImage;
  public HeadingImageView profileImage;
  public TextView txtTitle;
  public TextView txtSubTitle;
  public TextView txtAgo;

  public TookScreenshotItemViewHolder(View itemView){
    super(itemView);

    thumbImage = (ImageView)itemView.findViewById(R.id.thumb_pic);
    profileImage = (HeadingImageView)itemView.findViewById(R.id.profile_pic);
    txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
    txtSubTitle = (TextView)itemView.findViewById(R.id.txt_subtitle);
    txtAgo = (TextView)itemView.findViewById(R.id.txt_ago);

    ViewTreeObserver vto = profileImage.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        profileImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        int width = profileImage.getMeasuredWidth();
        profileImage.mRadius = width / 2.0f;
      }
    });
    itemView.setOnTouchListener(this);
  }

  @Override
  public boolean onTouch(View view, MotionEvent event){
    if (event.getAction() == MotionEvent.ACTION_UP){
      Log.i("Touched cell", txtSubTitle.getText().toString());
    }
    return true;
  }
}
