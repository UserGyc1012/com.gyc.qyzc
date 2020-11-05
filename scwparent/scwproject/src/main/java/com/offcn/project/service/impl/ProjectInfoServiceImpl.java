package com.offcn.project.service.impl;

import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProjectInfoServiceImpl
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:11
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {

    @Autowired
    private TReturnMapper returnMapper;
    @Autowired
    private TProjectMapper projectMapper;

    @Autowired
    private TProjectImagesMapper projectImagesMapper;

    @Autowired
    private TTagMapper tagMapper;
    @Autowired
    private TTypeMapper typeMapper;


    @Override
    public List<TReturn> gerProjectReturns(Integer projectId) {
        TReturnExample tReturnExample = new TReturnExample();
        tReturnExample.createCriteria().andProjectidEqualTo(projectId);
        return returnMapper.selectByExample(tReturnExample);
    }

    @Override
    public List<TProject> getAllProjects() {
        return projectMapper.selectByExample(null);
    }

    @Override
    public List<TProjectImages> getProjectImages(Integer id) {
        TProjectImagesExample tProjectImagesExample = new TProjectImagesExample();
        tProjectImagesExample.createCriteria().andProjectidEqualTo(id);
        return projectImagesMapper.selectByExample(tProjectImagesExample);
    }

    @Override
    public TProject getProjectInfo(Integer projectId) {
        TProject project = projectMapper.selectByPrimaryKey(projectId);
        return project;
    }

    @Override
    public List<TTag> getAllProjectTags() {
        return tagMapper.selectByExample(null);
    }

    @Override
    public List<TType> getProjectTypes() {
        return typeMapper.selectByExample(null);
    }

    @Override
    public TReturn getReturnInfo(Integer returnId) {
        return returnMapper.selectByPrimaryKey(returnId);
    }
}
