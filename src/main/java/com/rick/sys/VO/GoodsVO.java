package com.rick.sys.VO;

import com.rick.sys.entity.BusGoods;
import com.rick.sys.entity.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsVO extends BusGoods {

    private Integer page = 1;

    private Integer limit = 10;
}
