package net.kkennib.house.models;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Post")
public class Post {

    @DynamoDBRangeKey(attributeName="postId") // sort key
//    @DynamoDBAutoGeneratedKey
    @DynamoDBAttribute
    private String postId;

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String articleType;

    @DynamoDBAttribute
    private String subType;

    @DynamoDBAttribute
    private String title;

    @DynamoDBAttribute
    private String cover;

    @DynamoDBAttribute
    private String fit;

    @DynamoDBAttribute
    private String createAt;
}