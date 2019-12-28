package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.zhang.dao.AdminInfoDao;
import pers.zhang.pojo.AdminInfo;
import pers.zhang.service.AdminInfoService;

/**
 * @author zhang
 * @date 2019/12/21 - 16:25
 */
@Service("adminInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AdminInfosServiceImpl implements AdminInfoService {

    @Autowired
    AdminInfoDao adminInfoDao;

    @Override
    public AdminInfo login(AdminInfo ai) {
        return adminInfoDao.selectByNameAndPwd(ai);
    }

    @Override
    public AdminInfo getAdmininfoAndFunctions(Integer id) {
        return adminInfoDao.selectById(id);
    }
}
