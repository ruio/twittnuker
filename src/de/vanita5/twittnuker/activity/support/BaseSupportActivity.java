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

package de.vanita5.twittnuker.activity.support;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.app.TwittnukerApplication;
import de.vanita5.twittnuker.fragment.iface.IBasePullToRefreshFragment;
import de.vanita5.twittnuker.util.AsyncTwitterWrapper;
import de.vanita5.twittnuker.util.MessagesManager;
import de.vanita5.twittnuker.util.ThemeUtils;

@SuppressLint("Registered")
public class BaseSupportActivity extends BaseSupportThemedActivity implements Constants {

	private boolean mInstanceStateSaved, mIsVisible, mIsOnTop;

	public MessagesManager getMessagesManager() {
		return getTwittnukerApplication() != null ? getTwittnukerApplication().getMessagesManager() : null;
	}

    @Override
    public int getOverrideAccentColor() {
		return ThemeUtils.getUserThemeColor(this, getThemeResourceId());
    }

    @Override
    public int getThemeResourceId() {
        return ThemeUtils.getThemeResource(this);
    }

	public TwittnukerApplication getTwittnukerApplication() {
		return (TwittnukerApplication) getApplication();
	}

	public AsyncTwitterWrapper getTwitterWrapper() {
		return getTwittnukerApplication() != null ? getTwittnukerApplication().getTwitterWrapper() : null;
	}

	public boolean isOnTop() {
		return mIsOnTop;
	}

	public boolean isVisible() {
		return mIsVisible;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case MENU_BACK: {
				onBackPressed();
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void startActivity(final Intent intent) {
		super.startActivity(intent);
	}

	@Override
	public void startActivityForResult(final Intent intent, final int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	protected IBasePullToRefreshFragment getCurrentPullToRefreshFragment() {
		return null;
	}

	protected boolean isStateSaved() {
		return mInstanceStateSaved;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		mIsOnTop = false;
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mInstanceStateSaved = false;
		mIsOnTop = true;
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		mInstanceStateSaved = true;
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mIsVisible = true;
		final MessagesManager manager = getMessagesManager();
		if (manager != null) {
			manager.addMessageCallback(this);
		}
	}

	@Override
	protected void onStop() {
		mIsVisible = false;
		final MessagesManager manager = getMessagesManager();
		if (manager != null) {
			manager.removeMessageCallback(this);
		}
		super.onStop();
	}

}
