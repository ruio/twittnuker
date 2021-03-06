package de.vanita5.twittnuker.fragment.support;

import android.content.Context;
import android.view.Menu;

import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.menu.TwidereMenuInflater;
import de.vanita5.twittnuker.model.Account;
import de.vanita5.twittnuker.model.Account.AccountWithCredentials;
import de.vanita5.twittnuker.model.ParcelableUser;

public class IncomingFriendshipsMenuDialogFragment extends UserMenuDialogFragment {

    @Override
    protected void onPrepareItemMenu(final Menu menu, final ParcelableUser user) {
		final Context context = getThemedContext();
		final AccountWithCredentials account = Account.getAccountWithCredentials(context, user.account_id);
		if (AccountWithCredentials.isOfficialCredentials(context, account)) {
			final TwidereMenuInflater inflater = new TwidereMenuInflater(context);
            inflater.inflate(R.menu.action_incoming_friendship, menu);
        }
    }

}