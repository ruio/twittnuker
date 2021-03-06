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

package de.vanita5.twittnuker.model;

import android.os.Bundle;

public class SingleResponse<Data> {
	private final Exception exception;
	private final Data data;
	private final Bundle extras;

	public SingleResponse(final Data data, final Exception exception) {
		this(data, exception, null);
	}

	public SingleResponse(final Data data, final Exception exception, final Bundle extras) {
		this.data = data;
		this.exception = exception;
		this.extras = extras != null ? extras : new Bundle();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof SingleResponse)) return false;
		final SingleResponse<?> other = (SingleResponse<?>) obj;
		if (getData() == null) {
			if (other.getData() != null) return false;
		} else if (!getData().equals(other.getData())) return false;
		if (exception == null) {
			if (other.exception != null) return false;
		} else if (!exception.equals(other.exception)) return false;
		if (getExtras() == null) {
			if (other.getExtras() != null) return false;
		} else if (!getExtras().equals(other.getExtras())) return false;
		return true;
	}

	public Data getData() {
		return data;
	}

	public Exception getException() {
		return exception;
	}

	public Bundle getExtras() {
		return extras;
	}

	public boolean hasData() {
		return getData() != null;
	}

	public boolean hasException() {
		return exception != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getData() == null ? 0 : getData().hashCode());
		result = prime * result + (exception == null ? 0 : exception.hashCode());
		result = prime * result + (getExtras() == null ? 0 : getExtras().hashCode());
		return result;
	}

	public static <T> SingleResponse<T> getInstance() {
		return new SingleResponse<T>(null, null);
	}

	public static <T> SingleResponse<T> getInstance(final Exception exception) {
		return new SingleResponse<T>(null, exception);
	}

	public static <T> SingleResponse<T> getInstance(final T data) {
		return new SingleResponse<T>(data, null);
	}

	public static <T> SingleResponse<T> getInstance(final T data, final Exception exception) {
		return new SingleResponse<T>(data, exception);
	}
}
