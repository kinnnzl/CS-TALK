package com.example.seapp.ui.additional;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.seapp.R;
import java.util.List;

public class GuideViewPagerAdapter extends PagerAdapter {
    Context mContext ;
    List<ScreenItem> mListScreen;
    TextView titleHead, titleLabel, titleLabel2;
    ImageView iconClose;

    public GuideViewPagerAdapter(Context mContext, List<ScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layoutScreen = inflater.inflate(R.layout.layout_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        iconClose = layoutScreen.findViewById(R.id.icon_close);
        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GuideActivity)mContext).finish();
            }
        });

        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

        titleHead = layoutScreen.findViewById(R.id.guideHead);
        titleHead.setText(mListScreen.get(position).getTitleHead());

        titleLabel = layoutScreen.findViewById(R.id.guideLabel);
        titleLabel.setText(mListScreen.get(position).getTitleLabel());

        titleLabel2 = layoutScreen.findViewById(R.id.guideLabel2);

        if (position == 0) {
            iconClose.setVisibility(View.INVISIBLE);
            titleLabel2.setVisibility(View.VISIBLE);
            titleLabel2.setText(mListScreen.get(position).getTitleLabel2());
        }

        else {
            iconClose.setVisibility(View.VISIBLE);
            titleLabel2.setVisibility(View.INVISIBLE);
        }

        layoutScreen.setBackgroundResource(mListScreen.get(position).getBackgroundImg());
        container.addView(layoutScreen);

        return layoutScreen;

    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
