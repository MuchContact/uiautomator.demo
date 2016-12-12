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

/**
 * Basic sample for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIAutomatorTest {
    private static final String ESHOP_SAMPLE_PACKAGE = "cn.com.egova.mopad";
    private static final String CLASS_RADIO_BUTTON = "android.widget.RadioButton";
    private static final long UI_TIMEOUT = 10000;

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void navigateToMonitorPage() throws Exception {

        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "config_btnInstall"))
                .click();
        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "serverConfigURL"))
                .setText("http://192.168.32.179:8080/wudongde/");

        mDevice.findObject(By.res(ESHOP_SAMPLE_PACKAGE, "config_btnSubmit"))
                .click();
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
}
