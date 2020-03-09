package com.lightspace.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lightspace.framework.domain.cms.CmsPage;
import com.lightspace.framework.domain.cms.response.CmsPageResult;
import com.lightspace.framework.domain.cms.response.CmsPostPageResult;
import com.lightspace.framework.domain.course.*;
import com.lightspace.framework.domain.course.ext.CourseInfo;
import com.lightspace.framework.domain.course.ext.CourseView;
import com.lightspace.framework.domain.course.ext.TeachplanNode;
import com.lightspace.framework.domain.course.request.CourseListRequest;
import com.lightspace.framework.domain.course.response.AddCourseResult;
import com.lightspace.framework.domain.course.response.CourseCode;
import com.lightspace.framework.domain.course.response.CoursePublishResult;
import com.lightspace.framework.exception.ExceptionCast;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.QueryResult;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.manage_course.client.CmsPageClient;
import com.lightspace.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Resource
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    CourseMarketRepository courseMarketRepository;

    @Resource
    CourseMapper courseMapper;

    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CmsPageClient cmsPageClient;

    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;

    @Autowired
    CoursePubRepository coursePubRepository;

  @Autowired
  TeachplanMediaPubRepository teachplanMediaPubRepository;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;


    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {

        if (teachplan == null ||
                StringUtils.isEmpty(teachplan.getPname()) ||
                StringUtils.isEmpty(teachplan.getCourseid())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        String courseid = teachplan.getCourseid();

        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {

            parentid = getTeachplanRoot(courseid);
        }

        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        Teachplan teachplan1 = optional.get();

        String parent_grade = teachplan1.getGrade();

        Teachplan teachplanNew = new Teachplan();

        BeanUtils.copyProperties(teachplan, teachplanNew);

        teachplanNew.setParentid(parentid);
        if (parent_grade.equals("1")) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }
        teachplanNew.setStatus("0");
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    public String getTeachplanRoot(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();

        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachplanList == null || teachplanList.size() <= 0) {

            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplan.setPname(courseBase.getName());
            teachplanRepository.save(teachplan);
            return teachplan.getId();

        }

        return teachplanList.get(0).getId();

    }

    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest) {
        if (courseListRequest == null) {
            courseListRequest = new CourseListRequest();
        }
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> list = courseListPage.getResult();
        long total = courseListPage.getTotal();
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(list);
        courseInfoQueryResult.setTotal(total);
        return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS, courseInfoQueryResult);
    }

    @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase) {

        courseBase.setStatus("202001");
        courseBaseRepository.save(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS, courseBase.getId());
    }

    public CourseBase getCoursebaseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Transactional
    public ResponseResult updateCoursebase(String id, CourseBase courseBase) {
        CourseBase one = this.getCoursebaseById(id);
        if (one == null) {

        }
        one.setName(courseBase.getName());
        one.setMt(courseBase.getMt());
        one.setSt(courseBase.getSt());
        one.setGrade(courseBase.getGrade());
        one.setStudymodel(courseBase.getStudymodel());
        one.setUsers(courseBase.getUsers());
        one.setDescription(courseBase.getDescription());
        CourseBase save = courseBaseRepository.save(one);
        return new ResponseResult(CommonCode.SUCCESS);


    }

    public CourseMarket getCourseMarketById(String courseId) {
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Transactional
    public CourseMarket updateCourseMarket(String id, CourseMarket courseMarket) {
        CourseMarket one = this.getCourseMarketById(id);
        if(one!=null){
            one.setCharge(courseMarket.getCharge());
            one.setStartTime(courseMarket.getStartTime());
            one.setEndTime(courseMarket.getEndTime());
            one.setPrice(courseMarket.getPrice());
            one.setQq(courseMarket.getQq());
            one.setValid(courseMarket.getValid());
            courseMarketRepository.save(one);
        }else{

            one = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, one);

            one.setId(id);
            courseMarketRepository.save(one);
        } return one;
    }

    @Transactional
    public ResponseResult addCoursePic(String courseId, String pic) {

        CoursePic coursePic = null;

        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if(picOptional.isPresent()){
            coursePic = picOptional.get();
        }
        if(coursePic == null){
            coursePic  = new CoursePic();
        }
        coursePic.setPic(pic);
        coursePic.setCourseid(courseId);
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    public CoursePic findCoursePic(String courseId) {

        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            return coursePic;
        }
        return null;
    }

    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {

        long result = coursePicRepository.deleteByCourseid(courseId);
        if(result>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }


    public CourseView getCoruseView(String id) {
        CourseView courseView= new CourseView();


        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if(courseBaseOptional.isPresent()){
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }

        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(coursePic);
        }


        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if(marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }


        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);

        return courseView;

    }


    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if(baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            return courseBase;
        }
        ExceptionCast.cast(CourseCode.COURSE_DENIED_DELETE);
        return null;
    }
    public CoursePublishResult preview(String id) {

        CourseBase courseBaseById = this.findCourseBaseById(id);

        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        cmsPage.setPageName(id+".html");
        cmsPage.setPageAliase(courseBaseById.getName());
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setTemplateId(publish_templateId);

        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if(!cmsPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        CmsPage cmsPage1 = cmsPageResult.getCmsPage();
        String pageId = cmsPage1.getPageId();

        String url = previewUrl+pageId;

        return new CoursePublishResult(CommonCode.SUCCESS,url);
    }


    @Transactional
    public CoursePublishResult publish(String id) {

        CourseBase courseBaseById = this.findCourseBaseById(id);

        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        cmsPage.setPageName(id+".html");
        cmsPage.setPageAliase(courseBaseById.getName());
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setTemplateId(publish_templateId);
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if(!cmsPostPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        CourseBase courseBase = this.saveCoursePubState(id);
        if(courseBase == null){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }


        CoursePub coursePub = createCoursePub(id);

        saveCoursePub(id,coursePub);

        saveTeachplanMediaPub(id);
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

  private void saveTeachplanMediaPub(String courseId){

    teachplanMediaPubRepository.deleteByCourseId(courseId);
    List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
    List<TeachplanMediaPub> teachplanMediaPubs = new ArrayList<>();
    for(TeachplanMedia teachplanMedia:teachplanMediaList){
      TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
      BeanUtils.copyProperties(teachplanMedia,teachplanMediaPub);
      teachplanMediaPub.setTimestamp(new Date());
      teachplanMediaPubs.add(teachplanMediaPub);
    }

    teachplanMediaPubRepository.saveAll(teachplanMediaPubs);
  }

    private CoursePub saveCoursePub(String id,CoursePub coursePub){

        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
        if(coursePubOptional.isPresent()){
            coursePubNew = coursePubOptional.get();
        }else{
            coursePubNew = new CoursePub();
        }

        BeanUtils.copyProperties(coursePub,coursePubNew);
        coursePubNew.setId(id);
        coursePubNew.setTimestamp(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePubNew.setPubTime(date);
        coursePubRepository.save(coursePubNew);
        return coursePubNew;
    }

    private CoursePub createCoursePub(String id){
        CoursePub coursePub = new CoursePub();
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(id);
        if(baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            BeanUtils.copyProperties(courseBase,coursePub);
        }

        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }

        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if(marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
        }
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        String jsonString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(jsonString);
        return coursePub;

    }

    private CourseBase  saveCoursePubState(String courseId){
        CourseBase courseBaseById = this.findCourseBaseById(courseId);
        courseBaseById.setStatus("202002");
        courseBaseRepository.save(courseBaseById);
        return courseBaseById;
    }

  public ResponseResult savemedia(TeachplanMedia teachplanMedia) {
    if(teachplanMedia == null || StringUtils.isEmpty(teachplanMedia.getTeachplanId())){
      ExceptionCast.cast(CommonCode.INVALID_PARAM);
    }

    String teachplanId = teachplanMedia.getTeachplanId();
    Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
    if(!optional.isPresent()){
      ExceptionCast.cast(CommonCode.INVALID_PARAM);
    }
    Teachplan teachplan = optional.get();
    String grade = teachplan.getGrade();
    if(StringUtils.isEmpty(grade) || !grade.equals("3")){
      ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
    }
    Optional<TeachplanMedia> mediaOptional = teachplanMediaRepository.findById(teachplanId);
    TeachplanMedia one = null;
    if(mediaOptional.isPresent()){
      one = mediaOptional.get();
    }else{
      one = new TeachplanMedia();
    }

    one.setCourseId(teachplan.getCourseid());
    one.setMediaId(teachplanMedia.getMediaId());
    one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
    one.setMediaUrl(teachplanMedia.getMediaUrl());
    one.setTeachplanId(teachplanId);
    teachplanMediaRepository.save(one);

    return new ResponseResult(CommonCode.SUCCESS);
  }


  public QueryResponseResult<CourseInfo> findCourseList(String company_id, int page, int size, CourseListRequest courseListRequest) {
    if(courseListRequest == null){
      courseListRequest = new CourseListRequest();
    }
    courseListRequest.setCompanyId(company_id);
    PageHelper.startPage(page, size);
    Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
    List<CourseInfo> list = courseListPage.getResult();
    long total = courseListPage.getTotal();
    QueryResult<CourseInfo> courseIncfoQueryResult = new QueryResult<CourseInfo>();
    courseIncfoQueryResult.setList(list);
    courseIncfoQueryResult.setTotal(total);
    return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS,courseIncfoQueryResult);
  }

}
