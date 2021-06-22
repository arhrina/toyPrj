package toy.arhrina.board.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Entity
@Getter
@NoArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String memberId;
    private String password;
    private String salt;
    private String role;

    public MemberEntity(String id, String password, String role) {
        this.memberId = id;
        makeHashPassword(password);
        this.role = role;
    }


    public void makeSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < salt.length; i++) {
            sb.append(salt[i]);
        }
        this.salt = sb.toString();
    }

    public void makeHashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            makeSalt();
            md.update(salt.getBytes());
            byte[] pwd = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < pwd.length; i++) {
                sb.append(Integer.toString((pwd[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.password = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
