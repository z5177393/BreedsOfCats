package com.cat.www;

public interface Callback {
    void onSucceedData(String result);
    void onFailed(int i, String error);
}
