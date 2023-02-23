### 반응형 변수
```js
$: name
```
와 같이 $형 변수를 쓰면 동적으로 변수가 변함에 따라 페이지의 변수 값도 리로드 됨.

- 스벨트의 데이터 흐름도 단방향이다. 
- 스벨트 데이터 바인딩
- $로 변수생성시 직접 const a = 1; 처럼 사전에 변수를 선언하지 않아도 된다.
```html
<input type="text" value={name} on:input={nameInput} />
<input type="text" bind:value={name}/>
```

구문에서 html 출력하기
```html
    <p>{@html description}</p>
```

동적 css 클래스
```html
<!-- class: 뒤에 클래스명, = 구문에는 조건을 넣어준다. -->
<p class:description={userName}>A short description</p>
```

  if문
```html
{#if true}
    <p class:description={userName}>A short description</p>
{:else if true}
    <p class:description={userName}>A short description</p>
{:else}
    <p class:description={userName}>A short description</p>
{/if}
```

for 문
- key 가 없으면 전체 리렌더링의 경우가 생기므로 key를 넣어주어야 한다.

```html
{#each createContacts as contact, i (contact.key)}
  <h2>{i}</h2>
  <ContactCard 
  	userName={contact.name} 
	jobTitle={contact.jobTitle} 
	description={contact.desc} 
	userImage={contact.imageUrl} 
	/>
  
{/each}
```
each문 참고
  - https://svelte.dev/tutorial/each-blocks
  - https://svelte.dev/tutorial/keyed-each-block

svelte 수식어(Modifier)
- once: 수식어가 한번만 발생하도록 함.
-  passive: 스크롤 성능 향상 (이벤트에서 기본으로 설정되어있음)
-  capture: 캡처 단계에서 이벤트 핸들러 작동
-  stopPropagation: 이벤트 전파를 멈춤.(이벤트가 상위 이벤트로 전달되지 않도록)
-  preventDefault: 동작을 중단.

stateful component
- 데이터를 가지고 있으면서 다른 컴포넌트에 전달

presentational component
- 데이터를 조작하지 않고 출력만 함.
- modal, card, buttom 등이 해당. 

createEventDispatcher
- 디스패치 함수 생성
  
```js
import {createEventDispatcher} from 'svelte'

const dispatch = createEventDispatcher();

function addToCart(){
  //첫번째 인자는 전달할 이벤트명
  //두번째 명은 전달할 인자(배열 객채 등)
  dispatch('add-to-cart', {id:1, product:'냄비'})
}

```

슬롯(slot)
- 부모 컴포넌트가 자식 컴포넌트에게 콘텐츠를 전달하는 것

ex)App.svelte
```html
<script>
  import ChildComponent from './Child.svelte';
</script>

<div>
  <ChildComponent>
    <h2>Hello!</h2>
    <p>Name: 홍길동</p>
    <p>Age: 20</p>
  </ChildComponent>
</div>

<style>
div {
  text-align: center;
}
</style>
```
ex)ChildComponent
```html
<div>
  <slot>
    전달받은 콘텐츠가 없습니다.
  </slot>
</div>
```
출처: https://developer-talk.tistory.com/601

onMount
-  컴포넌트가 처음으로 DOM에 렌더링 될 때 실행

onDestroy
- 컴포넌트가 제거되었을 때 호출

tick()
- props 또는 state가 DOM에 적용되자마자 Promise 객체를 반환


데이터 바인딩
```html
<!-- checkbox -->
<input type="checkbox" bind:checked={value}/>

<!-- radio, checkbox  -->
<input type="radio" name="color" value="red" bind:group={value}/>
<input type="radio" name="color" value="green" bind:group={value}/>
<input type="radio" name="color" value="blue" bind:group={value}/>

<!-- select -->
<select bind:value={value}>
  <option value="green">green</option>
  <option value="red">red</option>
  <option value="blue">blue</option>
</select>

<!-- 요소 바인딩 -->
<script>
  let someDiv;
</script>
<div bind:this={someDiv}>someDiv</div>
```


## store
writable
- store 초기 상태 선언

subscribe
- 값이 변경될 때마다 관련 프로그램에게 알림을 보냄
```html
<script>
    import { count } from './stores.js';
    import Incrementer from './Incrementer.svelte';
    import Decrementer from './Decrementer.svelte';
    import Resetter from './Resetter.svelte';

    let countValue;

    count.subscribe(value => {
        countValue = value;
    });
</script>

<h1>The count is {countValue}</h1>

```

update
- store의 상태 업데이트
  

reset
- store 초기화

자동구독
- 컴포넌트가 제거 되었을 때 subscribe의 리턴 값을 onDestroy 라이프 사이클 함수에서 호출하는 이 일련의 과정을 자동화
- store 변수 정의는 최상위 스코프에 있어야 한다
- $를 접두사로 사용하는 변수를 선언할 수 없다.
```html
<script>
  import { count } from './stores.js';
  import Incrementer from './Incrementer.svelte';
  import Decrementer from './Decrementer.svelte';
  import Resetter from './Resetter.svelte';
</script>

<h1>The count is {$count}</h1>
```

readable
- 구독만 가능(update 및 reset 불가)

Tweend

- 트위닝 효과는 DOM에서 상태가 변경될 때, 변경되는 요소를 부드럽게 변경하는 것처럼 보여주는 효과로 프로그래스 바의 진행 막대를 변경할 때 주로 사용한다. Tweened는 아래와 같이 여러 개의 옵션을 제공한다.

 
```
tweened(value: any, { options })

options 종류

1) delay : Tweened 효과가 시작하기 전 시간
2) duration : Tweened 효과가 지속되는 시간
3) ease : 시간 경과에 따른 효과를 지정 (https://svelte.dev/docs#svelte_easing에 효과가 정의되어 있음)
4) interpolate : Tweened 효과의 보간법 설정
```


svelt:self
- <svelte:self>는 컴포넌트가 재귀적으로 자신을 포함할 수 있게 하는 요소d이다. 이 요소는 폴더 트리 구조와 같은 형태를 표현해야 할 때 유용함.

```html
<script>
let files = [
		{
			name: 'Important work stuff',
			files: [
				{ name: 'quarterly-results.xlsx' }
			]
		},
		{
			name: 'Animal GIFs',
			files: [
				{
					name: 'Dogs',
					files: [
						{ name: 'treadmill.gif' },
						{ name: 'rope-jumping.gif' }
					]
				},
				{
					name: 'Goats',
					files: [
						{ name: 'parkour.gif' },
						{ name: 'rampage.gif' }
					]
				},
				{ name: 'cat-roomba.gif' },
				{ name: 'duck-shuffle.gif' },
				{ name: 'monkey-on-a-pig.gif' }
			]
		},
		{ name: 'TODO.md' }
	];

</script>
{#each files as file}
  <li>
    {#if file.files}
      <svelte:self {...file}/>
    {:else}
      <File {...file}/>
    {/if}
  </li>
{/each}
```

