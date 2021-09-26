package com.eliottdup.gettalents.adapter.profile;

import android.content.Context;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.address.AddressesFragment;
import com.eliottdup.gettalents.ui.review.ReviewListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyProfilePagerAdapter extends FragmentStatePagerAdapter {
    private final Context context;
    private final boolean isEditable;

    public MyProfilePagerAdapter(@NonNull FragmentManager fm, int behavior, Context context, boolean isEditable) {
        super(fm, behavior);

        this.context = context;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                return ReviewListFragment.newInstance(user.getUid());
            case 1:
                return AddressesFragment.newInstance(isEditable);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_review);
            case 1:
                return context.getString(R.string.title_addresses);
            default:
                return null;
        }
    }


}
