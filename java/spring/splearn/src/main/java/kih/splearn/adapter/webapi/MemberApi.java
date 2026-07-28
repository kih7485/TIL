package kih.splearn.adapter.webapi;

import kih.splearn.adapter.webapi.dto.MemberRegisterResponse;
import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody MemberRegisterRequest request){
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);

    }
}
