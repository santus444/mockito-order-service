package com.bigbox.b2csite.order.service.impl;

import com.bigbox.b2csite.order.dao.OrderDao;
import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.transformer.OrderEntityToOrderSummaryTransformer;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.Order;
import java.util.LinkedList;
import java.util.List;

public class OrderServiceImplTest {
    protected @Mock OrderDao mockOrderDao;
    protected @Mock OrderEntityToOrderSummaryTransformer mockTransformer;
    private final static long CUSTOMER_ID = 1L;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void test_getOrderSummary_success() throws Exception{
        //Setup
        OrderServiceImpl target = new OrderServiceImpl();

        target.setOrderDao(mockOrderDao);

        target.setTransformer(mockTransformer);

        OrderEntity orderEntityFixture = new OrderEntity();
        List<OrderEntity> orderEntityListFixture = new LinkedList<>();
        orderEntityListFixture.add(orderEntityFixture);

        Mockito.when(mockOrderDao.findOrdersByCustomer(CUSTOMER_ID))
                .thenReturn(orderEntityListFixture);

        OrderSummary orderSummaryFixture = new OrderSummary();
        Mockito.when(mockTransformer.transform(orderEntityFixture))
                .thenReturn(orderSummaryFixture);
        //Execution
        List<OrderSummary> result = target.getOrderSummary(CUSTOMER_ID);

        //Verification
        Mockito.verify(mockOrderDao).findOrdersByCustomer(CUSTOMER_ID);
        Mockito.verify(mockTransformer).transform(orderEntityFixture);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertSame(orderSummaryFixture, result.get(0));
    }
	
}
