package com.senthink.www.domain.po;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.common
 * @Description :  封装HashMap,用于直接接收和返回Request和Response
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 上午10:07
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class PageData extends HashMap implements Map, Serializable {

    private static final long serialVersionUID = 1L;

    Map map = null;
    HttpServletRequest request;

    /**
     * 直接获取request参数列表并转换为PageData数据类型
     *
     * @param request
     */
    public PageData(HttpServletRequest request) {

        this.request = request;
        Map properties = request.getParameterMap();

        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();

        Entry entry;

        //key and value
        String name = "";
        String value = "";

        while (entries.hasNext()) {

            entry = (Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();

            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {

                String[] values = (String[]) valueObj;

                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        map = returnMap;
    }

    //init
    public PageData() {
        map = new HashMap();
    }

    /**
     * 获取一个对象
     *
     * @param key
     * @return
     */
    @Override
    public Object get(Object key) {
        Object obj = null;
        if (map.get(key) instanceof Object[]) {
            Object[] arr = (Object[]) map.get(key);
            obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
        } else {
            obj = map.get(key);
        }
        return obj;
    }

    /**
     * 获取一个String
     *
     * @param key
     * @return
     */
    public String getString(Object key) {
        return (String) get(key);
    }

    /**
     * put to map
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    /**
     * remove from map
     *
     * @param key
     * @return
     */
    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    /**
     * clear map
     */
    public void clear() {
        map.clear();
    }

    /**
     * 是否包含某个对象key值
     *
     * @param key
     * @return
     */
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return map.containsKey(key);
    }

    /**
     * 是否包含某个对象value值
     *
     * @param value
     * @return
     */
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return map.containsValue(value);
    }

    public Set entrySet() {
        // TODO Auto-generated method stub
        return map.entrySet();
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return map.isEmpty();
    }

    public Set keySet() {
        // TODO Auto-generated method stub
        return map.keySet();
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map t) {
        // TODO Auto-generated method stub
        map.putAll(t);
    }

    public int size() {
        // TODO Auto-generated method stub
        return map.size();
    }

    public Collection values() {
        // TODO Auto-generated method stub
        return map.values();
    }

}
