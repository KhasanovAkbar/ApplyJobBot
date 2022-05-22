package univ.tuit.applyjobbot.store.jpo;

import lombok.*;
import org.springframework.beans.BeanUtils;
import univ.tuit.applyjobbot.domain.Apply;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Apply")
public class ApplyJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applyId")
    private Integer applyId;

    private Long userId;

    private String jobId = "Register";

    private boolean isJobId = false;

    private String username;

    private String name = "Register";

    private boolean isName = false;

    private String age = "Register";

    private boolean isAge = false;

    private String phoneNumber = "Register";

    private boolean isPhone = false;

    private String state = "Register";

    private String filePath = "Register";

    private boolean isFilePath = false;

    private String token;

    public boolean isFilePath() {
        return isFilePath;
    }

    public void setIsFilePath(boolean filePath) {
        isFilePath = filePath;
    }

    private String registrationTime;

    public boolean isJobId() {
        return isJobId;
    }

    public void setIsJobId(boolean jobId) {
        isJobId = jobId;
    }

    public boolean isName() {
        return isName;
    }

    public void setIsName(boolean name) {
        isName = name;
    }

    public boolean isAge() {
        return isAge;
    }

    public void setIsAge(boolean age) {
        isAge = age;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setIsPhone(boolean phone) {
        isPhone = phone;
    }

    public ApplyJpo(Apply apply) {
        BeanUtils.copyProperties(apply, this);
    }

    public static List<Apply> toDomain(List<ApplyJpo>applyJpos){
        return applyJpos.stream().map(ApplyJpo::toDomain).collect(Collectors.toList());
    }

    public Apply toDomain() {
        Apply apply = new Apply();
        BeanUtils.copyProperties(this, apply);
        return apply;
    }
}
