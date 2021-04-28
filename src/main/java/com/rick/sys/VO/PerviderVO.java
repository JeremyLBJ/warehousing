package com.rick.sys.VO;

import com.rick.sys.entity.BusProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PerviderVO extends BusProvider {

    private Integer page = 1;

    private Integer limit = 10;
}
