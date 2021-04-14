package com.rick.sys.VO;

import com.rick.sys.entity.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeptVO extends SysDept {

    private Integer page = 1;

    private Integer limit = 10;
}
