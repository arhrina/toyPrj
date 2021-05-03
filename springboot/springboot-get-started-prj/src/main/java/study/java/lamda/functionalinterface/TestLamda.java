package study.java.lamda.functionalinterface;

@FunctionalInterface
public interface TestLamda {
    /**
     * 매개변수가 하나인 경우에는 괄호 생략 가능
     * 중괄호 안의 구현부가 한 문장인 경우 중괄호 생략 가능
     * 중괄호 안의 구현부가 한 문장이어도 return이 있으면 생략 불가
     * 중괄호 안의 구현부가 return문 하나라면 return과 중괄호 함께 생략 가능
     * @FunctionalInterface 사용, 1개의 함수만 생성 가능. 2개 불가
     *
     * 자료형에 기반해서 선언하고, 매개변수를 전달
     */
    int sum(int x, int y);
}
