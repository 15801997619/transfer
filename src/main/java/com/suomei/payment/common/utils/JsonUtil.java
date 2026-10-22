/**  
 * @Title: JsonUtil.java
 * @Package com.newland.java.common.util
 * @Description JSON转换工具类
 * @author Joakim
 * @date 2015年8月31日 上午9:28:17
 * @version V1.0  
 */
package com.suomei.payment.common.utils;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static Gson gson = new Gson();


	/**
	 *
	 * @Title toJson
	 * @Description 转换实体对象为JSON字符串
	 * @param obj 泛型对象
	 * @return
	 */
	public static <T> String toJson(T obj) {
//		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 *
	 * @Title fromJson
	 * @Description json字符串转对象
	 * @param jsonStr
	 * @param clz
	 * @return
	 */
	public static <T> T fromJson(String jsonStr, Class<T> clz) {
//		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clz);
	}

	/**
	 *
	 * @Title fromJson
	 * @Description 转换复杂类型对象
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String jsonStr, Type type) {
//		Gson gson = new Gson();
		return gson.fromJson(jsonStr, type);
	}

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
		Object obj = null;
		try {
			if (map == null)
				return null;
			obj = beanClass.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return obj;
	}

	public static Map<?, ?> objectToMap(Object obj) {
		if(obj == null)
			return null;
		return new org.apache.commons.beanutils.BeanMap(obj);
	}

	/**
	 * json key 首字母小写
	 * @param o1
	 * @return
	 */
	public static JSONObject transObject(JSONObject o1){
		JSONObject o2=new JSONObject();
		Iterator it = o1.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object object = o1.get(key);
			String keyStr = key.substring(0, 1).toLowerCase() + key.substring(1);
			if(object.getClass().toString().endsWith("String")){
				o2.accumulate(keyStr, object);
			}else if(object.getClass().toString().endsWith("JSONObject")){
				o2.accumulate(keyStr, transObject((JSONObject)object));
			}else if(object.getClass().toString().endsWith("JSONArray")){
				o2.accumulate(keyStr, transArray(o1.getJSONArray(key)));
			}
		}
		return o2;
	}

	public static JSONArray transArray(JSONArray o1){
		JSONArray o2 = new JSONArray();
		for (int i = 0; i < o1.size(); i++) {
			Object jArray=o1.getJSONObject(i);
			if(jArray.getClass().toString().endsWith("JSONObject")){
				o2.add(transObject((JSONObject)jArray));
			}else if(jArray.getClass().toString().endsWith("JSONArray")){
				o2.add(transArray((JSONArray)jArray));
			}
		}
		return o2;
	}

	public static com.alibaba.fastjson.JSONObject removeNulls(com.alibaba.fastjson.JSONObject jsonObject) {
		com.alibaba.fastjson.JSONObject object2 = jsonObject.clone();
		Set<String> keySet = jsonObject.keySet();
		for (String key : keySet) {
			Object object = jsonObject.get(key);
			if(object == null){
				object2.remove(key);
			}
		}
		return object2;
	}

}
