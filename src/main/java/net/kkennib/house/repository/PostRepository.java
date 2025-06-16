package net.kkennib.house.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kkennib.house.components.CustomSettings;
import net.kkennib.house.model.Post;
import net.kkennib.house.model.PostResponse;
import net.kkennib.house.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private CustomSettings customSettings;

    public Post getPostById(String articleType, int postId) {
        return dynamoDBMapper.load(Post.class, articleType, postId);
    }

    public PostResponse getPosts(String articleType, int pageNo) {
        int totalItemCount = getPostCount(articleType);
        int pageUnitCount = customSettings.getPageCount();
        int totalSegmentCount = totalItemCount / pageUnitCount + (totalItemCount % pageUnitCount == 0 ? 0 : 1);
        int endNo = totalItemCount - (pageNo - 1) * pageUnitCount;
        int startNo = Math.max(endNo -  pageUnitCount + 1, 1);

        // 본문 목록 조회
        DynamoDBQueryExpression<Post> queryExpression = new DynamoDBQueryExpression<Post>()
                .withKeyConditionExpression("articleType = :articleType AND postId BETWEEN :start AND :end")
                .withExpressionAttributeValues(Map.of(
                        ":articleType", new AttributeValue().withS(articleType),
                        ":start", new AttributeValue().withN(String.valueOf(startNo)),
                        ":end", new AttributeValue().withN(String.valueOf(endNo))
                ))
                .withScanIndexForward(false);

        List<Post> items = new ArrayList<>(dynamoDBMapper.query(Post.class, queryExpression));

        // 전체 subType 카운트 수집 (Scan 필요)
        Map<String, AttributeValue> scanValues = Map.of(
                ":articleType", new AttributeValue().withS(articleType)
        );

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("articleType = :articleType")
                .withExpressionAttributeValues(scanValues);

        List<Post> allItems = dynamoDBMapper.scan(Post.class, scanExpression);
        Map<String, Integer> subTypeCounts = allItems.stream()
                .collect(Collectors.groupingBy(Post::getSubType, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));

        // 응답 조립
        PostResponse result = new PostResponse();
        result.setTotalItemCount(totalItemCount);
        result.setTotalPageCount(totalSegmentCount);
        result.setCurrentPageNo(pageNo);
        result.setList(items);
        result.setSubTypeCounts(subTypeCounts);
        return result;
    }

    public PostResponse getPostsFromLocal(String articleType, int pageNo) {
        List<Post> posts = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("meta.json");
            List<Post> metaPosts = objectMapper.readValue(file, new TypeReference<List<Post>>(){});
            for (Post post : metaPosts) {
                if (!post.getArticleType().equals(articleType))
                    continue;
                posts.add(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalItemCount = posts.size();
        int pageUnitCount = customSettings.getPageCount();
        int totalSegmentCount = totalItemCount / pageUnitCount + (totalItemCount % pageUnitCount == 0 ? 0 : 1);
        int endNo = totalItemCount - (pageNo - 1) * pageUnitCount;
        int startNo = Math.max(endNo -  pageUnitCount + 1, 1);

        PostResponse result = new PostResponse();
        result.setTotalItemCount(totalItemCount);
        result.setTotalPageCount(totalSegmentCount);
        result.setCurrentPageNo(pageNo);
        result.setList(posts);
        return result;
    }

    public PostResponse createPosts(List<Post> postList) {
        PostResponse result = new PostResponse();

        int postCount = -1;
        int suffixCount = 0;
        for (Post post: postList) {
            if (postCount == -1) {
                postCount = getPostCount(post.getArticleType());
            }

            String currentDatetime = DateTimeUtil.getCurrentDateTimeKR();
            post.setCreateAt(currentDatetime);

            String articleType = post.getArticleType();
            int postId = postCount + (++suffixCount);
            post.setPostId(postId);
        }

        dynamoDBMapper.batchSave(postList);
        result.setList(postList);
        return result;
    }

    public PostResponse updatePosts(List<Post> newPostList) {
        PostResponse result = new PostResponse();
        for (Post newPost: newPostList) {
            String articleType = newPost.getArticleType();
            int postId = newPost.getPostId();

            Post curPost = dynamoDBMapper.load(Post.class, articleType, postId);
            if (curPost == null) continue;

            String currentDatetime = DateTimeUtil.getCurrentDateTimeKR();
            newPost.setCreateAt(currentDatetime);
        }

        dynamoDBMapper.batchSave(newPostList);
        result.setList(newPostList);
        return result;
    }

    public PostResponse deletePosts(List<Post> postList) {
        PostResponse result = new PostResponse();
        try
        {
            dynamoDBMapper.batchDelete(postList);
            result.setSuccess(true);
        } catch (Exception ex) {
            result.setSuccess(false);
        }
        return result;
    }

    private int getPostCount(String articleType) {
        DynamoDBQueryExpression<Post> queryExpression = new DynamoDBQueryExpression<Post>()
                .withKeyConditionExpression("articleType = :articleType")
                .withExpressionAttributeValues(Map.of(
                        ":articleType", new AttributeValue().withS(articleType)
                ))
                .withSelect(Select.COUNT);  // 개수만 조회

        return dynamoDBMapper.count(Post.class, queryExpression);
    }

}
