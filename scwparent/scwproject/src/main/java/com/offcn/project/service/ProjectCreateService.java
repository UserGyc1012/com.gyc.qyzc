package com.offcn.project.service;

import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

/**
 * @ClassName ProjectCreateService
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/3 18:39
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface ProjectCreateService {
    public String initCreateProjiect(Integer memberId);
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo projectvo);
}
