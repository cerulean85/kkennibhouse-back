package net.kkennib.house.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PostResponse {
    private boolean isSuccess;
    private int totalItemCount;
    private int totalPageCount;
    private int currentPageNo;
    private List<Post> list;
    private Map<String, Integer> subTypeCounts;
}