# GeoQuiz

## View Binding

```kotlin
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
```

- `binding = ActivityMainBinding.inflate(layoutInflater)`
  - ① obtain a reference to the layout
  - ② inflate the layout
- `setContentView()` 메서드
  - `setContentView(@LayoutRes int layoutResID)`
    - XML 레이아웃 파일의 리소스 ID을 받아 해당 XML 레이아웃을 inflate하고 Activity의 ContentView로 설정
      - Activity가 '내부적으로(internally)' 그것의 `layoutInflater`을 사용함
    - UI를 정적으로 정의할 때 주로 사용
  - `setContentView(View view)`
    - 이미 inflate 되어 인스턴스화된 View 객체를 사용하여 Activity의 ContentView로 설정
    - 동적으로 View를 생성하거나 다른 레이아웃 파일에서 inflate한 View를 사용할 때 유용

## Vector Drawable

## ViewModel
