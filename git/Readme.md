### log
각 커밋마다의 변경사항 함께보기
```git
git log -p
```

최근 n개 커밋만 보기
```
git log -n
```

통계와 함께보기
```
git log --stat
```

한줄로 보기
```
git log --oneline
```
변경사항 내 단어검색
```
git log -S 검색어
```
커밋 메세지로 검색
```
git log --grep 검색어
```

자주 사용되는 그래프로 로그보기
```
git log --all --decorate --oneline --graph 
```

### blame
- 파일의 라인 별로 누가 작업했는지 알 수 있음
- IDE 툴에서 대부분 지원하고 있다.


### bisect
- 이진 탐색 알고리즘으로 문제의 발생 시점을 알아냄.

이진탐색 시작
```
git bisect start
```

오류발생지점 표시
```
git bisect bad
```


의심지점으로 이동
```
git checkout (커밋 해시)
```


오류발생 아님 표시
```
git bisect good
```


완료 후 원래대로
```
git bisect reset
```