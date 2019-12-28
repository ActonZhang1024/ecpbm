package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.zhang.dao.UserInfoDao;
import pers.zhang.pojo.Pager;
import pers.zhang.pojo.UserInfo;
import pers.zhang.service.UserInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 16:18
 */
@Service("userInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public List<UserInfo> getValidUser() {
        return userInfoDao.getValidUser();
    }

    @Override
    public UserInfo getUserInfoById(int id) {
        return userInfoDao.getUserInfoById(id);
    }

    @Override
    public List<UserInfo> findUserInfo(UserInfo userInfo, Pager pager) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userInfo",userInfo);
        int recordCount = userInfoDao.count(params);
        pager.setRowCount(recordCount);
        if(recordCount > 0){
            params.put("pager", pager);
        }
        return userInfoDao.selectByPage(params);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return userInfoDao.count(params);
    }

    @Override
    public void modifyStatus(String ids, int flag) {
        userInfoDao.updateState(ids, flag);
    }
}
