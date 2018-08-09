package com.seriousplay.productivity.mybatis.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author changqingshun
 */
public class DTQueryVO {
	private List<DTColumn> columns;
	private int draw;
	private int start;
	private int length;
	private List<DTOrder> order;
	private DTSearch search;
	private Map<String, Object> extraParams;
	public List<DTColumn> getColumns() {
		return columns;
	}

	public int getDraw() {
		return draw;
	}

	public Map<String, Object> getExtraParams() {
		return extraParams;
	}

	public int getLength() {
		return length;
	}

	public List<DTOrder> getOrder() {
		return order;
	}

	public Map<String,Object> getParamMap(){
		Map<String,Object>ret=new HashMap<String, Object>();
		for(DTColumn c:this.columns){
			if(c.isSearchable()&&c.getSearch().getValue()!=null && c.getSearch().getValue().length()>0){
				ret.put(c.getData(), c.getSearch().getValue());
			}
		}
		if(this.search.getValue().length()>0){
			ret.put("key", "%"+this.search.getValue()+"%");
		}
		ret.put("_start_", this.getStart());
		ret.put("_length_", this.getLength());
		if(this.extraParams!=null&&extraParams.size()>0){
			ret.putAll(extraParams);
		}
		return ret;
	}

	public DTSearch getSearch() {
		return search;
	}

	public int getStart() {
		return start;
	}

	public void setColumns(List<DTColumn> columns) {
		this.columns = columns;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public void setExtraParams(Map<String, Object> extraParams) {
		this.extraParams = extraParams;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setOrder(List<DTOrder> order) {
		this.order = order;
	}

	public void setSearch(DTSearch search) {
		this.search = search;
	}
	public void setStart(int start) {
		this.start = start;
	}
}
