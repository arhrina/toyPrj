/**
 * 대소문자 구분, 유니코드 문자셋
 * 
 * var 변수선언 + 값 초기화. 지역 및 전역 변수
 * let 블록 범위(scope) 지역 변수 선언하고 값 초기화
 * const 블록 범위 읽기 전용 상수 선언
 * 
 * 
 * 선언되서 할당되지 않은 변수는 undefined. boolean에서 undefined는 false로 동작. 숫자에선 NaN
 * null과 undefined는 엄연하게 다른 값을 갖는다. null은 boolean에서 false, 숫자에선 0으로 동작한다
 * 
 * 
 * 
 * 변수 hoisting 호이스팅
 * 
 * 예외를 처리하지 않고도 나중에 선언된 변수를 참조할 수 있다. 변수가 최상단으로 끌어올려지는 것을 의미한다. 그렇게 끌어올려진 변수는 undefined 값
 */


 console.log(x); // java나 c 등의 언어에서는 선언되지 않은 변수가 있다면 syntax error를 나타내지만 js에서는 구문에 그 변수가 존재한다면 undefined 값을 갖지만, 상단으로 끌어와서 보여준다

 var x = 7;

 /**
  * 변수는 호이스팅되므로 var는 가능한 함수 상단으로 몰아두면 가독성이 좋아진다
  * 
  * 함수는 호이스팅되지 않고 TypeError를 발생시킨다
  */


  a(); // 호이스팅되지 않으므로 TypeError 발생

  function a() {
      console.log('test');
  }

  a(); // test

  /**
   * const로 선언한 상수 이름은 변수명과 함께 구분되어야한다. 상수에 a, 변수에 a라는 이름을 각각 할당하면 에러가 발생한다
   * 
   * 상수에 할당된 속성(실제 값)은 보호되지 않아서 얼마든지 변동이 가능하다. c의 #define, java의 final과는 다르게 값의 변경이 가능하다
   */

   Boolean w = true; // false
   null;
   undefined;
   Number intNumber = 7; // 숫자형. 정수, 실수 모두
   String strTest = '문자열';
   Symbol whatIsThis; // ECMAScript 6에서 도입. 인스턴스가 고유하고 불변

   // 이상 원시데이터형


   Object objectVariable; // 키와 값을 매핑하는 자료형. hashmap을 표현하는데 적합

   /**
    * 동적형지정 언어라서 변수형을 직접 지정해줄 필요가 없다
    * 
    * 
    * 문자열에 들어있는 숫자도 +를 제외한 연산에서는 숫자로 계산한다. +의 경우 숫자를 더해도 모두 문자열로 취급한다
    */

    "37"+3; // 373
    38-"4" // 33

    // 문자열을 숫자로 변환하고자할 때는 parseInt()나 parseFloat() 등을 사용하여 변환한다. parseInt의 괄호에는 진법을 매개변수로 포함해주어야한다