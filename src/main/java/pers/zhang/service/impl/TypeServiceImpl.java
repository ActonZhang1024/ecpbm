package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.zhang.dao.TypeDao;
import pers.zhang.pojo.Type;
import pers.zhang.service.TypeService;

import java.util.List;

/**
 * @author zhang
 * @date 2019/12/21 - 16:42
 */
@Service("typeSercice")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeDao typeDao;

    @Override
    public List<Type> getAll() {
        return typeDao.selectAll();
    }

    @Override
    public int addType(Type type) {
        return typeDao.add(type);
    }

    @Override
    public void updateType(Type type) {
        typeDao.update(type);
    }
}
