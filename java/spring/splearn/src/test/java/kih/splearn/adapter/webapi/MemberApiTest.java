package kih.splearn.adapter.webapi;

import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberApi.class)
class MemberApiTest {
    @MockitoBean
    private MemberRegister memberRegister;

    @Autowired
    MockMvcTester mvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register(){
        Member member = MemberFixture.createMember(1L);
        when(memberRegister.register(any())).thenReturn(member);

        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(
        mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.memberId").asNumber().isEqualTo(1);

        verify(memberRegister).register(request);
    }
}