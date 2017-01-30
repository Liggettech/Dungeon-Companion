package liggettech.dungeoncompanion;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CharacterSheet extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private final static int NUM_PAGES = 5;
    private List<ImageView> dots;
    private Runnable hideDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_sheet);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.char_overlay_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(2, false);
        addPageIndicator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch(position) {
                case 0: return FragmentCharFeatures.newInstance(position + 1);
                case 1: return FragmentCharStats.newInstance(position + 1);
                case 2: return FragmentCharCore.newInstance(position + 1);
                case 3: return FragmentCharInventory.newInstance(position + 1);
                case 4: return FragmentCharSpells.newInstance(position + 1);
                default: return FragmentCharCore.newInstance(2);
            }
            //return null;
        }

        @Override
        public int getCount() {
            // Show total pages.
            return NUM_PAGES;
        }
    }

    /**
     * Adds circle indicator at bottom of page
     */
    public void addPageIndicator() {
        dots = new ArrayList<>();
        final LinearLayout dotsLayout = (LinearLayout)findViewById(R.id.char_overlay_indicator);

        // Set fade out timer and animation

        for(int i = 0; i < NUM_PAGES; i++) {
            ImageView dot = new ImageView(this);

            //set default page as middle page, #2
            if (i == 2) {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.page_selected));
            }
            else {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.page_unselected));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(40, 0, 40, 0);
            dotsLayout.addView(dot, params);
            dots.add(dot);
        }

        /**
         * Make the dots hide after 1 second
         */
        hideDots = new Runnable() {
            public void run() {
                AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                fadeOut.setDuration(300);

                dotsLayout.startAnimation(fadeOut);
                dotsLayout.setVisibility(View.INVISIBLE);
            }
        };

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dotsLayout.removeCallbacks(hideDots);

                dotsLayout.setVisibility(View.VISIBLE);

                dotsLayout.postDelayed(hideDots, 1000);
            }

            @Override
            public void onPageSelected(int position) {
                selectPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void selectPage(int index) {
        Resources res = getResources();
        for(int i = 0; i < NUM_PAGES; i++) {
            int drawableId = (i==index)?(R.drawable.page_selected):(R.drawable.page_unselected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }

    /* Info button expanded list items */
    public void displayListItemInfo(View view) {
        ImageButton btnListItemInfo = (ImageButton) view;
        String listItemTag = btnListItemInfo.getTag().toString();

        DialogFragmentInfoPanel panel = DialogFragmentInfoPanel.newInstance(listItemTag);
        panel.show(getSupportFragmentManager(),"InfoPanel");

        //Toast.makeText(getApplicationContext(), listItemTag, Toast.LENGTH_SHORT).show();
    }
}
