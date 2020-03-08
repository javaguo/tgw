package com.tgw.basic.system.function.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import com.tgw.basic.system.function.dao.SysEnFunctionMapper;
import com.tgw.basic.system.function.service.SysEnFunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysEnFunctionService")
public class SysEnFunctionServiceImpl extends BaseServiceImpl implements SysEnFunctionService {

	@Resource
	private SysEnFunctionMapper sysEnFunctionMapper;

	@Override
	public void initMapper() {
		if( null!=sysEnFunctionMapper ){
			super.setBaseModelMapper( this.getSysEnFunctionMapper() );
		}
	}

	public List<Map<String, Object>> queryFunctionTreeMap() throws PlatformException {
		return this.getSysEnFunctionMapper().queryFunctionTreeMap();
	}

	public List<Map<String, Object>> queryMenuTreeMap() throws PlatformException {
		return this.getSysEnFunctionMapper().queryMenuTreeMap();
	}

	public SysEnFunctionMapper getSysEnFunctionMapper() {
		return sysEnFunctionMapper;
	}

	public void setSysEnFunctionMapper(SysEnFunctionMapper sysEnFunctionMapper) {
		this.sysEnFunctionMapper = sysEnFunctionMapper;
	}
}
