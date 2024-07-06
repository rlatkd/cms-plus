package kr.or.kosa.cmsplusmain.test.controller;

import kr.or.kosa.cmsplusmain.test.dto.PostRequestDto;
import kr.or.kosa.cmsplusmain.test.dto.PostResponseDto;
import kr.or.kosa.cmsplusmain.test.entity.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @GetMapping("/post")
    public ResponseEntity getPost() {
        Post post = buildPost("rlatkdtest1", "rlatkdtest2");
        PostResponseDto postResponseDto = buildResponseDto(post);
        return ResponseEntity.ok(postResponseDto);
    }

    @PostMapping("/post")
    public ResponseEntity createPost(@RequestBody PostRequestDto postRequestDto) {
        Post post = buildPost(postRequestDto.getName(), postRequestDto.getContent());
        PostResponseDto postResponseDto = buildResponseDto(post);
        return ResponseEntity.ok(postResponseDto);
    }

    private static Post buildPost(String name, String content) {
        return Post.builder()
                .name(name)
                .content(content)
                .build();
    }

    private static PostResponseDto buildResponseDto(Post post) {
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .name(post.getName())
                .content(post.getContent())
                .build();
        return postResponseDto;
    }
}