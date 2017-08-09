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

public class DaoBuilder extends AbstractBase {
	public static void main(String[] args) throws Exception {
		String sql = "SELECT * FROM `�û���ɫ` LIMIT 1";
		String host = "192.168.2.241";
		String db = "fych";
		String bpackage = "fych.db";
		Connection conn = SqlEx.newMysqlConnection(host, db);

		ResultSet rs = SqlEx.executeQuery(conn, sql);

		String xml = build(conn, rs, bpackage);
		System.out.println(xml);

	}

	public static String build(Connection conn, ResultSet rs, String pkg) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		List<Map<String, Object>> columns = SqlEx.getColumns(rs);
		String catalogName = (String) columns.get(0).get("catalogName");
		String table = (String) columns.get(0).get("tableName");
		String tableEn = PinYin.getShortPinYin(table);
		String tableUEn = StrEx.upperFirst(tableEn);
		Map<String, List<Map<String, Object>>> indexs = SqlEx.getIndexs(conn, table);
		String primaryKey = BeanBuilder.primaryKey(rsmd, columns);
		String primaryKeyType = JavaType.getType(rsmd, primaryKey);
		String columns1 = BeanBuilder.columns1(rsmd, columns);
//		String columns2 = BeanBuilder.columns2(rsmd, columns);
		String columns3 = BeanBuilder.columns3(rsmd, columns);
		String columns4 = BeanBuilder.columns4(rsmd, columns);
		String columns5 = BeanBuilder.columns5(rsmd, columns);
		String columns6 = BeanBuilder.columns6(rsmd, columns);
		String columns7 = BeanBuilder.columns7(rsmd, columns);
		String columns8 = BeanBuilder.columns8(rsmd, columns);
		String columns9 = BeanBuilder.columns9(rsmd, columns);
		
		String createSql = SqlEx.createMysqlTable(conn, rs, table);
		
		sn(sb, "package %s.dao;", pkg);
		sn(sb, "");
		sn(sb, "import java.util.*;");
		sn(sb, "import java.text.*;");
		sn(sb, "import java.sql.*;");
		sn(sb, "import org.springframework.jdbc.core.*;");
		sn(sb, "import org.springframework.jdbc.core.namedparam.*;");
		sn(sb, "import org.springframework.jdbc.support.*;");
		sn(sb, "import com.bowlong.text.*;");
		sn(sb, "import %s.bean.*;", pkg);
		sn(sb, "");
		sn(sb, "//%s - %s", catalogName, table);
		sn(sb, "@SuppressWarnings({\"rawtypes\", \"unchecked\"})");
		sn(sb, "public class %sDAO {", tableUEn);
		sn(sb, "    static final SimpleDateFormat sdfMm = new SimpleDateFormat(\"yyyyMM\");");
		sn(sb, "    static final SimpleDateFormat sdfDd = new SimpleDateFormat(\"yyyyMMdd\");");
		sn(sb, "");
		sn(sb, "    public NamedParameterJdbcTemplate _np;");
		sn(sb, "    public javax.sql.DataSource ds;");
		sn(sb, "");
		sn(sb, "    public static final String TABLE = \"%s\";", table);
		sn(sb, "    public static String TABLENAME = \"%s\";", table);
		sn(sb, "");
		sn(sb, "    public static String TABLEMM() {");
		sn(sb, "        return TABLE + sdfMm.format(new java.util.Date());");
		sn(sb, "    }");
		sn(sb, "");
		sn(sb, "    public static String TABLEDD() {");
		sn(sb, "        return TABLE + sdfDd.format(new java.util.Date());");
		sn(sb, "    }");
		sn(sb, "");
		sn(sb, "    public static String[] carrays ={%s};", columns1);
		sn(sb, "    public static String coulmns = \"%s\";", columns3);
		sn(sb, "    public static String coulmns2 = \"%s\";", columns4);
		sn(sb, "");

		sn(sb, "    public %sDAO(javax.sql.DataSource ds) {", tableUEn);
		sn(sb, "        this.ds = ds;");
		sn(sb, "        this._np = new NamedParameterJdbcTemplate(ds);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int insert(%s %s) {", tableUEn, tableEn);
		sn(sb, "        return insert(%s, TABLENAME);", tableEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int insert(%s %s, String TABLENAME2) {", tableUEn, tableEn);
		sn(sb, "        String sql;");
		sn(sb, "        try {");
		sn(sb, "            sql = \"INSERT INTO \" + TABLENAME2 + \" (%s) VALUES (%s)\";", columns4, columns5);
		sn(sb, "            SqlParameterSource ps = new BeanPropertySqlParameterSource(%s);", tableEn);
		sn(sb, "            KeyHolder keyholder = new GeneratedKeyHolder();");
		sn(sb, "            this._np.update(sql, ps, keyholder);");
		sn(sb, "            return keyholder.getKey().intValue();");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return 0;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int insert2(%s %s) {", tableUEn, tableEn);
		sn(sb, "        return insert2(%s, TABLENAME);", tableEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int insert2(%s %s, String TABLENAME2) {", tableUEn, tableEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"INSERT INTO \" + TABLENAME2 + \" (%s) VALUES (%s)\";", columns3, columns6);
		sn(sb, "            SqlParameterSource ps = new BeanPropertySqlParameterSource(%s);", tableEn);
		sn(sb, "            KeyHolder keyholder = new GeneratedKeyHolder();");
		sn(sb, "            this._np.update(sql, ps, keyholder);");
		sn(sb, "            return keyholder.getKey().intValue();");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return 0;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int[] insert(List<%s> %ss) {", tableUEn, tableEn);
		sn(sb, "        return insert(%ss, TABLENAME);", tableEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int[] insert(final List<%s> %ss, String TABLENAME2) {", tableUEn, tableEn);
		sn(sb, "        String sql;");
		sn(sb, "        try {");
		sn(sb, "            sql = \"INSERT INTO \" + TABLENAME2 + \" (%s) VALUES (%s)\";", columns4, columns7);
		sn(sb, "            return this._np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {");
		sn(sb, "                public int getBatchSize() {");
		sn(sb, "                    return %ss.size();", tableEn);
		sn(sb, "                }");
		sn(sb, "                public void setValues(PreparedStatement ps, int i) throws SQLException {");
		sn(sb, "                    %s %s = %ss.get(i);", tableUEn, tableEn, tableEn);
		int p = 0;
		for (Map<String, Object> m : columns) {
			String column = MapEx.get(m, "columnName");
			if(column.equals(primaryKey))
				continue;
			p ++;
//			String columnU = StrEx.upperFirst(column);
			String columnEn = PinYin.getShortPinYin(column);
			String columnUEn = StrEx.upperFirst(columnEn);
//			String javaType = JavaType.getType(rsmd, column);
			String setOp = BatchOP.setOP(rsmd, column);
			if(setOp.equals("setTimestamp")){
				sn(sb, "                    ps.%s(%d, new Timestamp(%s.get%s().getTime())); \t// %s", setOp, p, tableEn, columnUEn, column);
			}else{
				sn(sb, "                    ps.%s(%d, %s.get%s()); \t// %s ", setOp, p, tableEn, columnUEn, column);
			}
		}
		sn(sb, "                }");
		sn(sb, "            });");
		sn(sb, "         } catch (Exception e) {");
		sn(sb, "             e.printStackTrace();");
		sn(sb, "             return new int[0];");
		sn(sb, "         }");
		sn(sb, "");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int deleteByKey(%s %s) {", primaryKeyType, primaryKey);
		sn(sb, "        return deleteByKey(%s, TABLENAME);", primaryKey);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int deleteByKey(%s %s, String TABLENAME2) {", primaryKeyType, primaryKey);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"DELETE FROM \" + TABLENAME2 + \" WHERE %s=:%s\";", primaryKey, primaryKey);
		sn(sb, "            Map params = new HashMap();");
		sn(sb, "            params.put(\"%s\", %s);", primaryKey, primaryKey);
		sn(sb, "            return this._np.update(sql, params);");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return 0;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int[] deleteByKey(%s[] %ss) {", primaryKeyType, primaryKey);
		sn(sb, "        return deleteByKey(%ss, TABLENAME);", primaryKey);
		sn(sb, "    }");
		sn(sb, "");

		String setOP = BatchOP.setOP(rsmd, primaryKey);
		
		sn(sb, "    public int[] deleteByKey(final %s[] %ss, String TABLENAME2) {", primaryKeyType, primaryKey);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"DELETE FROM \" + TABLENAME2 + \" WHERE %s=?\";", primaryKey);
		sn(sb, "            return this._np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {");
		sn(sb, "                public int getBatchSize() {");
		sn(sb, "                    return %ss.length;", primaryKey);
		sn(sb, "                }");
		sn(sb, "                public void setValues(PreparedStatement ps, int i) throws SQLException {");
		sn(sb, "                    ps.%s(1, %ss[i]);", setOP, primaryKey);
		sn(sb, "                }");
		sn(sb, "            });");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new int[0];");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectAll() {", tableUEn);
		sn(sb, "        return selectAll(TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectAll(String TABLENAME2) {", tableUEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT %s FROM \" + TABLENAME2 + \" ORDER BY %s\";", columns3, primaryKey);
		sn(sb, "            return this._np.getJdbcOperations().query(sql, new BeanPropertyRowMapper(%s.class));", tableUEn);
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new Vector();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectLast(int num) {", tableUEn);
		sn(sb, "        return selectLast(num, TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectLast(int num, String TABLENAME2) {", tableUEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT %s FROM \" + TABLENAME2 + \" ORDER BY %s DESC LIMIT \" + num + \"\";", columns3, primaryKey);
		sn(sb, "            return this._np.getJdbcOperations().query(sql, new BeanPropertyRowMapper(%s.class));", tableUEn);
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new Vector();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public %s last() {", tableUEn);
		sn(sb, "        return last(TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public %s last(String TABLENAME2) {", tableUEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT %s FROM \" + TABLENAME2 + \" ORDER BY %s DESC LIMIT 1\";", columns3, primaryKey);
		sn(sb, "            Map params = new HashMap();");
		sn(sb, "            return (%s)this._np.queryForObject(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn, tableUEn);
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            // e.printStackTrace();");
		sn(sb, "            return null;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectGtKey(%s %s) {", tableUEn, primaryKeyType, primaryKey);
		sn(sb, "        return selectGtKey(%s, TABLENAME);", primaryKey);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectGtKey(%s %s, String TABLENAME2) {", tableUEn, primaryKeyType, primaryKey);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT %s FROM \" + TABLENAME2 + \" WHERE %s >= :%s ORDER BY %s\";", columns3, primaryKey, primaryKey, primaryKey);
		sn(sb, "            Map params = new HashMap();");
		sn(sb, "            params.put(\"%s\", %s);", primaryKey, primaryKey);
		sn(sb, "            return this._np.query(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn);
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new Vector();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public %s selectByKey(%s %s) {", tableUEn, primaryKeyType, primaryKey);
		sn(sb, "        return selectByKey(%s, TABLENAME);", primaryKey);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public %s selectByKey(%s %s, String TABLENAME2) {", tableUEn, primaryKeyType, primaryKey);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT %s FROM \" + TABLENAME2 + \" WHERE %s = :%s\";", columns3, primaryKey, primaryKey);
		sn(sb, "            Map params = new HashMap();");
		sn(sb, "            params.put(\"%s\", %s);", primaryKey, primaryKey);
		sn(sb, "            return (%s)this._np.queryForObject(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn, tableUEn);
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            // e.printStackTrace();");
		sn(sb, "            return null;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

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
				String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
				String COLUMN_NAME_UEN = StrEx.upperFirst(COLUMN_NAME_EN);
				String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
				if(INDEX_NAME.equals("PRIMARY"))
					continue;
				if(NON_UNIQUE.equals("false")){
					sn(sb, "    public %s selectBy%s(%s %s) {", tableUEn, COLUMN_NAME_UEN, COLUMN_NAME_TYPE, COLUMN_NAME_EN);
					sn(sb, "        return selectBy%s(%s, TABLENAME);", COLUMN_NAME_UEN, COLUMN_NAME_EN);
					sn(sb, "    }");
					sn(sb, "");

					sn(sb, "    public %s selectBy%s(%s %s, String TABLENAME2) {", tableUEn, COLUMN_NAME_UEN, COLUMN_NAME_TYPE, COLUMN_NAME_EN);
					sn(sb, "        String sql;");
					sn(sb, "        try{");
					sn(sb, "            sql=\"SELECT %s FROM \" + TABLENAME2 + \" WHERE %s = :%s\";", columns3, COLUMN_NAME, COLUMN_NAME);
					sn(sb, "            Map params = new HashMap();");
					sn(sb, "            params.put(\"%s\", %s);", COLUMN_NAME, COLUMN_NAME_EN);
					sn(sb, "            return (%s)this._np.queryForObject(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn, tableUEn);
					sn(sb, "        } catch(Exception e) {");
					sn(sb, "            // e.printStackTrace();");
					sn(sb, "            return null;");
					sn(sb, "        }");
					sn(sb, "    }");
					sn(sb, "");

				}else{
					sn(sb, "    public int countBy%s(%s %s) {", COLUMN_NAME_UEN, COLUMN_NAME_TYPE, COLUMN_NAME_EN);
					sn(sb, "        return  countBy%s(%s, TABLENAME);", COLUMN_NAME_UEN, COLUMN_NAME_EN);
					sn(sb, "    }");
					sn(sb, "");

					sn(sb, "    public int countBy%s(%s %s, String TABLENAME2) {", COLUMN_NAME_UEN, COLUMN_NAME_TYPE, COLUMN_NAME_EN);
					sn(sb, "        String sql;");
					sn(sb, "        try{");
					sn(sb, "            sql=\"SELECT COUNT(*) FROM \" + TABLENAME2 + \" WHERE %s = :%s \";", COLUMN_NAME, COLUMN_NAME);
					sn(sb, "            Map params = new HashMap();");
					sn(sb, "            params.put(\"%s\", %s);", COLUMN_NAME, COLUMN_NAME_EN);
					sn(sb, "            return this._np.getJdbcOperations().queryForInt(sql);");
					sn(sb, "        } catch(Exception e) {");
					sn(sb, "            e.printStackTrace();");
					sn(sb, "            return 0;");
					sn(sb, "        }");
					sn(sb, "    }");
					sn(sb, "");

					
					sn(sb, "    public List<%s> selectBy%s(%s %s) {", tableUEn, COLUMN_NAME_UEN, COLUMN_NAME_TYPE, COLUMN_NAME_EN);
					sn(sb, "        return selectBy%s(%s, TABLENAME);", COLUMN_NAME_UEN, COLUMN_NAME_EN);
					sn(sb, "    }");
					sn(sb, "");

					sn(sb, "    public List<%s> selectBy%s(%s %s, String TABLENAME2) {", tableUEn, COLUMN_NAME_UEN, COLUMN_NAME_TYPE, COLUMN_NAME_EN);
					sn(sb, "        String sql;");
					sn(sb, "        try{");
					sn(sb, "            sql=\"SELECT %s FROM \" + TABLENAME2 + \" WHERE %s = :%s ORDER BY %s \";", columns3, COLUMN_NAME, COLUMN_NAME, primaryKey);
					sn(sb, "            Map params = new HashMap();");
					sn(sb, "            params.put(\"%s\", %s);", COLUMN_NAME, COLUMN_NAME_EN);
					sn(sb, "            return this._np.query(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn);
					sn(sb, "        } catch(Exception e) {");
					sn(sb, "            e.printStackTrace();");
					sn(sb, "            return new Vector();");
					sn(sb, "        }");
					sn(sb, "    }");
					sn(sb, "");
				} 
			}else { // �������
				Map<String, Object> index = idx.get(0);
				String index1 = BeanBuilder.index1(rsmd, idx);
				String index2 = BeanBuilder.index2(rsmd, idx);
				String index3 = BeanBuilder.index3(rsmd, idx);
				String index4 = BeanBuilder.index4(rsmd, idx);
				String NON_UNIQUE = MapEx.get(index, "NON_UNIQUE");
				if(NON_UNIQUE.equals("false")){ // Ψһ���
					sn(sb, "    public %s selectBy%s(%s) {", tableUEn, index1, index2);
					sn(sb, "        return selectBy%s(%s, TABLENAME);", index1, index3);
					sn(sb, "    }");
					sn(sb, "");

					sn(sb, "    public %s selectBy%s(%s, String TABLENAME2) {", tableUEn, index1, index2);
					sn(sb, "        String sql;");
					sn(sb, "        try{");
					sn(sb, "            sql=\"SELECT %s FROM \" + TABLENAME2 + \" WHERE %s\";", columns3, index4);
					sn(sb, "            Map params = new HashMap();");
					for (Map<String, Object> m : idx) {
						//String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
						String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
						String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
						// String COLUMN_NAME_UEN =
						// StrEx.upperFirst(COLUMN_NAME_EN);
						// String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
						sn(sb, "            params.put(\"%s\", %s);", COLUMN_NAME, COLUMN_NAME_EN);
					}
					sn(sb, "            return (%s)this._np.queryForObject(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn, tableUEn);
					sn(sb, "        } catch(Exception e) {");
					sn(sb, "            // e.printStackTrace();");
					sn(sb, "            return null;");
					sn(sb, "        }");
					sn(sb, "    }");
					sn(sb, "");
				}else{ // ��Ψһ���
					sn(sb, "    public int countBy%s(%s) {", index1, index2);
					sn(sb, "        return  countBy%s(%s, TABLENAME);", index1, index3);
					sn(sb, "    }");
					sn(sb, "");

					sn(sb, "    public int countBy%s(%s, String TABLENAME2) {", index1, index2);
					sn(sb, "        String sql;");
					sn(sb, "        try{");
					sn(sb, "            sql=\"SELECT COUNT(*) FROM \" + TABLENAME2 + \" WHERE %s \";", index4);
					sn(sb, "            Map params = new HashMap();");
					for (Map<String, Object> m : idx) {
						//String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
						String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
						String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
						// String COLUMN_NAME_UEN =
						// StrEx.upperFirst(COLUMN_NAME_EN);
						// String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
						sn(sb, "            params.put(\"%s\", %s);", COLUMN_NAME, COLUMN_NAME_EN);
					}
					sn(sb, "            return this._np.getJdbcOperations().queryForInt(sql);");
					sn(sb, "        } catch(Exception e) {");
					sn(sb, "            e.printStackTrace();");
					sn(sb, "            return 0;");
					sn(sb, "        }");
					sn(sb, "    }");
					sn(sb, "");
					
					
					sn(sb, "    public List<%s> selectBy%s(%s) {", tableUEn, index1, index2);
					sn(sb, "        return selectBy%s(%s, TABLENAME);", index1, index3);
					sn(sb, "    }");
					sn(sb, "");

					sn(sb, "    public List<%s> selectBy%s(%s, String TABLENAME2) {", tableUEn, index1, index2);
					sn(sb, "        String sql;");
					sn(sb, "        try{");
					sn(sb, "            sql=\"SELECT %s FROM \" + TABLENAME2 + \" WHERE %s ORDER BY %s\";", columns3, index4, primaryKey);
					sn(sb, "            Map params = new HashMap();");
					for (Map<String, Object> m : idx) {
						//String INDEX_NAME = MapEx.get(m, "INDEX_NAME");
						String COLUMN_NAME = MapEx.get(m, "COLUMN_NAME");
						String COLUMN_NAME_EN = PinYin.getShortPinYin(COLUMN_NAME);
						// String COLUMN_NAME_UEN =
						// StrEx.upperFirst(COLUMN_NAME_EN);
						// String COLUMN_NAME_TYPE = JavaType.getType(rsmd, COLUMN_NAME);
						sn(sb, "            params.put(\"%s\", %s);", COLUMN_NAME, COLUMN_NAME_EN);
					}
					sn(sb, "            return this._np.query(sql, params, new BeanPropertyRowMapper(%s.class));", tableUEn);
					sn(sb, "        } catch(Exception e) {");
					sn(sb, "            e.printStackTrace();");
					sn(sb, "            return new Vector();");
					sn(sb, "        }");
					sn(sb, "    }");
					sn(sb, "");
					
				}
			}
			
		}
		
		sn(sb, "    public int count() {");
		sn(sb, "        return count(TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int count(String TABLENAME2) {");
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT COUNT(*) FROM \" + TABLENAME2 + \"\";");
		sn(sb, "            return this._np.getJdbcOperations().queryForInt(sql);");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return 0;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");
		
		sn(sb, "    public List<%s> selectByPage(int begin, int num) {", tableUEn);
		sn(sb, "        return selectByPage(begin, num, TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List<%s> selectByPage(int begin, int num, String TABLENAME2) {", tableUEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"SELECT %s FROM \" + TABLENAME2 + \" ORDER BY %s LIMIT \" + begin + \", \" + num + \"\";", columns3, primaryKey);
		sn(sb, "            return this._np.getJdbcOperations().query(sql,new BeanPropertyRowMapper(%s.class));", tableUEn);
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new Vector();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int updateByKey(%s %s) {", tableUEn, tableEn);
		sn(sb, "        return updateByKey(%s, TABLENAME);", tableEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int updateByKey(%s %s, String TABLENAME2) {", tableUEn, tableEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"UPDATE \" + TABLENAME2 + \" SET %s WHERE %s=:%s\";", columns8, primaryKey, primaryKey);
		sn(sb, "            SqlParameterSource ps = new BeanPropertySqlParameterSource(%s);", tableEn);
		sn(sb, "            return this._np.update(sql, ps);");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return 0;");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int[] updateByKey (final List<%s> %ss) {", tableUEn, tableEn);
		sn(sb, "        return updateByKey(%ss, TABLENAME);", tableEn);
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public int[] updateByKey (final List<%s> %ss, String TABLENAME2) {", tableUEn, tableEn);
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = \"UPDATE \" + TABLENAME2 + \" SET %s WHERE %s=?\";", columns9, primaryKey);
		sn(sb, "            return this._np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {");
		sn(sb, "                public int getBatchSize() {");
		sn(sb, "                    return %ss.size();", tableEn);
		sn(sb, "                }");
		sn(sb, "                public void setValues(PreparedStatement ps, int i) throws SQLException {");
		sn(sb, "                    %s %s = %ss.get(i);", tableUEn, tableEn, tableEn);
		p = 0;
		for (Map<String, Object> m : columns) {
			p ++;
			String column = MapEx.get(m, "columnName");
//			String columnU = StrEx.upperFirst(column);
			String columnEn = PinYin.getShortPinYin(column);
			String columnUEn = StrEx.upperFirst(columnEn);
//			String javaType = JavaType.getType(rsmd, column);
			String setOp = BatchOP.setOP(rsmd, column);
			if(setOp.equals("setTimestamp")){
				sn(sb, "                    ps.%s(%d, new Timestamp(%s.get%s().getTime())); \t// %s", setOp, p, tableEn, columnUEn, column);
			}else{
				sn(sb, "                    ps.%s(%d, %s.get%s()); \t// %s", setOp, p, tableEn, columnUEn, column);
			}
		}
		sn(sb, "                }");
		sn(sb, "            });");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new int[0];");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		
		String[] ss = createSql.split("\n");
		StringBuffer createSql2 = new StringBuffer();
		p = 0;
		for (String s : ss) {
			if(p > 0)
				s(createSql2, "                \"%s\"", s);
			else
				s(createSql2, "\"%s\"", s);
			p ++;
			if(p < ss.length){
				sn(createSql2, " +");
			}
		}

		sn(sb, "    public void createTable(String TABLENAME2){");
		sn(sb, "        String sql;");
		sn(sb, "        try{");
		sn(sb, "            sql = %s;\r\n", createSql2.toString());
		sn(sb, "            Map params = new HashMap();");
		sn(sb, "            params.put(\"TABLENAME\", TABLENAME2);");
		sn(sb, "            sql  = EasyTemplate.make(sql, params);");
		sn(sb, "            this.execute(sql);");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public void truncate(){");
		sn(sb, "        truncate(TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public void truncate(String TABLENAME2){");
		sn(sb, "        String sql=\"TRUNCATE TABLE \" + TABLENAME2 + \"\";");
		sn(sb, "        this.execute(sql);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public void repair(){");
		sn(sb, "        repair(TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public void repair(String TABLENAME2){");
		sn(sb, "        String sql=\"REPAIR TABLE \" + TABLENAME2 + \"\";");
		sn(sb, "        this.execute(sql);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public void optimize(){");
		sn(sb, "        optimize(TABLENAME);");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public void optimize(String TABLENAME2){");
		sn(sb, "        String sql=\"OPTIMIZE TABLE \" + TABLENAME2 + \"\";");
		sn(sb, "        this.execute(sql);");
		sn(sb, "    }");
		sn(sb, "");


		sn(sb, "    public void execute(String sql){");
		sn(sb, "        try{");
		sn(sb, "            this._np.getJdbcOperations().execute(sql);");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public List executeQuery(String sql, Class clazz){");
		sn(sb, "        try{");
		sn(sb, "            return this._np.getJdbcOperations().query(sql, new BeanPropertyRowMapper(clazz));");
		sn(sb, "        } catch(Exception e) {");
		sn(sb, "            e.printStackTrace();");
		sn(sb, "            return new Vector();");
		sn(sb, "        }");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "}");

		
		return sb.toString();
	}
}
