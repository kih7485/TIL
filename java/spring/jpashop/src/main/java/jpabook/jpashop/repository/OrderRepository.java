package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;
//    private final JPAQueryFactory query;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch ordersearch){
//        return query
//                .select(order)
//                .from(order)
//                .join(order.member, member)
//                .where(statusEq(ordersearch.getOrderStatus()),
//                    namelike(ordersearch.getMemberName()))
//                .limit(1000)
//                .fetch();


        return em.createQuery("select o from Order o join o.member m",
//                                "where o.status = :status " +
//                                "and m.name like :name",
                        Order.class)
//                .setParameter("status", ordersearch.getOrderStatus())
//                .setParameter("name", ordersearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
    }

    // member.name.like(orderSearch.getMemberName() 대신 메서드로 정리
    private BooleanExpression namelike(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        return member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return order.status.eq(statusCond);
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery("select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery("select new jpabook.jpashop.repository.OrderSimpleQueryDto(o) "+
                        "o from Order o "+
                "join o.member m " +
                "join o.delivery d", OrderSimpleQueryDto.class
                ).getResultList();
    }


    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d " +
                        "join fetch o.orderItems oi " +
                        "join fetch oi.item i", Order.class
                ).getResultList();
    }
}
