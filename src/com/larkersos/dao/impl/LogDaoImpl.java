package com.larkersos.dao.impl;


import org.springframework.stereotype.Repository;

import com.larkersos.dao.LogDao;
import com.larkersos.entity.Log;

/**
 * Dao实现类 - 日志
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log, String> implements LogDao {

}
