(function() {
    "use strict";


    function greet(JS) { // 함수 정의
        alert('Hello' + JS);
    }


    greet("World!") // 문자열은 Single Quote ''와 Double Quote "" 둘 중 아무거나 사용해도 상관없다
})();


// (function(){"use strict"})();은 성능을 크게 개선하고 실수하기 쉬운 Javasciprt symantic 일부를 막으며, 콘솔에서 실행되는 코드 스니펫이 서로 상호작용하지 않도록 한다(한 콘솔에서 생성된 내용을 다른 콘솔 실행에 사용)

