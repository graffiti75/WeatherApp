package com.example.rodrigo.weatherapp.view.activity.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.ActivityTestDatabaseBinding;
import com.example.rodrigo.weatherapp.view.fragment.LoadingDialogDatabaseFragment;

/**
 * TestDatabaseActivity.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public class TestDatabaseActivity extends AppCompatActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private ActivityTestDatabaseBinding mBinding;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test_database);

		LoadingDialogDatabaseFragment fragment = new LoadingDialogDatabaseFragment();
		fragment.setCancelable(false);
		fragment.show(getSupportFragmentManager(), LoadingDialogDatabaseFragment.TAG);
	}

	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void onLoadingFinished() {
		mBinding.idActivityTestDatabaseTextView.setText(R.string.dialog_ok);
	}
}