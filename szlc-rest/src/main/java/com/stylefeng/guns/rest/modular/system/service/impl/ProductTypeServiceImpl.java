package com.stylefeng.guns.rest.modular.system.service.impl;

import com.stylefeng.guns.rest.modular.system.model.ProductType;
import com.stylefeng.guns.rest.modular.system.dao.ProductTypeMapper;
import com.stylefeng.guns.rest.modular.system.service.IProductTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品类型 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-18
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

}
