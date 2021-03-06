package com.w3engineers.unicef.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.w3engineers.unicef.util.helper.WalletPrepareListener;
import com.w3engineers.walleter.wallet.Web3jWalletHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WalletUtilTest {
    private Context mContext;

    private WalletUtil walletUtil;

    @Before
    public void setup() {
        mContext = InstrumentationRegistry.getTargetContext();
        walletUtil = WalletUtil.getInstance(mContext);
    }

    @Test
    public void importWalletTest() {
        addDelay(500);

        walletUtil.importWallet(null, "", new WalletPrepareListener() {
            @Override
            public void onGetWalletInformation(String address, String publickKey) {

            }

            @Override
            public void onWalletLoadError(String errorMessage) {
                System.out.println("ResponseMessage: " + errorMessage);
                assertFalse(TextUtils.isEmpty(errorMessage));
            }
        });

        addDelay(5 * 1000);


        walletUtil.createWallet("", new WalletPrepareListener() {
            @Override
            public void onGetWalletInformation(String address, String publickKey) {

            }

            @Override
            public void onWalletLoadError(String errorMessage) {
                System.out.println("ResponseMessage: " + errorMessage);
                assertFalse(TextUtils.isEmpty(errorMessage));
            }
        });
        addDelay(5 * 1000);
    }

    private void addDelay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}