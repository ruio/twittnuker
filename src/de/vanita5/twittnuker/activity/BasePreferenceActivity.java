/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2014 vanita5 <mail@vanita5.de>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2014 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.vanita5.twittnuker.activity;

import android.app.ActionBar;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.activity.iface.IThemedActivity;
import de.vanita5.twittnuker.content.res.NoAccentResources;
import de.vanita5.twittnuker.menu.TwidereMenuInflater;
import de.vanita5.twittnuker.util.ThemeUtils;
import de.vanita5.twittnuker.util.theme.TwidereResourceHelper;

import static de.vanita5.twittnuker.util.Utils.restartActivity;

public abstract class BasePreferenceActivity extends PreferenceActivity implements Constants,
        IThemedActivity, TwidereResourceHelper.OnInitListener {

	private TwidereResourceHelper mResourceHelper;
    private int mCurrentThemeResource;
	private Theme mTheme;
	private TwidereMenuInflater mMenuInflater;

	@Override
	public boolean onCreateOptionsMenu(Menu menu, TwidereMenuInflater inflater) {
		return false;
	}

	@Override
	public final boolean onCreateOptionsMenu(Menu menu) {
		return onCreateOptionsMenu(menu, getTwidereMenuInflater());
	}

	@Override
	public TwidereMenuInflater getTwidereMenuInflater() {
		if (mMenuInflater != null) return mMenuInflater;
		final ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			return mMenuInflater = new TwidereMenuInflater(actionBar.getThemedContext());
		}
		return mMenuInflater = new TwidereMenuInflater(this);
	}

	@Override
	public void finish() {
		super.finish();
		overrideCloseAnimationIfNeeded();
	}

	@Override
    public int getCurrentThemeResourceId() {
        return mCurrentThemeResource;
    }

    @Override
    public Resources getDefaultResources() {
        return super.getResources();
    }

    @Override
	public Theme getTheme() {
		if (mTheme == null) {
			mTheme = getResources().newTheme();
			mTheme.setTo(super.getTheme());
			final int getThemeResourceId = getThemeResourceId();
			if (getThemeResourceId != 0) {
				mTheme.applyStyle(getThemeResourceId, true);
			}
		}
		return mTheme;
	}

    @Override
    public int getThemeBackgroundAlpha() {
        return 0;
    }

    @Override
    public int getOverrideAccentColor() {
        return 0;
    }

	@Override
    public Resources getResources() {
		if (mResourceHelper == null) {
            mResourceHelper = new TwidereResourceHelper(getThemeResourceId(), this);
		}
		return mResourceHelper.getResources(this, super.getResources());
	}

    @Override
    public String getThemeFontFamily() {
        return VALUE_THEME_FONT_FAMILY_REGULAR;
    }

    @Override
    public int getThemeResourceId() {
        return ThemeUtils.getSettingsThemeResource(this);
    }

	@Override
	public boolean isDarkDrawerEnabled() {
		return false;
	}

    @Override
	public void navigateUpFromSameTask() {
		NavUtils.navigateUpFromSameTask(this);
		overrideCloseAnimationIfNeeded();
	}

	@Override
	public void overrideCloseAnimationIfNeeded() {
		if (shouldOverrideActivityAnimation()) {
			ThemeUtils.overrideActivityCloseAnimation(this);
		} else {
			ThemeUtils.overrideNormalActivityCloseAnimation(this);
		}
	}

    @Override
	public final void restart() {
		restartActivity(this);
	}

	@Override
	public boolean shouldOverrideActivityAnimation() {
		return true;
	}

	protected final boolean isThemeChanged() {
		return getThemeResourceId() != mCurrentThemeResource;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		if (shouldOverrideActivityAnimation()) {
			ThemeUtils.overrideActivityOpenAnimation(this);
		}
		ThemeUtils.notifyStatusBarColorChanged(this, mCurrentThemeResource, 0, 0xFF);
        setTheme(mCurrentThemeResource = getThemeResourceId());
		super.onCreate(savedInstanceState);
		setActionBarBackground();
	}

    @Override
    protected void onResume() {
        super.onResume();
        if (isThemeChanged()) {
            restart();
		} else {
			ThemeUtils.notifyStatusBarColorChanged(this, mCurrentThemeResource, 0, 0xFF);
        }
	}

	private final void setActionBarBackground() {
		// ThemeUtils.applyActionBarBackground(getActionBar(), this,
		// mCurrentThemeResource);
	}

    @Override
    public void onInitResources(NoAccentResources resources) {
        ThemeUtils.initResourceInterceptors(this, resources);
    }
}