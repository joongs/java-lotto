package lottery.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LotteryGame {

    private final int price;
    private final int pickCounts;

    public LotteryGame(int price, int pickCounts) {
        this.price = price;
        this.pickCounts = pickCounts;
    }

    public int buyNumberOfLotteryTickets(int cost) {
        if (cost < price) {
            throw new IllegalArgumentException("로또 1장의 가격은 " + this.price + "입니다.");
        }
        return cost / this.price;
    }

    public List<LotteryTicket> buyLotteryTickets(int numberOfTickets, BuyBehavior behavior) {
        return IntStream.range(0, numberOfTickets)
                .boxed()
                .map(Integer -> LotteryTicketFactory.createLotteryTicket(behavior))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryGame that = (LotteryGame) o;
        return price == that.price &&
                pickCounts == that.pickCounts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, pickCounts);
    }
}
