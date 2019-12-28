package pers.zhang.service;

import pers.zhang.pojo.Pager;
import pers.zhang.pojo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 16:02
 */
public interface UserInfoService {
    //获取合法用户
    public List<UserInfo> getValidUser();

    //根据客户编号查询客户
    public UserInfo getUserInfoById(int id);

    //分页显示用户
    public List<UserInfo> findUserInfo(UserInfo userInfo, Pager pager);

    //客户计数
   public Integer count(Map<String, Object> params);

    //修改指定编号的用户状态
    public void modifyStatus(String ids, int flag);
}
