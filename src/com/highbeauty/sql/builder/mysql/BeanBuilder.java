package com.highbeauty.sql.builder.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.highbeauty.AbstractBase;
import com.highbeauty.lang.StrEx;
import com.highbeauty.pinyin.PinYin;
import com.highbeauty.sql.spring.builder.SqlEx;
import com.highbeauty.util.MapEx;

public class BeanBuilder extends AbstractBase {

	public static void main(String[] args) throws Exception {
		String sql = "SELECT * FROM `�û���ɫ` LIMIT 1";
		String host = "127.0.0.1";
		String db = "fych";
		String bpackage = "fych.db";
		Connection conn = SqlEx.newMysqlConnection(host, db);

		ResultSet rs = SqlEx.executeQuery(conn, sql);

		String xml = build(conn, rs, bpackage);
		System.out.println(xml);

	}

	public static String build(Connection conn, ResultSet rs, String pkg) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		List<Map<String, Object>> columns = SqlEx.getColumns(rs);
		String catalogName = (String) columns.get(0).get("catalogName");
		String table = (String) columns.get(0).get("tableName");
		
		String tableEn = PinYin.getShortPinYin(table);
		String tableUEn = StrEx.upperFirst(tableEn);
		//ͨ����������ȷ��������
//		String primaryKey = BeanBuilder.primaryKey(rsmd, columns);
		String columns1 = columns1(rsmd, columns);
		String columns2 = columns2(rsmd, columns);
		String columns3 = BeanBuilder.columns3(rsmd, columns);
		String columns4 = BeanBuilder.columns4(rsmd, columns);
		String columns5 = BeanBuilder.columns5(rsmd, columns);
		String columns6 = BeanBuilder.columns6(rsmd, columns);
		String columns7 = BeanBuilder.columns7(rsmd, columns);
		String columns8 = BeanBuilder.columns8(rsmd, columns);
		String columns9 = BeanBuilder.columns9(rsmd, columns);
		List<Map> iks = SqlEx.getImportedKeys(conn, table);
		List<Map> eks = SqlEx.getExportedKeys(conn, table);
	
		
		
		List<Map> primaryKey_list =  SqlEx.getPrimaryKeys(conn,table);
		String primaryKey ="";
		String primaryKey2 ="";
		if (primaryKey_list.size()==1) {
			//���primaryKey_list�ĳ���Ϊһ
			primaryKey = primaryKey_list.get(0).get("COLUMN_NAME").toString();
		}
		if (primaryKey_list.size()==2) {
			primaryKey = primaryKey_list.get(0).get("COLUMN_NAME").toString();
			primaryKey2 = primaryKey_list.get(1).get("COLUMN_NAME").toString();
		}
		
		// log(catalogName, tableName, tableNameEn, tableNameUEn);
		// log(columns);

		StringBuffer sb = new StringBuffer();
		sn(sb, "package %s.bean;", pkg);
		sn(sb, "");
		sn(sb, "import java.io.*;");
		sn(sb, "import java.util.*;");
//		sn(sb, "import com.hs.SellMgr.db.bean;");
//		sn(sb, "import %s.entity.*;", pkg);
		sn(sb, "");
		sn(sb, "//%s - %s", catalogName, table);
		sn(sb, "@SuppressWarnings({\"rawtypes\",  \"unchecked\", \"serial\" })");
		sn(sb, "public class %s  implements Cloneable , Serializable{", tableUEn);
		sn(sb, "");
		sn(sb, "    public static String TABLENAME = \"%s\";", table);
		sn(sb, "");
		sn(sb, "    public static String[] carrays ={%s};", columns1);
		sn(sb, "");
		for (Map<String, Object> m : columns) {
			String columnName = MapEx.get(m, "columnName");
			String javaType = JavaType.getType(rsmd, columnName);
			sn(sb, "    public %s %s;", javaType, columnName);
		}
		sn(sb, "");
		sn(sb, "    public Map extension = new HashMap();");
		sn(sb, "");
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			String columnU = StrEx.upperFirst(column);
			String columnEn = PinYin.getShortPinYin(column);
			String columnUEn = StrEx.upperFirst(columnEn);
			String javaType = JavaType.getType(rsmd, column);
			sn(sb, "    public %s get%s(){", javaType, columnU);
			sn(sb, "        return %s;", column);
			sn(sb, "    }");
			sn(sb, "");
			sn(sb, "    public void set%s(%s %s){", columnU, javaType, columnEn);
			sn(sb, "        this.%s = %s;", column, columnEn);
			sn(sb, "    }");
			sn(sb, "");
			
			if(column.equals(columnEn))
				continue;
			
			sn(sb, "    public %s get%s(){", javaType, columnUEn);
			sn(sb, "        return this.%s;", column);
			sn(sb, "    }");
			sn(sb, "");
			sn(sb, "    public void set%s(%s %s){", columnUEn, javaType, columnEn);
			sn(sb, "        this.%s = %s;", column, columnEn);
			sn(sb, "    }");
			sn(sb, "");

			sn(sb, "    public Map put%s(Map map){", columnUEn);
			sn(sb, "        return put%s(map, \"%s\");", columnUEn, columnEn);
			sn(sb, "    }");
			sn(sb, "");

			sn(sb, "    public Map put%s(Map map, String key){", columnUEn);
			sn(sb, "        map.put(key, this.%s);", column);
			sn(sb, "        return map;");
			sn(sb, "    }");
			sn(sb, "");

			sn(sb, "    public Map put%s(Map map, int key){", columnUEn);
			sn(sb, "        map.put(key, this.%s);", column);
			sn(sb, "        return map;");
			sn(sb, "    }");
			sn(sb, "");

		}
		
		sn(sb, "    public static %s new%s(%s) {", tableUEn, tableUEn, columns2);
		sn(sb, "        %s ret = new %s();", tableUEn, tableUEn);
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			String columnEn = PinYin.getShortPinYin(column);
			String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			sn(sb, "        ret.set%s(%s); \t// %s", columnUEn, columnEn, column);
		}
		sn(sb, "        return ret;");
		sn(sb, "    }");
		sn(sb, "");
		
		
		sn(sb, "    @SuppressWarnings(\"unused\")");
		sn(sb, "    public static void get%s(%s %s){", tableUEn, tableUEn, tableEn);
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			String columnEn = PinYin.getShortPinYin(column);
			String columnUEn = StrEx.upperFirst(columnEn);
			 String javaType = JavaType.getType(rsmd, column);
			sn(sb, "        %s %s = %s.get%s(); \t// %s", javaType, columnEn, tableEn, columnUEn, column);
		}
		sn(sb, "    }");
		sn(sb, "");
		
		sn(sb, "    public Map toMap(){", tableUEn, tableUEn, tableEn);
		sn(sb, "        Map _ret = new HashMap();");
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			sn(sb, "        _ret.put(\"%s\", this.%s);", columnEn, column);
		}
		sn(sb, "        return _ret;");
		sn(sb, "    }");
		sn(sb, "");

		// ��������������
		for (Map<String, Object> m : iks) {
			String t = MapEx.get(m, "PKTABLE_NAME");
			String tUn = PinYin.getShortPinYin(t);
			String tUen = StrEx.upperFirst(tUn);
			String c = MapEx.get(m, "FKCOLUMN_NAME");
			String cUn = PinYin.getShortPinYin(c);
			String cUen = StrEx.upperFirst(cUn);
			sn(sb, "    public final %s get%sFk%s() {", tUen, tUen, cUen);
			sn(sb, "        return %sEntity.getByKey(%s);", tUen, c);
			sn(sb, "    }");
			sn(sb, "");
		}
		
		// ����������������
		for (Map m : eks) {
			String t = MapEx.get(m, "FKTABLE_NAME");
			String tUn = PinYin.getShortPinYin(t);
			String tUen = StrEx.upperFirst(tUn);
			String c = MapEx.get(m, "FKCOLUMN_NAME");
			String cUn = PinYin.getShortPinYin(c);
			String cUen = StrEx.upperFirst(cUn);
			String p = MapEx.get(m, "PKCOLUMN_NAME");
			Map<String, List<Map<String, Object>>> indexs = SqlEx.getIndexs(conn, t);

			if(isNonUnique(indexs, c)){
				sn(sb, "    public final %s get%sFk%s() {", tUen, tUen, cUen);
			}else{
				sn(sb, "    public final List<%s> get%ssFk%s() {", tUen, tUen, cUen);
			}
			sn(sb, "        return %sEntity.getBy%s(%s);", tUen, cUen, p);
			sn(sb, "    }");
			sn(sb, "");
		}

		/*sn(sb, "    public final %s insert() {", tableUEn);
		sn(sb, "        return this.insert(this);", tableUEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public final %s update() {", tableUEn);
		sn(sb, "        return %sEntity.update(this);", tableUEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public final int delete() {", tableUEn);
		sn(sb, "        return %sEntity.delete(%s);", tableUEn, primaryKey);
		sn(sb, "    }");
		sn(sb, "");
*/
		sn(sb, "    public Object clone() throws CloneNotSupportedException {");
		sn(sb, "        return super.clone();");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public %s clone2() {", tableUEn);
		sn(sb, "        try{");
		sn(sb, "            return (%s) this.clone();", tableUEn);
		sn(sb, "        } catch (Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "        }");
		sn(sb, "        return null;");
		sn(sb, "    }");
		sn(sb, "");
		//////////////////////////////////////
		/**
		 * ����
		 */
		sn(sb, "public  String getPrimaryKey(){");
		sn(sb, "return \""+primaryKey+"\";");
		sn(sb, "}");
		sn(sb, "");
		sn(sb, "public static String primaryKey ="+"\""+primaryKey+"\";");
		sn(sb, "");
		/**
		 * 2011-7-2 �����Ӧ��SQL���
		 */
		sn(sb, "//��� ɾ��  ��ѯ  �޸� ��SQL");
		sn(sb,"public static String insert_SQL = \"INSERT INTO \" + TABLENAME + \" (%s) VALUES (%s)\";", columns4, columns5);
		sn(sb,"public static String insert2_SQL = \"INSERT INTO \" + TABLENAME + \" (%s) VALUES (%s)\";", new Object[] {columns3, columns6});
		if (primaryKey2!="") {
			sn(sb,"public static String deleteByKey_SQL = \"DELETE FROM \" + TABLENAME + \" WHERE %s=:%s and %s=:%s\";", primaryKey,primaryKey,primaryKey2,primaryKey2);
			sn(sb,"public static String deleteList_SQL = \"DELETE FROM \" + TABLENAME + \" WHERE %s=? and %s=?\";", primaryKey,primaryKey2);
			sn(sb,"public static String selectLast_SQL = \"SELECT %s FROM \" + TABLENAME + \" ORDER BY %s,%s DESC LIMIT \";", columns3, primaryKey,primaryKey2);
			sn(sb,"public static String selectLastOne_SQL = \"SELECT %s FROM \" + TABLENAME + \" ORDER BY %s,%s DESC LIMIT 1\";", columns3, primaryKey,primaryKey2);
			sn(sb,"public static String selectAll_SQL = \"SELECT %s FROM \" + TABLENAME + \" ORDER BY %s,%s\";", columns3, primaryKey,primaryKey2);
			sn(sb,"public static String selectGtKey_SQL = \"SELECT %s FROM \" + TABLENAME + \" WHERE %s >= :%s and %s >= :%s  ORDER BY %s,%s\";", columns3, primaryKey, primaryKey,primaryKey2, primaryKey2, primaryKey,primaryKey2);
			sn(sb,"public static String selectByKey_SQL = \"SELECT %s FROM \" + TABLENAME + \" WHERE %s = :%s and %s = :%s\";", columns3, primaryKey, primaryKey, primaryKey2, primaryKey2);
			sn(sb,"public static String updateByKey_SQL = \"UPDATE \" + TABLENAME + \" SET %s WHERE %s=:%s and %s=:%s\";", columns8, primaryKey, primaryKey,primaryKey2, primaryKey2);
			sn(sb,"public static String updateList_SQL = \"UPDATE \" + TABLENAME + \" SET %s WHERE %s=? and %s=?\";", columns9, primaryKey, primaryKey2);
		}else {
			sn(sb,"public static String deleteByKey_SQL = \"DELETE FROM \" + TABLENAME + \" WHERE %s=:%s\";", primaryKey,primaryKey);
			sn(sb,"public static String deleteList_SQL = \"DELETE FROM \" + TABLENAME + \" WHERE %s=?\";", primaryKey);		
			sn(sb,"public static String selectLast_SQL = \"SELECT %s FROM \" + TABLENAME + \" ORDER BY %s DESC LIMIT \";", columns3, primaryKey);
			sn(sb,"public static String selectLastOne_SQL = \"SELECT %s FROM \" + TABLENAME + \" ORDER BY %s DESC LIMIT 1\";", columns3, primaryKey);
			sn(sb,"public static String selectAll_SQL = \"SELECT %s FROM \" + TABLENAME + \" ORDER BY %s\";", columns3, primaryKey);
			sn(sb,"public static String selectGtKey_SQL = \"SELECT %s FROM \" + TABLENAME + \" WHERE %s >= :%s ORDER BY %s\";", columns3, primaryKey, primaryKey, primaryKey);
			sn(sb,"public static String selectByKey_SQL = \"SELECT %s FROM \" + TABLENAME + \" WHERE %s = :%s\";", columns3, primaryKey, primaryKey);
			sn(sb,"public static String updateByKey_SQL = \"UPDATE \" + TABLENAME + \" SET %s WHERE %s=:%s\";", columns8, primaryKey, primaryKey);
			sn(sb,"public static String updateList_SQL = \"UPDATE \" + TABLENAME + \" SET %s WHERE %s=?\";", columns9, primaryKey);
		}
		sn(sb, "//���ǿ�����Ĳ�ѯSQL");
		sn(sb,"public static String selectByOther_SQL = \"SELECT %s FROM \" + TABLENAME + \" WHERE \";", columns3);
		sn(sb,"public static String countBySequence_SQL = \"SELECT COUNT(*) FROM \" + TABLENAME + \"\";");
		

		sn(sb, "");
		//////////////////////////////////////
		sn(sb, "}");
		

		return sb.toString();
	}
	
	
	
	
	
	
	public static String primaryKey(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		for (Map<String, Object> m : columns) {
			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			if(isAutoIncrement)
				return column;
		}
		return "";
	}

	
	// "id","����id","����"
	public static String columns1(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);

			s(sb, "\"%s\"", column);
			p ++;
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// Integer id, Integer csid, 
	public static String columns2(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			 String columnEn = PinYin.getShortPinYin(column);
//			 String columnUEn = StrEx.upperFirst(columnEn);
			 String javaType = JavaType.getType(rsmd, column);

			s(sb, "%s %s", javaType, columnEn);
			p ++;
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	
	// id, csid, 
	public static String columns3(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);

			s(sb, "%s", column);
			p ++;
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// csid, ....
	public static String columns4(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			p ++;
			if(isAutoIncrement)
				continue;
			s(sb, "%s", column);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// :csid, ....
	public static String columns5(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			p ++;
			if(isAutoIncrement)
				continue;
			s(sb, ":%s", column);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// :id, :csid, ....
	public static String columns6(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
//			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			p ++;
//			if(isAutoIncrement)
//				continue;
			s(sb, ":%s", column);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// ?, ....
	public static String columns7(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
//			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			p ++;
			if(isAutoIncrement)
				continue;
			s(sb, "?");
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// csid = :csid, ....
	public static String columns8(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			p ++;
			if(isAutoIncrement)
				continue;
			s(sb, "%s=:%s", column, column);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// csid = ?, ....
	public static String columns9(ResultSetMetaData rsmd, List<Map<String, Object>> columns) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = columns.size();
		int p = 0;
		for (Map<String, Object> m : columns) {
			boolean isAutoIncrement = MapEx.get(m, "isAutoIncrement");
			String column = MapEx.get(m, "columnName");
			// String columnU = StrEx.upperFirst(column);
			// String columnEn = PinYin.getShortPinYin(column);
			// String columnUEn = StrEx.upperFirst(columnEn);
			// String javaType = JavaType.getType(rsmd, column);
			p ++;
			if(isAutoIncrement)
				continue;
			s(sb, "%s=?", column);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// XYCsid..
	public static String index1(ResultSetMetaData rsmd, List<Map<String, Object>> index) throws Exception{
		StringBuffer sb = new StringBuffer();
//		int size = index.size();
		int p = 0;
		
		for (Map<String, Object> m : index) {
//			String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
			String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
//			String NON_UNIQUE = MapEx.get(m, "NON_UNIQUE");
			String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
			String COLUMN_NAME_UEN = StrEx.upperFirst(COLUMN_NAME_EN);
//			String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
			p ++;
			s(sb, "%s", COLUMN_NAME_UEN);
//			if(p < size)
//				s(sb, "And");
		}
		return sb.toString();
	}

	// Integer x, Integer y, ...
	public static String index2(ResultSetMetaData rsmd, List<Map<String, Object>> index) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = index.size();
		int p = 0;
		
		for (Map<String, Object> m : index) {
//			String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
			String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
//			String NON_UNIQUE = MapEx.get(m, "NON_UNIQUE");
			String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
//			String COLUMN_NAME_UEN = StrEx.upperFirst(COLUMN_NAME_EN);
			String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
			p ++;
			s(sb, "%s %s", COLUMN_NAME_TYPE, COLUMN_NAME_EN);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// x, y, ...
	public static String index3(ResultSetMetaData rsmd, List<Map<String, Object>> index) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = index.size();
		int p = 0;
		
		for (Map<String, Object> m : index) {
//			String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
			String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
//			String NON_UNIQUE = MapEx.get(m, "NON_UNIQUE");
			String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
//			String COLUMN_NAME_UEN = StrEx.upperFirst(COLUMN_NAME_EN);
//			String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
			p ++;
			s(sb, "%s", COLUMN_NAME_EN);
			if(p < size)
				s(sb, ", ");
		}
		return sb.toString();
	}

	// x=:x AND y=:y, ...
	public static String index4(ResultSetMetaData rsmd, List<Map<String, Object>> index) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = index.size();
		int p = 0;
		for (Map<String, Object> m : index) {
//			String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
			String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
//			String NON_UNIQUE = MapEx.get(m, "NON_UNIQUE");
//			String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
//			String COLUMN_NAME_UEN = StrEx.upperFirst(COLUMN_NAME_EN);
//			String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
			p ++;
			s(sb, "%s=:%s", COLUMN_NAME, COLUMN_NAME);
			if(p < size)
				s(sb, " AND ");
		}
		return sb.toString();
	}

	// x-y-...
	public static String index5(ResultSetMetaData rsmd, List<Map<String, Object>> index) throws Exception{
		StringBuffer sb = new StringBuffer();
		int size = index.size();
		int p = 0;
		for (Map<String, Object> m : index) {
//			String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
			String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
//			String NON_UNIQUE = MapEx.get(m, "NON_UNIQUE");
			String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
//			String COLUMN_NAME_UEN = StrEx.upperFirst(COLUMN_NAME_EN);
//			String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
			p ++;
			s(sb, "%s", COLUMN_NAME_EN);
			if(p < size)
				s(sb, "+\"-\"+");
		}
		return sb.toString();
	}

	
	public static boolean isNonUnique(Map<String, List<Map<String, Object>>> indexs, String columnName){
		Iterator<String> it = indexs.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			List<Map<String, Object>> idx = indexs.get(key);
			int idx_size = idx.size();
			if(idx_size == 1){ // ������
				Map<String, Object> index = idx.get(0);
				String INDEX_NAME = MapEx.get(index, "INDEX_NAME");
				String COLUMN_NAME = MapEx.get(index, "COLUMN_NAME");
				String NON_UNIQUE = MapEx.get(index, "NON_UNIQUE");
				if(INDEX_NAME.equals("PRIMARY"))
					continue;
				if(columnName.equals(COLUMN_NAME)){
					return (NON_UNIQUE.equals("false"));
				}
			}
		}
		return false;
	}
}
