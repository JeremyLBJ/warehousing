package com.rick.sys.VO;

import com.rick.sys.entity.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PermissionVO extends SysPermission {

    private Integer page;

    private Integer limit;
}
