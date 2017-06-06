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

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ab.notificationscreen.Constants;
import com.example.ab.notificationscreen.HeadingImageView;
import com.example.ab.notificationscreen.R;
import java.util.ArrayList;
import java.util.HashMap;

public class CommentsItemViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

  private boolean bDrawn = false;

  public ImageView thumbImage;
  public TextView txtTitle;
  public TextView txtDescription;
  public TextView txtAgo;

  private LinearLayout usersIconLayout;
  private Context mContext;
  private ArrayList<String> mArrPhotoNames = new ArrayList<String>();
  private int mCount;

  public CommentsItemViewHolder(View itemView){
    super(itemView);
    thumbImage = (ImageView)itemView.findViewById(R.id.thumb_pic);
    txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
    txtDescription = (TextView)itemView.findViewById(R.id.txt_description);
    txtAgo = (TextView)itemView.findViewById(R.id.txt_ago);

    usersIconLayout = (LinearLayout)itemView.findViewById(R.id.users_icon_panel);
    ViewTreeObserver vto = usersIconLayout.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        usersIconLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Constants.gUsersIconPanelWidth = usersIconLayout.getMeasuredWidth();
        Constants.gUsersIconPanelHeight = usersIconLayout.getMeasuredHeight();
        addUsersIcon();
      }
    });

    itemView.setOnTouchListener(this);
  }

  public void setUsersIconData(Context context, ArrayList<HashMap<String, String>> arrCommentDatas,
                               int count){
    mContext = context;
    mCount = count;

    String strDescription = arrCommentDatas.get(0).get("comment").toString();;
    String strName = arrCommentDatas.get(0).get("name").toString();
    int nameLength = strName.length();
    strDescription = strName + ": " + strDescription;

    SpannableString description = new SpannableString(strDescription);
    description.setSpan(new StyleSpan(Typeface.BOLD), 0, nameLength, 0);
    this.txtDescription.setText(description);

    for (int i = 0; i < arrCommentDatas.size(); i++){
      HashMap likePeople = arrCommentDatas.get(i);
      mArrPhotoNames.add(likePeople.get("photo").toString());
    }

    addUsersIcon();
  }

  private void addUsersIcon(){
    if (Constants.gUsersIconPanelHeight == 0)
      return;
    if (bDrawn){
      return;
    }

    int margin = 15;
    int maxCount = (Constants.gUsersIconPanelWidth + margin) / (Constants.gUsersIconPanelHeight +
        margin);
    if (maxCount > mCount){
      maxCount = mCount;
    }
    int photosCount = mArrPhotoNames.size() > maxCount ? maxCount : mArrPhotoNames
        .size();

    for (int i = 0; i < photosCount; i++) {
      if (i == maxCount - 1){
        if (maxCount < mCount){
          this.addUserIcon("btn_more");
          break;
        }
      }
      String strPhotoName = mArrPhotoNames.get(i);
      this.addUserIcon(strPhotoName);
    }

    if (photosCount < maxCount && photosCount < mCount){
      this.addUserIcon("btn_more");
    }

    bDrawn = true;
  }

  private void addUserIcon(final String strPhotoName){
    HeadingImageView userIcon = new HeadingImageView(mContext, Constants.gUsersIconPanelHeight);
    int photoId = mContext.getResources().getIdentifier(strPhotoName, "drawable", mContext
        .getPackageName());
    userIcon.setImageResource(photoId);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Constants
        .gUsersIconPanelHeight, Constants.gUsersIconPanelHeight);
    layoutParams.setMargins(0, 0, 10, 0);
    userIcon.setLayoutParams(layoutParams);

    userIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.i("touched item", "touched profile photo('" + strPhotoName + "') on Comments cell");
      }
    });

    usersIconLayout.addView(userIcon);
  }

  @Override
  public boolean onTouch(View view, MotionEvent event){
    if (event.getAction() == MotionEvent.ACTION_UP){
      Log.i("Touched cell", "Go to comments list");
    }
    return true;
  }
}
