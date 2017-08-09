package com.jxh.AutoCoder;

import com.highbeauty.sql.spring.builder.*;


public class WradioAuto {
	public static void main(String[] args) throws Throwable {
		boolean src = false;
		boolean is_maven = false;
		String moduleName="";//当没有的时候就不加入到路径中去
		String pkg = "com.park.";
		/*String[] tablenames = {"user_info","car_in_out","fault_record","park_coupon","park_device","park_info","pay_park","rental_charging_rule","user_moneyback","user_park_coupon","user_login_log",
				"sms_running","sms_validate","user_feedback","user_carcode","pay_month_park","china_area","park_heartbeat","intimes_pay","user_vc_act","park_userinfo","parkinfo_partner","user_cash_apply"};*/
		String[] tablenames = {"user_info"};
		String ip = "127.0.0.1";
		int port = 3306;
		String user = "root";
		String password = "root";
		String databaseName = "stopcar";
		ABuilder.AutoCoder(is_maven,src,moduleName, pkg, tablenames, ip, port, user, password, databaseName);
	}

}
