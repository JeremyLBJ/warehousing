package com.rick.sys.VO;

import com.rick.sys.entity.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MenuVO extends SysPermission {

    private Integer page = 1;
    private Integer limit = 10;
}
