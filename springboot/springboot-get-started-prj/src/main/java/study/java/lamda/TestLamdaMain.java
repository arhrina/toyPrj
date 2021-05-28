package study.java.lamda;

import study.java.lamda.functionalinterface.TestLamda;

public class TestLamdaMain {
    public static void main(String[] args) {
        TestLamda intSum = (x, y)->(x + y); // x, y의 2개 매개변수를 받아서 TestLamda interface가 호출되면(->) x + y를 return 한다고 구현한 구현부. 익명함수이므로 함수명은 적지 않고 인터페이스로 호출
        /*
        생략을 안한 람다식의 구현
        TestLamda intSum = (x, y) -> {return x + y};

        람다식 구현부 자체를 변수처럼 사용할 수 있다(return을 람다식으로 만드는 것 같은)

        전통적인 방식으로는 interface를 extends하는 class를 만들고, 클래스에서 구현부를 만들어서 클래스를 인스턴스로 만들고 인스턴스로 호출하여 사용하는 것을 간소화
         */
    }
}
