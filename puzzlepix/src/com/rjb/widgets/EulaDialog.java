package com.rjb.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjb.Enums.AppHandler;
import com.rjb.service.Logger;
import com.rjb.puzzlepix.R;

public class EulaDialog extends Dialog {
	private static final Logger logger = new Logger(EulaDialog.class);
	private final Handler handler;

	public EulaDialog(Context context, Handler handler) {
		super(context);
		this.handler = handler;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eula_dialog);
		initializeApplicationBranding();
		initializeEulaDescription();
		initializeAgreeButton();
		initializeExitButton();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
			handler.sendEmptyMessage(AppHandler.EULA_EXIT.ordinal());
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void initializeApplicationBranding() {

			// Show logo, hide text
			ImageView logoImageView = (ImageView) findViewById(R.id.eula_app_logo);
			logoImageView.setVisibility(View.VISIBLE);

			TextView applicationNameTextView = (TextView) findViewById(R.id.eula_app_name);
			applicationNameTextView.setVisibility(View.GONE);

	}

	private void initializeEulaDescription() {
		TextView description = (TextView) findViewById(R.id.eula_description);
		String descriptionText = (String) description.getText();
		descriptionText = descriptionText.replaceAll("-url-", getContext().getString(R.string.terms_and_conditions_url));
		description.setText(descriptionText);
	}

	private void initializeAgreeButton() {
		Button button = (Button) findViewById(R.id.eula_agree_button);
		button.setOnClickListener(new EulaOnClickListener(AppHandler.EULA_AGREE.ordinal()));
	}

	private void initializeExitButton() {
		Button button = (Button) findViewById(R.id.eula_exit_button);
		button.setOnClickListener(new EulaOnClickListener(AppHandler.EULA_EXIT.ordinal()));
	}

	private final class EulaOnClickListener implements android.view.View.OnClickListener {
		private final int what;

		public EulaOnClickListener(int what) {
			this.what = what;
		}

		@Override
		public void onClick(View v) {
			handler.sendEmptyMessage(what);
		}
	}
}
