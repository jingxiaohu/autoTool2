package com.highbeauty.sql.builder.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.highbeauty.AbstractBase;
import com.highbeauty.lang.StrEx;
import com.highbeauty.pinyin.PinYin;
import com.highbeauty.sql.spring.builder.SqlEx;

public class EntityBuilder extends AbstractBase {

	public static void main(String[] args) throws Exception {
		String sql = "SELECT * FROM `�û���ɫ` LIMIT 1";
		String host = "192.168.2.241";
		String db = "fych";
		String bpackage = "fych.db";
		String appContext = "fych.context.AppContext";
		Connection conn = SqlEx.newMysqlConnection(host, db);

		ResultSet rs = SqlEx.executeQuery(conn, sql);

		String xml = build(conn, rs, bpackage, appContext);
		System.out.println(xml);

	}

	public static String build(Connection conn, ResultSet rs, String pkg, String appContext) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		System.out.println("-----------------------");
//		ResultSetMetaData rsmd = rs.getMetaData();
		List<Map<String, Object>> columns = SqlEx.getColumns(rs);
		String catalogName = (String) columns.get(0).get("catalogName");
		String table = (String) columns.get(0).get("tableName");
		String tableEn = PinYin.getShortPinYin(table);
		String tableUEn = StrEx.upperFirst(tableEn);

		// import
		if (pkg != null && pkg.length() > 0) {
			sb.append("package " + pkg + ".entity;");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		sn(sb, "import java.util.*;");
		sn(sb, "import com.bowlong.sql.*;");
		sn(sb, "import com.bowlong.lang.*;");
		sn(sb, "import org.springframework.jdbc.core.*;");
		sn(sb, "import org.apache.commons.logging.*;");
		sn(sb, "import org.springframework.jdbc.core.namedparam.*;");
		sn(sb, "import org.springframework.jdbc.support.*;");
		sn(sb, "import %s.bean.*;", pkg);
		sn(sb, "import %s.dao.*;",pkg);
		sn(sb, "import %s.internal.*;", pkg);
		sn(sb, "import %s;", appContext);
		sn(sb, "");

		// class
		sn(sb, "//%s - %s", catalogName, table);
		sn(sb, "@SuppressWarnings({ \"unchecked\", \"unused\", \"static-access\"})");
		sn(sb, "public class %sEntity extends %sInternal{", tableUEn, tableUEn);
		sn(sb, "    static Log log = LogFactory.getLog(%sEntity.class);", tableUEn);
		sn(sb, "");
		sn(sb, "    public static final %sEntity my = new %sEntity();", tableUEn, tableUEn);
		sn(sb, "");

		sn(sb, "    public static long TIMEOUT(){");
		sn(sb, "        return 0;");
		sn(sb, "    }");
		sn(sb, "");
		
		sn(sb, "    public static void insertDdTry(final %s %s) {", tableUEn, tableEn);
		sn(sb, "        SqlEx.execute4Fixed(new Runnable() {");
		sn(sb, "            public void run() {");
		sn(sb, "                %sDAO DAO = AppContext.%sDAO();", tableUEn, tableUEn);
		sn(sb, "                String TABLENAME2 = DAO.TABLEDD();");
		sn(sb, "                try {");
		sn(sb, "                    int n = DAO.insert(%s, TABLENAME2);", tableEn);
		sn(sb, "                    if(n <= 0){");
		sn(sb, "                        createTable(DAO, TABLENAME2);");
		sn(sb, "                        log.info(\"new table:\" + TABLENAME2);");
		sn(sb, "                        DAO.insert(%s, TABLENAME2);", tableEn);
		sn(sb, "                    }");
		sn(sb, "                } catch (Exception e) {");
		sn(sb, "                    log.info(e);");
		sn(sb, "                }");
		sn(sb, "            }");
		sn(sb, "        });");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public static void insertDdTry(final List<%s> %ss) {", tableUEn, tableEn);
		sn(sb, "        insert(%ss);", tableEn);
		sn(sb, "        SqlEx.execute4Fixed(new Runnable() {");
		sn(sb, "            public void run() {");
		sn(sb, "                %sDAO DAO = AppContext.%sDAO();", tableUEn, tableUEn);
		sn(sb, "                String TABLENAME2 = DAO.TABLEDD();");
		sn(sb, "                boolean b = SqlEx.isTableExist(DAO.ds, TABLENAME2);");
		sn(sb, "                if(!b) {");
		sn(sb, "                    log.info(\"new table:\" + TABLENAME2);");
		sn(sb, "                    DAO.createTable(TABLENAME2);");
		sn(sb, "                }");
		sn(sb, "                DAO.insert(%ss, TABLENAME2);", tableEn);
		sn(sb, "            }");
		sn(sb, "        });");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public static void insertMmTry(final %s %s) {", tableUEn, tableEn);
		sn(sb, "        SqlEx.execute4Fixed(new Runnable() {");
		sn(sb, "            public void run() {");
		sn(sb, "                %sDAO DAO = AppContext.%sDAO();", tableUEn, tableUEn);
		sn(sb, "                String TABLENAME2 = DAO.TABLEMM();");
		sn(sb, "                try {");
		sn(sb, "                    int n = DAO.insert(%s, TABLENAME2);", tableEn);
		sn(sb, "                    if(n <= 0){");
		sn(sb, "                        createTable(DAO, TABLENAME2);");
		sn(sb, "                        log.info(\"new table:\" + TABLENAME2);");
		sn(sb, "                        DAO.insert(%s, TABLENAME2);", tableEn);
		sn(sb, "                    }");
		sn(sb, "                } catch (Exception e) {");
		sn(sb, "                    log.info(e);");
		sn(sb, "                }");
		sn(sb, "            }");
		sn(sb, "        });");
		sn(sb, "    }");
		sn(sb, "");

		sn(sb, "    public static void insertMmTry(final List<%s> %ss) {", tableUEn, tableEn);
		sn(sb, "        insert(%ss);", tableEn);
		sn(sb, "        SqlEx.execute4Fixed(new Runnable() {");
		sn(sb, "            public void run() {");
		sn(sb, "                %sDAO DAO = AppContext.%sDAO();", tableUEn, tableUEn);
		sn(sb, "                String TABLENAME2 = DAO.TABLEMM();");
		sn(sb, "                boolean b = SqlEx.isTableExist(DAO.ds, TABLENAME2);");
		sn(sb, "                if(!b) {");
		sn(sb, "                    log.info(\"new table:\" + TABLENAME2);");
		sn(sb, "                    DAO.createTable(TABLENAME2);");
		sn(sb, "                }");
		sn(sb, "                DAO.insert(%ss, TABLENAME2);", tableEn);
		sn(sb, "            }");
		sn(sb, "        });");
		sn(sb, "    }");
		sn(sb, "");
		
		sn(sb, "}");
		sn(sb, "");

		return sb.toString();
	}
}
