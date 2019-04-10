package com.tcontacts.utils;

import android.text.TextUtils;
import android.util.Log;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxUtils {
	private static final String TAG = RxUtils.class.getSimpleName();
	
	public static void safeDispose(Disposable disposable) {
		if (disposable != null && !disposable.isDisposed()) {
			disposable.dispose();
		}
	}
	
	public static Consumer<Throwable> getEmptyErrorConsumer(String className, String methodName) {
		return throwable -> {
			String tag = TextUtils.isEmpty(className) ? TAG : "ClassName : " + className;
			String errorMsg = (TextUtils.isEmpty(methodName) ? "" : "MethodName : " + methodName + "\n") +
					"Error : " +
					throwable.toString();
			Log.e(tag, errorMsg);
		};
	}
}