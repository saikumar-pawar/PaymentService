package com.example.paymentservice.paymentgateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("stripe")
@Primary
public class StripePaymentGateway implements IPaymentGateway{

    @Value("${stripe.apikey}")
    private  String stripeApiKey;

    @Override
    public String getPaymentLink(String email, String name, String phoneNumber, String orderId, Long amount) {
        try {
            Stripe.apiKey = this.stripeApiKey;

            Price price = getPrice(amount);
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            )
                            .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        }catch (StripeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Price getPrice(Long amount) {
        try {
            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amount)
                            .setRecurring(
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()
                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                            )
                            .build();
            Price price = Price.create(params);
            return price;
        }catch (StripeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
