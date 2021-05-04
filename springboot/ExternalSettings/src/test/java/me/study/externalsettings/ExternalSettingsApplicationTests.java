package me.study.externalsettings;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class ExternalSettingsApplicationTests {
    /**
     * 1. test용 property를 변경하기 위해 resources를 만들고
     * prj settings에서 module에 디렉토리를 선택하고 Mark as로 test 클릭하여 파일 위치를 지정
     *
     * 2. @SpringBootTest의 option으로 properties = "key.value=값"
     *
     * 3. @TestPropertySource(properties = "key.value = 값")
     *
     * 값이 여러개면 {"key.value = 값", "key.value = 값" .... }
     *
     * 4. TestPropertySource의 option으로 locations = "classpath:/test.properties" 등으로 경로 지정
     * 우선순위 높음
     *
     ** 우선순위가 높을 때 key.value가 같으면 덮어 씀
     *
     */
    @Autowired
    Environment environment;

    @Test
    void contextLoads() {
        assertThat(environment.getProperty("keesun.age")).isEqualTo("10");
    }

}
