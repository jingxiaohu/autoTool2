package com.highbeauty.sql.spring.builder;

import com.highbeauty.pinyin.PinYin;

public class NewDaoFactoryBuilder {

	public String build(String[] tablenames, String pkg) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (pkg != null && pkg.length() > 0) {
			sb.append("package " + pkg + ";");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		sb.append("\r\n");
		sb.append("import org.springframework.stereotype.Repository;");
		sb.append("\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;");
		sb.append("\r\n");
		sb.append("\r\n");
		// class
		sb.append("//DAO Factory\r\n");
		sb.append("\r\n");
		sb.append("@Repository(\"daoFactory\")");
		sb.append("\r\n");
		sb.append("public class ");
		sb.append("DaoFactory");
		sb.append("{\r\n");
		sb.append("\r\n");

		// 遍历所有的表--处理拼装注解
		for (String tableName : tablenames) {
			String DaoName = StrEx.upperFirst(PinYin.getShortPinYin(tableName))
					+ "Dao";
			String dao_quote = PinYin.getShortPinYin(tableName) + "Dao";
			// 这里拼装注解
			sb.append("@Autowired");
			sb.append("\r\n");
			sb.append("protected " + DaoName + " " + dao_quote + ";");
			sb.append("\r\n");
		}
		sb.append("\r\n");
		sb.append("/*******************************下面是GET方法**************************************/");
		sb.append("\r\n");
		// 遍历所有的表--处理拼装GET方法
		for (String tableName : tablenames) {
			String DaoName = StrEx.upperFirst(PinYin.getShortPinYin(tableName))
					+ "Dao";
			String dao_quote = PinYin.getShortPinYin(tableName) + "Dao";
			// 这里拼装GET方法
			sb.append("public " + DaoName + " get" + DaoName+ "() {");
			sb.append("\r\n");
			sb.append("	return " + dao_quote + ";");
			sb.append("\r\n");
			sb.append("}");
			sb.append("\r\n");

		}
		
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("}");
		sb.append("\r\n");
		return sb.toString();
	}

}
