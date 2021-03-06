package com.w3engineers.unicef.telemesh.ui.createuser;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.w3engineers.ext.strom.application.ui.base.BaseActivity;
import com.w3engineers.ext.strom.util.helper.Toaster;
import com.w3engineers.mesh.util.DialogUtil;
import com.w3engineers.mesh.util.lib.mesh.HandlerUtil;
import com.w3engineers.unicef.telemesh.R;
import com.w3engineers.unicef.telemesh.data.helper.constants.Constants;
import com.w3engineers.unicef.telemesh.data.provider.ServiceLocator;
import com.w3engineers.unicef.telemesh.databinding.ActivityCreateUserBinding;
import com.w3engineers.unicef.telemesh.ui.chooseprofileimage.ProfileImageActivity;
import com.w3engineers.unicef.telemesh.ui.importwallet.ImportWalletActivity;
import com.w3engineers.unicef.telemesh.ui.main.MainActivity;
import com.w3engineers.unicef.telemesh.ui.security.SecurityActivity;
import com.w3engineers.unicef.util.helper.CommonUtil;
import com.w3engineers.unicef.util.helper.CustomDialogUtil;
import com.w3engineers.unicef.util.helper.DexterPermissionHelper;
import com.w3engineers.unicef.util.helper.WalletAddressHelper;
import com.w3engineers.unicef.util.helper.uiutil.UIHelper;
import com.w3engineers.walleter.wallet.WalletService;

import java.util.List;

/*
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * ============================================================================
 */

public class CreateUserActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCreateUserBinding mBinding;
    private int PROFILE_IMAGE_REQUEST = 1;
    public static int INITIAL_IMAGE_INDEX = -1;
    private CreateUserViewModel mViewModel;

    public static CreateUserActivity sInstance;
    private boolean isLoadAccount;

    private String mPassword;

    @NonNull
    public static String IMAGE_POSITION = "image_position";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_user;
    }

    @Override
    protected int statusBarColor() {
        return R.color.colorPrimaryDark;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startUI() {

        mBinding = (ActivityCreateUserBinding) getViewDataBinding();
        setTitle(getString(R.string.create_user));
        mViewModel = getViewModel();

        setClickListener(mBinding.imageViewBack);

        parseIntent();

        UIHelper.hideKeyboardFrom(this, mBinding.editTextName);

        mBinding.imageViewCamera.setOnClickListener(this);
        mBinding.buttonSignup.setOnClickListener(this);
        mBinding.imageProfile.setOnClickListener(this);

        mBinding.editTextName.setMaxCharacters(Constants.DefaultValue.MAXIMUM_TEXT_LIMIT);
        mBinding.editTextName.setMinCharacters(Constants.DefaultValue.MINIMUM_TEXT_LIMIT);

        mViewModel.textChangeLiveData.observe(this, this::nextButtonControl);
        mViewModel.textEditControl(mBinding.editTextName);

        sInstance = this;

        mBinding.editTextName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                nextAction();
                return true;
            }
            return false;
        });
    }

    private void nextButtonControl(String nameText) {
        if (!TextUtils.isEmpty(nameText) &&
                nameText.length() >= Constants.DefaultValue.MINIMUM_TEXT_LIMIT) {

            mBinding.buttonSignup.setBackgroundResource(R.drawable.ractangular_gradient);
            mBinding.buttonSignup.setTextColor(getResources().getColor(R.color.white));
            //mBinding.buttonSignup.setClickable(true);
        } else {

            mBinding.buttonSignup.setBackgroundResource(R.drawable.ractangular_white);
            mBinding.buttonSignup.setTextColor(getResources().getColor(R.color.new_user_button_color));
            //mBinding.buttonSignup.setClickable(false);
        }
    }

    private CreateUserViewModel getViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) ServiceLocator.getInstance().getCreateUserViewModel(getApplication());
            }
        }).get(CreateUserViewModel.class);
    }

    protected void requestMultiplePermissions() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            goNext();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                           CommonUtil.showPermissionPopUp(CreateUserActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> requestMultiplePermissions()).onSameThread().check();
    }

    @Override
    public void onClick(@NonNull View view) {
        super.onClick(view);

        int id = view.getId();

        switch (id) {
            case R.id.button_signup:
                nextAction();
                break;
            case R.id.image_profile:
            case R.id.image_view_camera:
                Intent intent = new Intent(this, ProfileImageActivity.class);
                intent.putExtra(CreateUserActivity.IMAGE_POSITION, mViewModel.getImageIndex());
                startActivityForResult(intent, PROFILE_IMAGE_REQUEST);
                break;
            case R.id.image_view_back:
                finish();
                break;

        }
    }

    private void nextAction() {
        if (isLoadAccount) {
            saveData();
        } else {
            goToPasswordPage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == PROFILE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            mViewModel.setImageIndex(data.getIntExtra(IMAGE_POSITION, INITIAL_IMAGE_INDEX));

            int id = getResources().getIdentifier(Constants.drawables.AVATAR_IMAGE + mViewModel.getImageIndex(), Constants.drawables.AVATAR_DRAWABLE_DIRECTORY, getPackageName());
            mBinding.imageProfile.setImageResource(id);

            nextButtonControl(mBinding.editTextName.getText().toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sInstance = null;
    }

    private void saveData() {
        if (CommonUtil.isValidName(mBinding.editTextName.getText().toString(), this)) {
            requestMultiplePermissions();
        }
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.IntentKeys.PASSWORD)) {
            isLoadAccount = true;
            mPassword = intent.getStringExtra(Constants.IntentKeys.PASSWORD);
        } else {
            if (WalletService.getInstance(this).isWalletExists()) {
                showWarningDialog();
            }
        }
    }

    protected void goNext() {
        if (mViewModel.storeData(mBinding.editTextName.getText() + "", mPassword)) {

            Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void goToPasswordPage() {

        if (CommonUtil.isValidName(mBinding.editTextName.getText().toString(), this)) {
            Intent intent = new Intent(CreateUserActivity.this, SecurityActivity.class);
            intent.putExtra(Constants.IntentKeys.USER_NAME, mBinding.editTextName.getText() + "");
            intent.putExtra(Constants.IntentKeys.AVATAR_INDEX, mViewModel.getImageIndex());
            startActivity(intent);
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(Html.fromHtml("<b>" + getString(R.string.alert_title_text) + "</b>"));
        builder.setMessage(WalletAddressHelper.getWalletSpannableString(this).toString());
        builder.setPositiveButton(Html.fromHtml("<b>"  + getString(R.string.button_postivive) + "<b>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                startActivity(new Intent(CreateUserActivity.this, ImportWalletActivity.class));
                finish();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<b>" + getString(R.string.negative_button) + "<b>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        builder.create();
        builder.show();
    }
}
