package com.okcat.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.util
 * @Description :  字符串辅助类
 * @Creation Date:  2018-10-10 下午3:35
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class StringUtil {
    /**
     * @param list
     * @param divider
     * @param dataAdapter
     * @return
     */
    public static String iterableJoin(Iterable<?> list, String divider, DataAdapter dataAdapter) {
        StringBuffer buffer = new StringBuffer();
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            buffer.append(dataAdapter.adapter(it.next()));
            buffer.append(divider);
        }
        int bufferSize = buffer.length();
        if (buffer.length() > 1) {
            buffer = buffer.deleteCharAt(bufferSize - 1);
        }
        return buffer.toString();
    }


    public interface DataAdapter<K, V> {
        V adapter(K src);
    }




    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (isNull(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 拼接map
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null ){
                return "";
            }
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        String substring = s.substring(0, s.length() - 1);
        return substring;
    }


    /**
     * 判断字符串是否为null
     *
     * @param s
     * @return true null, false 非null
     */
    public static boolean isNull(String s) {
        if (null == s || "".equals(s))
            return true;

        return false;
    }

}
