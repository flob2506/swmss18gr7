package com.group.tube.networking;

public interface AsyncResponse<T> {
    void processFinish(T response);
}
