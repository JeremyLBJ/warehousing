package com.rick.sys.VO;

import com.rick.sys.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserVO extends SysUser {

    private Integer page = 1;

    private Integer limit = 10;
}
