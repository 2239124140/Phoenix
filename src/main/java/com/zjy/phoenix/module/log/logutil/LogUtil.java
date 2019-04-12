package com.zjy.phoenix.module.log.logutil;

import com.alibaba.fastjson.JSON;

import com.zjy.phoenix.common.util.StrKit;
import com.zjy.phoenix.config.spring.SpringContextHolder;
import com.zjy.phoenix.module.log.enums.LogType;
import com.zjy.phoenix.module.log.model.Log;
import com.zjy.phoenix.module.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 操作日志工具类
 * @author zxl
 */
public class LogUtil {
	
	private LogUtil(){};  //私有化构造方法
	
	private final static Logger log = LoggerFactory.getLogger(LogUtil.class);
	private static LogService logService = SpringContextHolder.getBean(LogService.class);

	public static void saveMeetingLog(LogType type, Long modelId, Long uid, String desc, Object object) {
		saveLog(type, "mt_meeting", "会议", modelId, uid, desc, object);
	}
	
	//保存门户导航日志
	public static void savePortalNavigationLog(LogType type, Long modelId, Long uid, String desc, Object object) {
		saveLog(type, "portal_navigation", "门户导航管理", modelId, uid, desc, object);
	}

	private static void saveLog(LogType type, String tableName, String module, Long modelId, Long uid, String desc, Object object) {
		try {
			String paras = JSON.toJSONString(object);
			Log log = new Log();
			log.setModelId(modelId);
			log.setTableName(tableName);
			log.setModule(module);
			log.setDesc(desc);
			log.setType(type.getValue());
			log.setParas(paras);
			log.setUid(uid);
			logService.insert(log);
		} catch (Exception e) {
			log.warn("保存日志出现异常");
			log.warn(StrKit.printException(e));
		}
	}

}
