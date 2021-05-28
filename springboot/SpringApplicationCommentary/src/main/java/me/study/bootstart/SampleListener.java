package me.study.bootstart;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//@Component
public class SampleListener  implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("===========================");
        System.out.println("Application Starting Event");
        System.out.println("===========================");
        /**
         * ApplicationContext가 생성된 뒤 발생하는 이벤트 리스너는 bean이 알아서 실행되지만, context 생성 이전에 발생하는 이벤트는 동작하지 않는다.
         *
         * 이 경우 직접 리스너 클래스를 등록해줘야한다
         */
    }
}
