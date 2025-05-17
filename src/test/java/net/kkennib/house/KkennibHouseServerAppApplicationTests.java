package net.kkennib.house;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kkennib.house.models.Post;
import net.kkennib.house.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class KkennibHouseServerAppApplicationTests {
//
//	@Autowired
//	PostRepository postRepository;
//
//	@Test
//	void testGround() {
////		createPosts();
////		updatePosts();
////		deletePosts();
////		readJsonPost();
////        readAndDeletePosts("dev");
////		readAndDeletePosts("insight");
////		readAndDeletePosts("memo");
//	}
//
//	void createPosts() {
//		Post post1 = Post.builder()
//				.articleType("dev")
//				.postId(1)
//				.subType("")
//				.title("테스트 글1")
//				.cover("")
//				.fit("cover")
//				.createAt("")
//				.build();
//
//		Post post2 = Post.builder()
//				.articleType("dev")
//				.postId(1)
//				.subType("")
//				.title("테스트 글2")
//				.cover("")
//				.fit("cover")
//				.createAt("")
//				.build();
//
//		Post post3 = Post.builder()
//				.articleType("dev")
//				.postId(1)
//				.subType("")
//				.title("테스트 글3")
//				.cover("")
//				.fit("cover")
//				.createAt("")
//				.build();
//
//		List<Post> items = new ArrayList<>();
//		items.add(post1);
//		items.add(post2);
//		items.add(post3);
//		postRepository.createPosts(items);
//	}
//
//	void updatePosts() {
//		Post post1 = Post.builder()
//				.articleType("dev")
//				.postId(14)
//				.subType("")
//				.title("테스트 글1xxx")
//				.cover("")
//				.fit("cover")
//				.createAt("")
//				.build();
//
//		Post post2 = Post.builder()
//				.articleType("dev")
//				.postId(15)
//				.subType("")
//				.title("yyyyyyy테스트 글2")
//				.cover("")
//				.fit("cover")
//				.createAt("")
//				.build();
//
//		Post post3 = Post.builder()
//				.articleType("dev")
//				.postId(16)
//				.subType("")
//				.title("테wwwwwwwqw스트 글3")
//				.cover("")
//				.fit("cover")
//				.createAt("")
//				.build();
//
//		List<Post> items = new ArrayList<>();
//		items.add(post1);
//		items.add(post2);
//		items.add(post3);
//		postRepository.updatePosts(items);
//	}
//
//	void deletePosts() {
//		List<Post> items = new ArrayList<>(Arrays.asList(
//				Post.builder().articleType("dev").postId(13).build(),
//				Post.builder().articleType("dev").postId(14).build(),
//				Post.builder().articleType("dev").postId(15).build(),
//				Post.builder().articleType("dev").postId(16).build(),
//				Post.builder().articleType("dev").postId(17).build(),
//				Post.builder().articleType("dev").postId(18).build(),
//				Post.builder().articleType("dev").postId(19).build(),
//				Post.builder().articleType("dev").postId(20).build(),
//				Post.builder().articleType("dev").postId(21).build(),
//				Post.builder().articleType("dev").postId(22).build()
//		));
//		postRepository.deletePosts(items);
//	}
//
//	void readAndDeletePosts(String articleType) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			File file = new File("meta.json");
//			List<Post> posts = objectMapper.readValue(file, new TypeReference<List<Post>>(){});
//
//			List<Post> devPosts = posts.stream()
//					.filter(post -> articleType.equals(post.getArticleType()))
//					.collect(Collectors.toList());
//
//			postRepository.createPosts(devPosts);
////			for (Post post : posts) {
////				System.out.println(post);
////			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	void readAndCreatePosts() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			File file = new File("meta.json");
//			List<Post> posts = objectMapper.readValue(file, new TypeReference<List<Post>>(){});
//			for (Post post : posts) {
//				System.out.println(post);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	void readJsonPost() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			File file = new File("meta.json");
//			List<Post> posts = objectMapper.readValue(file, new TypeReference<List<Post>>(){});
//			for (Post post : posts) {
//				System.out.println(post);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
