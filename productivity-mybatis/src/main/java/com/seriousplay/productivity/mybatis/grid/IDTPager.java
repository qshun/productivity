package com.seriousplay.productivity.mybatis.grid;

import java.util.Map;

/**
 * @author changqingshun
 */
public interface IDTPager {
	/**
	 *
	 * @param namespace
	 * @param mapper
	 * @param paramMap
	 * @return
	 */
	<T> DTPageHelper<T> query(String namespace, String mapper, Map<String, Object> paramMap);
	/**
	 * 
	 * @param namespace
	 * @param mapper
	 * @param start
	 * @param length
	 * @return
	 */
	<T> DTPageHelper<T> query(String namespace, String mapper, int start, int length);

}
