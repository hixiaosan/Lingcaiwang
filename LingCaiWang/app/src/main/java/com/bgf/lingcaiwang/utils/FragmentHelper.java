package com.bgf.lingcaiwang.utils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment帮助类，提供常用fragment操作。
 */
public class FragmentHelper {

    public static final String TAG = "FragmentHelper";

    private static final String STATE_IDS = "fragment_ids";
    private static final String STATE_LAST_FRAGMENT_ID = "last_fragment_id";
    private static final String STATE_CURRENT_FRAGMENT_ID = "current_fragment_id";
    private static final String STATE_CURRENT_FRAGMENT = "current_fragment";

    private Context mContext;
    private FragmentManager mManager;
    private int mContainerId;

    private Map<String, SoftReference<Fragment>> mFragments = new HashMap<String, SoftReference<Fragment>>();
    private String mLastFragmentId = null;
    private String mCurrentFragmentId = null;
    private Fragment mCurrentFragment = null;

    public FragmentHelper(Context context, FragmentManager manager, int containerId) {
        mContext = context;
        mManager = manager;
        mContainerId = containerId;
    }

    public Fragment getFragment(String fragmentId) {
        Fragment fragment = null;
        SoftReference<Fragment> reference = mFragments.get(fragmentId);
        if (reference != null) {
            fragment = reference.get();
        }
        return fragment;
    }

    public String getCurrentFragmentId() {
        return mCurrentFragmentId;
    }

    public Fragment getCurrentFragment() {
        return getFragment(getCurrentFragmentId());
    }

    public void switchFragment(String id, Intent intent) {
        switchFragment(id, intent, -1);
    }

    public void switchFragment(String id, Intent intent, int transition) {
        try {
            Fragment newFragment = getFragment(id);
            if (mCurrentFragment == null || mCurrentFragment != newFragment) {
                FragmentTransaction ft = mManager.beginTransaction();
                if (mCurrentFragment != null) {
                    ft.detach(mCurrentFragment);
                }
                if (newFragment == null) {
                    if (intent != null) {
                        String fname = intent.getComponent().getClassName();
                        Bundle args = intent.getExtras();
                        newFragment = Fragment.instantiate(mContext, fname);
                        if (args != null) {
                            newFragment.setArguments(args);
                        }
                        mFragments.put(id, new SoftReference<Fragment>(newFragment));
                        ft.add(mContainerId, newFragment, id);
                    }
                } else {
                    ft.attach(newFragment);
                }
                if (newFragment == null) {
                    return;
                }
                if (transition != -1) {
                    ft.setTransition(transition);
                }
                mLastFragmentId = mCurrentFragmentId;
                mCurrentFragment = newFragment;
                mCurrentFragmentId = id;
                // ft.commit();
                ft.commitAllowingStateLoss();
                // mManager.executePendingTransactions();
            }
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 返回到上一个Fragment（只能回退一步）
     *
     * @return 是否跳转回去
     */
    public boolean back() {
        if (mLastFragmentId != null) {
            switchFragment(mLastFragmentId, null, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            mLastFragmentId = null;
            return true;
        }
        return false;
    }

    public void clearFragments() {
        if (mCurrentFragment != null) {
            mManager.beginTransaction().remove(mCurrentFragment).commit();
        }
        mFragments.clear();
        mCurrentFragmentId = null;
        mCurrentFragment = null;
    }

    /**
     * 在Bundle中保存状态
     *
     * @param bundle
     */
    public void saveState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        Logger.i(TAG, TAG + ": saving in bundle");
        ArrayList<String> idList = new ArrayList<String>(mFragments.keySet());
        bundle.putStringArrayList(STATE_IDS, idList);

        Iterator<Entry<String, SoftReference<Fragment>>> iter = mFragments.entrySet().iterator();
        Entry<String, SoftReference<Fragment>> entry;
        SoftReference<Fragment> reference;
        Fragment fragment;
        while (iter.hasNext()) {
            entry = iter.next();
            reference = entry.getValue();
            if (reference != null) {
                fragment = reference.get();
                if (fragment != null) {
                    mManager.putFragment(bundle, entry.getKey(), fragment);
                }
            }
        }

        if (mLastFragmentId != null) {
            bundle.putString(STATE_LAST_FRAGMENT_ID, mLastFragmentId);
        }

        if (mCurrentFragmentId != null) {
            bundle.putString(STATE_CURRENT_FRAGMENT_ID, mCurrentFragmentId);
        }

        if (mCurrentFragment != null) {
            mManager.putFragment(bundle, STATE_CURRENT_FRAGMENT, mCurrentFragment);
        }
    }

    /**
     * 在Bundle中保存状态，只保存当前的fragment
     *
     * @param bundle
     */
    public void saveStateSimple(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        Logger.i(TAG, TAG + ": saving in bundle simple");

        if (mCurrentFragmentId != null) {
            bundle.putString(STATE_CURRENT_FRAGMENT_ID, mCurrentFragmentId);

            ArrayList<String> idList = new ArrayList<String>(1);
            idList.add(mCurrentFragmentId);
            bundle.putStringArrayList(STATE_IDS, idList);

            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null) {
                mManager.putFragment(bundle, mCurrentFragmentId, currentFragment);
            }
        }

        if (mLastFragmentId != null) {
            bundle.putString(STATE_LAST_FRAGMENT_ID, mLastFragmentId);
        }

        if (mCurrentFragment != null) {
            mManager.putFragment(bundle, STATE_CURRENT_FRAGMENT, mCurrentFragment);
        }
    }

    /**
     * 从Bundle中恢复状态
     *
     * @param bundle
     */
    public void restoreFromBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        Logger.i(TAG, TAG + ": restoring from bundle");
        ArrayList<String> idList = bundle.getStringArrayList(STATE_IDS);
        if (idList != null) {
            Fragment fragment;
            for (String id : idList) {
                fragment = mManager.getFragment(bundle, id);
                if (fragment != null) {
                    mFragments.put(id, new SoftReference<Fragment>(fragment));
                }
            }
        }

        String lastId = bundle.getString(STATE_LAST_FRAGMENT_ID);
        if (lastId != null) {
            mLastFragmentId = lastId;
        }

        String currentId = bundle.getString(STATE_CURRENT_FRAGMENT_ID);
        if (currentId != null) {
            mCurrentFragmentId = currentId;
        }

        Fragment lastFragment = mManager.getFragment(bundle, STATE_CURRENT_FRAGMENT);
        if (lastFragment != null) {
            mCurrentFragment = lastFragment;
        }
    }
}
