package borad.HIP.controller;

import borad.HIP.controller.request.PostModifyRequest;
import borad.HIP.controller.request.PostRequest;
import borad.HIP.controller.request.UserJoinRequest;
import borad.HIP.domain.PostEntity;
import borad.HIP.exception.ErrorCode;
import borad.HIP.exception.SnsException;
import borad.HIP.fixture.PostEntityFixture;
import borad.HIP.model.Post;
import borad.HIP.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objMapper;

    @MockBean
    PostService postService;

    @Test
    @WithMockUser
    void 포스트작성() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsBytes(new PostRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 포스트작성시_로그인하지않은경우() throws Exception {

        String title = "title";
        String body = "body";
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsBytes(new PostRequest(title, body)))
                ).andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_TOKEN.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 포스트수정() throws Exception {

        String title = "title";
        String body = "body";

        when(postService.modify(eq(title), eq(body), any(), any()))
                .thenReturn(Post.fromEntity(PostEntityFixture.get("userName", 1L, 1L)));

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 포스트수정시_로그인하지않은경우() throws Exception {

        String title = "title";
        String body = "body";
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsBytes(new PostRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트수정시_본인이_작성한_글이_아닌경우() throws Exception {

        String title = "title";
        String body = "body";
        // mocking
        doThrow(new SnsException(ErrorCode.INVALID_PERMISSION)).when(postService).modify(eq(title), eq(body), any(), 1L);

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트수정시_글이_존재하지_않는경우() throws Exception {

        String title = "title";
        String body = "body";

        doThrow(new SnsException(ErrorCode.POST_NOT_FOUND)).when(postService).modify(eq(title), eq(body), any(), 1L);

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsBytes(new PostModifyRequest(title, body)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void 포스트삭제() throws Exception {

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 포스트삭제시_로그인하지않은경우() throws Exception {

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)

                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithAnonymousUser
    void 포스트삭제시_작성자와_삭제요청자가_일치하지_않은경우()throws Exception{

        doThrow(new SnsException(ErrorCode.INVALID_PERMISSION)).when(postService).delete( any(), any());

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithAnonymousUser
    void 포스트삭제시_삭제하려는_게시글이_존재하지_않은경우()throws Exception{
        doThrow(new SnsException(ErrorCode.POST_NOT_FOUND)).when(postService).delete( any(), any());

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
}
