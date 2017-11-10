package com.hrawat.projectvision;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.FontRequestEmojiCompatConfig;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v4.provider.FontRequest;
import android.util.Log;

/**
 * Created by hrawat on 11/10/2017.
 */
public class ProjectVision extends Application {

    private final String TAG = this.getClass().getSimpleName();
    /**
     * Change this to {@code false} when you want to use the downloadable Emoji font.
     */
    private static final boolean USE_BUNDLED_EMOJI = true;

    @Override
    public void onCreate() {
        super.onCreate();
        final EmojiCompat.Config config;
        if (USE_BUNDLED_EMOJI) {
            // Use the bundled font for EmojiCompat
            config = new BundledEmojiCompatConfig(getApplicationContext());
        } else {
            // Use a downloadable font for EmojiCompat
            final FontRequest fontRequest = new FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    "Noto Color Emoji Compat",
                    R.array.com_google_android_gms_fonts_certs);
            config = new FontRequestEmojiCompatConfig(getApplicationContext(), fontRequest)
                    .setReplaceAll(true)
                    .registerInitCallback(new EmojiCompat.InitCallback() {
                        @Override
                        public void onInitialized() {
                            Log.i(TAG, "EmojiCompat initialized");
                        }

                        @Override
                        public void onFailed(@Nullable Throwable throwable) {
                            Log.e(TAG, "EmojiCompat initialization failed", throwable);
                        }
                    });
        }
        EmojiCompat.init(config);
    }
}
