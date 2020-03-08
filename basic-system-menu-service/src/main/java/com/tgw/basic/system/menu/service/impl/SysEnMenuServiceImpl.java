package com.tgw.basic.system.menu.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import com.tgw.basic.system.menu.dao.SysEnMenuMapper;
import com.tgw.basic.system.menu.model.SysEnMenu;
import com.tgw.basic.system.menu.service.SysEnMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysEnMenuService")
public class SysEnMenuServiceImpl extends BaseServiceImpl implements SysEnMenuService {

	@Resource
	private SysEnMenuMapper sysEnMenuMapper;

	@Override
	public void initMapper() {
		if( null!=sysEnMenuMapper ){
			super.setBaseModelMapper( this.getSysEnMenuMapper() );
		}
	}

	public List<Map<String, Object>> queryMenuTreeMap() throws PlatformException {
		return this.getSysEnMenuMapper().queryMenuTreeMap();
	}

	public List<SysEnMenu> loadMenuByRole(String roleId) {
		List result = this.getSysEnMenuMapper().loadMenuByRole( Long.parseLong(roleId) );
		return result;
	}



	public SysEnMenuMapper getSysEnMenuMapper() {
		return sysEnMenuMapper;
	}

	public void setSysEnMenuMapper(SysEnMenuMapper sysEnMenuMapper) {
		this.sysEnMenuMapper = sysEnMenuMapper;
	}

}
