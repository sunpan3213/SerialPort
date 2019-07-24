package com.zkxl.mark.base_utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanUtil {
	public static int lan;// 1中文，2英文
	public static String KEY_LANGUAGE = "KEY_LANGUAGE";

	public static void init(Context context) {
		Resources res = context.getResources();
		Configuration cfg = res.getConfiguration();

		lan = SPUtils.INSTANCE.getInt(KEY_LANGUAGE);
		if (lan == -1) {// 无缓存
			if (Locale.SIMPLIFIED_CHINESE.equals(cfg.locale)) {// 系统是中文
				lan = 1;
			} else if (Locale.SIMPLIFIED_CHINESE.equals(cfg.locale)) {// 系统是英文
				lan = 2;
			} else {// 系统是其他语言
				cfg.locale = Locale.US;
				res.updateConfiguration(cfg, res.getDisplayMetrics());
				lan = 2;
			}
		} else if (lan == 1) {
			cfg.locale = Locale.SIMPLIFIED_CHINESE;
			res.updateConfiguration(cfg, res.getDisplayMetrics());
		} else if (lan == 2) {
			cfg.locale = Locale.US;
			res.updateConfiguration(cfg, res.getDisplayMetrics());
		}
	}

	public static void set(Context context, int code) {
		Resources res = context.getResources();
		Configuration cfg = res.getConfiguration();
		if (code == 1) {
			cfg.locale = Locale.SIMPLIFIED_CHINESE;
			res.updateConfiguration(cfg, res.getDisplayMetrics());
			SPUtils.INSTANCE.putInt(KEY_LANGUAGE, code);
			lan = code;
		} else if (code == 2) {
			cfg.locale = Locale.US;
			res.updateConfiguration(cfg, res.getDisplayMetrics());
			SPUtils.INSTANCE.putInt(KEY_LANGUAGE, code);
			lan = code;
		}
	}
}