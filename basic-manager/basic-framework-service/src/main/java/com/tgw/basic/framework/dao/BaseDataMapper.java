package com.tgw.basic.framework.dao;

import java.util.List;
import java.util.Map;


public interface BaseDataMapper<T>  {

	int insert(T t);

	int update(T t);

	int delete(Map<String, Object> params);

	List<T> list(Map<String, Object> params);

	long count(Map<String, Object> params);
	
	enum OrderMethod {

		ASC("asc") , DESC("desc");

		private String name;

		OrderMethod(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}
	
	class Order{
		
		private String propertry;
		
		private String method;
		
		private String column;
		
		public Order(String propertry, OrderMethod method) {
			this.propertry = propertry;
			char[] arr = propertry.toCharArray();
			StringBuilder parseColumn = new StringBuilder();
			for (char c : arr) {
				if (Character.isUpperCase(c)) {
					parseColumn.append("_");
					c = Character.toLowerCase(c);
				}
				parseColumn.append(c);
			}
			this.column = parseColumn.toString();
			this.method = method.toString();
		}

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public String getPropertry() {
			return propertry;
		}

		public void setPropertry(String propertry) {
			this.propertry = propertry;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}
		
	}
	
}
