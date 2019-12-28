package pers.zhang.service;

import pers.zhang.pojo.AdminInfo;

/**
 * @author zhang
 * @date 2019/12/21 - 16:05
 */
public interface AdminInfoService {
    //登录验证
    public AdminInfo login(AdminInfo ai);

    //根据管理员编号，获取管理员对象及关联的功能权限
    public AdminInfo getAdmininfoAndFunctions(Integer id);
}
