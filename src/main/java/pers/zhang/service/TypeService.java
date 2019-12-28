package pers.zhang.service;

import pers.zhang.pojo.Type;

import java.util.List;

/**
 * @author zhang
 * @date 2019/12/21 - 16:14
 */
public interface TypeService {
    //获取所有商品类型
    public List<Type> getAll();

    //添加商品类型
    public int addType(Type type);

    //更新商品类型
    public void updateType(Type type);
}
