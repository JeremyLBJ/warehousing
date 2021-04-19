package com.rick.sys.VO;

import com.rick.sys.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoleVO extends SysRole {

    private Integer page = 1;

    private Integer limit = 10;
}
