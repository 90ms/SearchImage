# Kotlin

- https://github.com/PRNDcompany/android-style-guide/blob/main/Kotlin.md?plain=1 다음의 문서를 기반으로 한다.

## 설정
- IDE 코드 스타일은 프로젝트내 'codeStyle.xml'을 적용 한다. [가이드 문서 링크](https://fastlane.atlassian.net/wiki/spaces/YR/pages/1898152292/Android)

### 함수 이름
- ViewModel을 observe()할때 모아놓는 함수 이름
  ```kotlin
  setupXXX()
  ```

- 서버에서 데이터를 불러올때 함수 이름
  ```kotlin
  fetchXXX()
  ```

- Return이 있는 데이터를 불러올때 함수 이름
  ```kotlin
  getXXX()
  ```

- 특정 객체를 찾는 함수이름
  ```
  findXXX()
  ```

- 복수형을 가져올때는 뒤에 s를 붙인다
  ```kotlin
  getBrands()      // O
  getBrandList()   // X
  ```

- BindingAdapter 함수 이름  
  ```
  bindXXX()
  ```

### 기타 룰
- 파일명은 파스칼 표기법을 사용
  ```kotlin
  class YeoshinApplication
  ```
  
- as키워드는 안전하게 사용하기 위해 as?로 형변환을 하여 null을 반환하도록 한다.  
  ```kotlin
  // bad
  val type = test as TestType
    
  // good (nullable)
  val type = test as? TestType
  ```
    
- null타입을 사용 시, 엘비스 연산자를 활용하여 null처리를 명시적으로 한다.

- !!키워드를 사용을 지양한다. 엘비스 연산자 혹은 함수내에 새로운 변수로 할당하여 null체크를 한다.
  
- xml을 생성(리소스, 레이아웃)할때는 언더스코어를 사용하여 생성한다. (의논해서 정해야함)  
  ```kotlin
  Layout View Naming : { view type }_{ description }
  Shape Naming : {sr or sc or sl}_{bgColor}_{strokeColor}_{cornerTypecornerSize}
  ```
    
- 접근제어자를 통해 구조를 더 명확하게 하고 안전하게 한다.
  
- 매개변수가 많은 함수의 경우, 이름을 함께 정의하여 함수를 사용한다.  
  ```kotlin
  test(
    name = a,
    age = b,
    address = c,
    etc = listOf(d,e,f)
  )
  ```
    
- 하나의 함수에서 동일한 객체를 사용하는 부분이 많다면 scope함수를 적극적으로 활용하자.
  ```kotlin
  // bad
  fun test() {
    binding.view.~~~
    binding.view.~~~
  }
  
  // good
  fun test() {
    with(binding.view) {
      ~~~
      ~~~
    }
  }
  ```

- 조건이 많은 if문의 경우, 별개의 함수 또는 함수내 변수로 명시적으로 할당하여 가독성을 높인다.
  ```kotlin
  // bad
  fun test(): Boolean {
    return listOf(ReservationStatusType.WAIT_CONFIRM, ReservationStatusType.CONFIRM).contains(status) || listOf(ReservationStatusType.VISIT_COMPLETE).contains(status) && reviewWritables?.isNotEmpty() == true
  }
  
  // good
  fun test(): Boolean {
    return isShowWriteReviewCtas() || isShowChangeReservationCtas()
  }

  fun isShowWriteReviewCtas() = listOf(ReservationStatusType.VISIT_COMPLETE).contains(status) && reviewWritables?.isNotEmpty() == true
  fun isShowChangeReservationCtas() = listOf(ReservationStatusType.WAIT_CONFIRM, ReservationStatusType.CONFIRM).contains(status)
  
  ```

- on[명사][동사]()
  - publisher가 이벤트만 전달하고 listener가 전적인 책임을 처리할 때
  - 이벤트를 handle하는 주체가 listen하고 있는 곳일때
    ```kotlin
    fun onClick()
    fun onFocusChange()
    fun onScrollChange()
    fun onAnimationStart()
    fun onTextChange()
  ```

- on[명사][동사 과거형]()
  - publisher가 무언가를 처리하고 listener에게 알려줄 때
  - 어떤 동작을 하고나서 이 동작이 일어났음을 listener에게 알려줄때
  - onEach(), doOnXXX() 개념처럼 특정 이벤트를 intercept해서 쓸때
  - 동작을 한뒤에 listener를 호출해야 과거형의 이름과 걸맞음
    ```kotlin
    fun onScrollStateChanged()
    fun onTextChanged()
    ```

- 한 줄에 들어가는 when 분기는 중괄호를 사용하지 않는다.
```kotlin
when (value) {
    0 -> return
    // ...
}
```

- 여러개의 조건을 동시에 사용하는 경우 `->`를 포함한 블록은 내려서 작성한다.
```kotlin
when (value) {
    foo -> // ...
    bar,
    baz
    -> return
}
```
