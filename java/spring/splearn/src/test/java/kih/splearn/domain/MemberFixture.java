package kih.splearn.domain;

import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberRegisterRequest;
import kih.splearn.domain.member.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "inhan", "secret2468!@");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(){
        return createMemberRegisterRequest("kih@splearn.app");
    }

    public static PasswordEncoder createPasswordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

    public static Member createMember(){
        return Member.create(createMemberRegisterRequest(), createPasswordEncoder());
    }
    public static Member createMember(Long id){
        Member member = Member.create(createMemberRegisterRequest(), createPasswordEncoder());
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
    public static Member createMember(String email){
        return Member.create(createMemberRegisterRequest(email), createPasswordEncoder());
    }
}
