# input JSON
 
```JSON
{
  "availableTags": [
    {
      "tag": "pool",
      "values": [
        "HikariPool-1"
      ]
    }
  ],
  "description": "Pending threads",
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 0.0
    }
  ],
  "name": "hikaricp.connections.pending"
}
```

## 결과
- 커넥션을 기다리는 스레드 수: 8 -> 6 -> 4 -> 2 -> 0

## 커넥션 풀
커넥션 풀은 데이터베이스와 연결되어있는 I/O 풀이다.
트랙잭션이 끝나는 시점에 해당 커넥션을 풀애 반납을 한다.
퀴리가 느리거나 코드레벨에서 코드가 느리면 해당 커넥션 풀의 반납 시간이 느려진다.
이 의미는 그만큼 성능에 영향이 가기 때문에 퀴리뿐만 아니라 코드에서도 빠르게 동작하도록 신경을 써야된다.

## 성능을 빠르게 하는 방법
퀴리가 느리다면 해당 퀴리를 분석후 퀴리를 바꾸거나 DB에 데이터가 많은 경우는 인덱스를 생성해야된다.
하지만 코드레벨에서는 하나의 트랜잭션안에서 전부 해결하는 구조가 있는 경우는 해당 코드를 개선해야된다.

예:
```java
// Before
@Service
public class OrderService {
    @Transactional
    public void order(OrderRequest erq) {
        PaymentResult result = paymentApi.call(req); // 외부 API 3초
        orderRepository.save(new Order(req, result));
    }
}
```
해당 코드의 문제점: 외부 API를 호출하는데 3초가 걸리기 때문에 커넥션이 3초동안 기다린다.
```java
// After
@Service
public class OrderService {
    private final OrderTxService orderTxService;

    public void order(OrderRequest req) {             // 트랜잭션 없음
        PaymentResult result = paymentApi.call(req);  // 커넥션 안 쥐고 3초 대기
        orderTxService.save(req, result);             // 여기서만 커넥션 사용
    }
}

@Service
public class OrderTxService {
    @Transactional
    public void save(OrderRequest req, PaymentResult result) {
        orderRepository.save(new Order(req, result));
    }
}
```
클래스를 나눈 이유: 스프링은 this.save()와 같은 @Transactional 메서드를 부르면 트랜잭션에 걸리지 않는다.
Spring은 프록시 객체를 통해 트랜잭션을 거는 내부 호출은 프록시를 거치지 않기 때문이다. 그래서 위와 같은 코드 처럼 별도 빈으로 분리를 해야된다.

트랜잭셜
