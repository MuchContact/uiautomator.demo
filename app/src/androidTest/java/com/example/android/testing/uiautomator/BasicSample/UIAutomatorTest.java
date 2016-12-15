/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.uiautomator.BasicSample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Basic sample for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIAutomatorTest {
    private static final String ESHOP_SAMPLE_PACKAGE = "cn.com.egova.mopad";
    private static final String CLASS_RADIO_BUTTON = "android.widget.RadioButton";
    private static final int LAUNCH_TIMEOUT = 20000;
    private static final long UI_TIMEOUT = 10000;

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(ESHOP_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(ESHOP_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void navigateToMonitorPage() throws Exception {

        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "config_btnInstall"))
                .click();
        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "serverConfigURL"))
                .setText("http://192.168.32.179:8080/wudongde/");

        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "config_btnSubmit")).click();

        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "login_btnSubmit"))
                .click();
        BySelector firstAccountSelector = By.clazz(CLASS_RADIO_BUTTON);
        if (mDevice.wait(Until.hasObject(firstAccountSelector), UI_TIMEOUT)) {
            mDevice.findObjects(firstAccountSelector).get(1).click();
        }

        mDevice.findObjects(firstAccountSelector).get(3).click();

        BySelector config_item_exit_rlt = By.res(ESHOP_SAMPLE_PACKAGE, "config_item_exit_rlt");
        if (mDevice.wait(Until.hasObject(config_item_exit_rlt), UI_TIMEOUT)) {
            mDevice.findObject(config_item_exit_rlt)
                    .click();

        }
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
