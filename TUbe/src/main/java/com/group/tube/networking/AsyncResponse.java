package com.group.tube.networking;

import org.json.JSONArray;

public interface AsyncResponse<T> {
    void processFinish(T response);
}
