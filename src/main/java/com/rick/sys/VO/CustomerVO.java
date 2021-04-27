package com.rick.sys.VO;

import com.rick.sys.entity.BusCustomer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class CustomerVO extends BusCustomer {

    private Integer page = 1;

    private Integer limit = 10;

    private Integer [] ids;
}
