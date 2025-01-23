package net.kkennib.house.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import net.kkennib.house.models.Post;
import net.kkennib.house.models.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Repository
public class PostRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Post getPostById(String articleType, String postId) {
        return dynamoDBMapper.load(Post.class, articleType, postId);
    }

    public PostResponse getPosts(String articleType, int pageNo) {
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":articleType", new AttributeValue().withS(articleType));
        String keyConditionExpression = "articleType = :articleType";
        DynamoDBQueryExpression<Post> scanExp =
                new DynamoDBQueryExpression<Post>()
                        .withKeyConditionExpression(keyConditionExpression)
                        .withExpressionAttributeValues(eav);

        int totalItemCount = dynamoDBMapper.count(Post.class, scanExp);
        int pageUnitCount = 20;
        int totalSegmentCount = totalItemCount / pageUnitCount + (totalItemCount % pageUnitCount == 0 ? 0 : 1);

        QueryResultPage<Post> page = dynamoDBMapper.queryPage(Post.class, scanExp);
        List<Post> items = page.getResults();
        Collections.sort(items, Comparator.comparing(Post::getCreateAt).reversed());

        PostResponse result = new PostResponse();
        result.setTotalItemCount(totalItemCount);
        result.setTotalPageCount(totalSegmentCount);
        result.setCurrentPageNo(pageNo);
        result.setList(page.getResults());

        return result;
    }

}
