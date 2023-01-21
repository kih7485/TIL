### 반응형 변수
```js
$: name
```
와 같이 $형 변수를 쓰면 동적으로 변수가 변함에 따라 페이지의 변수 값도 리로드 됨.

- 스벨트의 데이터 흐름도 단방향이다. 
- 스벨트 데이터 바인딩
```html
<input type="text" value={name} on:input={nameInput} />
<input type="text" bind:value={name}/>
```

- 구문에서 html 출력하기
```html
    <p>{@html description}</p>
```

- 동적 css 클래스
```html
<!-- class: 뒤에 클래스명, = 구문에는 조건을 넣어준다. -->
<p class:description={userName}>A short description</p>
```

- if문
```html
{#if true}
    <p class:description={userName}>A short description</p>
{:else if true}
    <p class:description={userName}>A short description</p>
{:else}
    <p class:description={userName}>A short description</p>
{/if}
```

- for 문
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
 - https://svelte.dev/tutorial/keyed-each-blocks