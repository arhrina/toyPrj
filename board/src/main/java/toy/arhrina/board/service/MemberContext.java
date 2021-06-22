package toy.arhrina.board.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import toy.arhrina.board.domain.entity.MemberEntity;

import java.util.Collection;

public class MemberContext extends User {
    private final MemberEntity member;

    public MemberContext(MemberEntity member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getMemberId(), member.getPassword(), authorities);
        this.member = member;
    }

    public MemberEntity getMember() {
        return member;
    }
}
