package com.offcn.project.service;

import com.offcn.project.po.*;

import java.util.List;

/**
 * @ClassName ProjectInfoService
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:09
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface ProjectInfoService {
    List<TReturn> gerProjectReturns(Integer projectId);

    List<TProject> getAllProjects();
    List<TProjectImages> getProjectImages(Integer id);

    TProject getProjectInfo(Integer projectId);
    List<TTag> getAllProjectTags();
    List<TType> getProjectTypes();
    TReturn getReturnInfo(Integer returnId);
}
