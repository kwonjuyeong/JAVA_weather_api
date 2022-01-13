package com.example.final_exam1;

import java.util.HashMap;

public interface OnHTTPCommTaskCompleteListener {
    /**
     * HTTP 비동기 작업(AsyncTask)가 종료된 후 해당 함수가 호출됩니다.
     * @param map 해당 파라미터로 HTTP 통신 결과가 Map 형태로 제공됩니다.
     */
    void OnComplete(HashMap<String, WeatherData> map);
}
