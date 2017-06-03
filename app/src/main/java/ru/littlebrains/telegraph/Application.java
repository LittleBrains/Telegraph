package ru.littlebrains.telegraph;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by evgeniy on 22.08.2016.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}