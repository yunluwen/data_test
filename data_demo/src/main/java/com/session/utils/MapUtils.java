package com.session.utils;

import com.session.constants.Constants;

public class MapUtils {
    public static Integer getPosition(String key){
        Integer value = 0;
        if(Constants.TIME_PERIOD_1s_3s.equals(key)){
            value = 0;
        }else if(Constants.TIME_PERIOD_4s_6s.equals(key)){
            value = 1;
        }else if(Constants.TIME_PERIOD_7s_9s.equals(key)){
            value = 2;
        }else if(Constants.TIME_PERIOD_10s_30s.equals(key)){
            value = 3;
        }else if(Constants.TIME_PERIOD_30s_60s.equals(key)){
            value = 4;
        }else if(Constants.TIME_PERIOD_1m_3m.equals(key)){
            value = 5;
        }else if(Constants.TIME_PERIOD_3m_10m.equals(key)){
            value = 6;
        }else if(Constants.TIME_PERIOD_10m_30m.equals(key)){
            value = 7;
        }else if(Constants.TIME_PERIOD_30m.equals(key)){
            value = 8;
        }else{
            value = -1;
        }
        return value;
    }
}