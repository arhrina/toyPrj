package toy.arhrina.board.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toy.arhrina.board.domain.entity.MemberEntity;
import toy.arhrina.board.repository.MemberRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public boolean checkDuplicationMember(Map<String, String> jsonMap) { // db에 중복 id 있는지 확인
        Optional<MemberEntity> byId = memberRepository.findById(Long.valueOf(jsonMap.get("id")));
        if(byId.isEmpty()) {
            return true;
        }
        else
            return false;
    }

    public boolean checkMemberLogin(String str) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = new HashMap<>();
        try {
            jsonMap = mapper.readValue(str, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String id = jsonMap.get("id");
        String password = jsonMap.get("password");

        MemberEntity memberEntity = memberRepository.findByMemberId(id);
        String encPassword = memberEntity.getPassword();
        String encEnterPassword = passwordEncryption(password, memberEntity.getSalt());
        if(encPassword.equals(encEnterPassword)) {
            return true;
        }
        else
            return false;
    }

    public boolean saveMember(Map<String, String> jsonMap) {
        MemberEntity memberEntity = new MemberEntity(jsonMap.get("id"), jsonMap.get("password"));
        MemberEntity save = memberRepository.save(memberEntity);
        if(save != null) {
            return true;
        }
        else
            return false;
    }

    public String passwordEncryption(String password, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt.getBytes());
        byte[] pwd = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < pwd.length; i++) {
            sb.append(Integer.toString((pwd[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
