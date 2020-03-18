package com.lightspace.search.service;

import com.lightspace.framework.domain.course.CoursePub;
import com.lightspace.framework.domain.course.TeachplanMediaPub;
import com.lightspace.framework.domain.search.CourseSearchParam;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsCourseService {

    @Value("${lightsapce.course.index}")
    private String index;
    @Value("${lightspace.course.type}")
    private String type;
    @Value("${lightspace.course.source_field}")
    private String source_field;
  @Value("${lightspace.media.index}")
  private String media_index;

  @Value("${lightsapce.media.type}")
  private String media_type;
  @Value("${lightsapce.media.source_field}")
  private String media_source_field;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) {
        if(courseSearchParam == null){
            courseSearchParam = new CourseSearchParam();
        }
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] source_field_array = source_field.split(",");
        searchSourceBuilder.fetchSource(source_field_array,new String[]{});
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if(StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "description", "teachplan")
                    .minimumShouldMatch("70%")
                    .field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        QueryResult<CoursePub> queryResult = new QueryResult();
        List<CoursePub> list = new ArrayList<>();

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            long totalHits = hits.totalHits;
            queryResult.setTotal(totalHits);
            SearchHit[] searchHits = hits.getHits();
            for(SearchHit hit:searchHits){
                CoursePub coursePub = new CoursePub();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String name = (String) sourceAsMap.get("name");
                coursePub.setName(name);
                String pic = (String) sourceAsMap.get("pic");
                coursePub.setPic(pic);
                Double price = null;
                try {
                    if(sourceAsMap.get("price")!=null ){
                        price = (Double) sourceAsMap.get("price");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                coursePub.setPrice(price);
                Double price_old = null;
                try {
                    if(sourceAsMap.get("price_old")!=null ){
                        price_old = (Double) sourceAsMap.get("price_old");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                coursePub.setPrice_old(price_old);
                list.add(coursePub);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        queryResult.setList(list);
        QueryResponseResult<CoursePub> queryResponseResult = new QueryResponseResult<CoursePub>(CommonCode.SUCCESS,queryResult);

        return queryResponseResult;
    }

  public QueryResponseResult<TeachplanMediaPub> getmedia(String[] teachplanIds) {
    SearchRequest searchRequest = new SearchRequest(media_index);
    searchRequest.types(media_type);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.termsQuery("teachplan_id",teachplanIds));
    String[] includes = media_source_field.split(",");
    searchSourceBuilder.fetchSource(includes,new String[]{});
    searchRequest.source(searchSourceBuilder);
    List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
    long total = 0;
    try {
      SearchResponse search = restHighLevelClient.search(searchRequest);
      SearchHits hits = search.getHits();
      total = hits.totalHits;
      SearchHit[] searchHits = hits.getHits();
      for(SearchHit hit:searchHits){
        TeachplanMediaPub teachplanMediaPub= new TeachplanMediaPub();
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        String courseid = (String) sourceAsMap.get("courseid");
        String media_id = (String) sourceAsMap.get("media_id");
        String media_url = (String) sourceAsMap.get("media_url");
        String teachplan_id = (String) sourceAsMap.get("teachplan_id");
        String media_fileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");

        teachplanMediaPub.setCourseId(courseid);
        teachplanMediaPub.setMediaUrl(media_url);
        teachplanMediaPub.setMediaFileOriginalName(media_fileoriginalname);
        teachplanMediaPub.setMediaId(media_id);
        teachplanMediaPub.setTeachplanId(teachplan_id);
        teachplanMediaPubList.add(teachplanMediaPub);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    QueryResult<TeachplanMediaPub> queryResult = new QueryResult<>();
    queryResult.setList(teachplanMediaPubList);
    queryResult.setTotal(total);
    QueryResponseResult<TeachplanMediaPub> queryResponseResult = new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    return queryResponseResult;

  }

  public Map<String, CoursePub> getall(String id) {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.termQuery("id",id));
    searchRequest.source(searchSourceBuilder);

    Map<String,CoursePub> map = new HashMap<>();
    try {
      SearchResponse search = restHighLevelClient.search(searchRequest);
      SearchHits hits = search.getHits();
      SearchHit[] searchHits = hits.getHits();
      for(SearchHit hit:searchHits){
        CoursePub coursePub = new CoursePub();
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        String courseId = (String) sourceAsMap.get("id");
        String name = (String) sourceAsMap.get("name");
        String grade = (String) sourceAsMap.get("grade");
        String charge = (String) sourceAsMap.get("charge");
        String pic = (String) sourceAsMap.get("pic");
        String description = (String) sourceAsMap.get("description");
        String teachplan = (String) sourceAsMap.get("teachplan");
        coursePub.setId(courseId);
        coursePub.setName(name);
        coursePub.setPic(pic);
        coursePub.setGrade(grade);
        coursePub.setTeachplan(teachplan);
        coursePub.setDescription(description);
        map.put(courseId,coursePub);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


    return map;
  }

}
