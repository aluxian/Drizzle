package com.aluxian.drizzle.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.aluxian.drizzle.R;
import com.aluxian.drizzle.api.models.Shot;
import com.aluxian.drizzle.utils.AlphaSatColorMatrixEvaluator;
import com.aluxian.drizzle.utils.PaletteTransformation;
import com.aluxian.drizzle.views.CustomEdgeScrollView;
import com.aluxian.drizzle.views.widgets.ShotAttachments;
import com.aluxian.drizzle.views.widgets.ShotComments;
import com.aluxian.drizzle.views.widgets.ShotReboundOf;
import com.aluxian.drizzle.views.widgets.ShotRebounds;
import com.aluxian.drizzle.views.widgets.ShotSummary;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class ShotActivity extends Activity {

    public static final String EXTRA_SHOT_DATA = "shot_data";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy");

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);

        Shot shot = new Gson().fromJson(getIntent().getStringExtra(EXTRA_SHOT_DATA), Shot.class);

        // Load the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);

        ShotSummary shotSummary = (ShotSummary) findViewById(R.id.shot_summary);
        shotSummary.load(shot);

        ImageView shotPreview = (ImageView) findViewById(R.id.shot_preview);
        new Picasso.Builder(this)
                .indicatorsEnabled(true)
                .build()
                .load(shot.images.largest())
                .transform(PaletteTransformation.instance())
                .noFade()
                .into(shotPreview, new Callback() {
                    @Override
                    public void onSuccess() {
                        AlphaSatColorMatrixEvaluator evaluator = new AlphaSatColorMatrixEvaluator();
                        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(evaluator.getColorMatrix());
                        shotPreview.getDrawable().setColorFilter(filter);

                        ObjectAnimator animator = ObjectAnimator.ofObject(filter, "colorMatrix", evaluator, evaluator.getColorMatrix());
                        animator.addUpdateListener(animation -> shotPreview.getDrawable().setColorFilter(filter));
                        animator.setDuration(1000);
                        animator.start();
                    }

                    @Override
                    public void onError() {

                    }
                });

        shotPreview.postDelayed(() -> {
            Palette palette = PaletteTransformation.getPalette(shotPreview);
            Palette.Swatch swatch = palette.getMutedSwatch();

            if (swatch == null) swatch = palette.getVibrantSwatch();
            if (swatch == null) swatch = palette.getDarkMutedSwatch();
            if (swatch == null) swatch = palette.getDarkVibrantSwatch();
            if (swatch == null) swatch = palette.getLightMutedSwatch();
            if (swatch == null) swatch = palette.getLightVibrantSwatch();

            if (swatch != null) {
                shotSummary.color(swatch);

                CustomEdgeScrollView scrollView = (CustomEdgeScrollView) findViewById(R.id.scroll_view);
                scrollView.setEdgeColor(swatch.getRgb());

                View toolbarBackground = findViewById(R.id.toolbar_background);
                toolbarBackground.setBackgroundColor(swatch.getRgb());
                toolbarBackground.setAlpha(0);

                View statusBarBackground = findViewById(R.id.status_bar_background);
                statusBarBackground.setBackgroundColor(swatch.getRgb());
                statusBarBackground.setAlpha(0);

                float full = shotPreview.getHeight() - statusBarBackground.getHeight();

                scrollView.setOnScrollChangedListener(() -> {
                    float alpha = scrollView.getScrollY() / full;
                    toolbarBackground.setAlpha(alpha);
                    statusBarBackground.setAlpha(alpha);
                });
            }
        }, 2000);

        TextView shotLikes = (TextView) findViewById(R.id.shot_likes);
        shotLikes.setText(shot.likesCount + " likes");

        TextView shotBuckets = (TextView) findViewById(R.id.shot_buckets);
        shotBuckets.setText(shot.bucketsCount + " buckets");

        TextView shotViews = (TextView) findViewById(R.id.shot_views);
        shotViews.setText(shot.viewsCount + " views");

        TextView shotTags = (TextView) findViewById(R.id.shot_tags);
        shotTags.setText(shot.tags.size() + " tags");

        if (shot.description != null) {
            TextView shotDescription = (TextView) findViewById(R.id.shot_description);
            shotDescription.setMovementMethod(LinkMovementMethod.getInstance());
            shotDescription.setText(Html.fromHtml(shot.description));
        }

        ShotReboundOf shotReboundOf = (ShotReboundOf) findViewById(R.id.shot_rebound_of);
        shotReboundOf.load(shot);

        ShotRebounds shotRebounds = (ShotRebounds) findViewById(R.id.shot_rebounds);
        shotRebounds.load(shot);

        ShotAttachments shotAttachments = (ShotAttachments) findViewById(R.id.shot_attachments);
        shotAttachments.load(shot);

        ShotComments shotComments = (ShotComments) findViewById(R.id.shot_comments);
        shotComments.load(shot);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isTaskRoot()) {
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
