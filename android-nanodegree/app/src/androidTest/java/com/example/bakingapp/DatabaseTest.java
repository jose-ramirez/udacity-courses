package com.example.bakingapp;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.provider.BakingAppProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;

import static org.junit.Assert.*;

/**
 * Created by jose on 25/07/17.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private Context context;

    @Before
    public void setUp(){
        this.context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void providerIsRegisteredCorrectly(){
        String packageName = context.getPackageName();
        String providerClassName = "com.example.bakingapp.generated.BakingAppProvider";
        ComponentName componentName = new ComponentName(packageName, providerClassName);

        try{
            PackageManager pm = context.getPackageManager();

            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = BakingAppProvider.AUTHORITY;

            assertEquals(actualAuthority, expectedAuthority);

        }catch (PackageManager.NameNotFoundException nnfe){
            fail(nnfe.getMessage());
        }
    }
}
