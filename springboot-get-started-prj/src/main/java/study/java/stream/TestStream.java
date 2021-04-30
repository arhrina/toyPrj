package study.java.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {
    /**
     * 자료의 연산을 위해 사용하는 객체
     *
     * IO에 사용하는 stream과는 다른 용어. 혼동X
     *
     * 자료의 대상과 관계없이 동일한 연산을 수행(자료의 추상화)
     * 배열, 컬렉션에 동일한 연산이 수행되어 일관성 있는 처리
     * 한번 생성하고 쓴(소모한) 스트림은 재사용 불가
     * 스트림 연산은 기존 자료를 변경하지 않음. 다른 메모리에서 수행
     * 중간 연산(자료 필터링, 변환 등)과 최종 연산(forEach, count, sum 등)으로 구분
     * 최종 연산이 수행되어야 결과를 확인할 수 있는 지연 연산 사용
     */

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        IntStream stream = Arrays.stream(arr); // stream 객체 생성
        int sum = stream.sum();
        long count = stream.count();

        int sum1 = Arrays.stream(arr).sum(); // 체이닝해서 생성

        List<String> list = new ArrayList<>();
        list.add("TOMAS");
        list.add("EDWARD");
        list.add("JACK");

        Stream<String> stream1 = list.stream(); // stream 객체 생성
        stream1.forEach(s -> System.out.println(s));

        list.stream().forEach(s -> System.out.println(s)); // 체이닝해서 생성

        list.stream().sorted().forEach(s -> System.out.println(s)); // 중간연산과 최종연산
        list.stream().map(s -> s.length()).forEach(n -> System.out.println(n)); // s에 stream의 string을 가져와서 length를 구해 n에 넣고 n 출력력
    }
}
